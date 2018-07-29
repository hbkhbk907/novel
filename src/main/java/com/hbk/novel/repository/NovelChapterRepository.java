package com.hbk.novel.repository;

import com.hbk.novel.entity.NovelChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 小说章节信息持久化类
 */
public interface NovelChapterRepository extends JpaRepository<NovelChapter, Long> {
    /**
     * 通过小说编号查询对应章节
     * @param novelId
     * @return
     */
    List<NovelChapter> findAllByNovelIdOrderById(Long novelId);


}
