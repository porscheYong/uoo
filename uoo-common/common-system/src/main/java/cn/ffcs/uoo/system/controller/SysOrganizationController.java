package cn.ffcs.uoo.system.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.entity.SysDeptPositionRef;
import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.service.SysDeptPositionRefService;
import cn.ffcs.uoo.system.service.SysOrganizationService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@RestController
@RequestMapping("/sysOrganization")
public class SysOrganizationController {

    @Autowired
    private SysOrganizationService sysOrganizationService;
    @Autowired
    private SysDeptPositionRefService sysDeptPositionRefService;

    @ApiOperation(value = "查询组织树信息-web", notes = "查询组织树信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织树", key = "getOrgRelTree")
    @RequestMapping(value = "/getOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(String id,boolean isSync,
                                                          Long userId, String accout) throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        String orgParams = "";
        String orgOrgTypeParams = "";

        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        if(isSync){
            treeNodeVos = sysOrganizationService.selectOrgTree();
        }else{
            treeNodeVos = sysOrganizationService.selectOrgTree(id);
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("组织树查询成功");
        ret.setData(treeNodeVos);
        return ret;
    }


    @ApiOperation(value = "检索组织信息-web", notes = "检索组织信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "检索组织信息", key = "getFuzzyOrgRelPage")
    @RequestMapping(value = "/getFuzzyOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysOrganizationVo>> getFuzzyOrgRelPage(String search,
                                                                      Integer pageSize,
                                                                      Integer pageNo,
                                                                      Long userId, String accout) throws IOException {
        ResponseResult<Page<SysOrganizationVo>> ret = new ResponseResult<Page<SysOrganizationVo>>();
        SysOrganizationVo vo = new SysOrganizationVo();
        if(!StrUtil.isNullOrEmpty(pageNo)){
            vo.setPageNo(pageNo);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            vo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(search)){
            vo.setSearch(search);
        }
        Page<SysOrganizationVo> page = sysOrganizationService.selectFuzzyOrgRelPage(vo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "获取组织信息", notes = "获取组织信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "获取组织信息", key = "getRestructOrgRelTree")
    @RequestMapping(value = "/getRestructOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(String id,
                                                                      Long userId, String accout) throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<List<TreeNodeVo>>();
        SysOrganizationVo vo = new SysOrganizationVo();
        vo.setOrgCode(id);
        List<TreeNodeVo> list = sysOrganizationService.getRestructOrgRelTree(vo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(list);
        return ret;
    }

    @ApiOperation(value = "获取组织关系翻页信息", notes = "获取组织关系翻页信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "获取组织关系翻页信息", key = "getOrgRelPage")
    @RequestMapping(value = "/getOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysOrganizationVo>> getOrgRelPage(String id,
                                                                  Long userId, String accout) throws IOException {
        ResponseResult<Page<SysOrganizationVo>> ret = new ResponseResult<Page<SysOrganizationVo>>();
        SysOrganizationVo vo = new SysOrganizationVo();
        vo.setOrgCode(id);
        Page<SysOrganizationVo> page = sysOrganizationService.getOrgRelPage(vo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "新增组织", notes = "新增组织")
    @ApiImplicitParams({
    })
    @UooLog(value = "新增组织", key = "addOrg")
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    public ResponseResult<String> addOrg(SysOrganizationVo vo) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        SysOrganization sysvo = new SysOrganization();
        BeanUtils.copyProperties(vo,sysvo);
        Long id = sysOrganizationService.getId();
        sysvo.setOrgId(id);
        sysvo.setCreateUser(vo.getUserId());
        sysOrganizationService.add(sysvo);
        List<SysPositionVo> vos = vo.getSysPositionVos();
        if(vos!=null && vos.size()>0){
            for(SysPositionVo sysvo1 : vos){
                Long orgPosRelId = sysDeptPositionRefService.getId();
                SysDeptPositionRef sysDeptPositionRef = new SysDeptPositionRef();
                sysDeptPositionRef.setDeptPositionRefId(orgPosRelId);
                sysDeptPositionRef.setOrgCode(vo.getOrgCode());
                sysDeptPositionRef.setPositionCode(sysvo1.getPositionCode());
                sysDeptPositionRef.setCreateUser(vo.getUserId());
                sysDeptPositionRefService.add(sysDeptPositionRef);
            }
        }

        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }


    @ApiOperation(value = "编辑组织", notes = "编辑组织")
    @ApiImplicitParams({
    })
    @UooLog(value = "编辑组织", key = "addOrg")
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public ResponseResult<String> updateOrg(SysOrganizationVo vo) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        SysOrganization sysvo = new SysOrganization();
        BeanUtils.copyProperties(vo,sysvo);
        sysOrganizationService.update(sysvo);


        List<SysPositionVo> sysPositionList = vo.getSysPositionVos();
        Wrapper orgPosWrapper = Condition.create()
                .eq("ORG_CODE",vo.getOrgCode())
                .eq("STATUS_CD","1000");
        List<SysDeptPositionRef> sysDeptPositionRefCur = sysDeptPositionRefService.selectList(orgPosWrapper);
        boolean isExists = false;
        if(sysPositionList!=null && sysPositionList.size()>0){
            for(SysPositionVo ot : sysPositionList){
                for(SysDeptPositionRef otf : sysDeptPositionRefCur){
                    if(ot.getPositionCode().equals(otf.getPositionCode())){
                        isExists = true;
                        break;
                    }else{
                        isExists = false;
                    }
                }
                if(!isExists){
                    Long orgPosRelId = sysDeptPositionRefService.getId();
                    SysDeptPositionRef sysDeptPositionRef = new SysDeptPositionRef();
                    sysDeptPositionRef.setDeptPositionRefId(orgPosRelId);
                    sysDeptPositionRef.setOrgCode(vo.getOrgCode());
                    sysDeptPositionRef.setPositionCode(ot.getPositionCode());
                    sysDeptPositionRef.setCreateUser(vo.getUserId());
                    sysDeptPositionRefService.add(sysDeptPositionRef);
                }
            }
            isExists = false;
            for(SysDeptPositionRef otf : sysDeptPositionRefCur){
                for(SysPositionVo ot : sysPositionList){
                    isExists = false;
                    if(ot.getPositionCode().equals(otf.getPositionCode())){
                        isExists = true;
                        break;
                    }else{
                        isExists = false;
                    }
                }
                if(!isExists){
                    otf.setUpdateUser(vo.getUserId());
                    sysDeptPositionRefService.delete(otf);
                }
            }
        }else{
            if(sysDeptPositionRefCur!=null && sysDeptPositionRefCur.size()>0){
                for(SysDeptPositionRef otf : sysDeptPositionRefCur){
                    otf.setUpdateUser(vo.getUserId());
                    sysDeptPositionRefService.delete(otf);
                }
            }
        }
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }


    @ApiOperation(value = "查询组织", notes = "查询组织")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织", key = "getOrg")
    @RequestMapping(value = "/getOrg", method = RequestMethod.GET)
    public ResponseResult<SysOrganizationVo> getOrg(String id) throws IOException {
        ResponseResult<SysOrganizationVo> ret = new ResponseResult<SysOrganizationVo>();
        sysOrganizationService.getOrg(id);

        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }



}

