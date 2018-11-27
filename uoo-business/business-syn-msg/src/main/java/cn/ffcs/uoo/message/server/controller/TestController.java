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
    public void test(){
        tbOrgMapper.getOrgVo(80000000009L,1L,1L);
    }
}
