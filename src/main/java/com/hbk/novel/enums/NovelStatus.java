package com.hbk.novel.enums;

/**
 * 小说完成情况
 */
public enum NovelStatus {
    UNFINISHED("连载"),FINISHED("完本");
    private String name;
    private NovelStatus(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}
