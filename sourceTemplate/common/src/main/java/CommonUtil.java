package ${groupId}.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonUtil {
	
	private final static Log log = LogFactory.getLog(CommonUtil.class);
	
	public static String getErrorDesc(String insideDesc, Object params){
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("requestId:");
			sb.append(RequestUtil.getRequestId()).append(" ; ");
			sb.append("errorDescription:").append(insideDesc).append(" ; ");
			sb.append("params:").append(JsonUtil.obj2json(params));
		} catch (Exception e) {
			log.error("getErrorDesc error", e);
		}
		return sb.toString();
	}
	
}
