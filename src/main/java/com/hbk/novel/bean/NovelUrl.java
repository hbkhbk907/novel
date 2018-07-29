package com.hbk.novel.bean;

import com.hbk.novel.entity.Novel;
import lombok.Data;

@Data
public class NovelUrl {
    /**
     * 小说资源路径域名（包含端口）
     */
    private String baseUrl;
    /**
     * 小说资源统一资源标识符
     */
    private String uri;
    /**
     * 小说统一资源定位符
     */
    private String url;
    /**
     * 对应的小说配置表中配置
     */
    private Novel novel;
}
