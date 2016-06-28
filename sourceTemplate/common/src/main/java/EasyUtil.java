package ${groupId}.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EasyUtil {
	private final static Log log = LogFactory.getLog(EasyUtil.class);
	
	/**
	 * 字符串拼接
	 * @param args
	 * @return
	 */
	public static String strConnect(Object... args) {
		StringBuilder builder = new StringBuilder();
		for (Object obj : args) {
			builder.append(obj);
		}
		return builder.toString();
	}
	
	/**
	 * 转int型
	 * @param origin
	 * @return
	 */
	public static int parseInt(Object origin) {
		return parseInt(origin, 0);
	}
	
	public static int parseInt(Object origin, int defaultValue) {
		int result = defaultValue;
		if (origin == null) {
			return result;
		}

		String s = origin.toString();
		try {
			result = Integer.parseInt(s);
		} catch (Exception e) {
			log.error("转型异常，参数：" + s);
		}
		return result;
	}
	
	/**
	 * 转long型
	 * @param origin
	 * @return
	 */
	public static long parseLong(Object origin) {
		return parseLong(origin, 0l);
	}
	
	public static long parseLong(Object origin, long defaultValue) {
		long result = defaultValue;
		if (origin == null) {
			return result;
		}

		String s = origin.toString();
		try {
			result = Long.parseLong(s);
		} catch (Exception e) {
			log.error("转型异常，参数：" + s);
		}
		return result;
	}

	
	/**
	 * 转double类型
	 * @param origin
	 * @return
	 */
	public static double parseDouble(Object origin) {
		return parseDouble(origin, 0.0);
	}

	private static double parseDouble(Object origin, double defaultValue) {
		double result = defaultValue;
		if (origin == null) {
			return result;
		}
		String s = origin.toString();
		try {
			result = Double.parseDouble(s);
		} catch (Exception e) {
			log.error("转型异常，参数：" + s);
		}
		return result;
	}
	
	/**
	 * 转String类型
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		return toString(o, "");
	}	

	public static String toString(Object origin, String defaultValue) {
		if (origin == null) {
			return defaultValue;
		}
		String temp = origin.toString();
		if (StringUtils.isNotBlank(temp)) {
			return origin.toString();
		}
		return defaultValue;
	}
	
	/**
	 * 日期类型转String
	 * @param date
	 * @return
	 */
	public static String date2Str(Date date){
		String res = "";
		if (date == null) {
			return res;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			res = format.format(date);
		} catch (Exception e) {
			log.error("date2Str转型异常");;
		}
		return res;
	}
	
	/**
	 * hash取模算法
	 * @param str
	 * @param shard
	 * @return
	 */
	public static int hashMod(String str, int shard) {
    	return ELFHash(str) % shard;
    }
	
    public static int ELFHash(String str) {
        int hash = 0;
        int x = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = (int) (hash & 0xF0000000L)) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }

        return (hash & 0x7FFFFFFF);
    }
    
}
