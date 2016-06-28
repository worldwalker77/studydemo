package com.project.maven.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 多模块maven项目自动生成
 * @author liujinfeng
 *
 */
public class MavenProjectGenerate {
	
	private static final Log log = LogFactory.getLog(MavenProjectGenerate.class);
	
	/**使用的模板文件夹名称*/
	private static final String sourceTemplate = "sourceTemplate";
	
	private static final String groupId = "com.zhaogang.wanghao";
	
	private static final String artifactId = "wanghao";
	
	/**在创建hessian调用客户端应用的时候才会使用*/
//	private static final String serverGroupId = "com.zhaogang.pricesoa";
//	private static final String serverArtifactId = "price-soa";
	
	/**电脑盘符，文件路径*/
	private static final String disk = "E";
	
	/**源模板文件基础路径*/
	private static final String sourceBasePath = disk + ":\\generateProject\\" + sourceTemplate;
	
	private static final String sourceBasePath1 = disk + ":\\\\generateProject\\\\" + sourceTemplate;
	/**目标文件基础路径*/
	private static final String targetBasePath = disk + ":\\generateProject\\targetProject" + "\\" + artifactId;
	
	public static void main(String[] args) {
		MavenProjectGenerate mpg = new MavenProjectGenerate();
		mpg.createProject();
	}
	
	/**
	 * 根据模板创建maven工程
	 */
	public void createProject(){
		makeDirectoryAndFileByRecursion(sourceBasePath);
	}
	
	/**
	 * 递归方式根据源目录和文件创建目标目录和文件
	 * @param path
	 */
	private void makeDirectoryAndFileByRecursion (String path){
		File[] fileAndDirs = getFileAndDirListFromSourceDir(path);
		if (null == fileAndDirs) {
			return;
		}
		for(File file : fileAndDirs){
			if (file.isDirectory()) {
				String sourceAbsolutePath = file.getAbsolutePath();
				String sourceFileName = null;
				String sourceDirPath = getReplacedSourceDirPath(sourceAbsolutePath, false, sourceFileName);
				String targetDirPath = getReplacedTargetDirPath(sourceAbsolutePath, sourceDirPath, sourceFileName, false);
				makeTargetDirectory(targetDirPath);
				makeDirectoryAndFileByRecursion(sourceDirPath);
			}else if(file.isFile()){
				String sourceAbsolutePath = file.getAbsolutePath();
				String sourceFileName = file.getName();
				String sourceDirPath = getReplacedSourceDirPath(sourceAbsolutePath, true, sourceFileName);
				String targetDirPath = getReplacedTargetDirPath(sourceAbsolutePath, sourceDirPath, sourceFileName, true);
				String targetFileName = sourceFileName;
				makeDirectoryAndFile(sourceDirPath, sourceFileName, targetDirPath, targetFileName);
			}
		}
	}
	
	/**
	 * 获取目标目录路径
	 * @param sourceAbsolutePath
	 * @param sourceDirPath
	 * @param sourceFileName
	 * @param isFile
	 * @return
	 */
	private String getReplacedTargetDirPath(String sourceAbsolutePath, String sourceDirPath, String sourceFileName, boolean isFile){
		String targetDriPath = null;
		/**如果是文件*/
		if (isFile) {
			/**如果是读取的是java文件,由于需要根据java文件第一行的包路径来得到最终路径，所以需要单独处理*/
			if (isJavaFileDir(sourceDirPath)) {
				targetDriPath = replacedSourceDirPath(sourceDirPath) + "\\" + getPackageDir(sourceDirPath, sourceFileName);
				
			}else{/**如果是非java文件，则直接根据源路径进行替换后得到目标路径*/
				targetDriPath = replacedSourceDirPath(sourceDirPath);
			}
		}else{/**如果是目录*/
			targetDriPath = replacedSourceDirPath(sourceDirPath);
		}
		return targetDriPath;
	}
	
	/**
	 * 判断此目录路径是否是java文件目录路径
	 * 引用注意：在正则表达式中的“\\”表示和后面紧跟着的那个字符构成一个转义字符（姑且先这样命名），代表着特殊的意义；所以如果你要在正则表达式中表示一个反斜杠\，应当写成“\\\\”
	 * @param sourceDirPath
	 * @return
	 */
	private  boolean isJavaFileDir(String sourceDirPath){
		String regex = sourceBasePath1 + "\\\\(web|service|dao|rpc|domain|common|client|cache)\\\\src\\\\main\\\\java";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sourceDirPath);
		if (m.find()) {
			return true;
		}
		return false;
	}
	
	private String replacedSourceDirPath(String sourceDirPath){
		String result = sourceDirPath
		.replace(sourceBasePath  + "\\web", targetBasePath + "\\" + artifactId + "-web")
		.replace(sourceBasePath  + "\\service", targetBasePath + "\\" + artifactId + "-service")
		.replace(sourceBasePath  + "\\dao", targetBasePath + "\\" + artifactId + "-dao")
		.replace(sourceBasePath  + "\\rpc", targetBasePath + "\\" + artifactId + "-rpc")
		.replace(sourceBasePath  + "\\domain", targetBasePath + "\\" + artifactId + "-domain")
		.replace(sourceBasePath  + "\\common", targetBasePath + "\\" + artifactId + "-common")
		.replace(sourceBasePath  + "\\client", targetBasePath + "\\" + artifactId + "-client")
		.replace(sourceBasePath  + "\\cache", targetBasePath + "\\" + artifactId + "-cache")
		.replace(sourceBasePath, targetBasePath);
		return result;
	}
	
	/**
	 * 获取源目录路径
	 * @param sourceAbsolutePath
	 * @param isFile
	 * @param sourceFileName
	 * @return
	 */
	private String getReplacedSourceDirPath(String sourceAbsolutePath, boolean isFile, String sourceFileName){
		String sourceDirPath = null;
		if (isFile) {
			sourceDirPath = sourceAbsolutePath.replace("\\" + sourceFileName, "");
		}else{
			sourceDirPath = sourceAbsolutePath;
		}
		return sourceDirPath;
	}
	
	/**
	 * 创建目录及文件
	 * @param sourceDirPath
	 * @param sourceFileName
	 * @param targetDirPath
	 * @param targetFileName
	 */
	private void makeDirectoryAndFile(String sourceDirPath, String sourceFileName, String targetDirPath, String targetFileName){
		String sourceContent = readContentFromSourceFile(sourceDirPath, sourceFileName);
		String newContent = getReplacedContent(sourceContent);
		if ("pom.xml".equals(sourceFileName)) {
			newContent = getReplacedJarVersion(newContent);
		}
		if (makeTargetDirectory(targetDirPath)) {
			if (makeTargetFile(targetDirPath, targetFileName)) {
				writeNewContentToTargetFile(targetDirPath, targetFileName, newContent);
			}
		}
	}
	
	/**
	 * 根据java文件的第一行获取包路径
	 * @param sourceDirPath
	 * @param sourceFileName
	 * @return
	 */
	private String getPackageDir(String sourceDirPath, String sourceFileName){
		String packageDir = null;
		File file = new File(sourceDirPath + "\\" + sourceFileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String firstLine = br.readLine();
			packageDir = getReplacedContent(firstLine).replace(".", "\\").replace("package ", "").replace(";", "");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return packageDir;
	}
	
	
	/**
	 * 获取文件和目录列表
	 * @param sourceDirPath
	 * @return
	 */
	private File[] getFileAndDirListFromSourceDir(String sourceDirPath){
		File file = new File(sourceDirPath);
		File[] fileList = file.listFiles();
		return fileList;
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
	 * 将文件中的占位符替换为需要的格式
	 * @param sourceContent
	 * @return
	 */
	private String getReplacedContent(String sourceContent){
		String result = sourceContent.replace("${groupId}", groupId).replace("${artifactId}", artifactId);
//		if ("sourceTemplate-client".equals(sourceTemplate)) {
//			result = result.replace("${server-groupId}", serverGroupId).replace("${server-artifactId}", serverArtifactId);
//		}
		return result;
	}
	
	/**
	 * 如果是pom.xml文件的话就需要替换里面的jar版本号
	 * @param sourceContent
	 * @return
	 */
	private String getReplacedJarVersion(String sourceContent){
		String result = sourceContent;
		Set<Entry<String, String>> set = JarDependencyVersion.jarVersionMap.entrySet();
		for(Entry<String, String> entry : set){
			result =  result.replace(entry.getKey(), entry.getValue());
		}
		return result;
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
