package cn.org.hentai.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by matrixy on 2016/12/21.
 */
public class DBSQL
{
    private JDBCBridge jdbcBridge;

    public DBSQL(JDBCBridge jdbcBridge)
    {
        this.jdbcBridge = jdbcBridge;
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
    }

    public JDBCBridge getJdbcBridge() {
        return jdbcBridge;
    }

    public String toSQL()
    {
        return toSQL(true);
    }

    protected String toSQL(boolean merged)
    {
        return null;
    }

    public String toPreparedSQL()
    {
        return null;
    }
}
