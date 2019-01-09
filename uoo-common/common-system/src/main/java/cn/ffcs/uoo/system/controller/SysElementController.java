package cn.ffcs.uoo.system.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysElement;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.service.ISysElementService;
import cn.ffcs.uoo.system.service.ISysPermissionElementRelService;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysElementVO;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/system/SysElement")
public class SysElementController {
    @Autowired
    SysMenuService menuSvc;
    @Autowired
    ISysElementService eleSvc;
    @Autowired
    ISysPermissionElementRelService permEleSvc;
    @ApiOperation(value = "分页查询元素", notes = "分页查询元素")
    @ApiImplicitParams({
    })
    @UooLog(key="listAll",value="分页查询元素")
    @SuppressWarnings("unchecked")
    @RequestMapping("/list")
    public ResponseResult<Page<SysElement>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<SysElement> wrapper = Condition.create().eq("STATUS_CD", StatusCD.VALID);
        Page<SysElement> page = eleSvc.selectPage(new Page<SysElement>(pageNo, pageSize), wrapper);
        ResponseResult<Page<SysElement>> rr = ResponseResult.createSuccessResult( "");
        rr.setData(page);
        return rr;
    }
    @ApiOperation(value = "查询账号下的元素", notes = "查询账号下的元素")
    @ApiImplicitParams({
    })
    @UooLog(key="getElementByAccout",value="查询账号下的元素")
    @RequestMapping("/getElementByAccout")
    public ResponseResult<List<SysElement>> getElementByAccout(String accout){
        List<SysElement> selectList = eleSvc.getElementByAccout(accout);
        return ResponseResult.createSuccessResult(selectList, "");
    }
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "get", notes = "get")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping("/get")
    public ResponseResult<SysElementVO> get(@RequestParam("id")Long id){
        SysElement obj = eleSvc.selectById(id);
        SysElementVO vo =new SysElementVO();
        BeanUtils.copyProperties(obj, vo);
        List<SysMenu> menus = menuSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("MENU_CODE", obj.getMenuCode()));
        if(menus!=null&&!menus.isEmpty()){
            vo.setMenuName(menus.get(0).getMenuName());
        }
        return ResponseResult.createSuccessResult(vo, "");
    }
    @ApiOperation(value = "新增", notes = "新增")
    @ApiImplicitParams({
    })
    @Transactional
    @UooLog(key="add",value="新增")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysElement ele){
        String code = ele.getElementCode();
        long size=eleSvc.selectCount(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("ELEMENT_CODE", code));
        if(size>0){
            return ResponseResult.createErrorResult("编码已存在");
        }
        if(StringUtils.isBlank(ele.getMenuCode())){
            return ResponseResult.createErrorResult("请选择菜单");
        }
        ele.setElementId(eleSvc.getId());
        ele.setCreateDate(new Date());
        eleSvc.insert(ele);
        return ResponseResult.createSuccessResult("新增成功");
    }
    @Transactional
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="更新")
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysElement ele){
        String funcCode = ele.getElementCode();
        SysElement one = eleSvc.selectById(ele.getElementId());
        if(one==null){
            return ResponseResult.createErrorResult("ID不存在");
        }
        List<SysElement> tmp = eleSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("ELEMENT_CODE", funcCode));
        if(tmp!=null&&!tmp.isEmpty()){
            if(tmp.size()>1){
                return ResponseResult.createErrorResult("编码已存在");
            }else{
                SysElement obj = tmp.get(0);
                if(!obj.getElementId().equals(ele.getElementId())){
                    return ResponseResult.createErrorResult("编码已存在");
                }
            }
        }
        if(StringUtils.isBlank(ele.getMenuCode())){
            return ResponseResult.createErrorResult("请选择菜单");
        }
        if(!ele.getElementCode().equals(one.getElementCode())){
            permEleSvc.updateForSet("ELEMENT_CODE='"+funcCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("ELEMENT_CODE", one.getElementCode()));
        }
        ele.setUpdateDate(new Date());
        eleSvc.updateById(ele);
        return ResponseResult.createSuccessResult("修改成功");
    }
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
    })
    @Transactional
    @UooLog(key="delete",value="删除")
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public ResponseResult<Void> delete(@RequestBody SysElement fun){
        SysElement df=new SysElement();
        df.setElementId(fun.getElementId());
        df.setStatusCd(StatusCD.INVALID);
        df.setStatusDate(new Date());
        eleSvc.updateById(df);
        return ResponseResult.createSuccessResult("修改成功");
    }
}
