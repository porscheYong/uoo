package cn.ffcs.interfaces.cpc.test;

import cn.ffcs.interfaces.cpc.service.CpcChannelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestStaff {
    @Autowired
    private CpcChannelService cpcChannelService;

    @Test
    public void test () throws Exception {
        String json = "{\"TransactionID\":\"1000000045201901078888457736\",\"CHANNEL\":{\"CHANNEL_NBR\":\"3301063199917\",\"CHANNEL_NAME\":\"西湖梦途丰谭路专柜\",\"CHANNEL_CLASS\":\"10\",\"CHN_TYPE_CD\":\"100202\",\"CHANNEL_TYPE_CD\":\"100000\",\"COMMON_REGION_ID\":\"8330106\",\"STATUS_CD\":\"1000\",\"STATUS_DATE\":\"20190109151622\",\"DESCRIPTION\":null,\"ACTION\":\"MOD\"},\"STAFF\":{\"SALES_CODE\":\"Y99999999\",\"STAFF_CODE\":\"Y99999999\",\"ACCOUNT\":\"999999\",\"STAFF_NAME\":\"测试yxg123\",\"CERT_TYPE\":\"1\",\"CERT_NUMBER\":\"330702197911041225\",\"MOBILE_PHONE\":\"15372128883\",\"E_MAIL\":null,\"COMMON_REGION_ID\":\"8330106\",\"STAFF_DESC\":null,\"STATUS_CD\":\"1000\",\"STATUS_DATE\":null,\"CREATE_DATE\":null,\"ACTION\":\"MOD\"}}";
        System.out.println(cpcChannelService.handle(json));
    }
}