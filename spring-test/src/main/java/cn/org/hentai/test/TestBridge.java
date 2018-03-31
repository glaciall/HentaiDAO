package cn.org.hentai.test;

import cn.org.hentai.dao.JDBCBridge;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public class TestBridge implements JDBCBridge
{
    @Override
    public Object insert(String sql, Object... values)
    {
        Log.debug("insert: " + sql);
        return 0;
    }

    @Override
    public long execute(String sql, Object... values)
    {
        Log.debug("execute: " + sql);
        return 0;
    }

    @Override
    public long update(String sql, Object... values)
    {
        Log.debug("update: " + sql);
        return 0;
    }

    @Override
    public <T> T queryOne(String sql, Class type)
    {
        Log.debug("queryOne: " + sql);
        return null;
    }

    @Override
    public <E> List<E> query(String sql, Class type, Object... values)
    {
        Log.debug("query: " + sql);
        return null;
    }

    @Override
    public <T> T queryForValue(String sql, Class type, Object... values)
    {
        Log.debug("queryForValue: " + sql);
        return null;
    }

}
