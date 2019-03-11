package cn.ffcs.uoo.web.maindata.common.system.shortmsg;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.ztesoft.uccp.dubbo.interfaces.UCCPSendService;





public class ConsumerInit {
	//生产
	public static final String route_ip="134.96.161.20";
	public static final String route_port="8001";
	//测试
	//public static final String route_ip="134.96.159.11";
	//public static final String route_port="8033";
	
	public static final String APP_NAME="33.1407";
	public static final String route_Protocol="dubbo";
	
	public static UCCPSendService initUCCPSendServiceReference() {
		//String APP_NAME = "33.1407";
		//String route_ip = "134.96.161.20";
		//String route_port = "8001";
		//String route_Protocol = "dubbo";
		String methodname = "sendShortMessage";
		String servcode = "33.1099.sendShortMessage";
		String version = "1.0";
		Integer DEFAULT_TIMEOUT = 10 * 1000;

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName(APP_NAME);

		// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
		// 引用远程服务
		ReferenceConfig<UCCPSendService> reference = new ReferenceConfig<UCCPSendService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setApplication(application);
		reference.setInterface(UCCPSendService.class);
		reference.setVersion(version);
		reference.setCheck(false);

		// 方法级配置
		List<MethodConfig> methods = new ArrayList<MethodConfig>();
		MethodConfig method = new MethodConfig();
		method.setName(methodname);
		method.setTimeout(DEFAULT_TIMEOUT);
		method.setRetries(0);
		method.setServicecode(servcode);
		methods.add(method);

		reference.setMethods(methods);

		// 配置注册中心
		List<RegistryConfig> registrys = new ArrayList<RegistryConfig>();
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(route_ip + ":" + route_port);
		registry.setProtocol(route_Protocol);
		registry.setCheck(false);

		registrys.add(registry);

		reference.setRegistries(registrys);
		 // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
		return reference.get();
	}

}
