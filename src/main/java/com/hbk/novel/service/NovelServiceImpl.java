package com.hbk.novel.service;

import com.hbk.novel.entity.Novel;
import com.hbk.novel.entity.NovelChapter;
import com.hbk.novel.repository.NovelChapterRepository;
import com.hbk.novel.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovelServiceImpl implements NovelService {
    @Autowired
    NovelChapterRepository novelChapterRepository;
    @Autowired
    NovelRepository novelRepository;
    @Override
    public List<NovelChapter> queryChaptersByNovelId(Long novelId) {
        return novelChapterRepository.findAllByNovelIdOrderById(novelId);
    }

    @Override
    public Novel queryNovelByNovelId(Long novelId) {
        return novelRepository.findById(novelId).get();
    }

    @Override
    public List<Novel> queryNovels() {
        return novelRepository.findAll(new Sort(Sort.Direction.ASC,"id"));
    }
}
