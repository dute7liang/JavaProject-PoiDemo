package com.app.core.utility.excel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 导入结果
 * 
 * @author hqb qb.huang@wescxx.com
 * @date 2015年10月16日 下午2:01:13
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public class ImportResult {

	private List<String> errMess;// 校验出错的信息

	private Collection succResult;// 校验成功的集合

	private Collection errResult;// 校验失败的集合

	public List<String> getErrMess() {
		if(errMess == null){
			errMess = new ArrayList<String>();
		}
		return errMess;
	}

	public void setErrMess(List<String> errMess) {
		this.errMess = errMess;
	}

	public Collection getSuccResult() {
		if(succResult == null){
			succResult = new ArrayList();
		}
		return succResult;
	}

	public void setSuccResult(Collection succResult) {
		this.succResult = succResult;
	}

	public Collection getErrResult() {
		if(errResult == null){
			errResult = new ArrayList();
		}
		return errResult;
	}

	public void setErrResult(Collection errResult) {
		this.errResult = errResult;
	}

}
