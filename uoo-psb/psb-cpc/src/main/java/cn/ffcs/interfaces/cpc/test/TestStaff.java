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
        String json = "{\n" +
                "\t\"TransactionID\": \"1000000045201901078888457736\",\n" +
                "\t\"CHANNEL\": {\n" +
                "\t\t\"CHANNEL_NBR\": \"3301063199917\",\n" +
                "\t\t\"CHANNEL_NAME\": \"西湖梦途丰谭路专柜4\",\n" +
                "\t\t\"CHANNEL_CLASS\": \"10\",\n" +
                "\t\t\"CHN_TYPE_CD\": \"100202\",\n" +
                "\t\t\"CHANNEL_TYPE_CD\": \"100000\",\n" +
                "\t\t\"COMMON_REGION_ID\": \"8330106\",\n" +
                "\t\t\"STATUS_CD\": \"1000\",\n" +
                "\t\t\"STATUS_DATE\": \"20190109151622\",\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t},\n" +
                "\t\"OPERATORS\": {\n" +
                "\t\t\"OPERATORS_NBR\": \"J33010757714\",\n" +
                "\t\t\"OPERATORS_NAME\": \"余婷\",\n" +
                "\t\t\"OPERATORS_TYPE_CD\": \"20\",\n" +
                "\t\t\"OPERATORS_AREA_GRADE\": \"1000\",\n" +
                "\t\t\"PARENT_OPER_NBR\":\"\" ,\n" +
                "\t\t\"COMMON_REGION_ID\": \"8330106\",\n" +
                "\t\t\"STATUS_CD\": \"1000\",\n" +
                "\t\t\"STATUS_DATE\": \"20190109151622\",\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t},\n" +
                "\t\"STAFF\": {\n" +
                "\t\t\"SALES_CODE\": \"Y99999999\",\n" +
                "\t\t\"STAFF_CODE\": \"Y99999999\",\n" +
                "\t\t\"ACCOUNT\": \"999999\",\n" +
                "\t\t\"STAFF_NAME\": \"测试yxg123\",\n" +
                "\t\t\"CERT_TYPE\": \"1\",\n" +
                "\t\t\"CERT_NUMBER\": \"330702197911041225\",\n" +
                "\t\t\"MOBILE_PHONE\": \"15372128883\",\n" +
                "\t\t\"E_MAIL\": \"\",\n" +
                "\t\t\"COMMON_REGION_ID\": \"8330106\",\n" +
                "\t\t\"STAFF_DESC\": \"\",\n" +
                "\t\t\"STATUS_CD\": \"1000\",\n" +
                "\t\t\"STATUS_DATE\": \"20190109151622\",\n" +
                "\t\t\"CREATE_DATE\": \"20190109151622\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t},\n" +
                "\t\"CHANNEL_OPERATORS_RELAS\": [{\n" +
                "\t\t\"CHANNEL_NBR\": \"3301063199917\",\n" +
                "\t\t\"OPERATORS_NBR\": \"J33010757714\",\n" +
                "\t\t\"RELA_TYPE\": \"10\",\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t}],\n" +
                "\t\"STAFF_CHANNEL_RELAS\": [{\n" +
                "\t\t\"SALES_CODE\": \"Y99999999\",\n" +
                "\t\t\"CHANNEL_NBR\": \"3301063199917\",\n" +
                "\t\t\"RELA_TYPE\": \"10\",\n" +
                "\t\t\"DESCRIPTION\": \"\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t}]\n" +
                "\n" +
                "\n" +
                "}";
        System.out.println(cpcChannelService.handle(json));
    }
}