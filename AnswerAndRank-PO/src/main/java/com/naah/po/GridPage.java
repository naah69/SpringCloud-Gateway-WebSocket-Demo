package com.naah.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * aliyun sdk 相应信息。
 *
 * @author lijie.ma
 * @since 1.0, May 19, 2014
 */
public class GridPage<T> implements Serializable {

	private static final long serialVersionUID = 7294025168689095197L;

	private String errorCode;

	private String message;

	private String method;
	private List<T> rows;
	private Long total = 0L;
	private Integer page = 1;
	private Integer size = 15;

	public GridPage() {

	}

	public GridPage(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	/**
	 * 构建分页Model
	 *
	 * @param page
	 */
	public GridPage(Page<T> page) {
		if (page == null) {
			return;
		}
		this.setPage(page.getPageNum());
		this.setRows(page);
		this.setSize(page.getPageSize());
		this.setTotal(page.getTotal());
	}

	public void setPageList(Page<T> page) {
		if (page == null) {
			return;
		}
		this.setPage(page.getPageNum());
		this.setRows(page);
		this.setSize(page.getPageSize());
		this.setTotal(page.getTotal());
	}

	public List<T> getRows() {
		return rows == null ? new ArrayList<T>(0) : rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return errorCode == null || "".equals(errorCode.trim())||errorCode=="0"||"0".equals(errorCode.trim());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
