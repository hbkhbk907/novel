package com.hbk.novel.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 小说章节实体类，小说--章节  1对多映射
 */
@Entity
@Table(name="novel_chapter")
@Getter
@Setter
public class NovelChapter implements Serializable {
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
     * 章节名称
     */
    @Column(name="chapter_name")
    private String chapterName;
    /**
     * 章节路径
     */
    @Column(name="chapter_path")
    private String chapterPath;
}
