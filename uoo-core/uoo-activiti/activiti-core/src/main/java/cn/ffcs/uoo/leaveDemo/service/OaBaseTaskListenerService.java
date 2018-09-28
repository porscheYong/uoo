package cn.ffcs.uoo.leaveDemo.service;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.leaveDemo.entity.AtiDelegateInfo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 监听器类继承此类
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
     * @param users
     * @param delegateTask
     */
    public List<String> addDelegateUsers(List<String> users, DelegateTask delegateTask) {
        String processDefinitionId = delegateTask.getProcessDefinitionId();

        for (String assignee : users) {
            Wrapper<AtiDelegateInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("ASSIGNEE", assignee);
            List<AtiDelegateInfo> delegateInfoList = atiDelegateInfoService.selectList(wrapper);
            if (null != delegateInfoList && delegateInfoList.size() > 0) {
                for (AtiDelegateInfo delegateInfo : delegateInfoList) {
                    //设置一个流程委托
                    if (processDefinitionId.equals(delegateInfo.getProcDefId())) {
                        delegateTask.addCandidateUser(delegateInfo.getAttorney());
                        users.add(delegateInfo.getAttorney());
                        continue;
                    }

                    //设置一类流程委托
                    if (StringUtils.isEmpty(delegateInfo.getProcDefId())) {
                        Long atiCategoryId = delegateInfo.getAtiCategoryId();
                        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().
                                processDefinitionCategory(String.valueOf(atiCategoryId)).active().list();
                        if (processDefinitionList == null || processDefinitionList.size() == 0) {
                            return users;
                        }
                        for (ProcessDefinition processDefinition : processDefinitionList) {
                            if (processDefinition.getId().equals(delegateInfo.getProcDefId())) {
                                delegateTask.addCandidateUser(delegateInfo.getAttorney());
                                users.add(delegateInfo.getAttorney());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return users;
    }

}
