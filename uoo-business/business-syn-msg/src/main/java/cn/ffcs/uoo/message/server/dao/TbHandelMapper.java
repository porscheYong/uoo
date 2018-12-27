package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbHandel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface TbHandelMapper extends BaseMapper<TbHandel> {

    //查看有没有正在执行的批次
    @Select("select count(*) from tb_handel where status_cd = '1'")
    int checkRun();

    //获取时间最长的一个批次
    @Select("select * from tb_handel where status_cd = '0' and rownum = 1 order by handle_date")
    TbHandel getTbHandleByTime();


}