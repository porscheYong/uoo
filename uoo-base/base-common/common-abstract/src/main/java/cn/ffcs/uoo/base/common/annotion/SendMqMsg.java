package cn.ffcs.uoo.base.common.annotion;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SendMqMsg {

    String type();

    String handle();

    String column();
}
