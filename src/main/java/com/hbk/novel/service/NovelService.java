package com.hbk.novel.service;

import com.hbk.novel.entity.Novel;
import com.hbk.novel.entity.NovelChapter;

import java.util.List;

public interface NovelService {
    /**
     * 根据小说编号查询章节信息
     * @param novelId
     * @return
     */
    List<NovelChapter> queryChaptersByNovelId(Long novelId, int sortType);

    /**
     * 通过小说编号查询小说信息
     * @param novelId
     * @return
     */
    Novel queryNovelByNovelId(Long novelId);

    /**
     * 查询小说列表
     * @return
     */
    List<Novel> queryNovels();
}
