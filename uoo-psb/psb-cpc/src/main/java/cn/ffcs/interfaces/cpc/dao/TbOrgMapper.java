package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.TbOrg;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TbOrgMapper extends BaseMapper<TbOrg> {
    /**
     * 渠道信息落到TB_ORG表中
     * @param tbOrg
     */
    void insertChannel(TbOrg tbOrg);
}