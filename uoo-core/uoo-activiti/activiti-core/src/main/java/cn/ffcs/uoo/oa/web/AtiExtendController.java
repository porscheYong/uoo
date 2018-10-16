package cn.ffcs.uoo.oa.web;

import cn.ffcs.uoo.oa.entity.AtiDelegateInfo;
import cn.ffcs.uoo.oa.service.*;
import cn.ffcs.uoo.oa.utils.ResponseResult;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 拓展功能相关前端控制器
 * Created by liuxiaodong on 2018/10/12.
 */
@RestController
@RequestMapping("/oa")
public class AtiExtendController extends BaseController {

    @Resource
    private AtiExtendService atiExtendService;
    @Resource
    private AtiProcessService atiProcessService;
    @Resource
    private AtiTaskService atiTaskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private AtiDelegateInfoService atiDelegateInfoService;

    /**
     * 流程实例任务指派
     *
     * @param procInstId 流程实例ID
     * @param taskDefKey 指定流程中的环节，用任务定义KEY表示
     * @param users      指定用户代理
     * @return 执行后的状态和消息
     */
    @RequestMapping(value = "/alterTaskAssignee", method = RequestMethod.POST)
    public ResponseResult<Void> alterTaskAssignee(String procInstId, String taskDefKey, String users) {
        // 校验参数
        ResponseResult<Void> result = atiExtendService.checkAlterTaskAssignee(procInstId, taskDefKey, users);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        try {
            List<String> userList = JSON.parseArray(users, String.class);
            atiProcessService.alterAssignee(procInstId, taskDefKey, userList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("success");
        return result;
    }

    /**
     * 取消任务指派
     *
     * @param procInstId 流程实例标识
     * @param taskDefKey 指定流程中的环节，用任务定义KEY表示
     * @return 执行后的状态和消息
     */
    @RequestMapping(value = "/removeTaskAlternative", method = RequestMethod.POST)
    public ResponseResult<Void> removeTaskAlternative(String procInstId, String taskDefKey) {
        // 校验参数
        ResponseResult<Void> result = atiExtendService.checkRemoveTaskAlternative(procInstId, taskDefKey);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        try {
            atiProcessService.removeTaskAlternative(procInstId, taskDefKey);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;

    }

    /**
     * 添加任务委托
     *
     * @param atiDelegateInfo 任务委托信息对象
     * @return 执行后的状态和消息
     */
    @RequestMapping(value = "/addDelegate", method = RequestMethod.POST)
    public ResponseResult<Void> addDelegate(@RequestBody AtiDelegateInfo atiDelegateInfo) {
        // 校验参数
        ResponseResult<Void> result = atiExtendService.checkAddDelegate(atiDelegateInfo);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        try {
            atiDelegateInfoService.insertOneDelegateInfo(atiDelegateInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;
    }

    /**
     * 任务委托信息列表
     *
     * @param assignee 原任务办理人
     * @return 任务委托集合和状态与消息
     */
    @RequestMapping(value = "/delegateList", method = RequestMethod.GET)
    public ResponseResult<List<AtiDelegateInfo>> getDelegateList(String assignee) {
        ResponseResult<List<AtiDelegateInfo>> result = new ResponseResult<>();

        if (StringUtils.isEmpty(assignee)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效，任务办理人不能为空！");
            return result;
        }

        List<AtiDelegateInfo> delegateInfoList;
        try {
            delegateInfoList = atiDelegateInfoService.delegateInfoList(assignee);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setData(delegateInfoList);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("获取委托信息列表成功！");
        return result;

    }

    /**
     * 修改任务委托
     *
     * @param atiDelegateInfo 任务委托信息对象
     * @return 执行后的状态和消息
     */
    @RequestMapping(value = "/modifyDelegate", method = RequestMethod.POST)
    public ResponseResult<Void> modifyDelegate(@RequestBody AtiDelegateInfo atiDelegateInfo) {
        // 添加校验
        ResponseResult<Void> result = atiExtendService.checkModifyDelegate(atiDelegateInfo);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        try {
            atiDelegateInfoService.modifyOneDelegateInfo(atiDelegateInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;
    }

    /**
     * 删除任务委托
     *
     * @param delegateInfoId 任务委托标识
     * @return 执行后的状态和消息
     */
    @RequestMapping(value = "/removeDelegate", method = RequestMethod.POST)
    public ResponseResult<Void> removeDelegate(String delegateInfoId) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(delegateInfoId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务委托标识不允许为空！");
            return result;
        }
        AtiDelegateInfo delegateInfo = atiDelegateInfoService.selectById(delegateInfoId);
        if (null == delegateInfo) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务委托不存在！");
            return result;
        }
        try {
            atiDelegateInfoService.removeOneDelegateInfo(delegateInfoId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;
    }

    /**
     * 当前环节任务与候选人
     *
     * @param procInstId 流程实例标识
     * @return 当前任务与候选人和状态与消息
     */
    @RequestMapping(value = "/currentTaskAssignNames", method = RequestMethod.GET)
    public ResponseResult<Map<String, Object>> getCurrentTaskAssignNames(String procInstId) {
        ResponseResult<Map<String, Object>> result = new ResponseResult<>();
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
        Map<String, Object> map;
        try {
            map = atiTaskService.getCurrentTaskAssignNames(procInstId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }
        result.setData(map);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("获取当前任务与候选人成功！");
        return result;
    }

}
