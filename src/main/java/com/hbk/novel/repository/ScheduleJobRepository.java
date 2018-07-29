package com.hbk.novel.repository;

import com.hbk.novel.entity.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 定时任务持久化类
 */
public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long> {
    /**
     * 通过小说编号查找任务信息
     * @param novelId
     * @return
     */
    public ScheduleJob findByNovelId(Long novelId);
}
