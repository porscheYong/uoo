package cn.ffcs.uoo.core.personnel.client;

import cn.ffcs.uoo.core.personnel.util.ResponseResult;
import cn.ffcs.uoo.core.personnel.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "business-organization")
public interface OrgPersonRelClient {
    /**
     * 人员新增，新增组织人员关系
     * @param psonOrgVo
     * @return
     */
    @RequestMapping(value = "/orgPersonRel/addOrgPsn", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
    public Object addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgVo);

    /**
     * 人员 组织关系
     * @param orgId
     * @param orgTreeId
     * @param personnelId
     * @param search
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/orgPersonRel/getPerOrgRelPage", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(@RequestParam("orgId") Integer orgId, @RequestParam("orgTreeId") Long orgTreeId,
                                                            @RequestParam("personnelId") Integer personnelId, @RequestParam("search") String search,
                                                            @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNo") Integer pageNo,
                                                            @RequestParam("accout") String accout);

    /**
     * 删除 人员组织关系
     * @param psonOrgVo
     * @return
     */
    @RequestMapping(value = "/orgPersonRel/deletePsnRel", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
    public Object deletePsnRel(@RequestBody PsonOrgVo psonOrgVo);


}
