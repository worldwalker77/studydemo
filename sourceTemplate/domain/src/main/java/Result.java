package ${groupId}.domain.result;

import java.io.Serializable;


public class Result implements Serializable{
	
	private static final long serialVersionUID = 1188275509308264519L;
	
	private int code = 0;
	private String desc;
	private Object data;
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
