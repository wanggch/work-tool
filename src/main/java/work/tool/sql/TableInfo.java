package work.tool.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    private String name;
    private String desc;
    private String schema;
    private String primaryKey;
    private List<ColumnInfo> columns;
}
