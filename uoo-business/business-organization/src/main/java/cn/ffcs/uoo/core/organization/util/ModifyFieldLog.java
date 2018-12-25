//package cn.ffcs.uoo.core.organization.util;/**
// * @description:
// * @author: ffcs-gzb
// * @date: 2018-12-24
// */
//
//import com.baomidou.mybatisplus.annotations.TableField;
//import com.baomidou.mybatisplus.annotations.TableId;
//import com.baomidou.mybatisplus.annotations.TableName;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//
///**
// * <p>
// *
// * </p>
// *
// * @author ffcs-gzb
// * @since 2018/12/24
// */
//public class ModifyFieldLog {
//
//
//    public String addModifyHistory(Object oldObj,Object newObj){
//        try {
//            if(oldObj==null && newObj!=null){
//
//                String operType = "insert";
//                String tableNam = getTableName(newObj);
//                if(StrUtil.isNullOrEmpty(tableNam)){
//                    return "对象对应表不存在";
//                }
//                Long newObjId = getTableId(newObj);
//                if(StrUtil.isNullOrEmpty(newObjId)){
//                    return "对象标识不能为空";
//                }
//                Long tabId = getCommonTableId(tableNam);
//                if(StrUtil.isNullOrEmpty(tabId)){
//                    return "SYS_TABLE数据没有配置";
//                }
//                Long mdyid = getId();
//                ModifyHistory modifyHistory = new ModifyHistory();
//                modifyHistory.setModifyId(mdyid);
//                modifyHistory.setTabId(tabId);
//                modifyHistory.setRecordId(newObjId.toString());
//                modifyHistory.setOperateType(operType);
//                modifyHistory.setBatchNumber(getBatchNumber());
//                add(modifyHistory);
//            }else if(oldObj!=null && newObj==null){
//                String operType  = "delete";
//                String tableNam = getTableName(oldObj);
//                if(StrUtil.isNullOrEmpty(tableNam)){
//                    return "对象对应表不存在";
//                }
//                Long newObjId = getTableId(oldObj);
//                if(StrUtil.isNullOrEmpty(newObjId)){
//                    return "对象标识不能为空";
//                }
//                Long tabId = getCommonTableId(tableNam);
//                if(StrUtil.isNullOrEmpty(tabId)){
//                    return "SYS_TABLE数据没有配置";
//                }
//                Long mdyid = getId();
//                ModifyHistory modifyHistory = new ModifyHistory();
//                modifyHistory.setModifyId(mdyid);
//                modifyHistory.setTabId(tabId);
//                modifyHistory.setRecordId(newObjId.toString());
//                modifyHistory.setOperateType(operType);
//                modifyHistory.setBatchNumber(getBatchNumber());
//                add(modifyHistory);
//            }else if(oldObj!=null && newObj!=null){
//
//                if(!newObj.getClass().getName().equals(oldObj.getClass().getName())){
//                    return "新对象和旧对象类型不一致";
//                }
//                String tableNam = getTableName(oldObj);
//                if(StrUtil.isNullOrEmpty(tableNam)){
//                    return "对象对应表不存在";
//                }
//                Long oldObjId = getTableId(oldObj);
//                if(StrUtil.isNullOrEmpty(oldObjId)){
//                    return "对象标识不能为空";
//                }
//                Long tabId = getCommonTableId(tableNam);
//                if(StrUtil.isNullOrEmpty(tabId)){
//                    return "SYS_TABLE数据没有配置";
//                }
//                String bathNum = getBatchNumber();
//                DiffNode diff = ObjectDifferBuilder.buildDefault().compare(oldObj, newObj);
//                if(diff.hasChanges()){
//                    Class<?> newCls = Class.forName(oldObj.getClass().getName());
//                    diff.visit(new DiffNode.Visitor(){
//                        @Override
//                        public void node(DiffNode node, Visit visit) {
//                            String operType = "update";
//                            final Object oldValue = node.canonicalGet(oldObj);
//                            final Object newValue = node.canonicalGet(newObj);
//                            String path = node.getPath().toString().replace("/","");
//                            String colName = "";
//                            if(!StrUtil.isNullOrEmpty(path)){
//                                Field[] fields = newCls.getDeclaredFields();
//                                for(Field f : fields){
//                                    if(path.equals(f.getName())){
//                                        TableField annotation = f.getAnnotation(TableField.class);
//                                        if(annotation!=null){
//                                            colName = annotation.value();
//                                        }
//                                        break;
//                                    }
//                                }
//                                Long mdyid = getId();
//                                ModifyHistory modifyHistory = new ModifyHistory();
//                                modifyHistory.setModifyId(mdyid);
//                                modifyHistory.setTabId(tabId);
//                                modifyHistory.setRecordId(oldObjId.toString());
//                                modifyHistory.setOperateType(operType);
//                                modifyHistory.setFieldName(colName);
//                                modifyHistory.setFieldValue(oldValue.toString());
//                                modifyHistory.setBatchNumber(bathNum);
//                                add(modifyHistory);
//                            }
//                        }
//                    });
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return e.getMessage();
//        } catch (Exception e){
//            e.printStackTrace();
//            return e.getMessage();
//        }
//        return "";
//    }
//
//
//
//    public String getTableName(Object obj) throws ClassNotFoundException {
//        Class<?> newCls = Class.forName(obj.getClass().getName());
//        Annotation newAnn = newCls.getAnnotation(TableName.class);
//        if(newAnn!=null){
//            return ((TableName) newAnn).value();
//        }
//        return "TableName不存在";
//    }
//
//    public Long getTableId(Object obj) throws ClassNotFoundException, IllegalAccessException {
//        Class<?> newCls = Class.forName(obj.getClass().getName());
//        Long objId = null;
//        Field[] oldfields = newCls.getDeclaredFields();
//        for(Field f : oldfields){
//            boolean flag = f.isAccessible();
//            f.setAccessible(true);
//            TableId annotation = f.getAnnotation(TableId.class);
//            if(annotation!=null){
//                objId = (Long)f.get(obj);
//            }
//            f.setAccessible(flag);
//        }
//        return objId;
//    }
//}
