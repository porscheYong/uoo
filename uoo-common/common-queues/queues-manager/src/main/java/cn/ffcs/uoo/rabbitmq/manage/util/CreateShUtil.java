package cn.ffcs.uoo.rabbitmq.manage.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class CreateShUtil {

    public static void createRabbitMqUser (HttpServletRequest request, String username, String password) throws Exception{
        String root = request.getRealPath("/");
        System.out.println(root);
        File sh = new File(root+"f.sh");

        if(sh.exists()) {
            sh.delete();
        }

        sh.createNewFile();
        sh.setExecutable(true);

        FileWriter fw = new FileWriter(sh);
        BufferedWriter bf = new BufferedWriter(fw);
        bf.write("rabbitmqctl add_user "+username+" "+password);
        bf.newLine();
        bf.write("rabbitmqctl set_permissions -p / username \".*\" \".*\" \".*\"");
        bf.flush();
        bf.close();
    }

    public static void updateRabbitMqUserPassword(HttpServletRequest request,String username,String oldPassword,String password) throws Exception {
        String root = request.getRealPath("/");
        File sh = new File(root+"f.sh");
        if(sh.exists()) {
            sh.delete();
        }
        sh.createNewFile();
        sh.setExecutable(true);
        FileWriter fw = new FileWriter(sh);
        BufferedWriter bf = new BufferedWriter(fw);
        bf.write("rabbitmqctl change_password "+username+" "+password);
        bf.flush();
        bf.close();
    }
}