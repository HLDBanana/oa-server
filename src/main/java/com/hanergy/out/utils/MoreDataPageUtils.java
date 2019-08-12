package com.hanergy.out.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 */
public class MoreDataPageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	private Long totalRecords;
	//每页记录数
	private Long pageSize;
	//总页数
	private int pageCount;
	//当前页数
	private Long pageNumber;
	//列表数据
	private List<?> list;

	//列表数据
	private List<?> virtualList;

	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalRecords  总记录数
	 * @param pageSize    每页记录数
	 * @param pageNumber    当前页数
	 */
	public MoreDataPageUtils(List<?> list, Long totalRecords, Long pageSize, Long pageNumber) {
		this.list = list;
		this.totalRecords = totalRecords;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.pageCount = (int)Math.ceil((double)totalRecords/pageSize);
	}

	/**
	 * 分页
	 */
	public MoreDataPageUtils(IPage<?> page) {
		if (page == null){
			this.totalRecords = Long.valueOf("0");
			return;
		}
		this.list = page.getRecords();
		this.totalRecords = page.getTotal();
		this.pageSize = page.getSize();
		this.pageNumber = page.getCurrent();
		this.pageCount = (int)page.getPages();
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public List<?> getVirtualList() {
		return virtualList;
	}

	public void setVirtualList(List<?> virtualList) {
		this.virtualList = virtualList;
	}
}
