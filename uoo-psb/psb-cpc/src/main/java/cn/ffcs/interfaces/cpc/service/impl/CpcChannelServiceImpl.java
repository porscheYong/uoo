package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.service.CpcChannelService;
import cn.ffcs.interfaces.cpc.util.Json2MapUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

@Service
public class CpcChannelServiceImpl implements CpcChannelService {
    @Override
    public String handle(String json) {
        Map<String, Object> map = Json2MapUtil.handle(json);
        //1000是失败，0成功
        String result_code = "1000";
        //结果描述
        StringBuffer result_msg = new StringBuffer();
        //流水号
        String TransactionID = "";
        //结果集
        Map<String, Object> rsMap = new HashMap<>();
        if (map == null) {
            result_msg.append("json is null.");
        } else {
            TransactionID = (String) map.get("TransactionID");
            result_code = "0";
        }
        rsMap.put("result_code", result_code);
        rsMap.put("result_msg", result_msg.toString());
        rsMap.put("TransactionID", TransactionID);
        return JSON.toJSONString(rsMap);
    }

    public static void main(String[] args) {
        String json = "{\"TransactionID\":\"1000000045201901078888457736\",\"CHANNEL\":{\"CHANNEL_NBR\":" +
                "\"3301063199917\",\"CHANNEL_NAME\":\"西湖梦途丰谭路专柜\",\"CHANNEL_CLASS\":\"10\"," +
                "\"CHN_TYPE_CD\":\"100202\",\"CHANNEL_TYPE_CD\":\"100000\",\"COMMON_REGION_ID\":\"8330106\"," +
                "\"STATUS_CD\":\"1000\",\"STATUS_DATE\":\"20190109151622\",\"DESCRIPTION\":null,\"ACTION\":" +
                "\"MOD\"},\"OPERATORS\":{\"OPERATORS_NBR\":\"J33010757714\",\"OPERATORS_NAME\":\"余婷\"," +
                "\"OPERATORS_TYPE_CD\":\"20\",\"OPERATORS_AREA_GRADE\":\"1000\",\"PARENT_OPER_NBR\":null," +
                "\"COMMON_REGION_ID\":\"8330106\",\"STATUS_CD\":\"1000\",\"STATUS_DATE\":null,\"DESCRIPTION\":null," +
                "\"ACTION\":\"MOD\"},\"STAFF\":{\"SALES_CODE\":\"Y99999999\",\"STAFF_CODE\":\"Y99999999\",\"ACCOUNT\":" +
                "\"999999\",\"STAFF_NAME\":\"测试yxg123\",\"CERT_TYPE\":\"1\",\"CERT_NUMBER\":\"330702197911041225\"," +
                "\"MOBILE_PHONE\":\"15372128883\",\"E_MAIL\":null,\"COMMON_REGION_ID\":\"8330106\",\"STAFF_DESC\":null," +
                "\"STATUS_CD\":\"1000\",\"STATUS_DATE\":null,\"CREATE_DATE\":null,\"ACTION\":\"MOD\"}," +
                "\"CHANNEL_OPERATORS_RELAS\":{\"CHANNEL_NBR\":\"3301063199917\",\"OPERATORS_NBR\":\"J33010757714\"," +
                "\"RELA_TYPE\":\"10\",\"DESCRIPTION\":null,\"ACTION\":\"MOD\"},\"STAFF_CHANNEL_RELAS\":{\"SALES_CODE\":" +
                "\"Y99999999\",\"CHANNEL_NBR\":\"3301063199917\",\"RELA_TYPE\":\"10\",\"DESCRIPTION\":null,\"ACTION\":\"MOD\"}}";
        System.out.println(new CpcChannelServiceImpl().handle(json));
    }
}
