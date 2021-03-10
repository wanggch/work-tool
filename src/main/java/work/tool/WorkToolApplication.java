package work.tool;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.tool.annotation.Column;
import work.tool.annotation.Id;
import work.tool.annotation.Table;
import work.tool.sql.ColumnInfo;
import work.tool.sql.TableInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目启动类
 * @author: wanggc
 */
//@Slf4j
@SpringBootApplication
public class WorkToolApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkToolApplication.class);
    private static final Map<String, String> TYPE_MAPPER = new HashMap<>(16);
    static {
        TYPE_MAPPER.put("java.lang.String", "VARCHAR2");
        TYPE_MAPPER.put("java.util.Date", "VARCHAR2");
        TYPE_MAPPER.put("java.math.BigDecimal", "NUMBER");
    }
    public static void main(String[] args) {
        SpringApplication.run(WorkToolApplication.class, args);
        try {
            // 类名
            String className = "work.tool.entity.User";
            Template template = getTemplate("sql.ftl");
            TableInfo tableInfo = getRenderData(className);
            // 用于渲染模板的数据
            Map<String, Object> data = BeanUtil.beanToMap(tableInfo);
            LOGGER.info("## render data ##\n{}", new Gson().toJson(data));
            // 模板渲染结果
            String result = template.render(data);
            LOGGER.info("## render result ##\n{}", result);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("## class not found. ##");
        }
    }

    public static TableInfo getRenderData(String className) throws ClassNotFoundException  {
        Class clazz = Class.forName(className);
        // @Table注解
        boolean isTableAnnotationPresent = clazz.isAnnotationPresent(Table.class);
        if (!isTableAnnotationPresent) {
            // 如果没有@Table注解，流程结束
            throw new RuntimeException("缺少@Table注解！");
        }
        Table table = (Table) clazz.getAnnotation(Table.class);
        LOGGER.info("table name: {}, desc: {}, schema: {}", table.name(), table.desc(), table.schema());
        // 字段信息列表：用于保存字段信息
        List<ColumnInfo> columnInfos = Lists.newArrayList();
        // 获取所有字段
        Field[] fields = clazz.getDeclaredFields();
        // 主键
        String primaryKey = null;
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldType = field.getType().getName();
            // @Column注解
            boolean isColumnAnnotationPresent = field.isAnnotationPresent(Column.class);
            // @Id注解
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
                columnInfos.add(new ColumnInfo(StrUtil.toUnderlineCase(fieldName).toUpperCase(), "-", TYPE_MAPPER.get(fieldType)));
            }
        }
        return new TableInfo(table.name(), table.desc(), table.schema(), primaryKey, columnInfos);
    }

    public static Template getTemplate(String templateName) {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        return engine.getTemplate(templateName);
    }
}