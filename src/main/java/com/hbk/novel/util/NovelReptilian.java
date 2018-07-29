package com.hbk.novel.util;

import com.hbk.novel.bean.NovelUrl;
import com.hbk.novel.entity.NovelChapter;
import com.hbk.novel.entity.ScheduleJob;
import com.hbk.novel.repository.NovelChapterRepository;
import com.hbk.novel.repository.ScheduleJobRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class NovelReptilian {
    @Autowired
    NovelChapterRepository novelChapterRepository;
    @Autowired
    ScheduleJobRepository scheduleJobRepository;


    @Value("${novel.filePath}")
    private String baseFilePath;
    /**
     * url处理任务队列
     */
    private static Queue<NovelUrl> urls = new ConcurrentLinkedQueue<>();

    public void start(NovelUrl url){
        urls.add(url);
        new WorkThread().start();
    }

    public static void main(String[] args) {
        NovelUrl url = new NovelUrl();
        url.setUrl("http://www.biqukan.com/1_1680/16813635.html");
        url.setUri("/1_1680/16813635.html");
        url.setBaseUrl("http://www.biqukan.com");

        new NovelReptilian().start(url);
    }


    public class WorkThread extends Thread{
        public void run(){
            while(true){
                try {
                    NovelUrl url = urls.poll();
                    if (url == null) {
                        break;
                    }
                    System.out.println(url.getUrl());
                    handle(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 检查资源格式是否合法
         * @param url
         * @return
         */
        public boolean check(String url){
            if(url == null || !url.endsWith(".html")){
                return false;
            }
            return true;
        }

        /**
         * 获取url对应存放的文件路径
         * @param uri
         * @return  返回Null表示无效资源路径
         */
        public String getFilePath(String uri){
            String filePath = null;
            if(!check(uri)){
                return filePath;
            }
            return baseFilePath + uri;
        }

        /**
         * 处理任务
         * @param novelUrl
         * @return
         */
        public void handle(NovelUrl novelUrl){
            String uri=novelUrl.getUri();
            String filePath = getFilePath(uri);
            String html = null;
            //过滤无效资源
            if(filePath == null){
                return;
            }
            //获取html资源的String值
            html = getHtml(novelUrl);
            if(html == null){
                //资源获取失败，重新添加任务
                urls.add(novelUrl);
                return;
            }
            try{
                //将html转换成doc对象
                Document doc =Jsoup.parse(html);
                //解析出下一个章节的资源路径
                String nextUri=doc.getElementsByClass("page_chapter").get(0)
                        .getElementsByTag("a").get(2).attr("href");
                //持久化章节信息
                saveChapter(doc,novelUrl);
                //校验url，并添加进任务队列
                if(check(nextUri)){
                    NovelUrl nextNovelUrl = new NovelUrl();
                    nextNovelUrl.setBaseUrl(novelUrl.getBaseUrl());
                    nextNovelUrl.setNovel(novelUrl.getNovel());
                    nextNovelUrl.setUri(nextUri);
                    nextNovelUrl.setUrl(novelUrl.getBaseUrl()+nextUri);
                    urls.add(nextNovelUrl);
                }else{
                    //如果url校验不通过则表示任务已经结束，需要更新对应的定时任务信息
                    stopJob(novelUrl);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 当同步任务结束时，更新同步任务信息
         * @param novelUrl
         */
        public void stopJob(NovelUrl novelUrl){
            try {
                ScheduleJob job = scheduleJobRepository.findByNovelId(novelUrl.getNovel().getId());
                job.setUri(novelUrl.getUri());
                job.setUpdateTime(new Date());
                scheduleJobRepository.save(job);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 获取html资源的String值
         * @param novelUrl
         * @return
         */
        public String getHtml(NovelUrl novelUrl){
            //html资源的String对象
            String html = null;
            String uri=novelUrl.getUri();
            String filePath = getFilePath(uri);
            File file = new File(filePath);
            File parentFile = file.getParentFile();
            //保证父级目录存在
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            //根据本地资源是否存在判断资源获取方式
            if(file.exists()){
                try(
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                        new FileInputStream(file),"UTF-8"));
                ) {
                    html = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                    //校验文件是否需要更新，目前页面中下一章不存在的资源需要进行更新操作
                    Document doc =Jsoup.parse(html);
                    String nextUri=doc.getElementsByClass("page_chapter").get(0)
                            .getElementsByTag("a").get(2).attr("href");
                    if(!check(nextUri)){
                        html = download(novelUrl,file);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    html = download(novelUrl,file);
                }
            }else {
                //从网络上获取资源并下载到本地
                html = download(novelUrl,file);
            }
            return html;
        }

        /**
         * 从网络上下载资源并返回html的string对象
         * @param novelUrl
         * @param file
         * @return
         */
        public String download(NovelUrl novelUrl,File file){
            String url = novelUrl.getUrl();
            String html = null;
            try(
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            HttpsUtil.creatGetRequest(url),"GBK"));
                    OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(
                            new FileOutputStream(file)),"UTF-8");
            ){
                html = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                html = html.replace("content=\"text/html; charset=gbk\"", "content=\"text/html; charset=UTF-8\"");
                osw.write(html);
                osw.flush();
            }catch(Exception e){
                e.printStackTrace();
                html = null;
                if(file.exists()){
                    file.delete();
                }
            }
            return html;
        }

        /**
         * 持久化章节信息(不影响到整个流程)
         * @param doc
         */
        public void saveChapter(Document doc,NovelUrl url){
            try {
                NovelChapter chapter = new NovelChapter();
                String chapterPath = url.getUri();
                String chapterName = doc.body().getElementsByClass("content").get(0).getElementsByTag("h1").get(0).html();
                chapter.setNovelId(url.getNovel().getId());
                chapter.setChapterName(chapterName);
                chapter.setChapterPath(chapterPath);
                novelChapterRepository.save(chapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        /**
         * 解析Html内容获取下一章对应的url
         * @param url
         * @return
         */
        @Deprecated
        public String getNextUrl(String url){
            String filePath = getFilePath(url);
            if(filePath == null){
                return null;
            }
            File file = new File(filePath);
            //文件不存在，正常情况文件应该已经存在
            if(!file.exists()){
                return null;
            }
            try (
                FileInputStream is = new FileInputStream(filePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             ){
                String result = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                int s = result.indexOf("下一章</a>");
                if(s == -1){
                    return null;
                }
                Pattern pattern = Pattern.compile("<a href=\"(.+)\">下一章</a>");
                Matcher matcher = pattern.matcher(result);
                if(matcher.find()){
                    String str = matcher.group(1);
                    return url.substring(0,url.indexOf("/",url.indexOf("//")+2))+ str;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
