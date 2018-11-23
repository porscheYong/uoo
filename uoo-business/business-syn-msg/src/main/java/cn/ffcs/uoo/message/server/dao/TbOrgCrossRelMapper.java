package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbOrgCrossRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbOrgCrossRelMapper extends BaseMapper<TbOrgCrossRel> {

    @Select("select cross_tran crossTran,rela_type relaType from tb_org_cross_rel where rela_type like #{systemCode} and status_cd = 1000 and org_id = #{orgId}")
    List<TbOrgCrossRel> getListByOrgIdAndSystemCode(@Param("orgId") Long orgId,@Param("systemCode") String systemCode);

}