package com.vain.manager.common.entity;

/**
 * @description: 分类实体父类
 * @author  vain
 * @date 2017/8/31 11:42
 */
public class PagedEntity extends Entity {

	/**
	 * 当前页码
	 */
	private Integer curPage;

	/**
	 * 每页的大小
	 */
	private Integer pageSize;

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 如果请求没有传分页参数，给默认值
	 */
	public void initPageParam() {
		if (this.curPage == null || this.pageSize == null) {
			this.curPage = 1;
			this.pageSize = 20;
		}
	}

}
