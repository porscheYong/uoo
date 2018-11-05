package cn.ffcs.uoo.rabbitmq.manage.util;

public class CheckRollbackUtil {
	
	/***
	 * 测试回滚。
	 */
	public static void check() {
		int temp = 10/0;
		System.out.println(temp);
	}
}