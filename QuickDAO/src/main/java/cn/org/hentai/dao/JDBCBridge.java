package cn.org.hentai.dao;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 * JDBC执行适配器
 */
public interface JDBCBridge
{
    /**
     * 执行INSERT插入语句
     * @param sql 待执行的INSERT语句
     * @param values INSERT语句的参数数组
     * @return 新记录的主键值
     */
    public <E> E insert(String sql, Object... values);

    /**
     * 执行DML语句
     * @param sql 待执行的SQL语句
     * @param values DML语句的参数数组
     * @return 影响的行数
     */
    public <E> E execute(String sql, Object... values);

    /**
     * 执行UPDATE语句
     * @param sql 待执行的UPDATE语句
     * @param values UPDATE语句的参数数组
     * @return 影响的行数
     */
    public <E> E update(String sql, Object... values);

    /**
     * 查询以得到单个结果
     * @param sql 待执行的SELECT语句
     * @param type 查询结果到实体类的映射类，期待返回此类的实例
     * @param values SELECT语句的参数数组
     * @return 查询结果到type类实例化的实体
     */
    public <E> E queryOne(String sql, Class type, Object... values);

    /**
     * 查询列表
     * @param sql 待执行的SELECT语句
     * @param type 查询结果到实体类的映射类，期待返回此类的实例列表
     * @param values SELECT语句的参数数组
     * @return 查询结果列表
     */
    public <E> List<E> query(String sql, Class type, Object... values);

    /**
     * 查询值
     * @param sql 待执行的SELECT语句
     * @param type 值的类型类，期待返回此类的结果值
     * @param values SELECT语句的参数数组
     * @return 由type指定值
     */
    public <E> E queryForValue(String sql, Class type, Object... values);
}
