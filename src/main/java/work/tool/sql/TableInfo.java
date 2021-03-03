package work.tool.sql;

import lombok.Data;

import java.util.List;

@Data
public class TableInfo {
    private String name;
    private String desc;
    private List<ColumnInfo> columns;
}
