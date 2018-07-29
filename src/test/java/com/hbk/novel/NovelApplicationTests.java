package com.hbk.novel;

import com.hbk.novel.entity.NovelChapter;
import com.hbk.novel.repository.NovelChapterRepository;
import com.hbk.novel.repository.NovelRepository;
import com.hbk.novel.util.NovelReptilian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories("com.hbk.novel.repository")   //dao类扫描路径
@EntityScan("com.hbk.novel.entity")             //数据库映射entity扫描路径
public class NovelApplicationTests {
    @Autowired
    NovelChapterRepository novelChapterRepository;
    @Autowired
    NovelRepository novelRepository;
    @Autowired
    NovelReptilian bean;

    @Test
    public void contextLoads() {
        NovelChapter chapter = new NovelChapter();
        chapter.setNovelId(1l);
        chapter.setChapterName("1");
        chapter.setChapterPath("2");
        novelChapterRepository.save(chapter);
    }


}
