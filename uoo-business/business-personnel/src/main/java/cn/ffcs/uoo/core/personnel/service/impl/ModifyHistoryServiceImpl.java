package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.dao.ModifyHistoryMapper;
import cn.ffcs.uoo.core.personnel.entity.ModifyHistory;
import cn.ffcs.uoo.core.personnel.service.ModifyHistoryService;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 是否为每张业务表设计历史流水记录表？？ 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-24
 */
@Service
public class ModifyHistoryServiceImpl extends ServiceImpl<ModifyHistoryMapper, ModifyHistory> implements ModifyHistoryService {


    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(ModifyHistory modifyHistory){
        modifyHistory.setStatusCd("1100");
        modifyHistory.setUpdateUser(StrUtil.isNullOrEmpty(modifyHistory.getUpdateUser())?0L:modifyHistory.getUpdateUser());
        updateById(modifyHistory);
    }

    @Override
    public void add(ModifyHistory modifyHistory){
        modifyHistory.setCreateUser(StrUtil.isNullOrEmpty(modifyHistory.getCreateUser())?0L:modifyHistory.getCreateUser());
        modifyHistory.setUpdateUser(StrUtil.isNullOrEmpty(modifyHistory.getCreateUser())?0L:modifyHistory.getCreateUser());
        insert(modifyHistory);
    }


    @Override
    public void update(ModifyHistory modifyHistory){
        modifyHistory.setUpdateUser(StrUtil.isNullOrEmpty(modifyHistory.getUpdateUser())?0L:modifyHistory.getUpdateUser());
        modifyHistory.setStatusDate(new Date());
        updateById(modifyHistory);
    }

    @Override
    public Long getCommonTableId(String tableName){
        return baseMapper.getCommonTableId(tableName);
    }

    public Long getSeqBatchNumber(){
        return baseMapper.getSeqBatchNumber();
    }

    @Override
    public String getBatchNumber(){
        Long batchNumber = getSeqBatchNumber();
        return StrUtil.padLeading(String.valueOf(batchNumber), 16 , "0");
    }
    @Override
    public String insertModifyHistory(Object newObj, Long userId, String batchNum){
        return addModifyHistory(newObj, "insert", userId, null, null, batchNum);
    }
    @Override
    public String updateModifyHistory(Object oldObj, Object newObj, Long userId, String batchNum){
        try {
        //String batchNumber = this.getBatchNumber();
        if(!newObj.getClass().getName().equals(oldObj.getClass().getName())){
            return "新对象和旧对象类型不一致";
        }
        String tableNam = getTableName(oldObj);
        if(StrUtil.isNullOrEmpty(tableNam)){
            System.out.println(oldObj+":对象对应表不存在");
            return "对象对应表不存在";
        }
        Long oldObjId = getTableId(oldObj);
        if(StrUtil.isNullOrEmpty(oldObjId)){
            System.out.println(oldObj+":对象标识不能为空");
            return "对象标识不能为空";
        }
        Long tabId = getCommonTableId(tableNam);
        if(StrUtil.isNullOrEmpty(tabId)){
            System.out.println(tableNam+":SYS_TABLE数据没有配置");
            return "SYS_TABLE数据没有配置";
        }
        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(oldObj, newObj);
        if(diff.hasChanges()){
            Class<?> newCls = Class.forName(oldObj.getClass().getName());
            diff.visit(new DiffNode.Visitor(){
                @Override
                public void node(DiffNode node, Visit visit) {
                    String operType = "update";
                    final Object oldValue = node.canonicalGet(oldObj);
                    final Object newValue = node.canonicalGet(newObj);
                    String path = node.getPath().toString().replace("/","");
                    String colName = "";
                    if(StrUtil.isNullOrEmpty(newValue) || (StrUtil.isNullOrEmpty(oldValue) && StrUtil.isNullOrEmpty(newValue))){
                        return;
                    }else{
                        if(!StrUtil.isNullOrEmpty(path)){
                            Field[] fields = newCls.getDeclaredFields();
                            String date = "";
                            for(Field f : fields){
                                if(path.equals(f.getName())){
                                    TableField annotation = f.getAnnotation(TableField.class);
                                    if(annotation!=null){
                                        Class type = f.getType();
                                        colName = annotation.value();
                                        if (("java.util.Date").equals(type.getName())) {
                                            if(!StrUtil.isNullOrEmpty(oldValue)){
                                                DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                                date=df.format(new Date(oldValue.toString()));
                                            }

                                        }
                                    }
                                    break;
                                }
                            }
                            Long mdyid = getId();
                            ModifyHistory modifyHistory = new ModifyHistory();
                            modifyHistory.setModifyId(mdyid);
                            modifyHistory.setTabId(tabId);
                            modifyHistory.setRecordId(oldObjId.toString());
                            modifyHistory.setOperateType(operType);
                            modifyHistory.setFieldName(colName);
                            if(!StrUtil.isNullOrEmpty(date)){
                                modifyHistory.setFieldValue(date);
                            }else{
                                modifyHistory.setFieldValue(StrUtil.isNullOrEmpty(oldValue)?"":oldValue.toString());
                            }
                            modifyHistory.setBatchNumber(batchNum);
                            modifyHistory.setCreateUser(userId);
                            add(modifyHistory);
                        }
                    }
                }
            });
        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "";
    }
    @Override
    public String deleteModifyHistory(Object oldObj, Long userId, String batchNum){
        return addModifyHistory(oldObj, "delete", userId, null, null, batchNum);
    }

    @Override
    public String addModifyHistory(Object oldObj,Object newObj,Long userId, String batchNum){
        if(oldObj==null && newObj!=null){
            insertModifyHistory(newObj, userId, batchNum);
        }else if(oldObj!=null && newObj==null){
            deleteModifyHistory(oldObj, userId, batchNum);
        }else if(oldObj!=null && newObj!=null){
            updateModifyHistory(oldObj, newObj, userId, batchNum);
        }
        return "";
    }

    public String getTableName(Object obj) throws ClassNotFoundException {
        Class<?> newCls = Class.forName(obj.getClass().getName());
        Annotation newAnn = newCls.getAnnotation(TableName.class);
        if(newAnn!=null){
            return ((TableName) newAnn).value();
        }
        return "TableName不存在";
    }

    public Long getTableId(Object obj) throws ClassNotFoundException, IllegalAccessException {
        Class<?> newCls = Class.forName(obj.getClass().getName());
        Long objId = null;
        Field[] oldfields = newCls.getDeclaredFields();
        for(Field f : oldfields){
            boolean flag = f.isAccessible();
            f.setAccessible(true);
            TableId annotation = f.getAnnotation(TableId.class);
            if(annotation!=null){
                objId = (Long)f.get(obj);
            }
            f.setAccessible(flag);
        }
        return objId;
    }

    @Override
    public String addModifyHistory(Object obj, String oper, Long userId, String filedName,String filedValue, String batchNum) {
        try {
            String tableNam = getTableName(obj);
            if (StrUtil.isNullOrEmpty(tableNam)) {
                System.out.println(obj + ":对象对应表不存在");
                return "对象对应表不存在";
            }
            Long oldObjId = getTableId(obj);
            if (StrUtil.isNullOrEmpty(oldObjId)) {
                System.out.println(obj + ":对象标识不能为空");
                return "对象标识不能为空";
            }
            Long tabId = getCommonTableId(tableNam);
            if (StrUtil.isNullOrEmpty(tabId)) {
                System.out.println(tableNam + ":SYS_TABLE数据没有配置");
                return "SYS_TABLE数据没有配置";
            }
            //String batchNumber = this.getBatchNumber();
            Long mdyid = getId();
            ModifyHistory modifyHistory = new ModifyHistory();
            modifyHistory.setModifyId(mdyid);
            modifyHistory.setTabId(tabId);
            modifyHistory.setRecordId(obj.toString());
            modifyHistory.setOperateType(oper);
            modifyHistory.setBatchNumber(batchNum);
            modifyHistory.setFieldName(StrUtil.strnull(filedName));
            modifyHistory.setFieldValue(StrUtil.strnull(filedValue));
            modifyHistory.setCreateUser(userId);
            add(modifyHistory);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
