package cn.ffcs.uoo.web.maindata.mdm.logs;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OperateLog {
    OperateType type() default OperateType.OTHER;
    String module() default "未知模块";//组织，区域，人员，。。。
    String methods() default "未知操作";// 新增，删除。修改。查询。。。。。。
    String desc() default "";//详情
}
