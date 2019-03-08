package cn.ffcs.uoo.web.maindata.common.system.shortmsg;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ztesoft.uccp.dubbo.interfaces.UCCPSendService;



@Service("smSendUccpSer")
@Scope("prototype")
public class SmSendUccpService {

	private static final Logger logger = LoggerFactory.getLogger(SmSendUccpService.class);

	public static final String UCCP_SUC_CODE="0000";
	
	private static final String UCCP_SYSTEM_CODE="4ASYS1";
	private static final String UCCP_PASSWD="a12345";
	private static final String UCCP_USER_ACCT="4ASYS1";
	public static final String UCCP_SCENE_ID_DX="4321";  //登陆短信验证
	public static final String UCCP_SCENE_ID_CZ="4322";  //密码重置验证
	public static final String UCCP_SCENE_ID_GJ="4323";  //告警短信
	
    //发布修改
	//private static UCCPSendService uCCPSendService = (UCCPSendService) ApplicationContextUtil.getBean("UCCPSendService");
	private static UCCPSendService uCCPSendService = null;
	


	public Map sendSms(String phoneNumber,String msg,String scene) throws Exception {
		HashMap params = new HashMap();
		//请求消息流水，格式：系统编码（6位）+yyyymmddhhmiss+10位序列号
		Date dataD = new Date();
		String dateStr = DateUtil.getDateTime(dataD);
		//18位 时间+随机码
		//yyyymmddhhmiss 14+4
		String TransactionId =DateFormatUtils.format(new Date(), "yyyyMMddhhmmss")+RandomUtils.nextInt(1000, 9999);
		//= smSendUccpSerDao.genTransId(UCCP_SYSTEM_CODE+dateStr, 30, "EAM.SEQ_UCCP_MSG");
		params.put("TransactionId",TransactionId);
		//UCCP分配的系统编码
		params.put("SystemCode",UCCP_SYSTEM_CODE);
		//UCCP分配的认证密码
		params.put("Password",UCCP_PASSWD);
		//UCCP分配的帐号
		params.put("UserAcct",UCCP_USER_ACCT);
		//请求的时间,请求发起的时间,必须为下边的格式
		params.put("RequestTime",DateUtil.dateTimeToStr(dataD));
		//接收消息推送的手机号码
		params.put("AccNbr",phoneNumber);
		//消息内容
		params.put("OrderContent",msg);
		//场景标识
		params.put("SceneId",scene);
	//	params.put("SceneId",UCCP_SCENE_ID_CZ);
		//本地网/辖区
		params.put("LanId","");
		//定时发送的时间设置
		params.put("SendDate","");
		//如果使用场景模板来发送短信,必须填值
		params.put("ContentParam","");
		//外系统流水ID,查询发送结果用,可填
		params.put("ExtOrderId","");
		//UCCPSendService service2=null;
		try{
		
		   // service2 = (UCCPSendService) ApplicationContextUtil.getBean("UCCPSendService");
			if(uCCPSendService==null){
				uCCPSendService = ConsumerInit.initUCCPSendServiceReference();
			}else{
				System.out.print("uCCPSendService is not null");
			}
			if(uCCPSendService==null){
				System.out.print("error:uCCPSendService is  null");
			}


		}catch(Exception e) {
			System.out.println("Exception sms e:"+e.getMessage());
			e.printStackTrace();
		}
		Map reqMap = uCCPSendService.sendShortMessage(params);
		System.out.println("接口返回结果:"+reqMap);
		return reqMap;
	}

	
	
}
