package cn.org.hentai.db;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public interface JDBCBridge
{
    // 提供JDBC连接或其它可执行SQL的句柄
    public Object provide();

    /**
     * 执行insert语句
     * @param sql
     * @param values
     * @return
     */
    public Object insert(Object jdbc, String sql, Object...values);

    /**
     * 执行DML操作
     * @param sql
     * @param values
     * @return
     */
    public long execute(Object jdbc, String sql, Object...values);

    /**
     * 执行update修改
     * @param sql
     * @param values
     * @return
     */
    public long update(Object jdbc, String sql, Object... values);

    /**
     * 查询一行结果
     * @param sql
     * @param type
     * @param <T>
     * @return
     */
    public <T> T queryOne(Object jdbc, String sql, Class type);

    /**
     * 查询一个列表
     * @param sql
     * @param type
     * @param values
     * @param <E>
     * @return
     */
    public <E> List<E> query(Object jdbc, String sql, Class type, Object... values);

    /**
     * 查询并返回一个值
     * @param sql
     * @param type
     * @param values
     * @param <T>
     * @return
     */
    public <T> T queryForValue(Object jdbc, String sql, Class type, Object... values);
}
