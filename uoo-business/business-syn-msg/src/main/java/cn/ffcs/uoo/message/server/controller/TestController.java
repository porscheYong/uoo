package cn.ffcs.uoo.message.server.controller;

import cn.ffcs.uoo.message.server.dao.TbBusinessSystemMapper;
import cn.ffcs.uoo.message.server.dao.TbOrgMapper;
import cn.ffcs.uoo.message.server.dao.TbSlaveAcctMapper;
import cn.ffcs.uoo.message.server.dao.TbSystemOrgTreeMapper;
import cn.ffcs.uoo.message.server.service.SystemRuleService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void test(){
       /* System.out.println(JSON.toJSONString(tbSlaveAcctMapper.insertOrUpdateSalveAcct(37674L)));
        System.out.println(JSON.toJSONString(tbSlaveAcctMapper.insertOrUpdateAcct(47779L,1L)));
        System.out.println(JSON.toJSONString(tbOrgMapper.getOrgVo(8077L,1L,1L)));*/

        System.out.println(tbOrgMapper.selectById(8077L));
    }
}
