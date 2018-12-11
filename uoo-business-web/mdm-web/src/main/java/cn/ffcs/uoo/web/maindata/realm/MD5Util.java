package cn.ffcs.uoo.web.maindata.realm;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * Created by liuxiaodong on 2018/9/17.
 * MD5加密，非对称算加密算法, salt + 明文密码
 */
public class MD5Util {

    /**
     * 初始化盐（扰码）
     * @return
     */
    public static String getSalt() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();

    }


    // md5 加密
    public static String md5Encoding(String password, String salt) {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(password + salt);

        //System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     *
     * @param password 明文
     * @param salt 密钥
     * @param md5Content 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String password, String salt, String md5Content) {
        //根据传入的密钥进行验证
        String md5Text = md5Encoding(password, salt);

        return md5Text.equalsIgnoreCase(md5Content);
    }

}
