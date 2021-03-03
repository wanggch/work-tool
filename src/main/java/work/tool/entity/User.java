package work.tool.entity;


import lombok.Data;
import work.tool.annotation.Column;
import work.tool.annotation.Id;
import work.tool.annotation.Table;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "T_USER", desc = "用户表", schema = "TEST")
public class User {
    @Id
    @Column(name = "ID", desc = "主键")
    private String id;
    @Column(name = "USERNAME", desc = "用户名")
    private String username;
    @Column(name = "PASSWORD", desc = "密码")
    private String password;
    @Column(name = "AMOUNT", desc = "余额", precision = 10, scale = 2)
    private BigDecimal amount;
    @Column(name = "CREATE_TIME", desc = "创建时间")
    private Date createTime;
}
