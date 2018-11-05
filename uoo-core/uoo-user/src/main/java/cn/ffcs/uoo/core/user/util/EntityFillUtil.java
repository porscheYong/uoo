package cn.ffcs.uoo.core.user.util;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.entity.EntityFill;

import java.util.Date;

public class EntityFillUtil {

    public static EntityFill  addEntity(){
        EntityFill entity = new EntityFill();
        Date nowDate = new Date();
        entity.setStatusCd(BaseUnitConstants.ENTT_STATE_ACTIVE);
        entity.setStatusDate(nowDate);
        entity.setCreateDate(nowDate);
        entity.setCreateUser(-1L);
        entity.setCreateDate(nowDate);
        entity.setUpdateUser(-1L);
        entity.setUpdateDate(nowDate);
        return entity;
    }

    public static EntityFill updateEntity(){
        EntityFill entity = new EntityFill();
        Date nowDate = new Date();
        entity.setUpdateUser(-2L);
        entity.setUpdateDate(nowDate);
        return entity;
    }

    public static EntityFill delEntity(){
        EntityFill entity = new EntityFill();
        Date nowDate = new Date();
        entity.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        entity.setStatusDate(nowDate);
        entity.setUpdateUser(-3L);
        entity.setUpdateDate(nowDate);
        return entity;
    }
}
