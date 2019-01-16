package ${package.Controller};


import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@RestController
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
    @Autowired
    ${table.serviceName} ${table.serviceName?uncap_first};
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<${entity}> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(${table.serviceName?uncap_first}.selectById(id),"");
    }
    
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @UooLog(key="LIST_PAGE",value="LIST_PAGE")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<Page<${entity}>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<${entity}> wrapper = Condition.create().eq("STATUS_CD","1000").orderBy("UPDATE_DATE", false);
        Page<${entity}> page = ${table.serviceName?uncap_first}.selectPage(new Page<${entity}>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(page ,"");
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="add")
    @Transactional
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody ${entity} ${entity?uncap_first}){
        ${entity?uncap_first}.setCreateDate(new Date());
        ${entity?uncap_first}.set<#list table.fields as field><#if field.keyFlag><#if field.convert>${field.propertyName?cap_first}</#if></#if></#list>(${table.serviceName?uncap_first}.getId());
        ${entity?uncap_first}.setStatusCd("1000");
        ${entity?uncap_first}.setStatusDate(new Date());
        ${table.serviceName?uncap_first}.insert(${entity?uncap_first});
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="update")
    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody ${entity} ${entity?uncap_first}){
        ${entity?uncap_first}.setUpdateDate(new Date());
        ${table.serviceName?uncap_first}.updateById(${entity?uncap_first});
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @Transactional
    public ResponseResult<Void> delete(@RequestBody ${entity} ${entity?uncap_first}){
        ${entity?uncap_first}.setStatusCd("1100");
        ${entity?uncap_first}.setStatusDate(new Date());
        ${table.serviceName?uncap_first}.updateById(${entity?uncap_first});
        return ResponseResult.createSuccessResult("");
    }
}
</#if>
</#if>
