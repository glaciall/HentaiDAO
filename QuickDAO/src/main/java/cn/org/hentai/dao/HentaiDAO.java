package cn.org.hentai.dao;

import cn.org.hentai.dao.converter.IFieldNameConverter;
import cn.org.hentai.dao.converter.CamelCaseConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by matrixy on 2017-03-06.
 */
public abstract class HentaiDAO
{
    // 修改相关
    public final UpdateSQL update()
    {
        return new UpdateSQL(jdbcBridge);
    }

    public final UpdateSQL update(String tableName)
    {
        return new UpdateSQL(jdbcBridge).setTableName(tableName);
    }

    // 插入相关
    public final InsertSQL insertInto()
    {
        return new InsertSQL(jdbcBridge);
    }

    public final InsertSQL insertInto(String tableName)
    {
        return new InsertSQL(jdbcBridge).setTableName(tableName);
    }

    // 查询相关
    protected final QuerySQL select()
    {
        return new QuerySQL(jdbcBridge);
    }

    public final QuerySQL select(String... fields)
    {
        return new QuerySQL(jdbcBridge).setFields(fields);
    }

    // where从句
    public final Clause clause(String sql, Object value)
    {
        return new Clause().and(sql, value);
    }

    public final Clause clause(String sql)
    {
        return new Clause().and(sql, null);
    }

    // 条件联接器
    protected static final Concatenation gtz(Object data)
    {
        return new Concatenation(Concatenation.Concate.gtz, data);
    }

    protected static final Concatenation notnull(Object data)
    {
        return new Concatenation(Concatenation.Concate.notnull, data);
    }

    protected static final Concatenation like(Object data)
    {
        return new Concatenation(Concatenation.Concate.like, data);
    }

    // 直接执行SQL语句
    public final <E> List<E> query(String sql, Class type, Object...values)
    {
        return jdbcBridge.query(sql, type, values);
    }

    public final <E> E queryForValue(String sql, Class type, Object...values)
    {
        return jdbcBridge.queryForValue(sql, type, values);
    }

    public final <E> E queryOne(String sql, Class type, Object...values)
    {
        return jdbcBridge.queryOne(sql, type, values);
    }

    public final long execute(String sql, Object...values)
    {
        return jdbcBridge.execute(sql, values);
    }

    // 日期函数
    public final String today()
    {
        return date(0);
    }

    public final String tomorrow()
    {
        return date(1);
    }

    public final String date(int offset)
    {
        long time = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * offset);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    public final String date(Date date, int offset)
    {
        if (null == date) return null;
        long time = date.getTime() + (1000L * 60 * 60 * 24 * offset);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    // 静态成员，需要在应用初始化时同时设置
    private static JDBCBridge jdbcBridge;
    public static void setupJDBCBridge(JDBCBridge bridge)
    {
        jdbcBridge = bridge;
    }

    // 默认的字段名转换器
    private static IFieldNameConverter customFieldNameConverter = new CamelCaseConverter();
    public static void setupFieldNameConverter(IFieldNameConverter converter)
    {
        customFieldNameConverter = converter;
    }
    public static IFieldNameConverter getFieldNameConverter()
    {
        return customFieldNameConverter;
    }
}
