package cn.org.hentai.test;

import cn.org.hentai.dao.JDBCBridge;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public class TestBridge implements JDBCBridge
{
    @Override
    public Long insert(String sql, Object... values)
    {
        Log.debug("insert: " + sql);
        return 0L;
    }

    @Override
    public Long execute(String sql, Object... values)
    {
        Log.debug("execute: " + sql);
        return 0L;
    }

    @Override
    public Long update(String sql, Object... values)
    {
        Log.debug("update: " + sql);
        return 0L;
    }

    @Override
    public <E> E queryOne(String sql, Class type, Object... values)
    {
        Log.debug("queryOne: " + sql);
        for (int i = 0; i < values.length; i++)
        {
            Object val = values[i];
            if (val.getClass().isArray())
            {
                for (int k = 0, l = Array.getLength(val); k < l; k++)
                {
                    Log.debug("\t\t" + Array.get(val, k));
                }
            }
            else Log.debug("\t\t" + values[i]);
        }
        return null;
    }

    @Override
    public <E> List<E> query(String sql, Class type, Object... values)
    {
        Log.debug("query: " + sql);
        for (int i = 0; i < values.length; i++)
        {
            Object val = values[i];
            if (val.getClass().isArray())
            {
                for (int k = 0, l = Array.getLength(val); k < l; k++)
                {
                    Log.debug("\t\t" + Array.get(val, k));
                }
            }
            else Log.debug("\t\t" + values[i]);
        }
        return null;
    }

    @Override
    public <E> E queryForValue(String sql, Class type, Object... values)
    {
        Log.debug("queryForValue: " + sql);
        for (int i = 0; i < values.length; i++)
        {
            Object val = values[i];
            if (val.getClass().isArray())
            {
                for (int k = 0, l = Array.getLength(val); k < l; k++)
                {
                    Log.debug("\t\t" + Array.get(val, k));
                }
            }
            else Log.debug("\t\t" + values[i]);
        }
        return null;
    }

}
