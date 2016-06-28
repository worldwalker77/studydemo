package com.project.maven.generator;

import java.util.HashMap;
import java.util.Map;

/**
 * 依赖jar包版本号管理类
 * @author liujinfeng
 *
 */
public class JarDependencyVersion {
	public static Map<String, String> jarVersionMap = new HashMap<String, String>();
	static{
		jarVersionMap.put("${spring-webmvc}", "4.0.4.RELEASE");
		jarVersionMap.put("${spring-jdbc}", "4.0.4.RELEASE");
		jarVersionMap.put("${spring-context-support}", "4.0.4.RELEASE");
		jarVersionMap.put("${spring-tx}", "4.0.4.RELEASE");
		jarVersionMap.put("${mybatis-spring}", "1.2.0");
		jarVersionMap.put("${mybatis}", "3.2.2");
		jarVersionMap.put("${mysql-connector-java}", "5.0.8");
		jarVersionMap.put("${commons-dbcp}", "1.4");
		jarVersionMap.put("${cglib-nodep}", "2.1_3");
		jarVersionMap.put("${javax.servlet-api}", "3.1.0");
		jarVersionMap.put("${velocity}", "1.7");
		jarVersionMap.put("${log4j}", "1.2.16");
		jarVersionMap.put("${velocity-tools}", "2.0");
		jarVersionMap.put("${org.codehaus.jackson}", "1.4.3");
		jarVersionMap.put("${commons-pool}", "1.6");
		
		jarVersionMap.put("${artifactId-parent}", "0.0.1-SNAPSHOT");
		jarVersionMap.put("${artifactId-web}", "0.0.1.0-SNAPSHOT");
		jarVersionMap.put("${artifactId-service}", "0.0.1.0-SNAPSHOT");
		jarVersionMap.put("${artifactId-dao}", "0.0.1.0-SNAPSHOT");
		jarVersionMap.put("${artifactId-rpc}", "0.0.1.0-SNAPSHOT");
		jarVersionMap.put("${artifactId-common}", "0.0.1.0-SNAPSHOT");
		jarVersionMap.put("${artifactId-domain}", "0.0.1.0-SNAPSHOT");
	}
}
