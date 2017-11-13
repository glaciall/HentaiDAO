package cn.org.hentai.test;

import cn.org.hentai.db.HentaiDAO;
import cn.org.hentai.db.JDBCBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
@Component
public class TestBridge implements JDBCBridge
{
    // @Autowired
    // JdbcTemplate jdbcTemplate;

    // 提供JDBCE运行器
    public Object provide()
    {
        return new Object();
    }

    public void dump(Object jdbc)
    {
        try
        {
            // Log.debug("Bridge: " + Integer.toHexString(hashCode()) + ", Conn: " + ((JdbcTemplate)jdbc).getNativeJdbcExtractor().toString());
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    public void release(Object jdbcInstance)
    {
        // do nothing here
    }

    @Override
    public long insert(Object jdbc, String sql, Object... values)
    {
        dump(jdbc);
        Log.debug("insert: " + sql);
        return 0;
    }

    @Override
    public long execute(Object jdbc, String sql, List values)
    {
        dump(jdbc);
        Log.debug("execute: " + sql);
        return 0;
    }

    @Override
    public long update(Object jdbc, String sql, List values)
    {
        dump(jdbc);
        Log.debug("update: " + sql);
        return 0;
    }

    @Override
    public <T> T queryOne(Object jdbc, String sql, Class type)
    {
        dump(jdbc);
        Log.debug("queryOne: " + sql);
        return null;
    }

    @Override
    public <E> List<E> query(Object jdbc, String sql, Class type, Object... values)
    {
        dump(jdbc);
        Log.debug("query: " + sql);
        return null;
    }

    @Override
    public <T> T queryForValue(Object jdbc, String sql, Class type, Object... values)
    {
        dump(jdbc);
        Log.debug("queryForValue: " + sql);
        return null;
    }
}
