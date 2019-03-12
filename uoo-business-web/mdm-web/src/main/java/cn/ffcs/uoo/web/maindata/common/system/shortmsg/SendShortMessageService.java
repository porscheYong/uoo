package cn.ffcs.uoo.web.maindata.common.system.shortmsg;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis.client.Call;
import org.springframework.stereotype.Service;

@Service
public class SendShortMessageService {
    public void sendSms(String phoneNumber,String randomCode) throws Exception{
        //接口地址
          String endPoint = "http://134.96.246.88:7001/eam-apps/services/eamAuthServices?wsdl";
          org.apache.axis.client.Service service = new org.apache.axis.client.Service();
          Call call = (Call) service.createCall();
          call.setTargetEndpointAddress(new URL(endPoint));
          //接口方法名
          call.setOperationName("sendRandomCodeToOctopus");
          //参数
          Map xmlMap = new HashMap(); 
          xmlMap.put("randomCode",randomCode);
          xmlMap.put("phoneNumber", phoneNumber);
          HashMap res = (HashMap) call.invoke(new Object[] {xmlMap});
          System.out.println("SendShortMessageService 短信返回："+res);
      }
}
