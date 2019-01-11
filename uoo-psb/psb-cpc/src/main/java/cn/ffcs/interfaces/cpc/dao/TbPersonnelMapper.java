package cn.ffcs.interfaces.cpc.dao;


import cn.ffcs.interfaces.cpc.pojo.TbPersonnel;
import com.baomidou.mybatisplus.mapper.BaseMapper;


public interface TbPersonnelMapper extends BaseMapper<TbPersonnel> {

    /**
     * 插入人员信息
     * @param tbPersonnel
     */
    void insertValueOfPersonnel(TbPersonnel tbPersonnel);
}
