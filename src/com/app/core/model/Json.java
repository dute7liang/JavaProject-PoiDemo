package com.app.core.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * JSON模型
 * 
 * 用户后台向前台返回的JSON对象
 * 
 * @author WXY
 * 
 */
public class Json extends AbstractBaseResultVO implements java.io.Serializable {

	private static final long serialVersionUID = 5998343208569121581L;

	private boolean success = false;

	private String msg = "";

	private Object obj = null;
	
	private Integer code = -1;
	
	public Json(){
		
	}
	
	public Json(String msg){
		this(false,msg,null);
	}
	
	public Json(boolean success,String msg){
		this(success,msg,null);
	}
	
	public Json(boolean success,String msg,Integer code){
		this(success,msg,null,code);
	}
	
	public Json(boolean success,String msg,Object obj,Integer code ){
		super();
		this.success = success;
		this.msg = msg;
		this.obj = obj;
		this.code = code;
	}
	
	public Json(boolean success,String msg,Object obj){
		super();
		this.success = success;
		this.msg = msg;
		this.obj = obj;
	}
	
	public String getJsonStr(){
		JSONObject obj = new JSONObject();
		obj.put("success", this.isSuccess());
		obj.put("obj", this.getObj());
		obj.put("message", this.getMsg());
		return obj.toJSONString();
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
