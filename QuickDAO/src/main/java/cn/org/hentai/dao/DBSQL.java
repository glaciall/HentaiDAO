package cn.org.hentai.dao;

/**
 * Created by matrixy on 2016/12/21.
 */
public class DBSQL
{
    private JDBCBridge jdbcBridge;

    public DBSQL(JDBCBridge jdbcBridge)
    {
        this.jdbcBridge = jdbcBridge;
    }

    public JDBCBridge getJdbcBridge()
    {
        return jdbcBridge;
    }

    public String toSQL()
    {
        return null;
    }

    public String toPreparedSQL()
    {
        return null;
    }
}
