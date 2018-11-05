package cn.ffcs.uoo.oa.web;

import cn.ffcs.uoo.oa.service.AtiProcessService;
import cn.ffcs.uoo.oa.utils.ResponseResult;
import cn.ffcs.uoo.oa.vo.ProcessDefinitionVo;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程相关前端控制器
 * Created by liuxiaodong on 2018/10/12.
 */
@RestController
@RequestMapping("/oa")
public class AtiProcessController extends BaseController {
    @Resource
    private AtiProcessService atiProcessService;
    @Resource
    private RepositoryService repositoryService;

    /**
     * 流程部署
     *
     * @param category 流程分类标识
     * @param file     部署文件
     * @return 状态和信息
     */
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public ResponseResult<Void> deploy(String category, MultipartFile file) {
        // 校验参数
        ResponseResult<Void> result = atiProcessService.checkDeploy(category, file);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        String message;
        try {
            message = atiProcessService.deploy(category, file);
        } catch (Exception e) {
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("部署失败");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage(message);
        return result;
    }

    /**
     * 流程定义列表
     *
     * @param categoryId 流程分类标识
     * @return List<ProcessDefinitionVo>
     */
    @RequestMapping(value = "/processList", method = RequestMethod.GET)
    public ResponseResult<List<ProcessDefinitionVo>> getProcessList(String categoryId) {
        ResponseResult<List<ProcessDefinitionVo>> result = atiProcessService.checkProcessList(categoryId);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        List<ProcessDefinitionVo> processDefinitionVoList;
        try {
            processDefinitionVoList = atiProcessService.processList(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_OK);
            result.setMessage("执行失败！");
            return result;
        }
        result.setState(ResponseResult.STATE_OK);
        result.setData(processDefinitionVoList);
        result.setMessage("已获取流程定义列表！");
        return result;
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "/deleteDeployment", method = RequestMethod.POST)
    public ResponseResult<Void> deleteDeployment(String deploymentId) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(deploymentId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程部署ID不允许为空！");
            return result;
        }
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (null == deployment) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程部署ID不存在！");
            return result;
        }
        try {
            atiProcessService.deleteDeployment(deploymentId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("已成功删除流程定义！");
        return result;
    }

    /**
     * 删除部署的流程实例
     *
     * @param procInstId   流程实例ID
     * @param deleteReason 删除原因
     */
    @RequestMapping(value = "/deleteProcIns", method = RequestMethod.POST)
    public ResponseResult<Void> deleteProcIns(String procInstId, String deleteReason) {
        // 添加校验
        ResponseResult<Void> result = atiProcessService.checkDeleteProcIns(procInstId, deleteReason);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        try {
            atiProcessService.deleteProcIns(procInstId, deleteReason);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("已成功删除流程实例！");
        return result;
    }


}
