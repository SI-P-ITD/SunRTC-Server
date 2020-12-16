package com.sinosig.suntrtc.engin;

public class ResultBean {

	private String resCode;
	private String resMsg;
	private Object resultContent;
	
	public ResultBean(String resCode, String resMsg){
		this.resCode = resCode;
		this.resMsg = resMsg;
	}
	
	public ResultBean(String resCode, String resMsg, Object resultContent){
		this.resCode = resCode;
		this.resMsg = resMsg;
		this.resultContent = resultContent;
	}
	public ResultBean(){}
	
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public Object getResultContent() {
		return resultContent;
	}
	public void setResultContent(Object resultContent) {
		this.resultContent = resultContent;
	}
}
