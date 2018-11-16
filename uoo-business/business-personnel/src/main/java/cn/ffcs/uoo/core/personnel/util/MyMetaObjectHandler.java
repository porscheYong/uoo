package cn.ffcs.uoo.core.personnel.util;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/** mybatisplus自定义填充公共字段 ,即没有传的字段自动填充*/
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {
    //新增填充
    @Override
    public void insertFill(MetaObject metaObject) {

        Object statusCd = metaObject.getValue("statusCd");
        Object createDate = metaObject.getValue("createDate");
        Object createUser = metaObject.getValue("createUser");
        Object updateDate = metaObject.getValue("updateDate");
        Object updateUser = metaObject.getValue("updateUser");
        Object statusDate = metaObject.getValue("statusDate");

        //Object enableDate = metaObject.getValue("enableDate");
        //Object disableDate = metaObject.getValue("disableDate");

        if(null == statusCd){
            metaObject.setValue("statusCd", BaseUnitConstants.ENTT_STATE_ACTIVE);
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
            metaObject.setValue("disableDate", StrUtil.str2date("20991231", "yyyyMMdd"));
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
            metaObject.setValue(statusCdStr, BaseUnitConstants.ENTT_STATE_ACTIVE);
            metaObject.setValue(statusDateStr, new Date());
        }else {
            if(!BaseUnitConstants.ENTT_STATE_ACTIVE.equals(String.valueOf(statusCd))){
                metaObject.setValue(statusDateStr, new Date());
            }
        }

    }

}

