package cn.ffcs.uoo.core.personnel.annotion;

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
