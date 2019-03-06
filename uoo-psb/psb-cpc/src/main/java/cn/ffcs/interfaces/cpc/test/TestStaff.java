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
                "\t\t\"ACTION\": \"MOD\",\n" +
                "\t\t\"CHANNEL_CLASS\": \"10\",\n" +
                "\t\t\"CHANNEL_NAME\": \"绍兴电信渠道测试001\",\n" +
                "\t\t\"CHANNEL_NBR\": \"3306001872019\",\n" +
                "\t\t\"CHANNEL_TYPE_CD\": \"120101\",\n" +
                "\t\t\"CHN_TYPE_CD\": \"120201\",\n" +
                "\t\t\"COMMON_REGION_ID\": 8330600,\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"ORG_ID\": \"1-7TLP\",\n" +
                "\t\t\"STATUS_CD\": \"1000\",\n" +
                "\t\t\"STATUS_DATE\": \"2019-01-17 21:28:46\"\n" +
                "\t},\n" +
                "\t\"STAFF\": {\n" +
                "\t\t\"STAFF_DESC\": \"\",\n" +
                "\t\t\"CERT_TYPE\": \"1\",\n" +
                "\t\t\"STATUS_DATE\": \"20190118160100\",\n" +
                "\t\t\"E_MAIL\": \"15395770068@189.cn\",\n" +
                "\t\t\"MOBILE_PHONE\": 15395770068,\n" +
                "\t\t\"ACCOUNT\": \"77P7129\",\n" +
                "\t\t\"CREATE_DATE\": \"20150131100100\",\n" +
                "\t\t\"SALES_CODE\": \"Y33032295011\",\n" +
                "\t\t\"CERT_NUMBER\": \"330219196810120237\",\n" +
                "\t\t\"ACTION\": \"MOD\",\n" +
                "\t\t\"COMMON_REGION_ID\": 8330300,\n" +
                "\t\t\"STAFF_NAME\": \"毛兴??\",\n" +
                "\t\t\"STATUS_CD\": \"1000\",\n" +
                "\t\t\"STAFF_CODE\": \"Y33032295011\"\n" +
                "\t},\n" +
                "\t\"STAFF_CHANNEL_RELAS\": [{\n" +
                "\t\t\"RELA_TYPE\": \"10\",\n" +
                "\t\t\"SALES_CODE\": \"Y33032295011\",\n" +
                "\t\t\"ACTION\": \"MOD\",\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"CHANNEL_NBR\": \"3306001872019\"\n" +
                "\t}],\n" +
                "\t\"TransactionID\": \"1000000045201901188892696255\"\n" +
                "}";
        System.out.println("result : " + cpcChannelService.handle(json));
    }
}