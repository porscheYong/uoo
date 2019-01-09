package cn.ffcs.uoo.system.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.service.ISysFileService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysFileVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/sysFile")
public class SysFileController {

    @Autowired
    private ISysFileService iSysFileService;

    @ApiOperation(value = "查询文件信息-web", notes = "查询文件信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询文件信息", key = "getSysFilePage")
    @RequestMapping(value = "/getSysFilePage", method = RequestMethod.GET)
    public ResponseResult<Page<SysFileVo>> getSysFilePage(String search,Integer pageSize,Integer pageNo,
                                                          Long userId, String accout){
        ResponseResult<Page<SysFileVo>> ret = new ResponseResult<>();
        Page<SysFileVo> page = iSysFileService.getSysFilePage(search,pageSize,pageNo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("系统文件查询成功");
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "查询文件信息-web", notes = "查询文件信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询文件信息", key = "getSysFile")
    @RequestMapping(value = "/getSysFile", method = RequestMethod.GET)
    public ResponseResult<SysFileVo> getSysFile(String id,Long userId, String accout){
        ResponseResult<SysFileVo> ret = new ResponseResult<>();
        Wrapper sysFileWrapper = Condition.create()
                .eq("FILE_ID",id)
                .eq("STATUS_CD","1000");
        SysFile sysFile = iSysFileService.selectOne(sysFileWrapper);
        SysFileVo vo = new SysFileVo();
        BeanUtils.copyProperties(sysFile, vo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("系统文件查询成功");
        ret.setData(vo);
        return ret;
    }

    @ApiOperation(value = "新增系统文件", notes = "新增系统文件")
    @ApiImplicitParams({
    })
    @UooLog(value = "新增系统文件", key = "addSysFile")
    @RequestMapping(value = "/addSysFile", method = RequestMethod.POST)
    public ResponseResult<String> addSysFile(@RequestBody SysFileVo sysFileVo){
        ResponseResult<String> ret = new ResponseResult<>();

        SysFile sysFile = new SysFile();
        BeanUtils.copyProperties(sysFileVo, sysFile);
        Long id = iSysFileService.getId();
        sysFile.setFileId(id);
        iSysFileService.add(sysFile);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("新增系统文件成功");
        return ret;
    }

    @ApiOperation(value = "更新系统文件", notes = "更新系统文件")
    @ApiImplicitParams({
    })
    @UooLog(value = "更新系统文件", key = "updateSysFile")
    @RequestMapping(value = "/updateSysFile", method = RequestMethod.POST)
    public ResponseResult<String> updateSysFile(@RequestBody SysFileVo sysFileVo){
        ResponseResult<String> ret = new ResponseResult<>();
        Wrapper sysFileWrapper = Condition.create()
                .eq("FILE_ID",sysFileVo.getFileId())
                .eq("STATUS_CD","1000");
        SysFile sysFile = iSysFileService.selectOne(sysFileWrapper);
        if(sysFile!=null){
            BeanUtils.copyProperties(sysFileVo, sysFile);
            iSysFileService.update(sysFile);
        }

        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("新增系统文件成功");
        return ret;
    }


    @ApiOperation(value = "删除系统文件", notes = "删除系统文件")
    @ApiImplicitParams({
    })
    @UooLog(value = "删除系统文件", key = "deleteSysFile")
    @RequestMapping(value = "/deleteSysFile", method = RequestMethod.POST)
    public ResponseResult<String> deleteSysFile(@RequestBody SysFileVo sysFileVo){
        ResponseResult<String> ret = new ResponseResult<>();
        Wrapper sysFileWrapper = Condition.create()
                .eq("FILE_ID",sysFileVo.getFileId())
                .eq("STATUS_CD","1000");
        SysFile sysFile = iSysFileService.selectOne(sysFileWrapper);
        if(sysFile!=null){
            iSysFileService.delete(sysFile);
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("删除系统文件成功");
        return ret;
    }
}

