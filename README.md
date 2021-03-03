
> 工作小助手。

## 开发环境

- Oracle
- JDK1.8
- Maven3.6.3
- IntelliJ IDEA 2021.3.2
- macOS Big Sur 11.2

## TODO清单

- [x] 根据实体生成建表脚本
- [ ] 根据表生成实体类

## 实体 --> 脚本

测试实体类：User.java

```java
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
```

生成脚本：

```sql
CREATE TABLE "TEST"."T_USER" (
  "ID" VARCHAR2(255), 
  "USERNAME" VARCHAR2(255), 
  "PASSWORD" VARCHAR2(255), 
  "AMOUNT" NUMBER(10,2), 
  "CREATE_TIME" VARCHAR2(255)
);
COMMENT ON TABLE "TEST"."T_USER" IS '用户表';
COMMENT ON COLUMN "TEST"."T_USER"."ID" IS '主键';
COMMENT ON COLUMN "TEST"."T_USER"."USERNAME" IS '用户名';
COMMENT ON COLUMN "TEST"."T_USER"."PASSWORD" IS '密码';
COMMENT ON COLUMN "TEST"."T_USER"."AMOUNT" IS '余额';
COMMENT ON COLUMN "TEST"."T_USER"."CREATE_TIME" IS '创建时间';
ALTER TABLE "TEST"."T_USER" ADD CONSTRAINT "PK_T_USER" PRIMARY KEY ("id");
```

## 参考文档

- [Freemarker语法入门](https://www.jianshu.com/p/c488709d6430)
- [Freemarker语法详解](https://www.cnblogs.com/Jimc/p/9791507.html)