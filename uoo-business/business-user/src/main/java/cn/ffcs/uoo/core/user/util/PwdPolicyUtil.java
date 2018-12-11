package cn.ffcs.uoo.core.user.util;

import java.util.regex.Pattern;
import org.springframework.util.StringUtils;


/**
 *功能说明:密码策略工具类.
 *
 *创建人:李涌
 *
 *创建时间:2014-1-7 下午3:15:34
 *
 *修改人             修改时间             修改描述
 *
 *@author
 *Copyright (c)2014 福建富士通信息软件有限公司-版权所有
 * 
 */
public class PwdPolicyUtil {
	//private static final Pattern pwdBasicPattern = Pattern.compile("(?=.*\\d)(?=.*[a-zA-Z])^[a-zA-Z\\d`~!@#$%^&\\*\\+\\=\\{\\}\\':;,\\[\\]\\.<>\\?\\(\\)\\_\\-\\|/\"]{6,20}$");
/**
	 * 
	 *功能说明:判断密码是否符合密码基础规范.
	 *创建人:李涌
	 *创建时间:2014-1-7 下午3:38:22
	 *@param pwd
	 *@return boolean
	 *
	 */
	public static boolean isMatchBasicPattern(String pwd){
		if(StringUtils.hasText(pwd)){
			//密码长度大于8位
			if (checkInput(pwd) && pwd.length() >= 8){
				return true;
			}else{
				return false;
			}
		}
        return false;
	}
	/**
	 * 检验是否符合密码规则 
	 * */
	private static Pattern NUMBER_PATTERN = Pattern.compile("\\d");
	private static Pattern LETTER_PATTERN = Pattern.compile("[a-z]");
	private static Pattern LETTER_UP_PATTERN = Pattern.compile("[A-Z]");
	private static Pattern SPECIAL_PATTERN = Pattern.compile("[-,~`.：/\\|=_;'!@#$%^&*()+?><]");
	public static boolean checkInput(String str){

        int num = 0;
		//包含数字
		num = NUMBER_PATTERN.matcher(str).find() ? num + 1 : num;
		//包含小写字母
        num = LETTER_PATTERN.matcher(str).find() ? num + 1 : num;
		//包含大写字母
        num = LETTER_UP_PATTERN.matcher(str).find() ? num + 1 : num;
		//包含特殊符号
        num = SPECIAL_PATTERN.matcher(str).find() ? num + 1 : num;
		//不超过3位重复数字或字母
        boolean dwcf = hasLH(str,4,0);
		//不超过3位连续的数字或字母
        boolean lx = hasLH(str,4,1);
		//键盘字母排序是否超过3位例如qwer
        boolean checkKB = checkKeyboardOrdering(str);
		//包含数据、小写字母、大写字母、特殊符号中的三种，且不超过3位重复数字或字母、不超过3位连续的数字或字母
        return num >= 3 & checkKB & (!dwcf) & (!lx);
	}
	
	/**
	 * 检验键盘字母排序是否超过3位例如qwer
	 * */
	public static boolean checkKeyboardOrdering(String str){
		String keyAll = "STR,ASDF,asdf,BVCX,bvcx,cvbn,CVBN,DFGH,dfgh,ERTY,erty,FDSA,fdsa,fghj,FGHJ,GFDS,gfds,ghjk,GHJK,hgfd,HGFD,"+
				     "HJKL,hjkl,IUYT,iuyt,jhgf,JHGF,kjhg,KJHG,LKJH,lkjh,MNBV,mnbv,NBVC,nbvc,oiuy,OIUY,poiu,POIU,QWER,qwer,"+
					 "REWQ,rewq,RTYU,rtyu,SDFG,sdfg,trew,TREW,tyui,TYUI,uiop,UIOP,UYTR,uytr,VBNM,vbnm,VCXZ,vcxz,WERT,wert,xcvb,XCVB,YTRE,ytre,yuio,YUIO,ZXCV,zxcv";
        String[] arr = keyAll.split(",");
		for(int i = 0; i < arr.length ; i ++){
			if (str.contains(arr[0])){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 根据ascii码判断字符串中包含连续的数字或字母或者重复的数字或字母， type为1时为判断连续的数字或字母，type为0时为判断重复的数字或字母
	 * */
	static boolean hasLH(String str,int count,int type) {
		int pre = 0;
		int len = 0;
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			char c = s.charAt(0);
			if (i == 0) {
				pre = c;
			}
			if (type==1){
				if (c - type == pre) {
					len++;
					if(len>=count){
						return true;
					}
				}else {
					len = 1;
				}
			}else{
				if (c == pre) {
					len++;
					if(len>=count){
						return true;
					}
				}else {
					len = 1;
				}
			}
			pre = c;
		}
		return false;
	}
	
}
