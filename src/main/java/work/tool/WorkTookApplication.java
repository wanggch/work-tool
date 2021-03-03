package work.tool;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.tool.annotation.Column;
import work.tool.annotation.Id;
import work.tool.annotation.Table;
import work.tool.sql.ColumnInfo;
import work.tool.sql.TableInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 项目启动类
 * @author: wanggc
 */
//@Slf4j
//@SpringBootApplication
public class WorkTookApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkTookApplication.class);
    private static final Map<String, String> TYPE_MAPPER = new HashMap<>(16);
    static {
        TYPE_MAPPER.put("java.lang.String", "VARCHAR2");
        TYPE_MAPPER.put("java.util.Date", "VARCHAR2");
        TYPE_MAPPER.put("java.math.BigDecimal", "NUMBER");
    }
    public static void main(String[] args) {
//        SpringApplication.run(WorkTookApplication.class, args);
//        LOGGER.info("## work tool application run successfully. ##");
        try {
            String className = "work.tool.entity.User";
            Class c = Class.forName(className);

            boolean isTableAnnotationPresent = c.isAnnotationPresent(Table.class);
            if (!isTableAnnotationPresent) {
                // 如果没有@Table注解，流程结束
                return;
            }
            Table table = (Table) c.getAnnotation(Table.class);
            LOGGER.info("table name: {}, desc: {}, schema: {}", table.name(), table.desc(), table.schema());

            List<ColumnInfo> columnInfos = Lists.newArrayList();
            Field[] fields = c.getDeclaredFields();
            String primaryKey = null;
            for (Field field : fields) {
                String fieldName = field.getName();
                String fieldType = field.getType().getName();
                boolean isColumnAnnotationPresent = field.isAnnotationPresent(Column.class);
                boolean isIdAnnotationPresent = field.isAnnotationPresent(Id.class);
                if (isIdAnnotationPresent) {
                    primaryKey = fieldName;
                }
                if (isColumnAnnotationPresent) {
                    Column column = field.getAnnotation(Column.class);
                    LOGGER.info("column name: {}, desc: {}, type: {}", column.name(), column.desc(), TYPE_MAPPER.get(fieldType));
                    columnInfos.add(new ColumnInfo(column.name(), column.desc(), TYPE_MAPPER.get(fieldType), column.length(), column.precision(), column.scale()));
                } else {
                    LOGGER.info("column name: {}, type: {}", fieldName, TYPE_MAPPER.get(fieldType));
                    columnInfos.add(new ColumnInfo(fieldName, "", TYPE_MAPPER.get(fieldType)));
                }
            }
            TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
            Template template = engine.getTemplate("sql.ftl");
            TableInfo tableInfo = new TableInfo(table.name(), table.desc(), table.schema(), primaryKey, columnInfos);
            Map<String, Object> data = new ObjectMapper().convertValue(tableInfo, Map.class);
            LOGGER.info("## render data ##\n{}", new Gson().toJson(data));
            String result = template.render(data);
            LOGGER.info("## render result ##\n{}", result);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("## class not found. ##");
        }
    }
}