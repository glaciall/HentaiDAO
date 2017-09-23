package cn.org.hentai.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by matrixy on 2016/12/21.
 */
public class DBSQL
{
    protected JdbcTemplate jdbcTemplate;

    public DBSQL()
    {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        this.jdbcTemplate = (JdbcTemplate)wac.getBean("jdbcTemplate");
    }

    protected void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected JdbcTemplate getJdbcTemplate()
    {
        return this.jdbcTemplate;
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

    public Integer execute()
    {
        return null;
    }

    /*
    public Object execute(Class<?> cls)
    {
        return null;
    }
    */

    public <E> E query(String sql, Class<?> cls)
    {
        return null;
    }
}
