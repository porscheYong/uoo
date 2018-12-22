package cn.ffcs.uoo.web.maindata.common.system.utils;

public class IPUtils {
    public static long string2Long(String ipAddress) {
        long result = 0;
        try {
            String[] ipAddressInArray = ipAddress.split("\\.");
            for (int i = 3; i >= 0; i--) {
                long ip = Long.parseLong(ipAddressInArray[3 - i]);
                // left shifting 24,16,8,0 and bitwise OR
                // 1. 192 << 24
                // 1. 168 << 16
                // 1. 1 << 8
                // 1. 2 << 0
                result |= ip << (i * 8);
            }
        } catch (NumberFormatException e) {
        }
        return result;
    }
    public static  String long2String(long i) {
        String ip="未知IP";
        try {
            ip=((i >> 24) & 0xFF) + 
                    "." + ((i >> 16) & 0xFF) + 
                    "." + ((i >> 8) & 0xFF) + 
                    "." + (i & 0xFF);
        } catch (Exception e) {
        }
        return ip;
 
    }
}
