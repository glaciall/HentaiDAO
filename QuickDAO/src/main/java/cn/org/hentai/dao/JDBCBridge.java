package cn.org.hentai.dao;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public interface JDBCBridge
{
    /**
     * 执行insert语句
     * @param sql
     * @param values
     * @return
     */
    public <E> E insert(String sql, Object... values);

    /**
     * 执行DML操作
     * @param sql
     * @param values
     * @return
     */
    public <E> E execute(String sql, Object... values);

    /**
     * 执行update修改
     * @param sql
     * @param values
     * @return
     */
    public <E> E update(String sql, Object... values);

    /**
     * 查询一行结果
     * @param sql
     * @param type
     * @param <E>
     * @return
     */
    public <E> E queryOne(String sql, Class type);

    /**
     * 查询一个列表
     * @param sql
     * @param type
     * @param values
     * @param <E>
     * @return
     */
    public <E> List<E> query(String sql, Class type, Object... values);

    /**
     * 查询并返回一个值
     * @param sql
     * @param type
     * @param values
     * @param <E>
     * @return
     */
    public <E> E queryForValue(String sql, Class type, Object... values);
}
