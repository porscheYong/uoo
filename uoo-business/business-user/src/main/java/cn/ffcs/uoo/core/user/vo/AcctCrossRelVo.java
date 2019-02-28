package cn.ffcs.uoo.core.user.vo;

import lombok.Data;
/**
 * 销售号编码
 *
 * @author wudj
 * @date 2019/02/26
 */
@Data
public class AcctCrossRelVo {

    private Long acctCrossId;

    private Long acctId;

    private String crossTran;

    private String relaType;

}
