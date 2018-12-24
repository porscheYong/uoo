//package cn.ffcs.uoo.core.organization.Api.service;/**
// * @description:
// * @author: ffcs-gzb
// * @date: 2018-12-21
// */
//import javax.persistence.Column;
//import javax.persistence.Table;
//import cn.ffcs.uoo.core.organization.entity.Org;
//import cn.ffcs.uoo.core.organization.util.StrUtil;
//import cn.ffcs.uoo.core.organization.vo.OrgVo;
//import com.baomidou.mybatisplus.annotations.TableField;
//import com.baomidou.mybatisplus.annotations.TableId;
//import com.baomidou.mybatisplus.annotations.TableName;
//import de.danielbechler.diff.ObjectDifferBuilder;
//import de.danielbechler.diff.node.DiffNode;
//import de.danielbechler.diff.node.Visit;
//import de.danielbechler.diff.path.NodePath;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Collections;
//import java.util.Map;
//
///**
// * <p>
// *
// * </p>
// *
// * @author ffcs-gzb
// * @since 2018/12/21
// */
//public class test {
//    public static String addModifyHistory(Object oldObj,Object newObj) {
//        try {
//            String operType = "";
//            if(oldObj==null && newObj!=null){
//                operType = "insert";
//                Class<?> newCls = Class.forName(newObj.getClass().getName());
//                Annotation newAnn = newCls.getAnnotation(TableName.class);
//                if(newAnn==null){
//                    return "对象对应表不存在";
//                }
//                String tableNam = ((TableName) newAnn).value();
//                Long newObjId = null;
//                Field[] fields = newCls.getDeclaredFields();
//                for(Field f : fields){
//                    boolean flag = f.isAccessible();
//                    f.setAccessible(true);
//                    TableId annotation = f.getAnnotation(TableId.class);
//                    if(annotation!=null){
//                        newObjId = (Long)f.get(newObj);
//                        System.out.println("新值ID："+newObjId);
//                    }
//                    f.setAccessible(flag);
//                }
//
//
//            }else if(oldObj!=null && newObj==null){
//                operType = "delete";
//            }else{
//                if(!newObj.getClass().getName().equals(oldObj.getClass().getName())){
//                    return "新对象和旧对象类型不一致";
//                }
//                Class<?> newCls = Class.forName(newObj.getClass().getName());
//                Annotation newAnn = newCls.getAnnotation(TableName.class);
//                if(newAnn==null){
//                    return "对象对应表不存在";
//                }
//                String tableNam = ((TableName) newAnn).value();
//
//                Long newObjId = null;
//                Field[] fields = newCls.getDeclaredFields();
//                for(Field f : fields){
//                    boolean flag = f.isAccessible();
//                    f.setAccessible(true);
//                    TableId annotation = f.getAnnotation(TableId.class);
//                    if(annotation!=null){
//                        newObjId = (Long)f.get(newObj);
//                        System.out.println("新值ID："+newObjId);
//                    }
//                    f.setAccessible(flag);
//                }
//                Class<?> oldCls = Class.forName(newObj.getClass().getName());
//                Annotation oldAnn = newCls.getAnnotation(TableName.class);
//                if(newAnn==null){
//                    return "对象对应表不存在";
//                }
//
//                Long oldObjId = null;
//                Field[] oldfields = oldCls.getDeclaredFields();
//                for(Field f : oldfields){
//                    boolean flag = f.isAccessible();
//                    f.setAccessible(true);
//                    TableId annotation = f.getAnnotation(TableId.class);
//                    if(annotation!=null){
//                        oldObjId = (Long)f.get(oldObj);
//                        System.out.println("旧值ID："+oldObjId);
//                    }
//                    f.setAccessible(flag);
//                }
//                if(StrUtil.isNullOrEmpty(oldObjId) || StrUtil.isNullOrEmpty(newObjId)){
//                    return "对象标识不能为空";
//                }
//                if(oldObjId.longValue()!=newObjId.longValue()){
//                    return "两个对象seq不相等";
//                }
//                operType = "update";
//                DiffNode diff = ObjectDifferBuilder.buildDefault().compare(oldObj, newObj);
//                if(diff.hasChanges()){
//                    System.out.println(diff.childCount());
//
//                    diff.visit(new DiffNode.Visitor() {
//                        @Override
//                        public void node(DiffNode node, Visit visit) {
//                            final Object oldValue = node.canonicalGet(oldObj);
//                            final Object newValue = node.canonicalGet(newObj);
//                            String path = node.getPath().toString().replace("/","");
//                            if(!StrUtil.isNullOrEmpty(path)){
//                                Field[] fields = newCls.getDeclaredFields();
//                                for(Field f : fields){
//                                    if(path.equals(f.getName())){
//                                        TableField annotation = f.getAnnotation(TableField.class);
//                                        if(annotation!=null){
//                                            System.out.println("columnName:"+annotation.value());
//                                        }
//                                    }
//                                }
//                                final String message = node.getPath().toString().replace("/","") + " changed from " +
//                                        oldValue + " to " + newValue;
//                                System.out.println(message);
//                            }
//
//                        }
//                    });
//
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return "sd";
////
////        Class c = obj.getClass();
////        System.out.println("类的名称是:" + c.getName());
//    }
//    public static void main(String[] args) {
//        Org orgVo1 = new Org();
//        //orgVo1.setOrgId(1L);
//        orgVo1.setFullName("11111");
//        orgVo1.setStatusCd("1000");
//        Org orgVo2 = new Org();
//        orgVo2.setOrgId(111L);
//        orgVo2.setFullName("222221111");
//        orgVo2.setStatusCd("12001111");
//        addModifyHistory(orgVo1,orgVo2);
//
//
//    }
//}
