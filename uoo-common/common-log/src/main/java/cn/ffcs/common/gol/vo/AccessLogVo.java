package cn.ffcs.common.gol.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wongs.common.persistence.jpa.entity.BaseOracleEntity;

import javax.persistence.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogVo{

    private Long id;

    private String url;

    private String method;

    private String ipAddress;

    private String clazz;

    private Long costMillis;

    private Long userId;

    private String operate;

}
