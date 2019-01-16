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
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t},\n" +
                "\t\"OPERATORS\": {\n" +
                "\t\t\"OPERATORS_NBR\": \"J33010757714\",\n" +
                "\t\t\"ACTION\": \"MOD\"\n" +
                "\t},\n" +
                "\t\"STAFF\": {\n" +
                "\t\t\"STAFF_CODE\": \"Y99999999\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t},\n" +
                "\t\"CHANNEL_OPERATORS_RELAS\": [{\n" +
                "\t\t\"CHANNEL_NBR\": \"3301063199917\",\n" +
                "\t\t\"OPERATORS_NBR\": \"J33010757714\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t}],\n" +
                "\t\"STAFF_CHANNEL_RELAS\": [{\n" +
                "\t\t\"SALES_CODE\": \"Y99999999\",\n" +
                "\t\t\"CHANNEL_NBR\": \"3301063199917\",\n" +
                "\t\t\"ACTION\": \"DEL\"\n" +
                "\t}]\n" +
                "\n" +
                "}";
        System.out.println("result : " + cpcChannelService.handle(json));
    }
}