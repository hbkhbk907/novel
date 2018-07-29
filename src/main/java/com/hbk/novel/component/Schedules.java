package com.hbk.novel.component;

import com.hbk.novel.entity.ScheduleJob;
import com.hbk.novel.repository.ScheduleJobRepository;
import com.hbk.novel.util.NovelReptilian;
import com.hbk.novel.bean.NovelUrl;
import com.hbk.novel.entity.Novel;
import com.hbk.novel.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Schedules {

    @Autowired
    NovelReptilian novelReptilian;
    @Autowired
    NovelRepository novelRepository;
    @Autowired
    ScheduleJobRepository scheduleJobRepository;

    @Scheduled(fixedDelay = 3600 * 1000)
    public void timerToNow(){
        List<ScheduleJob> schedules = scheduleJobRepository.findAll();
        if(schedules.size()>0){
            for(ScheduleJob job : schedules){
                String baseUrl = job.getBaseUrl();
                String uri = job.getUri();
                Novel novel = novelRepository.findById(job.novelId).get();
                NovelUrl url = new NovelUrl();
                url.setUrl(baseUrl+uri);
                url.setUri(uri);
                url.setBaseUrl(baseUrl);
                url.setNovel(novel);
                novelReptilian.start(url);
            }
        }
    }
}
