package cn.ffcs.uoo.oa.service;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.oa.constant.ApprovalConstant;
import cn.ffcs.uoo.oa.entity.AtiActCategory;
import cn.ffcs.uoo.oa.entity.AtiDelegateInfo;
import cn.ffcs.uoo.oa.utils.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 扩展功能相关Service
 * Created by liuxiaodong on 2018/10/12.
 */
@Service
public class AtiExtendService {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private AtiActCategoryService atiActCategoryService;
    @Resource
    private AtiDelegateInfoService atiDelegateInfoService;

    /**
     * 流程实例任务指派校验参数
     * @param procInstId 流程实例标识
     * @param taskDefKey 定流程中的环节，用任务定义KEY表示
     * @param users 指定用户代理
     */
    public ResponseResult<Void> checkAlterTaskAssignee(String procInstId, String taskDefKey, String users) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(procInstId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例ID不允许为空！");
            return result;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(procInstId).active().singleResult();
        if (null == processInstance) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不存在！");
            return result;
        }
        if (StringUtils.isEmpty(taskDefKey)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务定义值不允许为空！");
            return result;
        }
        if (!ApprovalConstant.TASK_DEF_KEYS[2].equals(taskDefKey) && !ApprovalConstant.TASK_DEF_KEYS[3].equals(taskDefKey)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务定义值不正确！");
            return result;
        }
        List<String> userList;
        try {
            userList = JSON.parseArray(users, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("用户参数无效！");
            return result;
        }
        if (null == userList || userList.size() == 0) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("没有可指派的用户代理！");
            return result;
        }

        return result;
    }

    /**
     * 取消任务指派校验参数
     * @param procInstId 流程实例标识
     * @param taskDefKey 指定流程中的环节，用任务定义KEY表示
     */
    public ResponseResult<Void> checkRemoveTaskAlternative(String procInstId, String taskDefKey) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(procInstId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例ID不允许为空！");
            return result;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(procInstId).active().singleResult();
        if (null == processInstance) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不存在！");
            return result;
        }
        if (StringUtils.isEmpty(taskDefKey)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务定义值不允许为空！");
            return result;
        }
        if (!ApprovalConstant.TASK_DEF_KEYS[2].equals(taskDefKey) && !ApprovalConstant.TASK_DEF_KEYS[3].equals(taskDefKey)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务定义值不正确！");
            return result;
        }
        Object procVar = runtimeService.getVariable(procInstId, procInstId + taskDefKey);
        if (null == procVar) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("该环节没有任务指派！");
            return result;
        }
        return result;
    }

    /**
     * 添加任务委托 校验参数
     * @param atiDelegateInfo 任务委托对象
     */
    public ResponseResult<Void> checkAddDelegate(AtiDelegateInfo atiDelegateInfo) {
        ResponseResult<Void> result = new ResponseResult<>();
        String assignee = atiDelegateInfo.getAssignee();
        if (StringUtils.isEmpty(assignee)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("原办理人不允许为空！");
            return result;
        }
        String attorney = atiDelegateInfo.getAttorney();
        if (StringUtils.isEmpty(attorney)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托人不允许为空！");
            return result;
        }
        Long categoryId = atiDelegateInfo.getAtiCategoryId();
        AtiActCategory category = atiActCategoryService.selectById(categoryId);
        if (null == category) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程分类不存在！");
            return result;
        }
        Date startTime = atiDelegateInfo.getStartTime();
        if (null == startTime) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托开始时间不允许为空！");
            return result;
        }
        Date endTime = atiDelegateInfo.getEndTime();
        if (null == endTime) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托截止时间不允许为空！");
            return result;
        }
        if (startTime.getTime() > endTime.getTime()) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托开始时间大于截止时间！");
            return result;
        }
        String delegateReason = atiDelegateInfo.getDelegateReason();
        if (StringUtils.isEmpty(delegateReason)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托原因不允许为空！");
            return result;
        }
        return result;
    }

    /**
     * 修改任务委托 校验参数
     * @param atiDelegateInfo 任务委托对象
     */
    public ResponseResult<Void> checkModifyDelegate(AtiDelegateInfo atiDelegateInfo) {
        ResponseResult<Void> result = new ResponseResult<>();

        Long delegateInfoId = atiDelegateInfo.getAtiDelegateInfoId();
        AtiDelegateInfo delegateInfo = atiDelegateInfoService.selectById(delegateInfoId);
        if (null == delegateInfo) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务委托信息标识不正确！");
            return result;
        }
        if (null != atiDelegateInfo.getStartTime() && null == atiDelegateInfo.getEndTime()) {
            if (atiDelegateInfo.getStartTime().getTime() > delegateInfo.getEndTime().getTime()) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("开始时间不能大于截止时间！");
                return result;
            }
        }
        if (null == atiDelegateInfo.getStartTime() && null != atiDelegateInfo.getEndTime()) {
            if (atiDelegateInfo.getEndTime().getTime() < delegateInfo.getStartTime().getTime()) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("截止时间不能小于开始开始时间！");
                return result;
            }
        }
        if (null != atiDelegateInfo.getStartTime() && null != atiDelegateInfo.getEndTime()) {
            if (atiDelegateInfo.getEndTime().getTime() < atiDelegateInfo.getStartTime().getTime()) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("开始时间不能大于截止时间！");
                return result;
            }
        }
        return result;
    }
}
