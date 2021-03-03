CREATE TABLE "${schema}"."${name}" (
<#list columns! as column>
    <#switch column.type>
        <#case "VARCHAR2">  "${column.name}" ${column.type}(${column.length})<#if column_index < columns?size - 1>, </#if>${"\n"}<#break>
        <#case "NUMBER">  "${column.name}" ${column.type}(${column.precision},${column.scale})<#if column_index < columns?size - 1>, </#if>${"\n"}<#break>
        <#default>  "${column.name}" ${column.type}(${column.length})<#if column_index < columns?size - 1>, </#if>${"\n"}
    </#switch>
</#list>
);
COMMENT ON TABLE "${schema}"."${name}" IS '${desc}';
<#list columns! as column>
COMMENT ON COLUMN "${schema}"."${name}"."${column.name}" IS '${column.desc}';
</#list>
<#if primaryKey??>
ALTER TABLE "${schema}"."${name}" ADD CONSTRAINT "PK_${name}" PRIMARY KEY ("${primaryKey}");
</#if>
