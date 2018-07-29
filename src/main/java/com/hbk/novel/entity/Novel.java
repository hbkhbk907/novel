package com.hbk.novel.entity;

import com.hbk.novel.enums.NovelStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 小说
 */
@Entity
@Getter
@Setter
@Table(name="novel")
public class Novel implements Serializable {
    /**
     * 小说编号
     */
    @Id
    private Long id;

    /**
     * 小说名称
     */
    @Column(name="name")
    private String name;
    /**
     * 作者
     */
    @Column(name="author")
    private String author;
    /**
     * 小说资源路径
     */
    @Column(name="file_path")
    private String filePath;
    /**
     * 小说完成情况
     */
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private NovelStatus status;

}
