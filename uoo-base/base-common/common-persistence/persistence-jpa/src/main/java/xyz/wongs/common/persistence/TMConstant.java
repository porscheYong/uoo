package xyz.wongs.common.persistence;

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
 * @ClassName TMConstant
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/11/13 21:03
 * @Version 1.0.0
*/
public enum TMConstant {


    EFF("生效","1000"),INVALID("失效","1100"),FREEZE("冻结","1200");

    private String name;
    private String code;

    TMConstant(String name,String code) {
        this.name = name;
        this.code = code;
    }

    public static String getName(String code) {
        for (TMConstant c : TMConstant.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
