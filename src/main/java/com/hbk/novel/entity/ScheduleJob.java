package com.hbk.novel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 小说资源同步定时任务实体
 */
@Entity
@Getter
@Setter
@Table(name="schedule_job")
public class ScheduleJob {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     * 小说编号
     */
    @Column(name ="novel_id")
    public Long novelId;

    /**
     * 域名+端口
     */
    @Column(name="base_url")
    private String baseUrl;

    /**
     * 任务初始资源连接
     */
    @Column(name="uri")
    private String uri;

    /**
     * 更新时间
     */
    @Column(name="update_time")
    private Date updateTime;
}
