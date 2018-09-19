package cn.ffcs.uoo.rabbitmq.manage.util;

import java.security.MessageDigest;

/**
 * 32位大写
 * @author WuHao
 *
 */
public class Md5Util {
	
	public static String md5(String str) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(str.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	private static String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}
	
	public static void main(String[] args) {
		//202CB962AC59075B964B07152D234B70
		System.out.println(md5("123"));
	}
}