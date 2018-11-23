package cn.ffcs.uoo.base.common.tool.util;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/** mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 *  yml 加配置 meta-object-handler: cn.ffcs.uoo.base.common.tool.util.UooMetaObjectHandler
 *  @author wudj
 *  @Date 2018/11/14
 * */
@Component
public class UooMetaObjectHandler extends MetaObjectHandler {
    //新增填充
    @Override
    public void insertFill(MetaObject metaObject) {

        Object statusCd = metaObject.getValue("statusCd");
        Object createDate = metaObject.getValue("createDate");
        Object createUser = metaObject.getValue("createUser");
        Object updateDate = metaObject.getValue("updateDate");
        Object updateUser = metaObject.getValue("updateUser");
        Object statusDate = metaObject.getValue("statusDate");

        if(null == statusCd){
            metaObject.setValue("statusCd", "1000");
        }
        if(null == statusDate){
            metaObject.setValue("statusDate", new Date());
        }
        if(null == createDate){
            metaObject.setValue("createDate", new Date());
        }
        if(null == createUser){
            metaObject.setValue("createUser", -1L);
        }
        if(null == updateDate){
            metaObject.setValue("updateDate", new Date());
        }
        if(null == updateUser){
            metaObject.setValue("updateUser", -1L);
        }
        if(metaObject.hasGetter("enableDate") && null == metaObject.getValue("enableDate")){
            metaObject.setValue("enableDate", new Date());
        }
        if(metaObject.hasGetter("disableDate") && null == metaObject.getValue("disableDate")){
            metaObject.setValue("disableDate", DateUtils.getDatebystr("20991231", "yyyyMMdd"));
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String statusCdStr = "statusCd";
        String updateDateStr = "updateDate";
        String updateUserStr = "updateUser";
        String statusDateStr = "statusDate";
        if(!metaObject.hasGetter("updateDate")){
            statusCdStr = "et." + statusCdStr;
            updateDateStr = "et." + updateDateStr;
            updateUserStr = "et." + updateUserStr;
            statusDateStr = "et." + statusDateStr;
        }


        Object statusCd = metaObject.getValue(statusCdStr);
        Object updateDate = metaObject.getValue(updateDateStr);
        Object updateUser = metaObject.getValue(updateUserStr);
        if(null == updateDate){
            metaObject.setValue(updateDateStr, new Date());
        }
        if(null == updateUser){
            metaObject.setValue(updateUserStr, -2L);
        }
        if(null == statusCd){
            metaObject.setValue(statusCdStr, "1000");
            metaObject.setValue(statusDateStr, new Date());
        }else {
            if(!"1000".equals(String.valueOf(statusCd))){
                metaObject.setValue(statusDateStr, new Date());
            }
        }

    }

}

