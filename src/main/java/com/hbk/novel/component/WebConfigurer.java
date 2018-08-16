package com.hbk.novel.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfigurer  implements WebMvcConfigurer {

    @Value("${novel.filePath}")
    private String baseFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        配置server虚拟路径，handler为jsp中访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/novel/**").addResourceLocations("file:"+baseFilePath+"/");
    }

}
