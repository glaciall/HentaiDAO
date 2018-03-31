package cn.org.hentai.db;

import cn.org.hentai.db.model.BaseModel;
import cn.org.hentai.db.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by matrixy on 2017-03-06.
 */
public abstract class HentaiDAO
{
    private static JDBCBridge jdbcBridge;

    public static void registerJDBCBridge(JDBCBridge bridge)
    {
        jdbcBridge = bridge;
    }

    // *************************************************************************
    // *************************************************************************
    // 类配置
    public abstract String[] configureFields();

    public abstract String configureTableName();

    public String primaryKey()
    {
        return "id";
    }

    public final String[] alias(String prefix, String... extras)
    {
        String[] fields = configureFields();
        String[] aliasedFields = new String[fields.length + extras.length];
        for (int i = 0; i < fields.length; i++) aliasedFields[i] = prefix + '.' + fields[i];
        for (int i = 0; i < extras.length; i++)
        {
            String field = extras[i];
            if (field.indexOf(' ') == -1 && field.indexOf('_') > -1) field = DbUtil.formatFieldName(field);
            aliasedFields[i + fields.length] = field;
        }
        return aliasedFields;
    }

    // 修改相关
    public final UpdateSQL update()
    {
        return new UpdateSQL(jdbcBridge).setTableName(configureTableName()).setPrimaryKey(this.primaryKey());
    }

    // 插入相关
    public final InsertSQL insertInto()
    {
        return new InsertSQL(jdbcBridge).setTableName(configureTableName()).setPrimaryKey(this.primaryKey());
    }

    public final InsertSQL insertInto(String tableName)
    {
        return new InsertSQL(jdbcBridge).setTableName(tableName).setPrimaryKey(this.primaryKey());
    }

    // 查询相关
    protected final QuerySQL select()
    {
        return new QuerySQL(jdbcBridge).setFields(configureFields()).from(configureTableName()).setPrimaryKey(primaryKey());
    }

    public final QuerySQL select(String... fields)
    {
        return new QuerySQL(jdbcBridge).setFields(fields).setPrimaryKey(primaryKey()).from(configureTableName());
    }

    public final Clause clause(String sql, Object value)
    {
        return new Clause().and(sql, value);
    }

    public final Clause clause(String sql)
    {
        return new Clause().and(sql, null);
    }

    // 条件联接器
    protected static Concatenation gtz(Object data)
    {
        return new Concatenation(Concatenation.Concate.gtz, data);
    }

    protected static Concatenation notnull(Object data)
    {
        return new Concatenation(Concatenation.Concate.notnull, data);
    }

    protected static Concatenation like(Object data)
    {
        return new Concatenation(Concatenation.Concate.like, data);
    }

    public <E> List<E> query(String sql, Class type)
    {
        return jdbcBridge.query(sql, type);
    }

    public <E> E queryForValue(String sql, Class type)
    {
        return jdbcBridge.queryForValue(sql, type);
    }

    public <E> E queryOne(String sql, Class type)
    {
        return jdbcBridge.queryOne(sql, type);
    }

    public long execute(String sql, Object...values)
    {
        return jdbcBridge.execute(sql, values);
    }

    // 日期函数
    public String today()
    {
        return date(0);
    }

    public String tomorrow()
    {
        return date(1);
    }

    public String date(int offset)
    {
        long time = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * offset);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    public String date(Date date, int offset)
    {
        if (null == date) return null;
        long time = date.getTime() + (1000L * 60 * 60 * 24 * offset);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }
}
