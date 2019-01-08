package cn.ffcs.uoo.system.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.entity.SysPositiontRoleRef;
import cn.ffcs.uoo.system.service.ISysPositiontRoleRefService;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.service.SysRoleService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@RestController
@RequestMapping("/sysPosition")
public class SysPositionController {

    @Autowired
    private SysPositionService sysPositionService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private ISysPositiontRoleRefService iSysPositiontRoleRefService;

    @ApiOperation(value = "查询职位信息", notes = "查询职位信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位信息", key = "getPositionTree")
    @RequestMapping(value = "/getPositionTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getPositionTree(String id,boolean isSync,
                                                            Long userId, String accout){
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();

        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        if(isSync){
            treeNodeVos = sysPositionService.selectPositionTree();
        }else{
            treeNodeVos = sysPositionService.selectPositionTree(id);
        }


        ret.setMessage("查询成功");
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(treeNodeVos);
        return ret;
    }

    @ApiOperation(value = "查询职位下级", notes = "查询职位下级")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位下级", key = "getPositionRelPage")
    @RequestMapping(value = "/getPositionRelPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<SysPositionVo>> getPositionRelPage(String positionId,
                                                                  String search,
                                                                  Integer pageSize,
                                                                  Integer pageNo,
                                                                  String isSearchlower,
                                                                  Long userId,
                                                                  String accout){
        ResponseResult<Page<SysPositionVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(positionId)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("职位标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(isSearchlower)){
            isSearchlower = "0";
        }
        SysPositionVo vo = new SysPositionVo();
        if(!StrUtil.isNullOrEmpty(pageNo)){
            vo.setPageNo(pageNo);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            vo.setPageSize(pageSize);
        }
        vo.setIsSearchlower(isSearchlower);
        vo.setPositionId(new Long(positionId));
        if(!StrUtil.isNullOrEmpty(search)){
            vo.setSearch(search);
        }
        Page<SysPositionVo> page = sysPositionService.getPositionRelPage(vo);
        ret.setMessage("查询成功");
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(page);
        return ret;
    }

    @ApiOperation(value = "查询职位角色", notes = "查询职位角色")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位角色", key = "getRolesByPositionId")
    @RequestMapping(value = "/getRolesByPositionId", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<HashMap<String,String>> getRolesByPositionId(String positionId){
        ResponseResult<HashMap<String,String>> ret = new ResponseResult<HashMap<String,String>>();
        if(StrUtil.isNullOrEmpty(positionId)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("职位标识不能为空");
        }
        String roleNames = sysPositionService.getRolesByPositionId(positionId);
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("ROLE_NAME",roleNames);
        ret.setData(map);
        return ret;
    }


    @ApiOperation(value = "查询职位", notes = "查询职位")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位", key = "getPosition")
    @RequestMapping(value = "/getPosition", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<SysPositionVo> getPosition(String id){
        ResponseResult<SysPositionVo> ret = new ResponseResult<SysPositionVo>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("职位标识不能为空");
            return ret;
        }
        SysPositionVo vo = sysPositionService.getPosition(id);
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(vo);
        return ret;
    }


    @ApiOperation(value = "编辑职位", notes = "编辑职位")
    @ApiImplicitParams({
    })
    @UooLog(value = "编辑职位", key = "updatePosition")
    @RequestMapping(value = "/updatePosition", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> updatePosition(@RequestBody SysPositionVo sysPositionVo){
        ResponseResult<String> ret = new ResponseResult<String>();

        Wrapper positionWrapper = Condition.create()
                .eq("POSITION_ID",sysPositionVo.getpPositionId())
                .eq("STATUS_CD","1000");
        SysPosition sysPosition = sysPositionService.selectOne(positionWrapper);
        if(sysPosition==null){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("职位不存在");
            return ret;
        }
        sysPosition.setStatusCd(sysPositionVo.getStatusCd());
        if(!StrUtil.isNullOrEmpty(sysPositionVo.getRegionNbr())) {
            sysPosition.setRegionNbr(sysPositionVo.getRegionNbr());
        }
        if(!StrUtil.isNullOrEmpty(sysPositionVo.getpPositionId())) {
            sysPosition.setpPositionId(sysPositionVo.getpPositionId());
        }
        sysPosition.setNotes(sysPositionVo.getNotes());
        if(!StrUtil.isNullOrEmpty(sysPositionVo.getSortNum())){
            sysPosition.setSortNum(sysPositionVo.getSortNum());
        }
        sysPosition.setPositionName(sysPositionVo.getPositionName());
        sysPosition.setPositionCode(sysPositionVo.getPositionCode());
        sysPosition.setUpdateUser(sysPositionVo.getUserId());
        List<SysRoleDTO> sysRoleDTOList = sysPositionVo.getSysRoleDTOList();
        Wrapper positionRolesWrapper = Condition.create()
                .eq("POSITION_CODE",sysPositionVo.getPositionCode())
                .eq("STATUS_CD","1000");
        List<SysPositiontRoleRef> curPosRoleList = iSysPositiontRoleRefService.selectList(positionRolesWrapper);
        boolean isExists = false;

        //职位角色
        if(sysRoleDTOList!=null && sysRoleDTOList.size()>0){
            for(SysRoleDTO ot : sysRoleDTOList){
                for(SysPositiontRoleRef otf : curPosRoleList){
                    if(ot.getRoleCode().equals(otf.getRoleCode())){
                        isExists = true;
                        break;
                    }else{
                        isExists = false;
                    }
                }
                if(!isExists){
                    Long posRoleRelId = iSysPositiontRoleRefService.getId();
                    SysPositiontRoleRef sysPositiontRoleRef = new SysPositiontRoleRef();
                    sysPositiontRoleRef.setPositiontRoleRefId(posRoleRelId);
                    sysPositiontRoleRef.setRoleCode(ot.getRoleCode());
                    sysPositiontRoleRef.setPositionCode(sysPositionVo.getPositionCode());
                    sysPositiontRoleRef.setCreateUser(sysPositionVo.getUserId());
                    sysPositiontRoleRef.setNotes(sysPositionVo.getNotes());
                    iSysPositiontRoleRefService.add(sysPositiontRoleRef);
                }
            }
            isExists = false;
            for(SysPositiontRoleRef otf : curPosRoleList){
                for(SysRoleDTO ot : sysRoleDTOList){
                    isExists = false;
                    if(ot.getRoleCode().equals(otf.getRoleCode())){
                        isExists = true;
                        break;
                    }else{
                        isExists = false;
                    }
                }
                if(!isExists){
                    otf.setUpdateUser(sysPositionVo.getUserId());
                    iSysPositiontRoleRefService.delete(otf);
                }
            }
        }else{
            if(curPosRoleList!=null && curPosRoleList.size()>0){
                for(SysPositiontRoleRef otf : curPosRoleList){
                    otf.setUpdateUser(sysPositionVo.getUserId());
                    iSysPositiontRoleRefService.delete(otf);
                }
            }
        }
        sysPositionService.update(sysPosition);

        ret.setState(ResponseResult.STATE_OK);
        ret.setData("成功");
        return ret;
    }


    @ApiOperation(value = "新增职位", notes = "新增职位")
    @ApiImplicitParams({
    })
    @UooLog(value = "新增职位", key = "addPosition")
    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<TreeNodeVo> addPosition(@RequestBody SysPositionVo pos){
        ResponseResult<TreeNodeVo> ret = new ResponseResult<TreeNodeVo>();
        Long positionId = sysPositionService.getId();
        SysPosition sysPosition = new SysPosition();
        sysPosition.setPositionCode(pos.getPositionCode());
        sysPosition.setPositionName(pos.getPositionName());
        sysPosition.setPositionId(positionId);
        sysPosition.setSortNum(pos.getSortNum());
        sysPosition.setStatusCd("1000");
        sysPosition.setNotes(pos.getNotes());
        sysPosition.setpPositionId(pos.getpPositionId());
        sysPosition.setRegionNbr(pos.getRegionNbr());
        sysPositionService.add(sysPosition);

        List<SysRoleDTO> sysRoleDTOList = pos.getSysRoleDTOList();


        for(SysRoleDTO vo: sysRoleDTOList){
            Long posRoleRelId = iSysPositiontRoleRefService.getId();
            SysPositiontRoleRef sysPositiontRoleRef = new SysPositiontRoleRef();
            sysPositiontRoleRef.setPositiontRoleRefId(posRoleRelId);
            sysPositiontRoleRef.setRoleCode(vo.getRoleCode());
            sysPositiontRoleRef.setPositionCode(pos.getPositionCode());
            sysPositiontRoleRef.setCreateUser(pos.getUserId());
            sysPositiontRoleRef.setNotes(vo.getNotes());
            iSysPositiontRoleRefService.add(sysPositiontRoleRef);
        }
        TreeNodeVo vo = new TreeNodeVo();
        vo.setId(positionId.toString());
        vo.setPid(pos.getpPositionId().toString());
        vo.setName(pos.getPositionName());
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(vo);
        return ret;
    }

    @ApiOperation(value = "删除职位", notes = "删除职位")
    @ApiImplicitParams({
    })
    @UooLog(value = "删除职位", key = "deletePosition")
    @RequestMapping(value = "/deletePosition", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> deletePosition(@RequestBody SysPositionVo pos){
        ResponseResult<String> ret = new ResponseResult<String>();
        if(StrUtil.isNullOrEmpty(pos.getPositionId())){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("职位标识不能为空");
            return ret;
        }
        Wrapper positionRolesWrapper = Condition.create()
                .eq("POSITION_ID",pos.getPositionId())
                .eq("STATUS_CD","1000");
        SysPosition vo = sysPositionService.selectOne(positionRolesWrapper);
        if(vo!=null){
            int num = 0;
            num = sysPositionService.getPositionUserRefCount(vo.getPositionCode());
            if(num>0){
                ret.setState(ResponseResult.STATE_ERROR);
                ret.setMessage("职位人员关系存在，不能删除");
                return ret;
            }
            num = sysPositionService.getPositionDepRefCount(vo.getPositionCode());
            if(num>0){
                ret.setState(ResponseResult.STATE_ERROR);
                ret.setMessage("职位组织关系存在，不能删除");
                return ret;
            }
            num = sysPositionService.getPositionRoleRefCount(vo.getPositionCode());
            if(num>0){
                ret.setState(ResponseResult.STATE_ERROR);
                ret.setMessage("职位角色关系存在，不能删除");
                return ret;
            }
        }
        sysPositionService.delete(vo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }


}

