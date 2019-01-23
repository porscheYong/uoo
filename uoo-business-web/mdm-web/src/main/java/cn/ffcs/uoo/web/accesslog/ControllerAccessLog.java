package cn.ffcs.uoo.web.accesslog;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

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
 * @ClassName ControllerAccessLog
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/23 10:45
 * @Version 1.0.0
*/
@Data
public class ControllerAccessLog implements Serializable {

    private String url;

    private String method;

    private String ipAddress;

    private String clazz;

    private List<Object> args;

    private Object response;

    private long costMillis;

    private Long userId;

    private String operate;

}
