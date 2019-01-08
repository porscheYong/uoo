package cn.ffcs.uoo.system.util;

import cn.ffcs.uoo.system.consts.BaseUnitConstants;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/** mybatisplus自定义填充公共字段 ,即没有传的字段自动填充*/
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {
    //新增填充
    @Override
    public void insertFill(MetaObject metaObject) {

        if(null == getFieldValByName("statusCd", metaObject)){
            setFieldValByName("statusCd", BaseUnitConstants.ENTT_STATE_ACTIVE, metaObject);
        }
        if(null == getFieldValByName("statusDate", metaObject)){
            setFieldValByName("statusDate", new Date(), metaObject);
        }
        if(null == getFieldValByName("createDate", metaObject)){
            setFieldValByName("createDate", new Date(), metaObject);
        }
        if(null == getFieldValByName("createUser", metaObject)){
            setFieldValByName("createUser", -1L, metaObject);
        }
        if(null == getFieldValByName("updateDate", metaObject)){
            setFieldValByName("updateDate", new Date(), metaObject);
        }
        if(null == getFieldValByName("updateUser", metaObject)){
            setFieldValByName("updateUser", -1L, metaObject);
        }
        if(this.hasGetter("enableDate", metaObject) && null == getFieldValByName("enableDate", metaObject)){
            setFieldValByName("enableDate", new Date(), metaObject);
        }
        if(this.hasGetter("disableDate", metaObject) && null == getFieldValByName("disableDate", metaObject)){
            setFieldValByName("disableDate", StrUtil.str2date("20991231", "yyyyMMdd"), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        setFieldValByName("updateDate", new Date(), metaObject);
        if(null == getFieldValByName("updateUser", metaObject)){
            setFieldValByName("updateUser", -2L, metaObject);
        }
    }

    public boolean hasGetter(String fieldName, MetaObject metaObject){
        if (metaObject.hasGetter(fieldName)) {
            return true;
        } else if (metaObject.hasGetter(META_OBJ_PREFIX + "." + fieldName)) {
            return true;
        }
        return false;
    }

}

