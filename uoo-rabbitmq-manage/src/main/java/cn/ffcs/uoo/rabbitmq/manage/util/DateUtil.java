package cn.ffcs.uoo.rabbitmq.manage.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static long stringTime2Long(String strTime,String format) {
		
		if(StringUtil.isEmpty(format)) {
			format = FORMAT;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(strTime);
		} catch (ParseException e) {
			logger.error("strTime:{},format:{}.---时间解析不了---.",strTime,format);
		}
		return date.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(stringTime2Long("2018-09-19 14:15:27",null));
	}
	
}