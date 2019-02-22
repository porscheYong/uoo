package cn.ffcs.uoo.system.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.entity.SysDeptPositionRef;
import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.service.ModifyHistoryService;
import cn.ffcs.uoo.system.service.SysDeptPositionRefService;
import cn.ffcs.uoo.system.service.SysOrganizationService;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.*;
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
    @Autowired
    private SysPositionService sysPositionService;
    @Autowired
    private ModifyHistoryService modifyHistoryService;


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
                                                                 String search,
                                                                 Integer pageSize,
                                                                 Integer pageNo,
                                                                 String isSearchlower,
                                                                 Long userId, String accout) throws IOException {
        ResponseResult<Page<SysOrganizationVo>> ret = new ResponseResult<Page<SysOrganizationVo>>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }

        SysOrganizationVo vo = new SysOrganizationVo();
        if(StrUtil.isNullOrEmpty(isSearchlower)){
            vo.setIsSearchlower("0");
        }else{
            vo.setIsSearchlower(isSearchlower);
        }
        vo.setOrgCode(id);
        if(!StrUtil.isNullOrEmpty(pageNo)){
            vo.setPageNo(pageNo);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            vo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(search)){
            vo.setSearch(search);
        }
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
    public ResponseResult<TreeNodeVo> addOrg(@RequestBody SysOrganizationVo vo) throws IOException {
        ResponseResult<TreeNodeVo> ret = new ResponseResult<TreeNodeVo>();

        if(StrUtil.isNullOrEmpty(vo.getOrgName())){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织名称不能为空");
            return ret;
        }
        Wrapper sysOrgWrapper = Condition.create()
                .eq("ORG_NAME",vo.getOrgName())
                .eq("STATUS_CD","1000");
        int num = sysOrganizationService.selectCount(sysOrgWrapper);
        if(num>0){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织名称重复");
            return ret;
        }

        String batchNum = modifyHistoryService.getBatchNumber();
        SysOrganization sysvo = new SysOrganization();
        BeanUtils.copyProperties(vo,sysvo);
        Long id = sysOrganizationService.getId();
        sysvo.setOrgId(id);
        sysvo.setOrgCode(id.toString());
        sysvo.setCreateUser(vo.getUserId());
        sysvo.setBatchNumber(batchNum);
        sysOrganizationService.add(sysvo);
        List<SysPositionVo> vos = vo.getSysPositionVos();
        if(vos!=null && vos.size()>0){
            for(SysPositionVo sysvo1 : vos){
                Long orgPosRelId = sysDeptPositionRefService.getId();
                SysDeptPositionRef sysDeptPositionRef = new SysDeptPositionRef();
                sysDeptPositionRef.setDeptPositionRefId(orgPosRelId);
                sysDeptPositionRef.setOrgCode(id.toString());
                sysDeptPositionRef.setPositionCode(sysvo1.getPositionCode());
                sysDeptPositionRef.setCreateUser(vo.getUserId());
                sysDeptPositionRef.setBatchNumber(batchNum);
                sysDeptPositionRefService.add(sysDeptPositionRef);
            }
        }
        TreeNodeVo vo1 = new TreeNodeVo();
        vo1.setId(sysvo.getOrgCode());
        vo1.setPid(sysvo.getParentOrgCode());
        vo1.setName(sysvo.getOrgName());
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(vo1);
        return ret;
    }


    @ApiOperation(value = "编辑组织", notes = "编辑组织")
    @ApiImplicitParams({
    })
    @UooLog(value = "编辑组织", key = "updateOrg")
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public ResponseResult<String> updateOrg(@RequestBody SysOrganizationVo vo) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        if(StrUtil.isNullOrEmpty(vo.getOrgId())){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(vo.getOrgCode())){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织编码不能为空");
            return ret;
        }
        if(vo.getParentOrgCode().equals(vo.getOrgCode())){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("父节点不能等于子节点");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(vo.getOrgName())){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织名称不能为空");
            return ret;
        }
        Wrapper sysOrgWrapper = Condition.create()
                .eq("ORG_NAME",vo.getOrgName())
                .eq("STATUS_CD","1000");
        int num = sysOrganizationService.selectCount(sysOrgWrapper);
        if(num>1){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织名称重复");
            return ret;
        }


        String batchNum = modifyHistoryService.getBatchNumber();
        SysOrganization sysvo = new SysOrganization();
        BeanUtils.copyProperties(vo,sysvo);
        sysvo.setUpdateUser(vo.getUserId());
        sysvo.setBatchNumber(batchNum);
        sysOrganizationService.update(sysvo);

        List<SysPositionVo> sysPositionList = vo.getSysPositionVos();
        List<SysPosition> sysPositions = new ArrayList<>();
        for(SysPositionVo vo1 : sysPositionList){
              Wrapper sysPositionWrapper = Condition.create()
                .eq("POSITION_CODE",vo1.getPositionCode())
                .eq("STATUS_CD","1000");
            SysPosition sp = sysPositionService.selectOne(sysPositionWrapper);
            sysPositions.add(sp);
        }
        List<SysDeptPositionRef> sysDeptPositionRefCur = sysDeptPositionRefService.getDeptPositionRelList(vo.getOrgCode());
        boolean isExists = false;
        if(sysPositions!=null && sysPositions.size()>0){
            for(SysPosition ot : sysPositions){
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
                    sysDeptPositionRef.setBatchNumber(batchNum);
                    sysDeptPositionRefService.add(sysDeptPositionRef);
                }
            }
            isExists = false;
            for(SysDeptPositionRef otf : sysDeptPositionRefCur){
                for(SysPosition ot : sysPositions){
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
                    otf.setBatchNumber(batchNum);
                    sysDeptPositionRefService.delete(otf);
                }
            }
        }else{
            if(sysDeptPositionRefCur!=null && sysDeptPositionRefCur.size()>0){
                for(SysDeptPositionRef otf : sysDeptPositionRefCur){
                    otf.setUpdateUser(vo.getUserId());
                    otf.setBatchNumber(batchNum);
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
    public ResponseResult<SysOrganizationVo> getOrg(String id,Long userId,String accout) throws IOException {
        ResponseResult<SysOrganizationVo> ret = new ResponseResult<SysOrganizationVo>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        Wrapper orgWrapper = Condition.create()
                .eq("ORG_CODE",id)
                .eq("STATUS_CD","1000");
        SysOrganization svo = sysOrganizationService.selectOne(orgWrapper);
        if(svo==null){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织不存在");
            return ret;
        }
        SysOrganizationVo vo = sysOrganizationService.getOrg(svo.getOrgCode());
        ret.setData(vo);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }


    @ApiOperation(value = "删除组织", notes = "删除组织")
    @ApiImplicitParams({
    })
    @UooLog(value = "删除组织", key = "deleteOrg")
    @RequestMapping(value = "/deleteOrg", method = RequestMethod.GET)
    public ResponseResult<String> deleteOrg(String id,Long userId,String accout) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织编码不能为空");
            return ret;
        }


        Wrapper orgWrapper = Condition.create()
                .eq("ORG_CODE",id)
                .eq("STATUS_CD","1000");
        SysOrganization sysOrganization = sysOrganizationService.selectOne(orgWrapper);
        if(sysOrganization==null){
            ret.setState(ResponseResult.STATE_OK);
            ret.setMessage("成功");
            return ret;
        }

        Wrapper orgRelWrapper = Condition.create()
                .eq("PARENT_ORG_CODE",id)
                .eq("STATUS_CD","1000");
        int orgRelNum = sysOrganizationService.selectCount(orgRelWrapper);
        if(orgRelNum>0){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("存在下级组织");
            return ret;
        }

        int num = sysOrganizationService.getOrgUserCount(sysOrganization.getOrgCode());
        if(num>0){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织下存在用户");
            return ret;
        }
//        num = sysOrganizationService.getOrgRoleCount(sysOrganization.getOrgCode());
//        if(num>0){
//            ret.setState(ResponseResult.STATE_ERROR);
//            ret.setMessage("组织下存在角色");
//            return ret;
//        }
        String batchNum = modifyHistoryService.getBatchNumber();
        sysOrganization.setUpdateUser(userId);
        sysOrganization.setBatchNumber(batchNum);
        sysOrganizationService.delete(sysOrganization);
        ret.setData("删除成功");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

    @ApiOperation(value = "查询组织职位", notes = "查询组织职位")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织职位", key = "getOrgPositionList")
    @RequestMapping(value = "/getOrgPositionList", method = RequestMethod.GET)
    public ResponseResult<List<SysPositionVo>> getOrgPositionList(String id,Long userId,String accout) throws IOException {
        ResponseResult<List<SysPositionVo>> ret = new ResponseResult<List<SysPositionVo>>();
        List<SysPositionVo> list = sysPositionService.getSysOrgPosition(id);
        ret.setData(list);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        return ret;
    }

    @ApiOperation(value = "查询组织人员", notes = "查询组织人员")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织人员", key = "getOrgUserPage")
    @RequestMapping(value = "/getOrgUserPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysUserVo>> getOrgUserPage(String id,
                                                                  String search,
                                                                  Integer pageSize,
                                                                  Integer pageNo,
                                                                  String isSearchlower,
                                                                  Long userId, String accout) throws IOException {
        ResponseResult<Page<SysUserVo>> ret = new ResponseResult<Page<SysUserVo>>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织编码不能为空");
            return ret;
        }
        Page<SysUserVo> page = sysPositionService.getOrgUserPage(id,search,pageSize,pageNo,isSearchlower);
        ret.setData(page);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        return ret;
    }


    @ApiOperation(value = "查询职位人员", notes = "查询职位人员")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位人员", key = "getPositionUserPage")
    @RequestMapping(value = "/getPositionUserPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysUserVo>> getPositionUserPage(String id,
                                                          String search,
                                                          Integer pageSize,
                                                          Integer pageNo,
                                                          String isSearchlower,
                                                          Long userId, String accout) throws IOException {
        ResponseResult<Page<SysUserVo>> ret = new ResponseResult<Page<SysUserVo>>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("职位编码不能为空");
            return ret;
        }
        Page<SysUserVo> page = sysPositionService.getPositionUserPage(id,search,pageSize,pageNo,isSearchlower);
        ret.setData(page);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        return ret;
    }

}

