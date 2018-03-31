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
        List<User> users = select()
            .where(clause("sex = ?", gtz(sex)).and("name = ?", like(name)))
            .orderBy("id", "desc")
            .queryForLimit(User.class, 100, 10);
    }
}
```
## 特点
1. 与SQL标准高度相似的API设计，上手快
2. 专为多重筛选条件设计的clause从句，让你一句话写到底，不用关心在什么情况下拼接什么条件从句
3. 简单的`insert`与`update`语句，避免了名与值的位置匹配问题
4. 表字段名与实体类属性名的自动转换/映射

## 举例说明
### 结构说明
1. 继承HentaiDAO，获得CRUD的快捷方式，在DAO类中声明具体的增删改查语句
2. 实现JDBCBridge接口，执行由DAO类所要求执行的SQL语句

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
public class User
{
	private Long id;
    private String name;
    private Long typeId;
    private java.util.Date createTime;
    private Boolean isDelete;
    private java.math.BigDecimal balance;
    
    @Transient
    private String typeName;
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
	// 指明CRUD操作的默认表名
	public String configureTableName()
    {
    	return "users";
    }
    
    // 设置查询时所用到的字段名，不需要转换为驼峰式，按原形式写就行了
    public String[] configureFields()
    {
    	return new String[] { "id", "name", "type_id", "is_delete", "create_time", "balance" };
    }
    
    // 指明表的主键字段名，默认为`id`，可不覆盖实现
    public String primaryKey()
    {
    	return "id";
    }
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
1. 名称转换，数据库里的字段名一段以下线线_联接，在与Java实体类属性对应时，会自动转为驼峰式，比如`user_id`将转换为`userId`的形式。
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
执行`insertInto().valueWith().save()`将输出
```SQL
insert into users (name, type_id, create_time, is_delete, balance)
values ('matrixy', 12, '2018-03-31 18:41:00', 1, 3.14)
```
* `insertInto`有重载方法，可另行指定表名，默认使用`configureTableName()`的返回值作为表名。
* `valueWith()`方法的参数为一个java实体类的实例，HentaiDAO将自动反射出需要持久化的字段名，并且转换为数据库字段名的带下划线的形式，比如`userId`将转换为`user_id`的形式。
* 如果实体类的某个字段不需要保存到数据库中，需要加上`@Transient`注解。
* `save()`方法的返回值为新插入行的id值。

## UPDATE 更新
执行`update().valueWith(user).by(clause("sex = ?", "男").and("age = ?", 20)).execute();`将输出：
```SQL
update users
set name = "matrixy",
	type_id = 12,
    create_time = '2018-03-31 18:41:00',
    is_delete = 1,
    balance = 3.14,
where
	sex = '男'
    and age = 20;
```

执行`update().valueWith(user).byId().execute();`将输出：
```SQL
update users
set name = "matrixy",
	type_id = 12,
    create_time = '2018-03-31 18:41:00',
    is_delete = 1,
    balance = 3.14,
where id = 112233;
```
使用`byId()`从句，将自动从DAO类的`primaryKey()`方法的返回值作为主键名，从实体类中得到主键的值来填充where从句的值。

* `skip(String... fields)`，跳过一个或多个字段，在更新时不更新这些字段（字段名应该是带下划线的数据库字段名）。
* `only(String... fields)`，只更新参数列表里的这些字段的值（字段名应该是带下划线的数据库字段名）。
* `valueWith(String fieldName, Object fieldValue)`手动指定要更新的字段与值（字段名应该是带下划线的数据库字段名）。
* 另外：`update`不会更新主键值。
* 另外，`only`、`skip`、`valueWith`等方法，都返回了本`UpdateSQL`实例，也就是说可以一直连续的接下去，比如：
```java
update("users")
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
对应于SELECT查询的几个基本要求，其中最简形式可以是`select().byId(1).queryForXXX()`，这将使用`configureFields()`方法的返回值作为待查的字段，`configureTableName()`方法的返回值作为待查询的表名，`byId()`方法是一个预定义的针对于主键查询的快速方法。
* `select()`或`select(String... fieldNames)`方法，用于创建查询语句，如果指定了`fieldNames`不定长参数时，将使用参数的值来代替`configureFields()`方法的返回值。另外，`fieldNames`参数的值应当为数据库字段名格式，不需要转换，`HentaiDAO`会自动进行转换映射，如果`fieldNames`值中有`as`重命名，将不会进行自动重命名。
* `from(String tableName)`方法，用于指定待查的表名，忽略此方法将使用`configureTableName()`方法的返回值。
* `where()`或`byId()`方法，指定查询时的条件，`where()`方法将在下面的条件从句上详解。`byId`方法将直接使用`primaryKey()`方法所声明的主键字段名，来做`=`比较。
* `orderBy(String fieldName, String order)`或`orderBy(String fieldName)`，指定排序的字段名以及升降序，如果未指定`order`参数，则默认为升序，如果`order`参数值不为`asc`（不区分大小写），则为降序。
* `desc()`或`asc()`，单独声明降序或升序排序。
* `Long queryForCount()`返回查询结果集的行数。
* `EntityClass query(Class entityClass)`返回单个结果，实例化并自动填充值到指定的类型。
* `List<EntityClass> queryForList(Class entityClass)`返回全部结果集，实例化并自动填充到指定的类型。
* `List<EntityClass> queryForLimit(Class entityClass, int offset, int count)`，返回结果集的自offset起，共count条结果，实例化并自动填充到指定的类型。

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
	).query(xxx)
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
1. `static void registerJDBCBridge(JDBCBridge bridge)`，注册JDBC执行委托
2. `abstract String[] configureFields()`，需要子类实现的，完成表的字段名配置
3. `abstract String configureTableName()`，需要子类实现的，完成表名的配置
4. `String primaryKey()`，可在子类覆盖实现，完成表的主键名的配置，默认返回值为"id"
5. `UpdateSQL update()`，创建UPDATE语句
6. `InsertSQL insertInto()`，创建INSERT语句
7. `InsertSQL insertInto(String tableName)`，创建INSERT语句，并且指定表名
8. `QuerySQL select()`，创建SELECT语句
9. `QuerySQL select(String... fields)，以指定的字段名，创建SELECT语句
10. `Clause clause(String sql, Object value)`，以参数化的从句以值，创建条件从句
11. `Clause clause(String sql)`，以SQL直接创建条件从句
12. `Concatenation gtz(Object data)`，以指定的值创建gtz语义的条件联接值
13. `Concatenation notnull(Object data)`，以指定的值创建notnull语义的条件联接值
14. `Concatenation like(Object data)`，以指定的值创建like语义的条件联接值
15. `List<TypeClass> query(String sql, Class type)`，直接执行查询语句，并且返回结果集
16. `TypeClass queryForValue(String sql, Class type)`，直接执行查询语句，并且返回相应的类型值
17. `TypeClass queryForOne(String sql, Class type)`，直接执行查询语句，并且返回相应的一个结果
18. `long execute(String sql, Object...values)`，直接执行UPDATE/INSERT语句，并且返回影响的行数
19. `today()`，返回当天的字符串表示，比如`2018-03-31`。
20. `tomorrow()`，返回当天的后一天的字符串表示，比如`2018-04-01`。
21. `date(int offset)`，返回以当天为基准，之前或之后第offset天的字符串表示，比如`date(-1)`将返回前一天的日期字符串。
22. `date(Date date, int offset)`，返回以指定参数指定的日期为基准，之前或之后第offset天的字符串表示。


## TODO
1. 连接的事务会话测试
2. JDBCBridge，与其它连接池管理包配合
3. 提供字段名与实体类属性名映射接口
4. `group by`从句
5. 实体类反映结果的缓存

## 问题
1. 难以表述过于复杂的SQL语句
2. 暂时只支持`integer`类型的自增主键
3. 目前只在Mysql上使用过，其它数据库未进行适配与测试







































