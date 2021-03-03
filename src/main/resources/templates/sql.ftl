CREATE TABLE "${schema}"."${table}" (
<#list columnList! as column>
    <#switch column.type>
        <#case "VARCHAR2">  "${column.name}" ${column.type}(${column.length})<#if column_index < columnList?size - 1>, </#if>${"\n"}<#break>
        <#case "NUMBER">  "${column.name}" ${column.type}(${column.precision},${column.scale})<#if column_index < columnList?size - 1>, </#if>${"\n"}<#break>
        <#default>  "${column.name}" ${column.type}(${column.length})<#if column_index < columnList?size - 1>, </#if>${"\n"}
    </#switch>
</#list>
);
<#list columnList! as column>
COMMENT ON COLUMN "${schema}"."${table}"."${column.name}" IS '${column.desc}';
</#list>
<#if primaryKey??>
ALTER TABLE "${schema}"."${table}" ADD CONSTRAINT "PK_${table}" PRIMARY KEY ("${primaryKey}");
</#if>
