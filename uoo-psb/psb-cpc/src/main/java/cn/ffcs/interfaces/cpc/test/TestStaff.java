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
    public void test1() throws Exception {
        String json = "{\n" +
                "\t\"CHANNEL\": {\n" +
                "\t\t\"ACTION\": \"ADD\",\n" +
                "\t\t\"CHANNEL_CLASS\": \"10\",\n" +
                "\t\t\"CHANNEL_NAME\": \"龙湾泰恒公众行销团队\",\n" +
                "\t\t\"CHANNEL_NBR\": \"3303033889503\",\n" +
                "\t\t\"CHANNEL_TYPE_CD\": \"100401\",\n" +
                "\t\t\"CHN_TYPE_CD\": \"100202\",\n" +
                "\t\t\"COMMON_REGION_ID\": 8330303,\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"ORG_ID\": \"1-7TL9\",\n" +
                "\t\t\"STATUS_CD\": \"1000\",\n" +
                "\t\t\"STATUS_DATE\": \"2019-02-26 16:37:02\"\n" +
                "\t},\n" +
                "\t\"CHANNEL_OPERATORS_RELAS\": [{\n" +
                "\t\t\"RELA_TYPE\": \"10\",\n" +
                "\t\t\"ACTION\": \"ADD\",\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"OPERATORS_NBR\": \"J33030332006\",\n" +
                "\t\t\"CHANNEL_NBR\": \"3303033889503\"\n" +
                "\t}],\n" +
                "\t\"TransactionID\": \"1000000045201902268804223809\"\n" +
                "}";
        System.out.println("result : " + cpcChannelService.handle(json));
    }
}