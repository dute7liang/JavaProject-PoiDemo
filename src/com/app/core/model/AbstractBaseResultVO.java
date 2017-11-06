package com.app.core.model;

import java.io.Serializable;

public class AbstractBaseResultVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8426632022829989935L;
	private Object tableConfig;
	
	private Object delegateInfo;

	public Object getTableConfig() {
		return tableConfig;
	}

	public void setTableConfig(Object tableConfig) {
		this.tableConfig = tableConfig;
	}

	public Object getDelegateInfo() {
		return delegateInfo;
	}

	public void setDelegateInfo(Object delegateInfo) {
		this.delegateInfo = delegateInfo;
	}
}
