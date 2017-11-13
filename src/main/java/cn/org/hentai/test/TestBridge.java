package cn.org.hentai.test;

import cn.org.hentai.db.HentaiDAO;
import cn.org.hentai.db.JDBCBridge;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public class TestBridge implements JDBCBridge
{
    Object jdbc = new Object();

    // 提供JDBCE运行器
    public Object provide()
    {
        return jdbc;
    }

    @Override
    public Object insert(Object jdbc, String sql, Object... values)
    {
        Log.debug("insert[" + Integer.toHexString(jdbc.hashCode()) + "]: " + sql);
        return 0;
    }

    @Override
    public long execute(Object jdbc, String sql, Object... values)
    {
        Log.debug("execute[" + Integer.toHexString(jdbc.hashCode()) + "]: " + sql);
        return 0;
    }

    @Override
    public long update(Object jdbc, String sql, Object... values)
    {
        Log.debug("update[" + Integer.toHexString(jdbc.hashCode()) + "]: " + sql);
        return 0;
    }

    @Override
    public <T> T queryOne(Object jdbc, String sql, Class type)
    {
        Log.debug("queryOne[" + Integer.toHexString(jdbc.hashCode()) + "]: " + sql);
        return null;
    }

    @Override
    public <E> List<E> query(Object jdbc, String sql, Class type, Object... values)
    {
        Log.debug("query[" + Integer.toHexString(jdbc.hashCode()) + "]: " + sql);
        return null;
    }

    @Override
    public <T> T queryForValue(Object jdbc, String sql, Class type, Object... values)
    {
        Log.debug("queryForValue[" + Integer.toHexString(jdbc.hashCode()) + "]: " + sql);
        return null;
    }
}
