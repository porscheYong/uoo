package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
 * <p>
 <#if table.comment??>
* ${table.comment} Mapper 接口
</#if>
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
    <#list table.fields as field><#if field.keyFlag><#if field.convert>${field.propertyType}</#if></#if></#list> getId();
}
</#if>
