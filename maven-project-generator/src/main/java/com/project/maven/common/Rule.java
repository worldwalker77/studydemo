package com.project.maven.common;

import java.util.List;
import java.util.Map;

public class Rule {
	
	private String fileName;
	
	private List<Column> columns;
	
	private Map<String, String> select;
	
	private Map<String, String> edit;
	
	private Map<String, String> add;
	
	private Map<String, String> delete;
	
	private Map<String, String> status;

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Map<String, String> getDelete() {
		return delete;
	}

	public void setDelete(Map<String, String> delete) {
		this.delete = delete;
	}

	public Map<String, String> getStatus() {
		return status;
	}

	public void setStatus(Map<String, String> status) {
		this.status = status;
	}

	public Map<String, String> getEdit() {
		return edit;
	}

	public void setEdit(Map<String, String> edit) {
		this.edit = edit;
	}

	public Map<String, String> getSelect() {
		return select;
	}

	public void setSelect(Map<String, String> select) {
		this.select = select;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getAdd() {
		return add;
	}

	public void setAdd(Map<String, String> add) {
		this.add = add;
	}
	
}
