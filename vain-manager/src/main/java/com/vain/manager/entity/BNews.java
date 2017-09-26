package com.vain.manager.entity;

import com.vain.manager.common.entity.Entity;

/**
 * Created by vain on 2017/8/22.
 * 百度搜索头条新闻实体
 */
public class BNews extends Entity {

    /**
     * 新闻标题
     */
    private String title;
    /**
     * 新闻链接
     */
    private String url;
    /**
     * 新闻排名
     */
    private Integer numTop;
    /**
     * 新闻搜询次数
     */
    private Integer searchCounts;
    /**
     * 是否下降
     */
    private Boolean isDrop;
    /**
     * 是否是新新闻
     */
    private Boolean isNew;

    public BNews() {
    }

    public BNews(String title, String url, Integer numTop, Integer searchCounts, Boolean isDrop, Boolean isNew) {
        this.title = title;
        this.url = url;
        this.numTop = numTop;
        this.searchCounts = searchCounts;
        this.isDrop = isDrop;
        this.isNew = isNew;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNumTop() {
        return numTop;
    }

    public void setNumTop(Integer numTop) {
        this.numTop = numTop;
    }

    public Integer getSearchCounts() {
        return searchCounts;
    }

    public void setSearchCounts(Integer searchCounts) {
        this.searchCounts = searchCounts;
    }

    public Boolean getDrop() {
        return isDrop;
    }

    public void setDrop(Boolean drop) {
        isDrop = drop;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }
}
