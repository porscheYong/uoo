package cn.ffcs.uoo.web.maindata.tool;


import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;

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
 * @ClassName Operat
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/23 12:16
 * @Version 1.0.0
*/
public enum Operat {

    CREATE("查询", LoginConsts.QUERY_STR), UPDATE("修改", LoginConsts.UPDATE_STR), DELTE("删除", LoginConsts.DELETE_STR);

    private String desc;
    private String code;

    private Operat(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public static String getDesc(String code) {
        for (Operat c : Operat.values()) {
            if (c.getCode() == code) {
                return c.desc;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}