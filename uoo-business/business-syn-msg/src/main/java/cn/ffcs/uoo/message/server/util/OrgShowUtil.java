package cn.ffcs.uoo.message.server.util;
import cn.ffcs.uoo.message.server.pojo.TbAreaCode;
import java.math.BigDecimal;
import cn.ffcs.uoo.message.server.pojo.TbPoliticalLocation;
import com.google.common.collect.Lists;
import cn.ffcs.uoo.message.server.vo.TbOrgRelVo;
import cn.ffcs.uoo.message.server.vo.OrgLevelVo;
import java.util.Date;
import java.util.*;

import cn.ffcs.uoo.message.server.pojo.TbBusinessSystem;
import cn.ffcs.uoo.message.server.pojo.TbOrgCrossRel;
import cn.ffcs.uoo.message.server.vo.*;

public class OrgShowUtil {
    public static void noShow(TbOrgVo vo, TbBusinessSystem system){
        if (vo == null) {
            return;
        }
        vo.setLocId(null);
        vo.setAreaCodeId(null);
        vo.setBusinessSystemId(null);
        vo.setOrgTreeId(null);
        if(vo.getExtendInfo2() != null && vo.getExtendInfo2().size() !=0){
            Set<String> set = new HashSet<>();

            vo.getExtendInfo2().forEach((t)->{
                set.add(t.getColumnName());
            });

            List<Map<String,Object>> mapArrayList = new ArrayList<>();

            set.forEach((t)->{
                Map<String,Object> map = new HashMap<>();
                List<Object> list = new ArrayList<>();
                vo.getExtendInfo2().forEach((temp)->{
                    if(temp.getColumnName().equals(t)){
                        list.add(temp.getValue());
                    }

                });
                map.put("column",t);
                map.put("value",list);
                mapArrayList.add(map);
            });

            vo.setExtendInfo2(null);
            vo.setExtendInfo(mapArrayList);
        }

        if(vo.getOrgLevels() != null){
            vo.getOrgLevels().setOrgLevelId(null);
            vo.getOrgLevels().setOrgId(null);
            vo.getOrgLevels().setOrgTreeId(null);
            vo.getOrgLevels().setStatusCd(null);
            vo.getOrgLevels().setCreateDate(null);
            vo.getOrgLevels().setCreateUser(null);
            vo.getOrgLevels().setUpdateDate(null);
            vo.getOrgLevels().setUpdateUser(null);
            vo.getOrgLevels().setStatusDate(null);
            if(vo.getOrgLevels().getOrgTreeInfo() != null){
                vo.getOrgLevels().getOrgTreeInfo();
                vo.getOrgLevels().getOrgTreeInfo().setOrgTreeId(null);
                vo.getOrgLevels().getOrgTreeInfo().setStatusCd(null);
                vo.getOrgLevels().getOrgTreeInfo().setCreateDate(null);
                vo.getOrgLevels().getOrgTreeInfo().setCreateUser(null);
                vo.getOrgLevels().getOrgTreeInfo().setUpdateDate(null);
                vo.getOrgLevels().getOrgTreeInfo().setUpdateUser(null);
                vo.getOrgLevels().getOrgTreeInfo().setStatusDate(null);
            }
        }

        if(vo.getOrgRelations() != null){
            vo.getOrgRelations().setOrgRelId(null);
            vo.getOrgRelations().setOrgId(null);
            vo.getOrgRelations().setOrgRelTypeId(null);
            vo.getOrgRelations().setStatusCd(null);
            vo.getOrgRelations().setCreateDate(null);
            vo.getOrgRelations().setCreateUser(null);
            vo.getOrgRelations().setUpdateDate(null);
            vo.getOrgRelations().setUpdateUser(null);
            vo.getOrgRelations().setStatusDate(null);
            if(vo.getOrgRelations().getOrgRefTypeInfo() != null ){
                vo.getOrgRelations().getOrgRefTypeInfo().setOrgRelTypeId(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setStatusCd(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setCreateDate(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setCreateUser(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setUpdateDate(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setUpdateUser(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setStatusDate(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setSupOrgRefTypeName(null);
                vo.getOrgRelations().getOrgRefTypeInfo().setSupOrgRefTypeId(null);
            }
        }

        if(vo.getOrgTypes() != null){
            vo.getOrgTypes().forEach((temp)->{
                temp.setOrgTypeId(null);
                temp.setSupPkOrgType(null);
                temp.setSupPkOrgTypeName(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

      if(vo.getOrgCrossRelations() != null){
          /*Iterator<TbOrgCrossRel> it = vo.getOrgCrossRelations().iterator();

          while(it.hasNext()){
              TbOrgCrossRel rel = it.next();
              if(rel.getRelaType().indexOf(system.getSystemCode()+"_") != 0){
                  it.remove();
              }
          }*/
          vo.getOrgCrossRelations().forEach((temp)->{
              temp.setOrgCrossRelId(null);
              temp.setOrgId(null);
              temp.setStatusCd(null);
              temp.setCreateDate(null);
              temp.setCreateUser(null);
              temp.setUpdateDate(null);
              temp.setUpdateUser(null);
              temp.setStatusDate(null);
          });
        }

        if(vo.getOrgCerts() != null){
            vo.getOrgCerts().forEach((temp)->{
                temp.setCertId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if(vo.getContactRelations() != null){
            vo.getContactRelations().forEach((temp)->{
                if(temp.getPersonalInfo() != null){
                    temp.getPersonalInfo().setPersonnelId(null);
                    temp.getPersonalInfo().setStatusCd(null);
                    temp.getPersonalInfo().setCreateDate(null);
                    temp.getPersonalInfo().setCreateUser(null);
                    temp.getPersonalInfo().setUpdateDate(null);
                    temp.getPersonalInfo().setUpdateUser(null);
                    temp.getPersonalInfo().setStatusDate(null);
                }
                temp.setOrgContactRelId(null);
                temp.setOrgId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if(vo.getPoliticalLocationInfo() != null){
            vo.getPoliticalLocationInfo().setLocId(null);
            vo.getPoliticalLocationInfo().setStatusCd(null);
            vo.getPoliticalLocationInfo().setCreateDate(null);
            vo.getPoliticalLocationInfo().setCreateUser(null);
            vo.getPoliticalLocationInfo().setUpdateDate(null);
            vo.getPoliticalLocationInfo().setUpdateUser(null);
            vo.getPoliticalLocationInfo().setStatusDate(null);
        }

        if(vo.getAreaCodeInfo() != null){
            vo.getAreaCodeInfo().setAreaCodeId(null);
            vo.getAreaCodeInfo().setAreaNbr(null);
            vo.getAreaCodeInfo().setCommonRegionId(null);
            vo.getAreaCodeInfo().setStatusCd(null);
            vo.getAreaCodeInfo().setCreateDate(null);
            vo.getAreaCodeInfo().setCreateUser(null);
            vo.getAreaCodeInfo().setUpdateDate(null);
            vo.getAreaCodeInfo().setUpdateUser(null);
            vo.getAreaCodeInfo().setStatusDate(null);
        }
    }
}