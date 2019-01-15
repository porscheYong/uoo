package cn.ffcs.interfaces.cpc.plug;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:application-context-dubbo.xml"})
public class DubboPlug {

}
