package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.TbAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TbAcctMapper  extends BaseMapper<TbAcct> {
    TbAcct selectByPersonnelId(@Param("personnelId") Long personnelId);
}