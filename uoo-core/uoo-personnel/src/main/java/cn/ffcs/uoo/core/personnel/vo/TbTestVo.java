package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.core.personnel.entity.TbEdu;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import lombok.Data;

import java.util.List;

@Data
public class TbTestVo {

    private Integer id;

    private String name;

    private TbPersonnel tbPersonnel;

    private List<TbEdu> tbEduList;


}
