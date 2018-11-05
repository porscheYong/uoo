package cn.ffcs.uoo.oa.service;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.oa.entity.AtiDelegateInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.repository.ProcessDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听器类继承此类,处理任务委托等
 *
 * @author liuxiaodong
 * @date 2018/9/27
 */
public class OaBaseTaskListenerService {
    @Resource
    private AtiDelegateInfoService atiDelegateInfoService;
    @Resource
    private RepositoryService repositoryService;


    /**
     * 添加委托人
     *
     * @param users 任务候选人
     * @param delegateTask delegateTask对象
     */
    List<String> addDelegateUsers(List<String> users, DelegateTask delegateTask) {
        String processDefinitionId = delegateTask.getProcessDefinitionId();
        List<String> candidates = new ArrayList<>();
        candidates.addAll(users);
        for (String assignee : users) {
            List<AtiDelegateInfo> delegateInfoList = atiDelegateInfoService.delegateInfoList(assignee);
            if (null != delegateInfoList && delegateInfoList.size() > 0) {
                for (AtiDelegateInfo delegateInfo : delegateInfoList) {
                    //设置一个流程委托
                    if (processDefinitionId.equals(delegateInfo.getProcDefId())) {
                        delegateTask.addCandidateUser(delegateInfo.getAttorney());
                        candidates.add(delegateInfo.getAttorney());
                        continue;
                    }

                    //设置一类流程委托
                    if (StringUtils.isEmpty(delegateInfo.getProcDefId())) {
                        Long atiCategoryId = delegateInfo.getAtiCategoryId();
                        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().
                                processDefinitionCategory(String.valueOf(atiCategoryId)).active().list();
                        if (processDefinitionList == null || processDefinitionList.size() == 0) {
                            return candidates;
                        }
                        for (ProcessDefinition processDefinition : processDefinitionList) {
                            if (processDefinition.getId().equals(delegateTask.getProcessDefinitionId())) {
                                delegateTask.addCandidateUser(delegateInfo.getAttorney());
                                candidates.add(delegateInfo.getAttorney());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return candidates;
    }

}
