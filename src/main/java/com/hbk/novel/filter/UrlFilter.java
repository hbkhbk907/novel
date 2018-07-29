package com.hbk.novel.filter;

import  static  com.hbk.novel.util.CommonUtil.*;
import com.hbk.novel.entity.Novel;
import com.hbk.novel.service.NovelService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Component
public class UrlFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //处理url
        String url = getString(request.getRequestURI());
        //过滤其他静态资源请求
        if(url.endsWith(".js") || url.endsWith(".css") || url.startsWith("/novel/")){
            filterChain.doFilter(request,response);
            return;
        }
        if(url.equals("/") || url.equals("")){
            response.sendRedirect("/index");
            return;
        }
        if(url.endsWith("/")){
            url = url.substring(0,url.length()-1);
        }
        ServletContext context = this.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
        NovelService novelService = ac.getBean(NovelService.class);
        List<Novel> novelList = novelService.queryNovels();
        if(novelList!= null && novelList.size()>0){
            for(Novel novel :novelList){
                String filePath = novel.getFilePath();
                //访问小说目录
                if(url.endsWith(filePath)){
                    response.sendRedirect("/catalog?id=" + novel.getId());
                    return;
                //访问小说静态资源
                }else if(url.startsWith(filePath) && url.endsWith(".html")){
                    response.sendRedirect("/novel" + url);
                    return;
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
