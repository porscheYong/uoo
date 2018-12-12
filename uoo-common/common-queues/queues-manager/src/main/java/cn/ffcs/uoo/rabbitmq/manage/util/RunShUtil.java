package cn.ffcs.uoo.rabbitmq.manage.util;

import java.io.IOException;

public class RunShUtil {

    public static boolean run(String name){
        try {
            Process p = Runtime.getRuntime().exec(name);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
