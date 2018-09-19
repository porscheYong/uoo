package cn.ffcs.uoo.rabbitmq.manage.util;

public class StringUtil {
	
	/**
	 * 字符串是否为空判断
	 */
	public static boolean isEmpty(String str){
		if(str == null || str.equals("")) {
			return true;
		}else 
			return false;
	}
	
}