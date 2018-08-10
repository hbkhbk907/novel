package com.hbk.novel;

import com.hbk.novel.bean.NovelUrl;
import com.hbk.novel.entity.Novel;
import com.hbk.novel.entity.NovelChapter;
import com.hbk.novel.repository.NovelChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.hbk.novel.repository")   //dao类扫描路径
@EntityScan("com.hbk.novel.entity")             //数据库映射entity扫描路径
@EnableScheduling
public class NovelApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelApplication.class,args);

    }
}
