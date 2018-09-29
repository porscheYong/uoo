package cn.ffcs.uoo.leaveDemo.service;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.leaveDemo.constant.ApprovalConstant;
import cn.ffcs.uoo.leaveDemo.vo.ProcessDefinitionVo;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程定义相关Service
 * Created by liuxiaodong on 2018/9/20.
 */
@Service
public class AtiProcessService {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    /**
     * 部署 BPMN 文件
     *
     * @param category 流程分类标识
     * @param file 部署文件
     * @return
     */
    public String deploy(String category, MultipartFile file) {

        String message = "";
        String fileName = file.getOriginalFilename();

        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment;
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else if (extension.equals("png")) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (fileName.indexOf("bpmn20.xml") != -1) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
                String baseName = FilenameUtils.getBaseName(fileName);
                deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
            } else {
                message = "不支持的文件类型：" + extension;
                return message;
            }

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                message += "部署成功，流程ID=" + processDefinition.getId();
            }

            if (list.size() == 0) {
                message = "部署失败，没有流程。";
            }

        } catch (Exception e) {
            throw new ActivitiException("部署失败！", e);
        }

        return message;
    }

    /**
     * 流程定义列表
     * @param category 流程分类标识
     * @return
     */
    public List<ProcessDefinitionVo> processList(String category) {

        List<ProcessDefinitionVo> processDefinitionVoList = new ArrayList<>();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().orderByProcessDefinitionKey().asc();

        if (StringUtils.isNotEmpty(category)){
            processDefinitionQuery.processDefinitionCategory(category);
        }

//        for (ProcessDefinition processDefinition : processDefinitionList) {
//            String deploymentId = processDefinition.getDeploymentId();
//            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
//            list.add(new Object[]{processDefinition, deployment});
//        }

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        if (null != processDefinitionList && processDefinitionList.size() > 0) {
            for (ProcessDefinition processDefinition : processDefinitionList) {
                ProcessDefinitionVo processDefinitionVo = setProcessDefinitionVo(processDefinition);
                processDefinitionVoList.add(processDefinitionVo);
            }
        }

        return processDefinitionVoList;

    }

    /**
     * 删除部署的流程，级联删除流程实例
     * @param deploymentId 流程部署ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * 删除部署的流程实例
     * @param procInsId 流程实例ID
     * @param deleteReason 删除原因
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcIns(String procInsId, String deleteReason) {
        runtimeService.deleteProcessInstance(procInsId, deleteReason);
    }

    /**
     * 任务改派
     * @param procInstId
     * @param taskDefKey
     * @param users
     */
    public void alterAssignee(String procInstId,String taskDefKey,List<String> users) {

        Task currentTask = taskService.createTaskQuery().processInstanceId(procInstId).active().singleResult();
        //当前任务改派
        if(taskDefKey.equals(currentTask.getTaskDefinitionKey())) {
            //获取当前节点候选人
            List<String> taskCandidateUsers = (List<String>) taskService.getVariable(currentTask.getId(),taskDefKey);
            //1. 清空所有候选人
            for(String user : taskCandidateUsers) {
                taskService.deleteCandidateUser(currentTask.getId(), user);
            }

            //2. 给节点添加办理人
            for(String user : users) {
                taskService.addCandidateUser(currentTask.getId(), user);
            }

            return;
        }

        // 非当前任务改派
        // 任务改派时，往流程实例中添加变量，到节点指派候选人时再取出该变量中的用户
        // 变量名格式为：procInstId + taskDefKey
        switch (taskDefKey) {
            case "deptLeaderAudit" :
                runtimeService.setVariable(procInstId, procInstId + ApprovalConstant.TASK_DEF_KEYS[2], users);
                break;
            case "hrAudit" :
                runtimeService.setVariable(procInstId, procInstId + ApprovalConstant.TASK_DEF_KEYS[3], users);
                break;
        }

    }

    /**
     * 取消任务改派
     * @param procInstId
     * @param variable
     */
    public void removeTaskAlternative(String procInstId, String variable) {
        runtimeService.removeVariable(procInstId, variable);
    }


    private ProcessDefinitionVo setProcessDefinitionVo(ProcessDefinition processDefinition) {
        ProcessDefinitionVo processDefinitionVo = new ProcessDefinitionVo();
        processDefinitionVo.setId(processDefinition.getId());
        processDefinitionVo.setCategoryId(processDefinition.getCategory());
        processDefinitionVo.setDeploymentId(processDefinition.getDeploymentId());
        processDefinitionVo.setDescription(processDefinition.getDescription());
        processDefinitionVo.setDiagramResourceName(processDefinition.getDiagramResourceName());
        processDefinitionVo.setKey(processDefinition.getKey());
        processDefinitionVo.setName(processDefinition.getName());
        processDefinitionVo.setResourceName(processDefinition.getResourceName());
        processDefinitionVo.setVersion(String.valueOf(processDefinition.getVersion()));
        return processDefinitionVo;
    }

}
