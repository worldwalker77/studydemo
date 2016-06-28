package com.project.maven.common;

public class Column {
	
	private String field;
	private String title;
	private String fromatter;
	private String checkMethod;
	private String errorTip;
	private String grid;
	private String type;
	private String events;
	private String placeholder;
	private String maxlength;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFromatter() {
		return fromatter;
	}
	public void setFromatter(String fromatter) {
		this.fromatter = fromatter;
	}
	public String getCheckMethod() {
		return checkMethod;
	}
	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}
	public String getGrid() {
		return grid;
	}
	public void setGrid(String grid) {
		this.grid = grid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public String getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	public String getEvents() {
		return events;
	}
	public void setEvents(String events) {
		this.events = events;
	}
	public String getErrorTip() {
		return errorTip;
	}
	public void setErrorTip(String errorTip) {
		this.errorTip = errorTip;
	}
	
}
