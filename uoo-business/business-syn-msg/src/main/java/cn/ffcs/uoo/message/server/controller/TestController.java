package cn.ffcs.uoo.message.server.controller;

import cn.ffcs.uoo.message.server.dao.TbBusinessSystemMapper;
import cn.ffcs.uoo.message.server.dao.TbOrgMapper;
import cn.ffcs.uoo.message.server.dao.TbSlaveAcctMapper;
import cn.ffcs.uoo.message.server.dao.TbSystemOrgTreeMapper;
import cn.ffcs.uoo.message.server.service.SystemRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private TbSystemOrgTreeMapper tbSystemOrgTreeMapper;

    @Resource
    private TbBusinessSystemMapper tbBusinessSystemMapper;

    @Autowired
    private SystemRuleService systemRuleService;

    @Resource
    private TbSlaveAcctMapper tbSlaveAcctMapper;

    @Resource
    private TbOrgMapper tbOrgMapper;

    @Autowired
    private ReceiveDateListener listener;

    @GetMapping("test")
    public Object test(){
       /*String json1 = "{\"type\":\"org\",\"handle\":\"insert\",\"context\":{\"column\":\"orgId\",\"value\":800000000037}}";
       String json2 = "{\"type\":\"org\",\"handle\":\"delete\",\"context\":{\"column\":\"orgId\",\"value\":800000000037}}";

       String json3 = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"personnelId\",\"value\":56006}}";
       String json4 = "{\"type\":\"person\",\"handle\":\"delete\",\"context\":{\"column\":\"personnelId\",\"value\":56006}}";


       String json5 = "{\"type\":\"person\",\"handle\":\"insert\",\"context\":{\"column\":\"slaveAcctId\",\"value\":52838}}";
       String json6 = "{\"type\":\"person\",\"handle\":\"delete\",\"context\":{\"column\":\"slaveAcctId\",\"value\":52838}}";*/

        //return tbSlaveAcctMapper.insertOrUpdateSalveAcct(52838L);

        return tbOrgMapper.getOrgVo(800000000037L,1L,1L);
        //listener.process(json1);
    }
}
