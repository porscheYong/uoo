package cn.ffcs.uoo.base.common.config;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName Global
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/6 14:51
 * @Version 1.0.0
*/
public class Global {

    private Global() {}
    /**
     * 当前对象实例
     */
    private static Global global = null;

    private static EnvProperties envProperties = new EnvProperties();


    public static EnvProperties getEnvProperties(){
        return envProperties;
    }
}
