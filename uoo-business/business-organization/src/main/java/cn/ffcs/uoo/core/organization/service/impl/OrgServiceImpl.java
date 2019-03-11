package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.dao.OrgMapper;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.util.OrgConstant;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.*;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.Today;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.stereotype.Service;
import sun.swing.StringUIClientPropertyKey;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Service("orgService")
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {


    @Autowired
    private OrgTypeService orgTypeService;

    @Autowired
    private OrgOrgtreeRelService orgOrgtreeRelService;

    @Autowired
    private OrgRelService orgRelService;

    @Autowired
    private OrgRelTypeService orgRelTypeService;

    @Autowired
    private ModifyHistoryService modifyHistoryService;

    @Autowired
    private OrgPersonRelService orgPersonRelService;

    @Autowired
    private OrgLevelService orgLevelService;

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private OrgTreeService orgTreeService;

    @Autowired
    private OgtOrgReltypeConfService ogtOrgReftypeConfService;

    @Autowired
    private OrgPositionRelService orgPositionRelService;

    @Autowired
    private OrgCertRelService orgCertRelService;

    @Autowired
    private OrgContactRelService orgContactRelService;

    @Autowired
    private IOrgTreeSynchRuleService iOrgTreeSynchRuleService;


    @Override
    public Long getId() {
        return baseMapper.getId();
    }


    @Override
    public void delete(Org org) {
        org.setStatusCd("1100");
        org.setStatusDate(new Date());
        org.setUpdateDate(new Date());
        org.setUpdateUser(StrUtil.isNullOrEmpty(org.getUpdateUser()) ? 0L : org.getUpdateUser());
        updateById(org);
    }

    @Override
    public void add(Org org) {
        org.setCreateDate(new Date());
        org.setCreateUser(StrUtil.isNullOrEmpty(org.getCreateUser()) ? 0L : org.getCreateUser());
        org.setStatusCd("1000");
        org.setStatusDate(new Date());
        insert(org);
    }


    @Override
    public void update(Org org) {
        org.setUpdateDate(new Date());
        org.setUpdateUser(StrUtil.isNullOrEmpty(org.getUpdateUser()) ? 0L : org.getUpdateUser());
        org.setStatusDate(new Date());
        updateById(org);
    }

    /**
     * 查询组织列表
     *
     * @param org
     * @return
     */
    @Override
    public List<Org> getOrgList(Org org) {
        return baseMapper.getOrgList(org);
    }

    @Override
    public String getGenerateOrgCode() {
        Long seq = baseMapper.getOrgCodeSeq();
        DecimalFormat decimalFormat = new DecimalFormat("000000000");
        String orgCodeEnd = decimalFormat.format(seq);
        return OrgConstant.ORG_CODE_N + orgCodeEnd;
    }

    /**
     * 查询组织关系分页
     *
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> selectOrgRelPage(OrgVo orgVo) {

        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo() == 0 ? 1 : orgVo.getPageNo(),
                orgVo.getPageSize() == 0 ? 10 : orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectOrgRelPage(page, orgVo);
        if(orgVolist!=null && orgVolist.size()>0){
            for(OrgVo vo : orgVolist){
                HashMap<String,String> ls = getChannelInfo(vo.getOrgId().toString());
                if(!StrUtil.isNullOrEmpty(ls) && !ls.isEmpty()){
                    vo.setIsChannel(ls.get("isChannel"));
                    vo.setChannelNBR(ls.get("channelNbr"));
                }
            }
        }
        page.setRecords(orgVolist);
        return page;
    }

    /**
     * 组织分页
     *
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> selectOrgPage(OrgVo orgVo) {
        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo() == 0 ? 1 : orgVo.getPageNo(),
                orgVo.getPageSize() == 0 ? 10 : orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectOrgPage(page, orgVo);
        if (orgVolist != null && orgVolist.size() > 0) {
            for (OrgVo vo : orgVolist) {
                List<OrgVo> orgListVo = baseMapper.getFullOrgList("1", vo.getOrgId().toString());
                String fullName = "";
                if (orgListVo != null && orgListVo.size() > 0) {
                    for (OrgVo vo1 : orgListVo) {
                        fullName += vo1.getOrgName() + "->";
                    }
                }
                if (!StrUtil.isNullOrEmpty(fullName)) {
                    vo.setFullName(fullName.substring(0, fullName.length() - 2));
                }
                String trees = baseMapper.getAppOrgTrees(vo.getOrgId().toString());
                vo.setOrgTreeInfos(trees);
//                if(!StrUtil.isNullOrEmpty(orgVo.getOrgTreeId())){
//                    com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeWrapper = Condition.create()
//                            .eq("ORG_TREE_ID",orgVo.getOrgTreeId())
//                            .eq("ORG_ID",vo.getOrgId())
//                            .eq("STATUS_CD","1000");
//                    List<OrgOrgtreeRel> orgOrgtreeList = orgOrgtreeRelService.selectList(orgOrgTreeWrapper);
//                    if(orgOrgtreeList!=null && orgOrgtreeList.size()>0){
//                        vo.setFlag(1);
//                    }
//                }

            }
        }
        page.setRecords(orgVolist);
        return page;
    }

    @Override
    public String JudgeOrgParams(OrgVo org) {
        if (StrUtil.isNullOrEmpty(org.getStatusCd())) {
            return "组织状态不能为空";
        }
        if (StrUtil.isNullOrEmpty(org.getOrgTreeId())) {
            return "组织树标识不能为空";
        }
        if (org.getOrgTypeList() == null || org.getOrgTypeList().size() < 0) {
            return "组织类别不能为空";
        }
        if (StrUtil.isNullOrEmpty(org.getAreaCodeId())) {
            return "组织区号不能为空";
        }
//        if(org.getPositionList() == null || org.getPositionList().size() <0){
//            return "组织岗位不能为空";
//        }
//        if(org.getPostList() == null || org.getPostList().size() <0){
//            return "组织职位不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getOrgRootId())){
//            return "组织树根节点不能为空";
//        }
        if (StrUtil.isNullOrEmpty(org.getOrgName())) {
            return "组织名称不能为空";
        }
//        if(StrUtil.isNullOrEmpty(org.getLocId())){
//            return "组织行政区域不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getSupOrgId())){
//            return "组织父节点不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getShortName())){
//            return "组织简称不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getCityTown())){
//            return "城乡属性不能为空";
//        }
        return "";
    }

//    @Override
//    public void insertByObj(OrgVo org){
//        baseMapper.insertByObj(org);
//    }


    @Override
    public String getSysFullName(String orgTreeId, String orgId) {
        return baseMapper.getSysFullName(orgTreeId, orgId);
    }

    @Override
    public OrgVo selectOrgByOrgId(String orgId, String orgTreeId) {
        return baseMapper.selectOrgByOrgId(orgId, orgTreeId);
    }

    @Override
    public List<PoliticalLocation> getOrgLoc(String orgId) {
        return baseMapper.getOrgLoc(orgId);
    }

    @Override
    public List<AreaCodeVo> getOrgAreaCode(String orgId) {
        return baseMapper.getOrgAreaCode(orgId);
    }


    /**
     * 获取组织名称全路
     *
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public String getFullOrgNameList(String orgTreeId, String orgId, String split) {
        List<OrgVo> voList = baseMapper.getFullOrgList(orgTreeId, orgId);
        String fullName = "";
        if (voList != null && voList.size() > 0) {
            for (int i = 0; i < voList.size(); i++) {
                fullName += voList.get(i).getOrgName();
                if (!StrUtil.isNullOrEmpty(split)) {
                    fullName += split;
                }
            }
            if (!StrUtil.isNullOrEmpty(fullName) && !StrUtil.isNullOrEmpty(split)) {
                fullName = fullName.substring(0, fullName.length() - split.length());
            }
        }
        return fullName;
    }


    /**
     * 获取组织标识名称全路径
     *
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public String getFullOrgIdList(String orgTreeId, String orgId, String split) {
        List<OrgVo> voList = baseMapper.getFullOrgList(orgTreeId, orgId);
        String fullNameId = "";
        if (voList != null && voList.size() > 0) {
            for (int i = 0; i < voList.size(); i++) {
                fullNameId += voList.get(i).getOrgId();
                if (!StrUtil.isNullOrEmpty(split)) {
                    fullNameId += split;
                }
            }
            if (!StrUtil.isNullOrEmpty(fullNameId) && !StrUtil.isNullOrEmpty(split)) {
                fullNameId = fullNameId.substring(0, fullNameId.length() - split.length());
            }
        }
        return fullNameId;
    }

    @Override
    public String JudgeUpdateOrgName(String orgId, String orgName, Long orgTreeId) {
        if (StrUtil.isNullOrEmpty(orgId)) {
            return "组织标识不能为空";
        }
        if (StrUtil.isNullOrEmpty(orgName)) {
            return "组织名称不能为空";
        }
        OrgVo org = selectOrgByOrgId(orgId, orgTreeId.toString());
        if (StrUtil.isNullOrEmpty(org)) {
            return "组织不存在";
        }
        if (orgName.length() > 150) {
            return "组织名称长度超出范围";
        }
        return "";
    }

    @Override
    public String JudgeMoveOrg(Long orgId, Long parentOrgId, String orgName, Long orgTreeId) {
        if (StrUtil.isNullOrEmpty(orgId)) {
            return "组织标识不能为空";
        }
        if (StrUtil.isNullOrEmpty(parentOrgId)) {
            return "组织父节点不能为空";
        }
        OrgVo curOrg = selectOrgByOrgId(orgId.toString(), orgTreeId.toString());
        if (StrUtil.isNullOrEmpty(curOrg)) {
            return "组织不存在";
        }
        if (!curOrg.getOrgName().equals(orgName)) {
            return "组织名称和现有组织名称不匹配";
        }
        OrgVo parentOrg = selectOrgByOrgId(parentOrgId.toString(), orgTreeId.toString());
        if (StrUtil.isNullOrEmpty(parentOrg)) {
            return "移动的父组织不存在";
        }
        // TODO: 2019/3/7 判断数据同步权限
        OrgUpdateCheckResult syncRet = new OrgUpdateCheckResult();
        syncRet = iOrgTreeSynchRuleService.check(OrgUpdateCheckResult.OrgOperateType.UPDATE,orgTreeId,orgId);
        if(!syncRet.isVaild()){
            return "同步规则不允许更新操作";
        }

        // TODO: 2019/1/28 组织全程
        String fullNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(), orgId.toString(), ",");
        String fullParentNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(), parentOrgId.toString(), ",");
        fullNameSplit += ",";
        fullParentNameSplit += ",";
        if (fullParentNameSplit.contains(fullNameSplit)) {
            return "节点不能移动到该节点的子节点上";
        }
//        String fullName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),orgId.toString(),"");
//        com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeWrapper = Condition.create()
//                .eq("ORG_TREE_ID",orgTreeId)
//                .eq("STATUS_CD","1000")
//                .like("ORG_BIZ_FULL_NAME",fullName,SqlLike.RIGHT);
//        int count = orgOrgtreeRelService.selectCount(orgOrgTreeWrapper);
//        if(count>50){
//            return "移动组织的下级组织数量太大，请联系管理员操作";
//        }
        return "";
    }


    @Override
    public String moveOrg(Long orgId, Long parentOrgId, Long orgTreeId, Long userId, String batchNumber) {
        String str = "";
        if ((StrUtil.strnull(orgId)).equals(StrUtil.strnull(parentOrgId))) {
            return "当前节点不能和移动节点相同";
        }
        OrgVo parentOrg = selectOrgByOrgId(parentOrgId.toString(), orgTreeId.toString());
        if (StrUtil.isNullOrEmpty(parentOrg)) {
            return "移动的父组织不存在";
        }
        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTreeId.toString());
        OrgRelType ortCur = new OrgRelType();
        if (orgRelTypeListCur != null && orgRelTypeListCur.size() > 0) {
            ortCur = orgRelTypeListCur.get(0);
        }
        // TODO: 2019/3/8  组织不能移动到渠道组织下级
        if("0412".equals(ortCur.getRefCode())){
            HashMap<String, String> map = new HashMap<String, String>();
            map = getChannelInfo(parentOrgId.toString());
            if(!map.isEmpty()){
                String isChannel = map.get("isChannel");
                if(!StrUtil.isNullOrEmpty(isChannel)){
                    return "渠道组织下面不能挂组织";
                }
            }
        }
        //当前节点的父节点 全路径
        // TODO: 2019/1/28 组织全程
        String fullNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(), orgId.toString(), ",");
        String fullParentNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(), parentOrgId.toString(), ",");
        fullNameSplit += ",";
        fullParentNameSplit += ",";
        if (fullParentNameSplit.contains(fullNameSplit) && !fullNameSplit.equals(fullParentNameSplit)) {
            return "节点不能移动到该节点的子节点上";
        }
        String fullNameId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(), orgId.toString(), "");
//        com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeWrapper = Condition.create()
//                .eq("ORG_TREE_ID",orgTreeId)
//                .eq("STATUS_CD","1000")
//                .like("ORG_BIZ_FULL_ID",fullNameId,SqlLike.RIGHT);


//        int count = orgOrgtreeRelService.selectCount(orgOrgTreeWrapper);
//        if(count>50){
//            return "移动组织的下级组织数量太大，请联系管理员操作";
//        }

        com.baomidou.mybatisplus.mapper.Wrapper orgRelWrapper = Condition.create()
                .eq("REF_CODE", ortCur.getRefCode())
                .eq("STATUS_CD", "1000")
                .eq("ORG_ID", orgId);
        OrgRel orgRel = orgRelService.selectOne(orgRelWrapper);
        OrgRel orgRelOld = new OrgRel();
        BeanUtils.copyProperties(orgRel, orgRelOld);
        orgRel.setUpdateUser(userId);
        orgRel.setParentOrgId(parentOrgId);
        orgRelService.update(orgRel);
        modifyHistoryService.addModifyHistory(orgRelOld, orgRel, userId, batchNumber);


        List<OrgVo> lowOrgVos = getLowOrgsByRefCode(ortCur.getRefCode(), orgId.toString());

        if (lowOrgVos != null && lowOrgVos.size() > 0) {
            for (OrgVo vo : lowOrgVos) {
                com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeWrapper = Condition.create()
                        .eq("ORG_TREE_ID", orgTreeId)
                        .eq("STATUS_CD", "1000")
                        .eq("ORG_ID", vo.getOrgId());
                OrgOrgtreeRel orgOrgTreeRel = orgOrgtreeRelService.selectOne(orgOrgTreeWrapper);
                if (orgOrgTreeRel != null) {
                    String fullName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(), vo.getOrgId().toString(), "");
                    String fullOrgId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(), vo.getOrgId().toString(), ",");
                    fullOrgId = "," + fullOrgId + ",";
                    orgOrgTreeRel.setOrgBizFullName(fullName);
                    orgOrgTreeRel.setOrgBizFullId(fullOrgId);
                    orgOrgTreeRel.setUpdateUser(userId);
                    orgOrgtreeRelService.update(orgOrgTreeRel);
                }
            }

        }

//        List<OrgOrgtreeRel> orgOrgTreeRels =  orgOrgtreeRelService.selectList(orgOrgTreeWrapper);
//        if(orgOrgTreeRels!=null && orgOrgTreeRels.size()>0){
//            for(OrgOrgtreeRel ootr : orgOrgTreeRels){
//                String fullName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),ootr.getOrgId().toString(),"");
//                String fullOrgId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(),ootr.getOrgId().toString(),",");
//                fullOrgId =","+fullOrgId+",";
//                ootr.setOrgBizFullName(fullName);
//                ootr.setOrgBizFullId(fullOrgId);
//                ootr.setUpdateUser(userId);
//                orgOrgtreeRelService.update(ootr);
//            }
//        }
        return str;
    }


    @Override
    public List<TreeNodeVo> getFullOrgVo(String orgTreeId, String orgId) {
        List<OrgVo> orgList = baseMapper.getFullOrgList(orgTreeId, orgId);
        List<TreeNodeVo> treeNodes = new ArrayList<>();
        if (orgList != null && orgList.size() > 0) {
            for (OrgVo vo : orgList) {
                TreeNodeVo nodeVo = new TreeNodeVo();
                nodeVo.setId(vo.getOrgId().toString());
                if (!StrUtil.isNullOrEmpty(vo.getSupOrgId())) {
                    nodeVo.setPid(vo.getSupOrgId().toString());
                }
                nodeVo.setName(vo.getOrgName());
                treeNodes.add(nodeVo);
            }
        }
        return treeNodes;
    }

    @Override
    public List<OrgVo> getLowOrgs(String orgTreeId, String orgId) {
        return baseMapper.getLowOrgs(orgTreeId, orgId);
    }

    @Override
    public List<OrgVo> getLowOrgsByRefCode(String refCode, String orgId) {
        return baseMapper.getLowOrgsByRefCode(refCode, orgId);
    }

    @Override
    public HashMap<String, String> getChannelInfo(String orgId) {
        HashMap<String, String> map = new HashMap<String, String>();
        //获取标识
        String isChannel = baseMapper.getChannelSign(orgId);
        if (!StrUtil.isNullOrEmpty(isChannel)) {
            map.put("isChannel", isChannel);
            //获取渠道
            String channelNbr = baseMapper.getChannelNBR(orgId);
            map.put("channelNbr", channelNbr);
        }
        return map;
    }

    /**
     * 获取渠道组织翻页
     *
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> getChannelOrgPage(OrgVo orgVo) {
        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo() == 0 ? 1 : orgVo.getPageNo(),
                orgVo.getPageSize() == 0 ? 10 : orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectChannelOrgPage(page, orgVo);
        if (orgVolist != null && orgVolist.size() > 0) {
            for (OrgVo vo : orgVolist) {
                com.baomidou.mybatisplus.mapper.Wrapper orgRelWrapper = Condition.create()
                        .eq("REF_CODE", orgVo.getRefCode())
                        .eq("STATUS_CD", "1000")
                        .eq("ORG_ID", vo.getOrgId());
                OrgRel orgRel = orgRelService.selectOne(orgRelWrapper);
                if (orgRel != null) {
                    vo.setChannelOrgLoadSign("1");
                    //全路径
                    String paths = getFullOrgIdList(orgVo.getOrgTreeId().toString(),vo.getOrgId().toString(),",");
                    vo.setOrgPaths(paths);
                }
//                HashMap<String,String> ls = getChannelInfo(vo.getOrgId().toString());
//                if(!StrUtil.isNullOrEmpty(ls) && !ls.isEmpty()){
//                    vo.setIsChannel(ls.get("isChannel"));
//                    //vo.setChannelNBR(ls.get("channelNbr"));
//                }
            }
        }
        page.setRecords(orgVolist);
        return page;
    }


    /**
     * 渠道组织数
     *
     * @return
     */
    @Override
    public int getChannelOrgCount() {
        int num = baseMapper.getChannelOrgCount();
        return num;
    }

    /**
     * 渠道组织已经被挂载树
     *
     * @param orgTreeId
     * @return
     */
    @Override
    public int getChannelOrgLoader(Long orgTreeId, Long orgId) {
        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTreeId.toString());
        OrgRelType ortCur = null;
        int num = 0;
        if (orgRelTypeListCur != null && orgRelTypeListCur.size() > 0) {
            ortCur = orgRelTypeListCur.get(0);
            num = baseMapper.getChannelOrgLoaderCount(ortCur.getRefCode(), StrUtil.isNullOrEmpty(orgId)?"":orgId.toString());
        }
        return num;
    }

    /**
     * 渠道组织保存数据
     *
     * @param channelOrgVo
     * @return
     */
    @Override
    public String JudgeChannelOrgParams(ChannelOrgVo channelOrgVo) {
        if (StrUtil.isNullOrEmpty(channelOrgVo)) {
            return "数据不能为空";
        }
        if (StrUtil.isNullOrEmpty(channelOrgVo.getOrgTreeId())) {
            return "组织树标识不能为空";
        }
        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(channelOrgVo.getOrgTreeId().toString());
        OrgRelType ortCur = null;
        if (orgRelTypeListCur != null && orgRelTypeListCur.size() > 0) {
            ortCur = orgRelTypeListCur.get(0);
        } else {
            return "组织关系类型不存在";
        }
        if (!StrUtil.isNullOrEmpty(channelOrgVo.getAddNodes()) && channelOrgVo.getAddNodes().size() > 0) {
            for (TreeNodeVo vo : channelOrgVo.getAddNodes()) {
                if(StrUtil.isNullOrEmpty(vo.getId())){
                    return "组织标识不能为空";
                }
                if(StrUtil.isNullOrEmpty(vo.getPid())){
                    return "组织标识[" + vo.getId() + "]组织名称[" + vo.getName() + "]:父节点不能为空";
                }
                com.baomidou.mybatisplus.mapper.Wrapper orgRelWrapper = Condition.create()
                        .eq("REF_CODE", ortCur.getRefCode())
                        .eq("STATUS_CD", "1000")
                        .eq("ORG_ID", vo.getId());
                OrgRel orgRel = orgRelService.selectOne(orgRelWrapper);
                if (!StrUtil.isNullOrEmpty(orgRel)) {
                    if(orgRel.getOrgId().equals(vo.getId()) && orgRel.getParentOrgId().equals(vo.getPid())){
                        return "组织标识[" + vo.getId() + "]组织名称[" + vo.getName() + "]:组织关系已经存在";
                    }
                    if(!StrUtil.isNullOrEmpty(channelOrgVo.getDelNodes()) || channelOrgVo.getDelNodes().size() > 0){
                        boolean isErr = true;
                        for(TreeNodeVo vo1 : channelOrgVo.getDelNodes()){
                            if(vo1.getId().equals(orgRel.getOrgId().toString())){
                                isErr = false;
                                break;
                            }
                        }
                        if(isErr){
                            return "组织标识[" + vo.getId() + "]组织名称[" + vo.getName() + "]:组织关系已经存在";
                        }
                    }
                }
            }
        }
        if(!StrUtil.isNullOrEmpty(channelOrgVo.getDelNodes()) || channelOrgVo.getDelNodes().size() > 0){
            for(TreeNodeVo vo : channelOrgVo.getDelNodes()){
                List<OrgPersonRel> oplist = orgPersonRelService.getOrgAcctRel(channelOrgVo.getOrgTreeId().toString(),vo.getId());
                if(oplist!=null && oplist.size()>0){
                    return "组织下存在账号无法删除";
                }
            }
        }
        return "";
    }

    @Override
    public void addChannelOrg(ChannelOrgVo channelOrgVo){
        String batchNumber = modifyHistoryService.getBatchNumber();
        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(channelOrgVo.getOrgTreeId().toString());
        OrgRelType ortCur = orgRelTypeListCur.get(0);
        if(!StrUtil.isNullOrEmpty(channelOrgVo.getDelNodes()) && channelOrgVo.getDelNodes().size() > 0){
            for(TreeNodeVo vo : channelOrgVo.getDelNodes()){
                com.baomidou.mybatisplus.mapper.Wrapper orgWrapper = Condition.create()
                        .eq("ORG_ID",vo.getId())
                        .eq("STATUS_CD","1000");
                Org org = this.selectOne(orgWrapper);
                Org oldOrg = new Org();
                BeanUtils.copyProperties(org,oldOrg);
                org.setStandardFlag(0L);
                this.update(org);
                modifyHistoryService.addModifyHistory(oldOrg,org,channelOrgVo.getUserId(),batchNumber);


                List<OrgRel> orgRelList = orgRelService.getOrgRel(channelOrgVo.getOrgTreeId().toString(),vo.getId());
                for(OrgRel orgRel : orgRelList){
                    orgRel.setUpdateUser(channelOrgVo.getUserId());
                    orgRelService.delete(orgRel);
                    modifyHistoryService.addModifyHistory(orgRel,null,channelOrgVo.getUserId(),batchNumber);
                    com.baomidou.mybatisplus.mapper.Wrapper orgTreeRelWrapper = Condition.create()
                            .eq("ORG_ID",org.getOrgId())
                            .eq("STATUS_CD","1000")
                            .eq("ORG_TREE_ID",channelOrgVo.getOrgTreeId());
                    List<OrgOrgtreeRel> orgOrgtreeRelList = orgOrgtreeRelService.selectList(orgTreeRelWrapper);
                    for(OrgOrgtreeRel ootr : orgOrgtreeRelList){
                        ootr.setUpdateUser(channelOrgVo.getUserId());
                        orgOrgtreeRelService.delete(ootr);
                        modifyHistoryService.addModifyHistory(ootr,null,channelOrgVo.getUserId(),batchNumber);
                    }

                    com.baomidou.mybatisplus.mapper.Wrapper orgLevelWrapper = Condition.create()
                            .eq("ORG_TREE_ID",channelOrgVo.getOrgTreeId())
                            .eq("STATUS_CD","1000")
                            .eq("ORG_ID",org.getOrgId());
                    List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
                    for(OrgLevel ol : orgLevelList){
                        ol.setUpdateUser(channelOrgVo.getUserId());
                        orgLevelService.delete(ol);
                        modifyHistoryService.addModifyHistory(ol,null,channelOrgVo.getUserId(),batchNumber);
                    }
                    // TODO: 2019/3/7  下发mq
                    String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+orgRel.getOrgId()+"}}" ;
                    template.convertAndSend("message_sharing_center_queue",mqmsg);
                }
            }
        }

        if (!StrUtil.isNullOrEmpty(channelOrgVo.getAddNodes()) && channelOrgVo.getAddNodes().size() > 0) {
            for(TreeNodeVo vo : channelOrgVo.getAddNodes()){
                OrgRel orgRef = new OrgRel();
                Long orgRefId = orgRelService.getId();
                orgRef.setOrgRelId(orgRefId);
                orgRef.setOrgId(new Long(vo.getId()));
                orgRef.setParentOrgId(new Long(vo.getPid()));
                orgRef.setRefCode(ortCur.getRefCode());
                orgRef.setStatusCd("1000");
                orgRef.setCreateUser(channelOrgVo.getUserId());
                orgRelService.add(orgRef);
                modifyHistoryService.addModifyHistory(null,orgRef,channelOrgVo.getUserId(),batchNumber);

                String fullBizName = "";
                fullBizName = orgOrgtreeRelService.getFullBizOrgNameList(channelOrgVo.getOrgTreeId().toString(),vo.getPid().toString(),"");
                fullBizName+=StrUtil.strnull(vo.getName());
                String fullBizNameId = "";
                fullBizNameId = orgOrgtreeRelService.getFullBizOrgIdList(channelOrgVo.getOrgTreeId().toString(),vo.getPid().toString(),",");
                fullBizNameId=","+fullBizNameId+","+vo.getId()+",";
                Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
                OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
                orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
                orgOrgtreeRef.setOrgId(new Long(vo.getId()));
                orgOrgtreeRef.setOrgTreeId(channelOrgVo.getOrgTreeId());
                orgOrgtreeRef.setOrgBizName(StrUtil.strnull(vo.getName()));
                orgOrgtreeRef.setOrgBizFullName(fullBizName);
                orgOrgtreeRef.setStatusCd("1000");
                orgOrgtreeRef.setCreateUser(channelOrgVo.getUserId());
                orgOrgtreeRef.setOrgBizFullId(fullBizNameId);
                if(!StrUtil.isNullOrEmpty(vo.getSort())){
                    orgOrgtreeRef.setSort(Integer.valueOf(vo.getSort()));
                }
                orgOrgtreeRelService.add(orgOrgtreeRef);
                modifyHistoryService.addModifyHistory(null,orgOrgtreeRef,channelOrgVo.getUserId(),batchNumber);

                com.baomidou.mybatisplus.mapper.Wrapper orgLevelWrapper = Condition.create()
                        .eq("ORG_TREE_ID",channelOrgVo.getOrgTreeId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",vo.getPid());
                List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
                if(orgLevelList != null && orgLevelList.size() > 0){
                    OrgLevel orgL = orgLevelList.get(0);
                    int lv = orgL.getOrgLevel()+1;
                    Long  orgLevelId = orgLevelService.getId();
                    OrgLevel orgLevel = new OrgLevel();
                    orgLevel.setOrgLevelId(orgLevelId);
                    orgLevel.setOrgId(new Long(vo.getId()));
                    orgLevel.setOrgLevel(lv);
                    orgLevel.setOrgTreeId(channelOrgVo.getOrgTreeId());
                    orgLevel.setStatusCd("1000");
                    orgLevel.setCreateUser(channelOrgVo.getUserId());
                    orgLevelService.add(orgLevel);
                    modifyHistoryService.addModifyHistory(null,orgLevel,channelOrgVo.getUserId(),batchNumber);
                }

                // TODO: 2019/3/7  下发mq
                String mqmsg = "{\"type\":\"org\",\"handle\":\"insert\",\"context\":{\"column\":\"orgId\",\"value\":"+vo.getId()+"}}" ;
                template.convertAndSend("message_sharing_center_queue",mqmsg);
            }
        }
        if(!StrUtil.isNullOrEmpty(channelOrgVo.getUpdateNodes()) && channelOrgVo.getUpdateNodes().size() > 0){
            for(TreeNodeVo vo : channelOrgVo.getUpdateNodes()){
                com.baomidou.mybatisplus.mapper.Wrapper orgTreeRelOneWrapper = Condition.create()
                        .eq("ORG_ID",vo.getId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_TREE_ID",channelOrgVo.getOrgTreeId());
                OrgOrgtreeRel orgOrgtreeRelOne = orgOrgtreeRelService.selectOne(orgTreeRelOneWrapper);
                OrgOrgtreeRel orgOrgtreeRelOLd = new OrgOrgtreeRel();
                BeanUtils.copyProperties(orgOrgtreeRelOne,orgOrgtreeRelOLd);
                if(!StrUtil.isNullOrEmpty(orgOrgtreeRelOne)){
                    if(!StrUtil.isNullOrEmpty(vo.getSort())){
                        orgOrgtreeRelOne.setSort(Integer.valueOf(vo.getSort()));
                    }
                    orgOrgtreeRelOne.setUpdateUser(channelOrgVo.getUserId());
                    orgOrgtreeRelService.update(orgOrgtreeRelOne);
                    modifyHistoryService.addModifyHistory(orgOrgtreeRelOLd,orgOrgtreeRelOne,channelOrgVo.getUserId(),batchNumber);
                    // TODO: 2019/3/7
                    String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+vo.getId()+"}}" ;
                    template.convertAndSend("message_sharing_center_queue",mqmsg);
                }
            }
        }

        return ;
    }



    /**
     * 组织新增同步
     * @param orgTreeIds
     * @param orgId
     * @param orgParentId
     * @param userId
     * @param batchNumber
     */
    @Override
    public void orgAddSync(List<Long> orgTreeIds,Long orgId,Long orgParentId,Long userId,String batchNumber){
        for(Long orgTreeId : orgTreeIds){
            if(StrUtil.isNullOrEmpty(orgId)){
                continue;
            }
            if(StrUtil.isNullOrEmpty(orgParentId)){
                continue;
            }
            com.baomidou.mybatisplus.mapper.Wrapper orgtreeWrapper = Condition.create().eq("ORG_TREE_ID",orgTreeId)
                    .eq("STATUS_CD","1000");
            OrgTree orgTree = orgTreeService.selectOne(orgtreeWrapper);
            if(orgTree==null){
                continue;
            }
            //判断组织是否需已经下挂在该组织树上
            com.baomidou.mybatisplus.mapper.Wrapper orgOrgtreeRefWrapper = Condition.create().eq("ORG_ID",orgId)
                    .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                    .eq("STATUS_CD","1000");
            List<OrgOrgtreeRel> orgOrgtreeRefList = orgOrgtreeRelService.selectList(orgOrgtreeRefWrapper);
            if(orgOrgtreeRefList != null && orgOrgtreeRefList.size()>0){
                continue;
            }
            //查询组织
            com.baomidou.mybatisplus.mapper.Wrapper orgWrapper = Condition.create().eq("ORG_ID",orgId)
                    .eq("STATUS_CD","1000");
            Org o = selectOne(orgWrapper);
            if(o==null){
                continue;
            }
            com.baomidou.mybatisplus.mapper.Wrapper ogtOrgReftypeConfWrapper = Condition.create()
                    .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                    .eq("STATUS_CD","1000");

            List<OgtOrgReltypeConf> ogtOrgReftypeConfList =  ogtOrgReftypeConfService.selectList(ogtOrgReftypeConfWrapper);
            if(ogtOrgReftypeConfList == null || ogtOrgReftypeConfList.size() < 0){
                continue;
            }
            OgtOrgReltypeConf ogtOrgReftypeConf = ogtOrgReftypeConfList.get(0);
            com.baomidou.mybatisplus.mapper.Wrapper orgReltypeConfWrapper = Condition.create()
                    .eq("ORG_REL_TYPE_ID",ogtOrgReftypeConf.getOrgRelTypeId())
                    .eq("STATUS_CD","1000");
            OrgRelType ort = orgRelTypeService.selectOne(orgReltypeConfWrapper);
            if(ort==null){
                continue;
            }

            if("1".equals(orgTree.getOrgTreeId().toString())){
                Org oldOrg = new Org();
                BeanUtils.copyProperties(o,oldOrg);
                o.setStandardFlag(1L);
                update(o);
                modifyHistoryService.addModifyHistory(oldOrg,0,userId,batchNumber);
            }

            //新增组织关系
            OrgRel orgRel = new OrgRel();
            Long orgRefId = orgRelService.getId();
            orgRel.setOrgRelId(orgRefId);
            orgRel.setOrgId(orgId);
            orgRel.setParentOrgId(orgParentId);
            orgRel.setRefCode(ort.getRefCode());
            orgRel.setStatusCd("1000");
            orgRel.setCreateUser(userId);
            orgRelService.add(orgRel);
            modifyHistoryService.addModifyHistory(null,orgRel,userId,batchNumber);


            //新增组织层级
            com.baomidou.mybatisplus.mapper.Wrapper orgLevelWrapper = Condition.create().eq("ORG_TREE_ID",orgTreeId)
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",orgParentId);
            List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
            if(orgLevelList != null || orgLevelList.size() > 0){
                OrgLevel orgL = orgLevelList.get(0);
                int lv = orgL.getOrgLevel()+1;
                Long  orgLevelId = orgLevelService.getId();
                OrgLevel orgLevel = new OrgLevel();
                orgLevel.setOrgLevelId(orgLevelId);
                orgLevel.setOrgId(orgId);
                orgLevel.setOrgLevel(lv);
                orgLevel.setOrgTreeId(orgTree.getOrgTreeId());
                orgLevel.setStatusCd("1000");
                orgLevel.setCreateUser(userId);
                orgLevelService.add(orgLevel);
                modifyHistoryService.addModifyHistory(null,orgLevel,userId,batchNumber);
            }
            //新增组织层级
            com.baomidou.mybatisplus.mapper.Wrapper orgorgtreerelWrapper = Condition.create().eq("ORG_TREE_ID",orgTreeId)
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",orgId);
            OrgOrgtreeRel orgOrgtreeRefv =  orgOrgtreeRelService.selectOne(orgorgtreerelWrapper);

            //组织组织树关系
            // TODO: 2019/1/23
            String fullBizName = "";
            fullBizName = orgOrgtreeRelService.getFullBizOrgNameList(orgTree.getOrgTreeId().toString(),orgParentId.toString(),"");
            fullBizName+=StrUtil.strnull(StrUtil.isNullOrEmpty(orgOrgtreeRefv.getOrgBizName())?o.getOrgName():orgOrgtreeRefv.getOrgBizName());
            String fullBizNameId = "";
            fullBizNameId = orgOrgtreeRelService.getFullBizOrgIdList(orgTree.getOrgTreeId().toString(),orgParentId.toString(),",");
            fullBizNameId=","+fullBizNameId+","+o.getOrgId()+",";

            Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
            OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
            orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
            orgOrgtreeRef.setOrgId(orgId);
            orgOrgtreeRef.setOrgTreeId(orgTree.getOrgTreeId());
            orgOrgtreeRef.setStatusCd("1000");
            orgOrgtreeRef.setCreateUser(userId);
            // TODO: 2019/1/23
            orgOrgtreeRef.setOrgBizName(orgOrgtreeRefv.getOrgBizName());
            orgOrgtreeRef.setOrgBizFullName(fullBizName);
            orgOrgtreeRef.setOrgBizFullId(fullBizNameId);

            orgOrgtreeRelService.add(orgOrgtreeRef);
            modifyHistoryService.addModifyHistory(null,orgOrgtreeRef,userId,batchNumber);

            /*新增化小编码*/
            //新增营销组织扩展属性
//            List<ExpandovalueVo> extValueList = org.getExpandovalueVoList();
//            if(extValueList !=null && extValueList.size()>0){
//                if(extValueList!=null && extValueList.size()>0){
//                    ResponseResult<ExpandovalueVo> publicRet = new ResponseResult<ExpandovalueVo>();
//                    for(ExpandovalueVo extVo : extValueList){
//                        extVo.setTableName("TB_ORG");
//                        extVo.setRecordId(org.getOrgId().toString());
//                        publicRet = expandovalueService.addExpandoInfo(extVo);
//                    }
//                }
//
//
//
//                String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
//                    @Override
//                    public String doInConnection(Connection conn) throws SQLException, DataAccessException {
//                        CallableStatement cstmt = null;
//                        String result = "";
//                        try {
//                            cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_GENERATOR (?,?,?)}");
//                            cstmt.setObject(1, org.getOrgCode());
//                            cstmt.registerOutParameter(2, Types.VARCHAR);
//                            cstmt.registerOutParameter(3, Types.VARCHAR);
//                            cstmt.execute();
//                            if(!StrUtil.isNullOrEmpty(cstmt.getString(2))){
//                                result = cstmt.getString(2).toString();
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            if (cstmt != null) {
//                                cstmt.close();
//                                cstmt = null;
//                            }
//                            if (conn != null) {
//                                conn.close();
//                                conn = null;
//                            }
//                        }
//                        return result;
//                    }
//                });
//            }

        }
        return;
    }

    /**
     * 组织更新同步
     * @param orgTreeIds
     * @param org
     * @param userId
     * @param batchNumber
     */
    @Override
    public void orgUpdateSync(List<Long> orgTreeIds,OrgVo org,Long userId,String batchNumber){
        for(Long orgTreeId : orgTreeIds){
            //修改组织关系
            if(org.getMoveParentOrgId()!=null && org.getSupOrgId()!=null && !org.getMoveParentOrgId().toString().equals(org.getSupOrgId().toString())){
                String moveMsg = moveOrg(org.getOrgId(),org.getMoveParentOrgId(),orgTreeId,org.getUpdateUser(),batchNumber);
                if(!StrUtil.isNullOrEmpty(moveMsg)){
                    continue;
                }
            }
            //更新组织组织树关系
            Wrapper orgTreeRelOneWrapper = Condition.create()
                    .eq("ORG_ID",org.getOrgId())
                    .eq("STATUS_CD","1000")
                    .eq("ORG_TREE_ID",orgTreeId);
            OrgOrgtreeRel orgOrgtreeRelOne = orgOrgtreeRelService.selectOne(orgTreeRelOneWrapper);
            OrgOrgtreeRel orgOrgtreeRelOLd = new OrgOrgtreeRel();
            BeanUtils.copyProperties(orgOrgtreeRelOne,orgOrgtreeRelOLd);
            if(orgOrgtreeRelOne!=null){
                orgOrgtreeRelOne.setOrgBizName(StrUtil.isNullOrEmpty(org.getOrgBizName())?org.getOrgName():org.getOrgBizName());
                // TODO: 2019/1/23
                String fullBizName = "";
                String fullBizNameId = "";

                if(!StrUtil.isNullOrEmpty(org.getMoveParentOrgId())){
                    fullBizName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),org.getMoveParentOrgId().toString(),"");
                    fullBizNameId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(),org.getMoveParentOrgId().toString(),",");
                }else if(!StrUtil.isNullOrEmpty(org.getSupOrgId())){
                    fullBizName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),org.getSupOrgId().toString(),"");
                    fullBizNameId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(),org.getSupOrgId().toString(),",");
                }
                fullBizName+=StrUtil.isNullOrEmpty(org.getOrgBizName())?org.getOrgName():org.getOrgBizName();
                fullBizNameId=","+fullBizNameId+","+org.getOrgId()+",";
                orgOrgtreeRelOne.setOrgBizFullName(fullBizName);
                orgOrgtreeRelOne.setOrgBizFullId(fullBizNameId);


                if(!StrUtil.isNullOrEmpty(org.getSort())){
                    orgOrgtreeRelOne.setSort(Integer.valueOf(org.getSort()));
                }
                orgOrgtreeRelOne.setUpdateUser(org.getUpdateUser());
                orgOrgtreeRelService.update(orgOrgtreeRelOne);
                modifyHistoryService.addModifyHistory(orgOrgtreeRelOLd,orgOrgtreeRelOne,org.getUpdateUser(),batchNumber);
            }
        }
        return;
    }

    /**
     *
     * @param orgTreeIds
     * @param org
     * @param userId
     * @param batchNumber
     */
    @Override
    public void orgDelSync(List<Long> orgTreeIds,OrgVo org,Long userId,String batchNumber){
        for(Long orgTreeId : orgTreeIds) {
            List<OrgRel> orList = orgRelService.getOrgRel(orgTreeId.toString(),
                    org.getOrgId().toString());
            if(orList==null || orList.size()<1){
                continue;
            }
            OrgRel or = orList.get(0);
            Wrapper orgTreeRelWrapper = Condition.create()
                    .eq("ORG_ID", org.getOrgId())
                    .eq("STATUS_CD", "1000")
                    .eq("ORG_TREE_ID", orgTreeId);
            List<OrgOrgtreeRel> orgOrgtreeRelList = orgOrgtreeRelService.selectList(orgTreeRelWrapper);
            if (orgOrgtreeRelList != null && orgOrgtreeRelList.size() > 0) {
                for (OrgOrgtreeRel ootr : orgOrgtreeRelList) {
                    ootr.setUpdateUser(org.getUpdateUser());
                    orgOrgtreeRelService.delete(ootr);
                    modifyHistoryService.addModifyHistory(ootr, null, org.getUpdateUser(), batchNumber);
                }
            }
            Wrapper orgLevelWrapper = Condition.create()
                    .eq("ORG_TREE_ID", orgTreeId)
                    .eq("STATUS_CD", "1000")
                    .eq("ORG_ID", org.getOrgId());
            List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
            if (orgLevelList != null && orgLevelList.size() > 0) {
                for (OrgLevel ol : orgLevelList) {
                    ol.setUpdateUser(org.getUpdateUser());
                    orgLevelService.delete(ol);
                    modifyHistoryService.addModifyHistory(ol, null, org.getUpdateUser(), batchNumber);
                }
            }
            Wrapper orgPositionWrapper = Condition.create()
                    .eq("ORG_TREE_ID", orgTreeId)
                    .eq("STATUS_CD", "1000")
                    .eq("ORG_ID", org.getOrgId());
            List<OrgPositionRel> orgPositionRelList = orgPositionRelService.selectList(orgPositionWrapper);
            if (orgPositionRelList != null && orgPositionRelList.size() > 0) {
                for (OrgPositionRel opr : orgPositionRelList) {
                    opr.setUpdateUser(org.getUpdateUser());
                    orgPositionRelService.delete(opr);
                    modifyHistoryService.addModifyHistory(opr, null, org.getUpdateUser(), batchNumber);
                }
            }


            //删除证件组织关系
            Wrapper orgCertListWrapper = Condition.create()
                    .eq("STATUS_CD", "1000")
                    .eq("ORG_ID", org.getOrgId());
            List<OrgCertRel> orgCertRelList = orgCertRelService.selectList(orgCertListWrapper);
            if (orgCertRelList != null && orgCertRelList.size() > 0) {
                for (OrgCertRel vo : orgCertRelList) {
                    vo.setUpdateUser(org.getUpdateUser());
                    orgCertRelService.delete(vo);
                    modifyHistoryService.addModifyHistory(vo, null, org.getUpdateUser(), batchNumber);
                }
            }

            //删除组织联系人关系
            Wrapper orgContactListWrapper = Condition.create()
                    .eq("STATUS_CD", "1000")
                    .eq("ORG_ID", org.getOrgId());
            List<OrgContactRel> orgContactRelList = orgContactRelService.selectList(orgContactListWrapper);
            if (orgContactRelList != null && orgContactRelList.size() > 0) {

                for (OrgContactRel vo : orgContactRelList) {
                    vo.setUpdateUser(org.getUpdateUser());
                    orgContactRelService.delete(vo);
                    modifyHistoryService.addModifyHistory(vo, null, org.getUpdateUser(), batchNumber);
                }
            }
            or.setUpdateUser(org.getUpdateUser());
            orgRelService.delete(or);
            modifyHistoryService.addModifyHistory(or, null, org.getUpdateUser(), batchNumber);
        }
        return;
    }

    /**
     * 游离组织新增
     * @param orgTreeIds
     * @param org
     * @param userId
     * @param batchNumber
     */
    @Override
    public void freeOrgAddSync(List<Long> orgTreeIds,OrgVo org,Long userId,String batchNumber){

        for(Long orgTreeId : orgTreeIds) {
            //查询组织树
            Wrapper orgWrapper = Condition.create().eq("ORG_ID",org.getOrgId())
                    .eq("STATUS_CD","1000");
            Org o = selectOne(orgWrapper);
            if(o==null){
                continue;
            }
            com.baomidou.mybatisplus.mapper.Wrapper ogtOrgReftypeConfWrapper = Condition.create()
                    .eq("ORG_TREE_ID",orgTreeId)
                    .eq("STATUS_CD","1000");

            List<OgtOrgReltypeConf> ogtOrgReftypeConfList =  ogtOrgReftypeConfService.selectList(ogtOrgReftypeConfWrapper);
            if(ogtOrgReftypeConfList == null || ogtOrgReftypeConfList.size() < 0){
                continue;
            }
            OgtOrgReltypeConf ogtOrgReftypeConf = ogtOrgReftypeConfList.get(0);
            com.baomidou.mybatisplus.mapper.Wrapper orgReltypeConfWrapper = Condition.create()
                    .eq("ORG_REL_TYPE_ID",ogtOrgReftypeConf.getOrgRelTypeId())
                    .eq("STATUS_CD","1000");
            OrgRelType ort = orgRelTypeService.selectOne(orgReltypeConfWrapper);
            if(ort==null){
                continue;
            }
            //新增组织关系
            OrgRel orgRel = new OrgRel();
            Long orgRefId = orgRelService.getId();
            orgRel.setOrgRelId(orgRefId);
            orgRel.setOrgId(org.getOrgId());
            orgRel.setParentOrgId(org.getSupOrgId());
            orgRel.setRefCode(ort.getRefCode());
            orgRel.setStatusCd("1000");
            orgRel.setCreateUser(org.getUpdateUser());
            orgRelService.add(orgRel);
            modifyHistoryService.addModifyHistory(null,orgRel,org.getUpdateUser(),batchNumber);



            //新增组织层级
            Wrapper orgLevelWrapper = Condition.create().eq("ORG_TREE_ID",org.getOrgTreeId())
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",org.getSupOrgId());
            List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
            if(orgLevelList != null || orgLevelList.size() > 0){
                OrgLevel orgL = orgLevelList.get(0);
                int lv = orgL.getOrgLevel()+1;
                Long  orgLevelId = orgLevelService.getId();
                OrgLevel orgLevel = new OrgLevel();
                orgLevel.setOrgLevelId(orgLevelId);
                orgLevel.setOrgId(org.getOrgId());
                orgLevel.setOrgLevel(lv);
                orgLevel.setOrgTreeId(orgTreeId);
                orgLevel.setStatusCd("1000");
                orgLevel.setCreateUser(org.getUpdateUser());
                orgLevelService.add(orgLevel);
                modifyHistoryService.addModifyHistory(null,orgLevel,org.getUpdateUser(),batchNumber);
            }

            //组织组织树关系
            // TODO: 2019/1/23
            String fullBizName = "";
            fullBizName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),org.getSupOrgId().toString(),"");
            fullBizName+=StrUtil.strnull(StrUtil.isNullOrEmpty(org.getOrgBizName())?o.getOrgName():org.getOrgBizName());
            String fullBizNameId = "";
            fullBizNameId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(),org.getSupOrgId().toString(),",");
            fullBizNameId=","+fullBizNameId+","+o.getOrgId()+",";

            Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
            OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
            orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
            orgOrgtreeRef.setOrgId(org.getOrgId());
            orgOrgtreeRef.setOrgTreeId(orgTreeId);
            orgOrgtreeRef.setStatusCd("1000");
            orgOrgtreeRef.setCreateUser(org.getUpdateUser());
            // TODO: 2019/1/23
            orgOrgtreeRef.setOrgBizName(StrUtil.isNullOrEmpty(org.getOrgBizName())?o.getOrgName():org.getOrgBizName());
            orgOrgtreeRef.setOrgBizFullName(fullBizName);
            orgOrgtreeRef.setOrgBizFullId(fullBizNameId);

            orgOrgtreeRelService.add(orgOrgtreeRef);
            modifyHistoryService.addModifyHistory(null,orgOrgtreeRef,org.getUpdateUser(),batchNumber);
        }
        return;
    }

    /**
     * excel组织导入移动
     * @param orgTreeIds
     * @param orgId
     * @param orgParentId
     * @param userId
     * @param batchNumber
     */
    @Override
    public void orgExcelMoveSync(List<Long> orgTreeIds,Long orgId,Long orgParentId,Long userId,String batchNumber){
        for(Long orgTreeId : orgTreeIds) {
            moveOrg(orgId,orgParentId,orgTreeId,userId,batchNumber);
            com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeRelWrapper = Condition.create()
                    .eq("ORG_ID",orgId)
                    .eq("STATUS_CD","1000")
                    .eq("ORG_TREE_ID",orgTreeId);
            OrgOrgtreeRel ootr = orgOrgtreeRelService.selectOne(orgOrgTreeRelWrapper);
            if(ootr!=null){
                String fullName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),ootr.getOrgId().toString(),"");
                String fullOrgId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(),ootr.getOrgId().toString(),",");
                fullOrgId =","+fullOrgId+",";
                ootr.setOrgBizFullName(fullName);
                ootr.setOrgBizFullId(fullOrgId);
                ootr.setUpdateUser(userId);
                orgOrgtreeRelService.update(ootr);
            }
        }
        return;
    }

}
