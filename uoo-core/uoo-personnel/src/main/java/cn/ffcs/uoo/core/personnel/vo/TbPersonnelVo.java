package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName TbPersonnelVo
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/7 11:12
 * @Version 1.0.0
*/

@Data
public class TbPersonnelVo extends BaseVo {


    /**
     * 人员姓名
     */
    private String psnName;

    /**
     * 员工工号
     */
    private String psnNbr;

    /**
     * 证件号
     */
    private String certNo;

    /**
     * 状态
     */
    private String statusCd;

    private String orgId;
}
