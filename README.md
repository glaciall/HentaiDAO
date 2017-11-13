# HentaiDAO
> 一个简单快捷的生成SQL语句的开发工具库，易上手、可读性高、防注入，可通过JdbcBridge可以快速的与其它的连接池进行对接。

## 特性
1. 

## 依赖与准备
1. 本项目可无任何依赖
2. 假如有表和实体类如下：
| 字段 | 类型 | 说明 |
|--------|--------|---------|
|id|bigint|主键|
|name|varchar(64)|-|
|state|tinyint|状态，1表示可用，2表示禁用|
|create_time|datetime|创建时间|



3. DAO访问类需要继承HentaiDAO，并实现相关方法

```java

```

## INSERT语句

```java
insertInto()
    .valueWith(model)   // 以model类的成员来创建值对
    .valueWith()
    .save();
```

## UPDATE语句

## SELECT语句
