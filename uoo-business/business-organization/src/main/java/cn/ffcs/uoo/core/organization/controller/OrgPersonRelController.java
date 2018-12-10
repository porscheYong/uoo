package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgPersonRel;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.entity.OrgtreeOrgpersonRel;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@RestController
@RequestMapping("/orgPersonRel")
public class OrgPersonRelController extends BaseController {

    @Autowired
    private OrgPersonRelService orgPersonRelService;

    @Autowired
    private OrgtreeOrgpersonRelService orgtreeOrgpersonRelService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRelService orgRelService;

    @Autowired
    private OrgTreeService orgTreeService;

    @Autowired
    private SolrService solrService;


    @ApiOperation(value = "新增组织人员关系-web" , notes = "新增组织人员")
    @ApiImplicitParams({
    })
    @UooLog(value = "新增组织人员关系", key = "addOrgPsn")
    @RequestMapping(value = "/addOrgPsn", method = RequestMethod.POST)
    public ResponseResult<String> addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgList){
        System.out.println(new Date());
        ResponseResult<String> ret = new ResponseResult<String>();
        if(psonOrgList!=null){
            for(PsonOrgVo psonOrgVo : psonOrgList){
                Wrapper orgTreeConfWrapper = Condition.create()
                        .eq("ORG_TREE_ID",psonOrgVo.getOrgTreeId())
                        .eq("STATUS_CD","1000");
                OrgTree orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
                if(orgtree==null){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("组织树不存在");
                    return ret;
                }

                OrgPersonRel orgPersonRel = orgPersonRelService.convertObj(psonOrgVo);
                Long orgPsndocRefId = orgPersonRelService.getId();
                orgPersonRel.setOrgPersonId(orgPsndocRefId);
                orgPersonRelService.add(orgPersonRel);

                Long orgTreePerId = orgtreeOrgpersonRelService.getId();
                OrgtreeOrgpersonRel orgtreeOrgpersonRel = new OrgtreeOrgpersonRel();
                orgtreeOrgpersonRel.setOrgTreeId(orgtree.getOrgTreeId());
                orgtreeOrgpersonRel.setOrgPersonId(orgPsndocRefId);
                orgtreeOrgpersonRel.setOrgtreeOrgpersonId(orgTreePerId);
                orgtreeOrgpersonRel.setStatusCd("1000");
                orgtreeOrgpersonRelService.add(orgtreeOrgpersonRel);



//            SolrInputDocument input = new SolrInputDocument();
//            input.addField("psnName", psonOrgVo.getPsnName());
//            input.addField("certNo", psonOrgVo.getCertNo());
//            input.addField("orgRootId", psonOrgVo.getOrgRootId());
//            input.addField("userId", psonOrgVo.getUserId());
//            input.addField("id", orgPsndocRefId);
//            String sysfullName = orgService.getSysFullName(orgtree.getOrgId(),orgPersonRel.getOrgId().toString());
//            input.addField("psnFullName", sysfullName);
//            input.addField("acct", psonOrgVo.getAcct());
//            input.addField("mobile", psonOrgVo.getMobile());
//            solrService.addDataIntoSolr("pson",input);
            }
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }

    @ApiOperation(value = "修改组织人员-web", notes = "修改组织人员")
    @ApiImplicitParams({
             })
    @UooLog(value = "修改组织人员", key = "updateOrgPsn")
    @RequestMapping(value = "/updateOrgPsn", method = RequestMethod.POST)
    public ResponseResult<String> updateOrgPsn(@RequestBody PsonOrgVo psonOrgVo){
        System.out.println(new Date());
        ResponseResult<String> ret = new ResponseResult<String>();
        Long personnelId = psonOrgVo.getPersonnelId();
        if(StrUtil.isNullOrEmpty(personnelId)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织人员标识不能为空");
            return ret;
        }
        Wrapper orgTreeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",psonOrgVo.getOrgTreeId())
                .eq("STATUS_CD","1000");
        OrgTree orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgtree==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        List<OrgPersonRel> orgPersonRelList = orgPersonRelService.getOrgPsnByOrgAndPsnId(
                                                    psonOrgVo.getOrgTreeId().toString(),
                                                    personnelId.toString(),
                                                    psonOrgVo.getOrgId().toString());
        if(orgPersonRelList!=null && orgPersonRelList.size()>0){
            OrgPersonRel orgPersonRel = orgPersonRelList.get(0);
            orgPersonRel.setDoubleName(StrUtil.strnull(psonOrgVo.getDoubleName()));
            orgPersonRel.setProperty(StrUtil.strnull(psonOrgVo.getProperty()));
            if(!StrUtil.isNullOrEmpty(psonOrgVo.getPostId())){
                orgPersonRel.setPostId(new Long(psonOrgVo.getPostId()));
            }
            if(!StrUtil.isNullOrEmpty(psonOrgVo.getSort())){
                orgPersonRel.setSort(new Double(psonOrgVo.getSort()));
            }
            orgPersonRelService.update(orgPersonRel);
        }
//        if(orgPersonRel == null){
//            ret.setState(ResponseResult.STATE_ERROR);
//            ret.setMessage("人员组织关系不存在");
//            return ret;
//        }
//        orgPersonRelService.delete(orgPersonRel);
        //solrService.deleteDataIntoSolr("pson",orgPersonRel.getOrgPersonId().toString());

//        Long orgPsndocRefIdnew = orgPersonRelService.getId();
//        OrgPersonRel orgPersonRelT = orgPersonRelService.convertObj(psonOrgVo);
//        orgPersonRelT.setOrgPersonId(orgPsndocRefIdnew);
//        orgPersonRelService.add(orgPersonRelT);


//        SolrInputDocument input = new SolrInputDocument();
//        input.addField("psnName", psonOrgVo.getPsnName());
//        input.addField("certNo", psonOrgVo.getCertNo());
//        input.addField("orgRootId", psonOrgVo.getOrgRootId());
//        input.addField("userId", psonOrgVo.getUserId());
//        input.addField("id", orgPsndocRefIdnew);
//        String sysfullName = orgService.getSysFullName(orgtree.getOrgId(),orgPersonRel.getOrgId().toString());
//        input.addField("psnFullName", sysfullName);
//        input.addField("acct", psonOrgVo.getAcct());
//        input.addField("mobile", psonOrgVo.getMobile());
//        solrService.addDataIntoSolr("pson",input);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("更新成功");
        return ret;
    }

    @ApiOperation(value = "删除组织人员关系", notes = "删除组织人员关系")
    @ApiImplicitParams({
              })
    @UooLog(value = "删除组织人员关系", key = "deleteOrgPsn")
    @RequestMapping(value = "/deleteOrgPsn", method = RequestMethod.POST)
    public ResponseResult<String> deleteOrgPsn(@RequestBody PsonOrgVo psonOrgVo){
        System.out.println(new Date());
        ResponseResult<String> ret = new ResponseResult<String>();

        Long orgPsndocRefId = psonOrgVo.getPersonnelId();
        if(StrUtil.isNullOrEmpty(orgPsndocRefId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织人员标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getOrgTreeId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getOrgId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
//        if(StrUtil.isNullOrEmpty(psonOrgVo.getOrgRootId())){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织树根节点不能为空");
//            return ret;
//        }
        Wrapper orgTreeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",psonOrgVo.getOrgTreeId())
                .eq("STATUS_CD","1000");
        OrgTree orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgtree==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        Wrapper orgPerConfWrapper = Condition.create()
                .eq("PERSONNEL_ID",orgPsndocRefId)
                .eq("ORG_ID",psonOrgVo.getOrgId())
                .eq("STATUS_CD","1000");
        OrgPersonRel orgPersonRel = orgPersonRelService.selectOne(orgPerConfWrapper);
        if(orgPersonRel != null){
            orgPersonRelService.delete(orgPersonRel);

            Wrapper orgTreePerConfWrapper = Condition.create()
                    .eq("ORG_PERSON_ID",orgPersonRel.getOrgPersonId())
                    .eq("STATUS_CD","1000")
                    .eq("ORG_TREE_ID",orgtree.getOrgTreeId());
            OrgtreeOrgpersonRel orgtreeOrgpersonRel = orgtreeOrgpersonRelService.selectOne(orgTreePerConfWrapper);
            if(orgtreeOrgpersonRel!=null){
                orgtreeOrgpersonRelService.delete(orgtreeOrgpersonRel);
            }
        }
//        solrService.deleteDataIntoSolr("pson",orgPersonRel.getOrgPersonId().toString());
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }


    @ApiOperation(value = "查询组织人员信息", notes = "查询组织人员信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织人员信息", key = "getOrgPsn")
    @RequestMapping(value = "/getOrgPsn", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<PsonOrgVo>> getFuzzyOrgPsnPage(PsonOrgVo psonOrgVo){
        ResponseResult<List<PsonOrgVo>> ret = new ResponseResult<List<PsonOrgVo>>();
        Page<PsonOrgVo> page = orgPersonRelService.selectFuzzyOrgPsnPage(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }





    @ApiOperation(value = "查询人员组织信息列表", notes = "查询人员组织信息列表")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询人员组织信息列表",key = "getPerOrgRelList")
    @RequestMapping(value = "/getPerOrgRelList",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<PsonOrgVo>> getPerOrgRelList(String orgTreeId,String personnelId){
        ResponseResult<List<PsonOrgVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(personnelId)){
            ret.setMessage("人员标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        PsonOrgVo psonOrgVo = new PsonOrgVo();
        psonOrgVo.setPersonnelId(new Long(personnelId));
        if(!StrUtil.isNullOrEmpty(orgTreeId)){
            psonOrgVo.setOrgTreeId(new Long(orgTreeId));
        }
        List<PsonOrgVo> psonList = orgPersonRelService.getPerOrgRelList(psonOrgVo);
        if(psonList==null || psonList.size()<0){
            ret.setMessage("人员组织关系不存在");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(psonList);
        return ret;
    }


    @ApiOperation(value = "查询人员组织信息翻页-web", notes = "查询人员组织信息翻页")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询人员组织信息翻页",key = "getPerOrgRelPage")
    @RequestMapping(value = "/getPerOrgRelPage",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(String orgId,
                                                            String orgTreeId,
                                                            String refCode,
                                                            String orgRootId,
                                                            String personnelId,
                                                            String isSearchlower,
                                                            String search,
                                                            String sortField,
                                                            String sortOrder,
                                                            Integer pageSize,
                                                            Integer pageNo
                                                            ){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<>();

        if(!StrUtil.isNullOrEmpty(sortField)){
            if(StrUtil.isNullOrEmpty(sortOrder)){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("排序方式不能为空");
                return ret;
            }
            if(!"DESC".equals(sortOrder.toUpperCase()) && !"ASC".equals(sortOrder.toUpperCase())){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("排序参数不对");
                return ret;
            }
        }

        if(!StrUtil.isNullOrEmpty(sortOrder)){
            if(StrUtil.isNullOrEmpty(sortField)){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("排序字段不能为空");
                return ret;
            }
        }

        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setMessage("组织标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
//        if(StrUtil.isNullOrEmpty(orgRootId)){
//            ret.setMessage("组织树根节点不能为空");
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            return ret;
//        }
        if(StrUtil.isNullOrEmpty(orgTreeId) && StrUtil.isNullOrEmpty(refCode)){
            ret.setMessage("组织树标识和组织树关系编码不能同时为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }



        OrgTree orgtree = null;
        if(!StrUtil.isNullOrEmpty(refCode)){
            orgtree = orgTreeService.getOrgTreeByRefCode(refCode);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }else {
            //获取组织树
            Wrapper orgTreeConfWrapper = Condition.create()
                    .eq("ORG_TREE_ID", orgTreeId)
                    .eq("STATUS_CD", "1000");
            orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }
        PsonOrgVo psonOrgVo = new PsonOrgVo();
        psonOrgVo.setIsSearchlower(StrUtil.isNullOrEmpty(isSearchlower)?"0":isSearchlower);
        psonOrgVo.setOrgId(new Long(orgId));
        //psonOrgVo.setOrgRootId(new Long(orgRootId));
        psonOrgVo.setOrgTreeId(orgtree.getOrgTreeId());
        psonOrgVo.setSortField(StrUtil.strnull(sortField));
        psonOrgVo.setSortOrder(StrUtil.strnull(sortOrder));
        if(!StrUtil.isNullOrEmpty(personnelId)){
            psonOrgVo.setPersonnelId(new Long(personnelId));
        }

        if(!StrUtil.isNullOrEmpty(search)){
            psonOrgVo.setSearch(search);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            psonOrgVo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(pageNo)){
            psonOrgVo.setPageNo(pageNo);
        }
        Page<PsonOrgVo> page = orgPersonRelService.selectPerOrgRelPage(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "查询人员-web", notes = "查询人员")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询人员",key = "getPerOrOrgRelPage")
    @RequestMapping(value = "/getPerOrOrgRelPage",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<PsonOrgVo>> getPerOrOrgRelPage(PsonOrgVo psonOrgVo){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<>();
        Page<PsonOrgVo> page = orgPersonRelService.selectPerOrgRelPage(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(page);
        return ret;
    }





    @ApiOperation(value = "查询用户-web", notes = "查询用户")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询用户",key = "getUserOrgRelPage")
    @RequestMapping(value = "/getUserOrgRelPage",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<PsonOrgVo>> getUserOrgRelPage(String orgId,
                                                             String orgTreeId,
                                                             String refCode,
                                                             String isSearchlower,
                                                             String search,
                                                             Integer pageSize,
                                                             Integer pageNo
                                                             ){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgTreeId) && StrUtil.isNullOrEmpty(refCode)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识和组织树关系类型不能同时为空");
            return ret;
        }
        OrgTree orgtree = null;
        if(!StrUtil.isNullOrEmpty(refCode)){
            orgtree = orgTreeService.getOrgTreeByRefCode(refCode);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }else {
            //获取组织树
            Wrapper orgTreeConfWrapper = Condition.create()
                    .eq("ORG_TREE_ID", orgTreeId)
                    .eq("STATUS_CD", "1000");
            orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }


        PsonOrgVo psonOrgVo = new PsonOrgVo();
        psonOrgVo.setIsSearchlower(StrUtil.isNullOrEmpty(isSearchlower)?"0":isSearchlower);
        psonOrgVo.setOrgId(new Long(orgId));
        psonOrgVo.setOrgTreeId(orgtree.getOrgTreeId());
        if(!StrUtil.isNullOrEmpty(search)){
            psonOrgVo.setSearch(search);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            psonOrgVo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(pageNo)){
            psonOrgVo.setPageNo(pageNo);
        }
        Page<PsonOrgVo> page = orgPersonRelService.selectUserOrgRelPage(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(page);
        return ret;
    }

    @ApiOperation(value = "查询用户归属信息-web", notes = "查询用户归属信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询用户归属信息",key = "getPerOrgAttribution")
    @RequestMapping(value = "/getPerOrgAttribution",method = RequestMethod.GET)
    public ResponseResult<Page<PsonOrgVo>> getPerOrgAttribution(String orgId,
                                                                String orgTreeId,
                                                                String personnelId,
                                                                Integer pageSize,
                                                                Integer pageNo){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<Page<PsonOrgVo>>();
        if(!StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(!StrUtil.isNullOrEmpty(personnelId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("人员标识不能为空");
            return ret;
        }
        if(!StrUtil.isNullOrEmpty(personnelId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("人员标识不能为空");
            return ret;
        }
        return ret;
    }

}

