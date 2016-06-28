package com.project.maven.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.project.maven.common.Column;
import com.project.maven.common.JsonUtil;
import com.project.maven.common.Rule;

public class BootStrapHtmlJsGenerate {
	
	private static final Log log = LogFactory.getLog(BootStrapHtmlJsGenerate.class);
	
	/**电脑盘符，文件路径*/
	private static final String disk = "E";
	
	/**源模板文件基础路径*/
	private static final String sourceBasePath = disk + ":\\generateProject\\generateHtmlJs\\sourceHtmlJsTemplate";
	
	/**目标文件基础路径*/
	private static final String targetBasePath = disk + ":\\generateProject\\generateHtmlJs\\targetHtmlJsDirectory";
	
	private static final String rulesJson = "{"
											+ "\"fileName\":\"testIndex\","
											+ "\"columns\":["
															+ "{\"field\":\"id\",\"title\":\"编号\",\"fromatter\":\"\",\"type\":\"text\"},"
															+ "{\"field\":\"merchantName\",\"title\":\"商家名称\",\"fromatter\":\"\",\"checkMethod\":\"validCode\",\"errorTip\":\"提示：商家名称为1-15位中文，字母，数字或-\",\"type\":\"text\"},"
															+ "{\"field\":\"csrName\",\"title\":\"客服名称\",\"fromatter\":\"\",\"checkMethod\":\"validCode\",\"errorTip\":\"提示：客服名称为1-15位中文，字母，数字或-\",\"type\":\"text\"},"
															+ "{\"field\":\"customerName\",\"title\":\"顾客名称\",\"fromatter\":\"\",\"checkMethod\":\"validCode\",\"errorTip\":\"提示：顾客名称为1-15位中文，字母，数字或-\",\"type\":\"text\"},"
															+ "{\"field\":\"status\",\"title\":\"状态\",\"fromatter\":\"statusFormatter\",\"checkMethod\":\"\"},"
															+ "{\"field\":\"\",\"title\":\"操作\",\"fromatter\":\"operationFormatter\",\"checkMethod\":\"\",\"events\":\"add_edit_status\"}"
														+ "],"
											+ "\"select\":{"
														+ "\"selectUrl\":\"/test/list\","
														+ "\"condition\":\"1_2_3\""
													   + "},"
											+ "\"edit\":{"
														+ "\"updateUrl\":\"/test/update\","
														+ "\"condition\":\"0_1_2_3\""
													   + "},"
										   + "\"add\":{"
														+ "\"addUrl\":\"/test/add\""
													+ "}," 
										    + "\"delete\":{"
														+ "\"deleteUrl\":\"/test/delete\","
														+ "\"comfirmColumn\":\"csrName\""
													   + "},"
										    + "\"status\":{"
														+ "\"statusUrl\":\"/test/updateStatus\","
														+ "\"comfirmColumn\":\"csrName\""
													   + "}"
										  + "}";
	private  Rule rule;
	private  String fileName;
	private  List<Column> columns;
	private  Map<String, String> select;
	private  Map<String, String> edit;
	private  Map<String, String> add;
	private  Map<String, String> delete;
	private  Map<String, String> status;
	
	public static void main(String[] args) {
		BootStrapHtmlJsGenerate bshjg = new BootStrapHtmlJsGenerate();
		bshjg.init();
		String breadCrumbArea = bshjg.getBreadCrumbArea();
		String selectRowsArea = bshjg.getSelectRowsArea1();
		String addButtonArea = bshjg.getAddButtonArea();
		String editDialogArea = bshjg.getEditDialogArea();
		bshjg.createTargetFtlFile(
									breadCrumbArea, 
									selectRowsArea, 
									addButtonArea, 
									editDialogArea);
		
		String columnsJs = bshjg.getColumnsJs();
		String selectDtoJs = bshjg.getSelectDtoJs();
		String updateMethodJs = bshjg.getUpdateMethodJs();
		String operateEventsJs = bshjg.getOperateEventsJs();
		String validMethodJs = bshjg.getValidMethodJs();
		bshjg.createTargetJsFile(
									columnsJs, 
									selectDtoJs, 
									updateMethodJs, 
									operateEventsJs, 
									validMethodJs);
	}
	
	private void init(){
		rule = JsonUtil.json2pojo(rulesJson, Rule.class);
		columns = rule.getColumns();
		select = rule.getSelect();
		edit = rule.getEdit();
		add = rule.getAdd();
		delete = rule.getDelete();
		status = rule.getStatus();
		fileName = rule.getFileName();
	}
	
	/**
	 * 
	 */
	public void createTargetFtlFile(String breadCrumbArea, 
											String selectRowsArea, 
											String addButtonArea,
											String editDialogArea){
		
		String sourceftlContent = readContentFromSourceFile(sourceBasePath, "ftlTemplate.ftl");
		String targetFtlContent = sourceftlContent.replace("${breadCrumbArea}", breadCrumbArea)
												  .replace("${selectRowsArea}", selectRowsArea)
												  .replace("${addButtonArea}", addButtonArea)
												  .replace("${editDialogArea}", editDialogArea);
		
		if (makeTargetDirectory(targetBasePath + "\\" + fileName)) {
			if (makeTargetFile(targetBasePath + "\\" + fileName, fileName + ".ftl")) {
				writeNewContentToTargetFile(targetBasePath + "\\" + fileName, fileName + ".ftl", targetFtlContent);
			}
		}
		
	}
	
	public void createTargetJsFile(
									String columnsJs, 
									String selectDtoJs, 
									String updateMethodJs, 
									String operateEventsJs, 
									String validMethodJs){
		String sourceJsContent = readContentFromSourceFile(sourceBasePath, "jsTemplate.js");
		String targetJsContent = sourceJsContent.replace("${columnsJs}", columnsJs)
												  .replace("${selectDtoJs}", selectDtoJs)
												  .replace("${updateMethodJs}", updateMethodJs)
												  .replace("${operateEventsJs}", operateEventsJs)
												  .replace("${validMethodJs}", validMethodJs)
												  .replace("${selectUrl}", select.get("selectUrl"));
		if (makeTargetDirectory(targetBasePath + "\\" + fileName)) {
			if (makeTargetFile(targetBasePath + "\\" + fileName, fileName + ".js")) {
				writeNewContentToTargetFile(targetBasePath + "\\" + fileName, fileName + ".js", targetJsContent);
			}
		}
	}
	/**
	 * 获取columns js
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 下午2:36:28
	 */
	private String getColumnsJs(){
		StringBuffer columnsJs = new StringBuffer("columns : [ ").append("\n");
		for(int i = 0,len = columns.size(); i < len ; i++){
			Column column = columns.get(i);
			columnsJs.append("{\n");
			if (StringUtils.isNotBlank(column.getField())) {
				columnsJs.append("field : '" + column.getField() + "'").append(",").append("\n");
			}
			columnsJs.append("title : '" + column.getTitle() + "'");
			if (StringUtils.isNotBlank(column.getFromatter())) {
				columnsJs.append(",\n");
				columnsJs.append("formatter : " + column.getFromatter());
			}
			if (StringUtils.isNotBlank(column.getEvents())) {
				columnsJs.append(",\n");
				columnsJs.append("events : Manager.operateEvents");
			}
			if (i == len -1 ) {
				columnsJs.append("}");
			}else{
				columnsJs.append("},");
			}
		}
		columnsJs.append("\n").append("]");
		return columnsJs.toString();
//		columns : [ {
//			field : 'pkid',
//			title : '公司编号'
//		}, {
//			field : 'expName',
//			title : '公司名称'
//		}, {
//			field : 'expPhone',
//			title : '公司电话'
//		}, {
//			field : 'expWebsite',
//			title : '公司网址',
//			formatter : Manager.webFormatter
//		}, {
//			field : 'expCode',
//			titleTooltip : '重要数据，不确定情况下，不要修改!',
//			formatter : Manager.codeFormatter,
//			title : '公司代码'
//		}, {
//			field : 'status',
//			formatter : Manager.statusFormatter,
//			title : '状态'
//		}, {
//			field : 'lastModifyedTime',
//			title : '最后修改时间',
//			visible : false
//		}, {
//			formatter : Manager.operationFormatter,
//			events : Manager.operateEvents,
//			title : '操作'
//		} ]
	}
	
	/**
	 * 获取查询条件dto js
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 下午2:33:40
	 */
	private String getSelectDtoJs(){
		StringBuffer selectDtoJs = new StringBuffer("var dto = {\n");
		String conditionStr = select.get("condition");
		String[] conditionArray = conditionStr.split("_");
		for(int i = 0, len = conditionArray.length; i < len ; i++){
			Column column = columns.get(Integer.valueOf(conditionArray[i]));
			selectDtoJs.append(column.getField() + ":" + "$.trim($('#" + column.getField() + "').val())");
			if (i != len-1) {
				selectDtoJs.append(",");
			}
			selectDtoJs.append("\n");
		}
		selectDtoJs.append("};");
		return selectDtoJs.toString();
	}
	
	private String getUpdateMethodJs(){
		String updateDtoJs = getUpdateDtoJs();
		String updateMethodJs = readContentFromSourceFile(sourceBasePath + "\\edit", "updateMethodJs.js");
		updateMethodJs = updateMethodJs.replace("${updateDtoJs}", updateDtoJs)
									   .replace("${addUrl}", add.get("addUrl"))
									   .replace("${updateUrl}", edit.get("updateUrl"));
		
		return updateMethodJs;
	}
	/**
	 * 获取更新条件dto js
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 下午3:18:15
	 */
	private String getUpdateDtoJs(){

		StringBuffer updateDtoJs = new StringBuffer("var dto = {\n");
		String conditionStr = edit.get("condition");
		String[] conditionArray = conditionStr.split("_");
		for(int i = 0, len = conditionArray.length; i < len ; i++){
			
			Column column = columns.get(Integer.valueOf(conditionArray[i]));
			updateDtoJs.append(column.getField() + ":" + "$.trim($('#edit" + column.getField() + "').val())");
			if (i != len-1) {
				updateDtoJs.append(",");
			}
			updateDtoJs.append("\n");
			
		}
		updateDtoJs.append("\n").append("};");
		return updateDtoJs.toString();
	
	}
	
	private String getOperateEventsJs(){
		String operateEventsJs = readContentFromSourceFile(sourceBasePath + "\\edit", "operateEventsJs.js");
		String statusButtonClickJs = getStatusButtonClickJs();
		String editButtonClickJs = getEditButtonClickJs();
		operateEventsJs = operateEventsJs.replace("${statusButtonClickJs}", statusButtonClickJs)
										 .replace("${editButtonClickJs}", editButtonClickJs);
		return operateEventsJs;
	}
	
	private String getStatusButtonClickJs(){
		String statusButtonClickJs = readContentFromSourceFile(sourceBasePath + "\\edit", "statusButtonClickJs.js");
		statusButtonClickJs = statusButtonClickJs.replace("${comfirmColumn}", status.get("comfirmColumn"))
												 .replace("${statusUrl}", status.get("statusUrl"));
		return statusButtonClickJs;
	}
	private String getEditButtonClickJs(){
		String editButtonClickJs = readContentFromSourceFile(sourceBasePath + "\\edit", "editButtonClickJs.js");
		String editFieldFillJs = getEditFieldFillJs();
		editButtonClickJs = editButtonClickJs.replace("${editFieldFillJs}", editFieldFillJs);
		return editButtonClickJs;
	}
	
	private String getEditFieldFillJs(){

		StringBuffer editFieldFillJs = new StringBuffer();
		String conditionStr = edit.get("condition");
		String[] conditionArray = conditionStr.split("_");
		for(String index : conditionArray){
			Column column = columns.get(Integer.valueOf(index));
			editFieldFillJs.append("$('#edit"+ column.getField() + "').val(row." + column.getField() + ")").append(";");
			
		}
		return editFieldFillJs.toString();
//		$("#pkid").val(row.pkid);
//		$("#editExpName").val(row.expName);
//		$("#editExpPhone").val(row.expPhone);
//		$("#editExpCode").val(row.expCode);
//		$("#editExpSite").val(row.expWebsite);
	}
	
	private String getDeleteButtonClickJs(){
		
		return null;
	}
	
	private String getValidMethodJs(){
		
		StringBuffer param = new StringBuffer("");
		StringBuffer valid = new StringBuffer("");
		for(Column column : columns){
			if (StringUtils.isNotBlank(column.getCheckMethod())) {
				param.append("var " + column.getField() + "= $.trim($('#edit" + column.getField() + "').val());").append("\n");
				valid.append("if(" + column.getField() + "== null || " + column.getCheckMethod() + "(" + column.getField() + ".replace(/[-]/g,''))){").append("\n");
				valid.append("$('#editError').text('" + column.getErrorTip() + "');").append("\n");
				valid.append("return false;").append("}").append("\n");
			}
		}
		String validJs = param.toString() + valid.toString();
		String validMethodJs = readContentFromSourceFile(sourceBasePath + "\\edit", "validMethodJs.js");
		validMethodJs = validMethodJs.replace("${validJs}", validJs);
		return validMethodJs;
		
	}
	/**
	 * 获取导航头部html
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 上午10:25:40
	 */
	private String getBreadCrumbArea(){
		String breadCrumb = readContentFromSourceFile(sourceBasePath + "\\breadCrumb", "breadcrumb.ftl");
		return breadCrumb;
	}
	/**
	 * 获取条件查询区域html
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 上午10:09:38
	 */
	private String getSelectRowsArea(){
		StringBuffer selectRowsArea = new StringBuffer();
		selectRowsArea.append("<div class=\"row\">");
		String conditionStr = select.get("condition");
		String[] conditionArray = conditionStr.split("_");
		int len = conditionArray.length;
		for(String index : conditionArray){
			Column column = columns.get(Integer.valueOf(index));
			String type = column.getType();
			String unit = "";
			if ("text".equals(type)) {
				unit = readContentFromSourceFile(sourceBasePath + "\\select", "text.ftl");
				unit = unit.replace("${grid}", column.getGrid())
								   .replace("${title}", column.getTitle())
								   .replace("${field}", column.getField())
								   .replace("${checkMethod}", column.getCheckMethod());
			}else if("select".equals(type)){
				
			}else{
				
			}
			selectRowsArea.append("\n");
			selectRowsArea.append(unit);
		}
		selectRowsArea.append("\n");
		selectRowsArea.append("				</div>");
		return selectRowsArea.toString();
	}
	
	private String getSelectRowsArea1(){
		StringBuffer selectRowsArea = new StringBuffer();
		String resetButtonArea = getResetButtonArea();
		String searchButtonArea = getSearchButtonArea();
		selectRowsArea.append("");
		String conditionStr = select.get("condition");
		String[] conditionArray = conditionStr.split("_");
		int len = conditionArray.length;
		int rowNum = len/3;
		int lastRowNum = len%3;
		for(int i = 0; i < rowNum; i++){
			StringBuffer rowUnit = new StringBuffer("				<div class=\"row\">\n");
			for(int j = 0; j < 3; j++){
				Column column = columns.get(Integer.valueOf(conditionArray[i*3 + j]));
				String type = column.getType();
				String unit = "";
				if ("text".equals(type)) {
					unit = readContentFromSourceFile(sourceBasePath + "\\select", "text.ftl");
					unit = unit.replace("${grid}", "col-xs-3")
									   .replace("${title}", column.getTitle())
									   .replace("${field}", column.getField())
									   .replace("${checkMethod}", column.getCheckMethod());
				}else if("select".equals(type)){
					
				}else{
					
				}
				rowUnit.append("\n").append(unit);
			}
			rowUnit.append("\n");
			if (0 == lastRowNum) {
				rowUnit.append(resetButtonArea).append("\n");
				rowUnit.append(searchButtonArea).append("\n");
			}
			rowUnit.append("				</div>");
			selectRowsArea.append(rowUnit.toString()).append("\n");
		}
		StringBuffer lastRowUnit = new StringBuffer("");
		if (0 != lastRowNum) {
			lastRowUnit.append("				<div class=\"row\">\n");
			for(int j = 0; j < lastRowNum; j++){
				Column column = columns.get(Integer.valueOf(conditionArray[rowNum*3 + j]));
				String type = column.getType();
				String unit = "";
				if ("text".equals(type)) {
					unit = readContentFromSourceFile(sourceBasePath + "\\select", "text.ftl");
					unit = unit.replace("${grid}", "col-xs-3")
									   .replace("${title}", column.getTitle())
									   .replace("${field}", column.getField())
									   .replace("${checkMethod}", column.getCheckMethod());
				}else if("select".equals(type)){
					
				}else{
					
				}
				lastRowUnit.append(unit);
				lastRowUnit.append("\n");
			}
			lastRowUnit.append(resetButtonArea).append("\n");
			lastRowUnit.append(searchButtonArea).append("\n");
			lastRowUnit.append("				</div>");
			selectRowsArea.append(lastRowUnit.toString());
		}
		return selectRowsArea.toString();
	}
	
	
	/**
	 * 获取编辑对话框区域html
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 上午10:18:44
	 */
	private String getEditDialogArea(){
		StringBuffer editRowsArea = new StringBuffer();
		String conditionStr = edit.get("condition");
		String[] conditionArray = conditionStr.split("_");
		for(String index : conditionArray){
			Column column = columns.get(Integer.valueOf(index));
			String type = column.getType();
			String unit = "";
			if ("id".equals(column.getField())) {
				continue;
			}
			if ("text".equals(type)) {
				unit = readContentFromSourceFile(sourceBasePath + "\\edit", "text.ftl");
				unit = unit.replace("${title}", column.getTitle())
						   .replace("${editField}", "edit" + column.getField())
						   .replace("${checkMethod}", column.getCheckMethod());
			}else if("select".equals(type)){
				
			}else{
				
			}
			editRowsArea.append("\n");
			editRowsArea.append(unit);
		}
		
		String editDialogArea = readContentFromSourceFile(sourceBasePath + "\\edit", "edittemplate.ftl");
		editDialogArea = editDialogArea.replace("${editRowsArea}", editRowsArea.toString());
		return editDialogArea;
	}
	
	
	/**
	 * 获取清空按钮区域html
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 上午10:10:00
	 */
	private String getResetButtonArea(){
		String resetButton = readContentFromSourceFile(sourceBasePath + "\\select", "resetbutton.ftl");
		return resetButton;
	}
	/**
	 * 获取查询按钮区域html
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 上午10:11:55
	 */
	private String getSearchButtonArea(){
		String searchButton = readContentFromSourceFile(sourceBasePath + "\\select", "searchbutton.ftl");
		return searchButton;
	}
	/**
	 * 获取新增按钮区域html
	 * @return
	 * @author jinfeng.liu
	 * @date 2016年4月15日 上午10:16:22
	 */
	private String getAddButtonArea(){
		String addButton = readContentFromSourceFile(sourceBasePath + "\\select", "addbutton.ftl");
		return addButton;
	}
	
	/**
	 * 创建目录
	 * @param dirPath
	 */
	private boolean makeTargetDirectory(String dirPath){
		try {
			File file =new File(dirPath);    
			if  (!file .exists()  && !file.isDirectory()){       
			    file .mkdirs(); 
			    System.out.println(dirPath);
			}
		} catch (Exception e) {
			log.error("dirPath:" + dirPath, e);
			return false;
		}
		return true;
	}
	
	/**
	 * 创建文件
	 * @param dirPath
	 * @param fileName
	 */
	private boolean makeTargetFile(String targetDirPath, String targetFileName){
		try {
			File file = new File(targetDirPath + "\\" + targetFileName);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			log.error("targetDirPath:" + targetDirPath + ", targetFileName:" + targetFileName, e);
			return false;
		}
		return true;
	}
	
	private void writeNewContentToTargetFile(String targetDirPath, String targetFileName, String newContent){
		FileWriter fw = null;
		try {
			fw = new FileWriter(targetDirPath + "\\" + targetFileName);
			fw.write(newContent);
			System.out.println(targetDirPath + "\\" + targetFileName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 一次性读出文件中所有内容
	 * @param sourceDirPath
	 * @param sourceFileName
	 * @return
	 */
	private String readContentFromSourceFile(String sourceDirPath, String sourceFileName){
		String encoding = "utf-8";  
        File file = new File(sourceDirPath + "\\" + sourceFileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
	}
	
}
