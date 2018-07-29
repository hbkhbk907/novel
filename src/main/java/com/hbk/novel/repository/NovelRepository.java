package com.hbk.novel.repository;

import com.hbk.novel.entity.Novel;
import com.hbk.novel.entity.NovelChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 小说持久化类
 */
public interface NovelRepository extends JpaRepository<Novel, Long> {


}
