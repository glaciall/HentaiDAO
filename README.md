# HentaiDAO
使用全新而又熟悉的方式来编写CRUD
```java
public class Test
{
    public static void main(String[] args) throws Exception
    {
        int sex = 1;
        String name = "test";
        // 查询用户表中，按id倒排，自结果集的第100行起取10行
        // 如果sex值大于0，则参与到where从句中来
        // 如果name不为空字符串，则参与到where从句中来，并且转为like模糊匹配
        List<User> users = select(User.class)
            .where(clause("sex = ?", gtz(sex)).and("name = ?", like(name)))
            .orderBy("id", "desc")
            .queryForLimit(100, 10);
    }
}
```
## 特点
1. 与SQL标准高度相似的API设计，上手快
2. 专为多重筛选条件设计的clause从句，让你一句话写到底，不用写又臭又长的条件拼接从句
3. 简单的`insert`与`update`语句，避免了名与值的位置匹配问题
4. 表字段名与实体类属性名的自动转换/映射

## 举例说明
### 结构说明
1. 继承HentaiDAO，获得CRUD的快捷方法，在DAO类中声明具体的增删改查语句
2. 实现JDBCBridge接口，执行由DAO类所生成的要求执行的SQL语句

### 用户表结构 
| 字段名 | 类型 | 长度 |
|---|---|---|
| id | bigint | -- |
| name | varchar | 50 |
| type_id | bigint | -- |
| create_time | datetime | -- |
| is_delete | bit | -- |
| balance | decimal(10, 2) | -- |

### 用户类实体
```java
@Table("sys_user")
public class User
{
	@Field(pk = true)
    private Long id;
    @Field
    private String name;
    @Field
    private Long typeId;
    @Field
    private java.util.Date createTime;
    @Field
    private Boolean isDelete;
    @Field
    private java.math.BigDecimal balance;
    
    @Transient
    private String typeName;
    
    // getter/setter methods bellow...
}
```

### 测试用User实例
```java
User user = new User();
user.setId(112233L);
user.setName("matrixy");
user.setTypeId(12L);
user.setDelete(true);
user.setBalance(new BigDecimal("3.14"));
user.setCreateTime(new java.util.Date());
```

### 编写DAO类，需要继承`HentaiDAO`
```java
public class UserDAO extends HentaiDAO
{
    
}
```

### 编写测试用JDBCBridge
```java
// 这里只是输出生成的SQL，用于跟踪与调试
public class TestBridge implements JDBCBridge
{
    @Override
    public Object insert(String sql, Object... values)
    {
        System.out.println("insert: " + sql);
        return 0;
    }

    @Override
    public long execute(String sql, Object... values)
    {
        System.out.println("execute: " + sql);
        return 0;
    }

    @Override
    public long update(String sql, Object... values)
    {
        System.out.println("update: " + sql);
        return 0;
    }

    @Override
    public <T> T queryOne(String sql, Class type)
    {
        System.out.println("queryOne: " + sql);
        return null;
    }

    @Override
    public <E> List<E> query(String sql, Class type, Object... values)
    {
        System.out.println("query: " + sql);
        return null;
    }

    @Override
    public <T> T queryForValue(String sql, Class type, Object... values)
    {
        System.out.println("queryForValue: " + sql);
        return null;
    }
}
```

### 注册JDBCBridge
在应用初始化时，调用`HentaiDAO.registerJDBCBridge(new TestBridge())`来设置运行时的JDBCBridge。

## 字段名转换映射
1. 名称转换，数据库里的字段名一段以下线线_联接，在与Java实体类属性对应时，可通过`HentaiDAO.setupFieldNameConverter(new CamelCaseConverter())`来设置使用字段名到类成员的转换器（CamelCaseConverter就是由_风格转为驼峰式的内置转换器）。
2. 类型映射，Java实体类的属性类型必须为装箱后的对象类型，对照关系如下：

| 数据库类型 | Java类型 | 备注 |
|---|---|---|
| bigint | Long | -- |
| tinyint | Integer | -- |
| Int | Integer | -- |
| Varchar | String | -- |
| Datetime | java.util.Date | -- |
| Bit | Boolean | 因为各IDE生成的setter与getter方法名不统一，可能会有问题 |
| TEXT | String | -- |
| Decimal | java.math.BigDecimal | -- |

## INSERT 插入
执行`insertInto().valueWith(user).save()`将输出
```SQL
insert into users (name, type_id, create_time, is_delete, balance)
values (?, ?, ?, ?, ?)
```
* `insertInto`有重载方法，可另行指定表名。
* `valueWith()`方法的参数为一个java实体类的实例，HentaiDAO将自动反射出需要持久化的字段名，并且转换为数据库字段名的带下划线的形式，比如`userId`将转换为`user_id`的形式。
* 如果实体类的某个字段不需要保存到数据库中，需要加上`@Transient`注解。
* `save()`方法的返回值为新插入行的id值。

## UPDATE 更新
执行`update().valueWith(user).by(clause("sex = ?", "男").and("age = ?", 20)).execute();`将输出：
```SQL
update users
set name = "matrixy",
    type_id = ?,
    create_time = ?,
    is_delete = ?,
    balance = ?,
where
    sex = ?
    and age = ?;
```

执行`update().valueWith(user).byId().execute();`将输出：
```SQL
update users
set name = "matrixy",
    type_id = ?,
    create_time = ?,
    is_delete = ?,
    balance = ?,
where id = ?;
```
使用`byId()`从句，将自动从实体类中反射得到`@Field(pk = true)`的成员属性转换后得到的字段名，用于where从句的字段名。

* `skip(String... fields)`，跳过一个或多个字段，在更新时不更新这些字段（字段名应该是带下划线的数据库字段名）。
* `only(String... fields)`，只更新参数列表里的这些字段的值（字段名应该是带下划线的数据库字段名）。
* `valueWith(String fieldName, Object fieldValue)`手动指定要更新的字段与值（字段名应该是带下划线的数据库字段名）。
* 另外：`update`不会更新主键值。
* 另外，`only`、`skip`、`valueWith`等方法，都返回了本`UpdateSQL`实例，也就是说可以一直连续的接下去，比如：
```java
update("sys_user")
    .valueWith("name", "test")
    .valueWith("is_delete", 1)
    .valueWith("create_time", new java.util.Date())
    .by(clause("id = ?", 1))
    .execute();
```

## SELECT 查询
查询的完整形式如下：
```
    select(String...fields)
        .from(String tableName)
        .where()
        .orderBy()
        .queryForXXX()
```
对应于SELECT查询的几个基本要求，其中最简形式可以是`select(Bean.class).byId(1).queryForXXX()`，这将使用实体类成员属性里带有`@Field(pk = true)`的属性名转转换后作为待查的字段，`byId()`方法是一个预定义的针对于主键查询的快速方法。
* `select()`或`select(String... fieldNames)`方法，用于创建查询语句，如果指定了`fieldNames`不定长参数时，将使用参数的值来代替`configureFields()`方法的返回值。另外，`fieldNames`参数的值应当为数据库字段名格式，不需要转换，`HentaiDAO`会自动进行转换映射，如果`fieldNames`值中有`as`重命名，将不会进行自动重命名。
* `from(String tableName)`方法，用于指定待查的表名。或使用from(Bean.class)，由类注解的`@Table()`来获取表名。
* `where()`或`byId()`方法，指定查询时的条件，`where()`方法将在下面的条件从句上详解。`byId`方法将使用`Bean.class`类成员属性上设定了`@Field(pk = true)`的成员属性名称转换后作为字段名，使用`=`比较。
* `orderBy(String fieldName, String order)`或`orderBy(String fieldName)`，指定排序的字段名以及升降序，如果未指定`order`参数，则默认为升序，如果`order`参数值不为`asc`（不区分大小写），则为降序。
* `desc()`或`asc()`，单独声明降序或升序排序。
* `Long queryForCount()`返回查询结果集的行数。
* `EntityClass query()`返回单个结果，实例化并自动填充值到指定的类型。
* `List<EntityClass> queryForList()`返回全部结果集，实例化并自动填充到指定的类型。
* `List<EntityClass> queryForLimit(int offset, int count)`，返回结果集的自offset起，共count条结果，实例化并自动填充到指定的类型。

### Clause 条件从句
`clause()`方法是`HentaiDAO`类中实明的方法，用于创建一个`Clause`类的实例，提供联合的条件从句，目前只支持`and`的联合，`clause()`方法返回的值为自身实例引用，可使用`and()`无限联接。
条件联接：添加条件从句时，可根据传入值的情况决定是否使条件从句生效，是否联接到SQL语句中去：
* `gtz`，只有当值大于0时，此条件从句才生效（greater than zero）。
* `like`，只有当值非null非空串非不可见字符串，此条件从句才生效，将自动在值的前后追回上`%`，并且将从句中的`=`改为`like`。
* `notnull`，只有当值非null非空串非不可见字符串，此条件从句才生效。

举例有如下查询，现在只关心clause方法：
```java
select("id")
    .from("test")
    .where(
        clause("id = ?", 1)
    ).query()
```
生成的SQL如下：
```SQL
select id from test where id = 1;
```
1. 当使用：`clause("id = ?", gtz(1)).and("sex = ?", gtz(0))`时，将输出：
```SQL
-- gtz要求值必须大于0，如果值小于等于0，将忽略掉整个条件从句，所以后一个条件从句将被忽略
select id from test where id = 1;
```
2. 当使用：`clause("name = ?", like("ha")).and("name like ?", like(""))`时，将输出：
```SQL
-- like要求值必须为非空的可见字符串，所以将忽略后一个条件从句，前一个条件从句将生效，并且将=替换为like
select id from test where name like '%ha%';
```
3. 当使用：`clause("name = ?", null)`时，将输出：
```SQL
-- null不是以=相比较，所以这里做了适配
select id from test where name is null
```

## HentaiDAO类成员方法列表
1. `static void setupJDBCBridge(JDBCBridge bridge)`，设置JDBC执行委托
2. `static void setupFieldNameConverter(IFieldNameConverter converter)`，设置表字段名到类成员名称的转换器，默认是`CamelCaseConverter`。
3. `abstract String[] configureFields()`，需要子类实现的，完成表的字段名配置
4. `abstract String configureTableName()`，需要子类实现的，完成表名的配置
5. `String primaryKey()`，可在子类覆盖实现，完成表的主键名的配置，默认返回值为"id"
6. `UpdateSQL update()`，创建UPDATE语句
7. `UdateSQL update(String tableName)`，创建UPDATE语句
8. `InsertSQL insertInto()`，创建INSERT语句
9. `InsertSQL insertInto(String tableName)`，创建INSERT语句，并且指定表名
10. `QuerySQL select()`，创建SELECT语句
11. `QuerySQL select(String... fields)，以指定的字段名，创建SELECT语句
12. `Clause clause(String sql, Object value)`，以参数化的从句以值，创建条件从句
13. `Clause clause(String sql)`，以SQL直接创建条件从句
14. `Concatenation gtz(Object data)`，以指定的值创建gtz语义的条件联接值
15. `Concatenation notnull(Object data)`，以指定的值创建notnull语义的条件联接值
16. `Concatenation like(Object data)`，以指定的值创建like语义的条件联接值
17. `List<TypeClass> query(String sql, Class type, Object...values)`，直接执行查询语句，并且返回结果集
18. `TypeClass queryForValue(String sql, Class type, Object...values)`，直接执行查询语句，并且返回相应的类型值
19. `TypeClass queryForOne(String sql, Class type, Object...values)`，直接执行查询语句，并且返回相应的一个结果
20. `long execute(String sql, Object...values)`，直接执行UPDATE/INSERT语句，并且返回影响的行数
21. `today()`，返回当天的字符串表示，比如`2018-03-31`。
22. `tomorrow()`，返回当天的后一天的字符串表示，比如`2018-04-01`。
23. `date(int offset)`，返回以当天为基准，之前或之后第offset天的字符串表示，比如`date(-1)`将返回前一天的日期字符串。
24. `date(Date date, int offset)`，返回以指定参数指定的日期为基准，之前或之后第offset天的字符串表示。


## TODO
1. 连接的事务会话测试
2. JDBCBridge，与其它连接池管理包配合
3. `group by`从句

## 问题
1. 难以表述过于复杂的SQL语句
2. 暂时只支持`integer`类型的自增主键
3. 目前只在Mysql上使用过，其它数据库未进行适配与测试







































