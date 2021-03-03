package work.tool.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {
    private String name;
    private String desc;
    private String type;
    private Integer length;
    private Integer precision;
    private Integer scale;

    public ColumnInfo(String name, String desc, String type) {
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.length = 255;
        this.precision = 0;
        this.scale = 0;
    }
}
