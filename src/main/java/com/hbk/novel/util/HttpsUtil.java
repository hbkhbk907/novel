package com.hbk.novel.util;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpsUtil {
    //编码
    public static final String ENCODED = "UTF-8";

    public static final String URL="http://www.biqukan.com/1_1680/16813635.html";

    /**
     * 发起http GET请求
     * @param url  请求路径
     * @return  如果连接失败，最多进行10重试次连接，如果仍旧失败，返回null
     */
    public static InputStream creatGetRequest(String url){
        InputStream inputStream = null;
        //如果获取响应流出现异常，则最多重试10次
        for(int i=0; i<10 && inputStream == null; i++){
            try{
                inputStream = httpGet(url);
            }catch (IOException e){
                inputStream = null;
            }
        }
        return inputStream;
    }

    /**
     * 发起http get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static InputStream httpGet(String url) throws IOException{
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        httpGet.setConfig(requestConfig);
        HttpResponse httpResponse = client.execute(httpGet);
        return httpResponse.getEntity().getContent();
    }

}
