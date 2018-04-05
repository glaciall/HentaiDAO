package cn.org.hentai.test;

import cn.org.hentai.dao.JDBCBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public class SpringJDBCTemplateBridge implements JDBCBridge
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public <E> E insert(String sql, Object... values)
    {
        Log.debug("insert: " + sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Long autoIncId = 0L;

        jdbcTemplate.update(new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException
            {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                return ps;
            }
        }, keyHolder);

        autoIncId = keyHolder.getKey().longValue();
        return (E)autoIncId;
    }

    @Override
    public <E> E execute(String sql, Object... values)
    {
        Log.debug("execute: " + sql);
        if (values != null && values.length > 0)
            return (E)(new Long(jdbcTemplate.update(sql, values)));
        else
            return (E)(new Long(jdbcTemplate.update(sql)));
    }

    @Override
    public <E> E update(String sql, Object... values)
    {
        Log.debug("update: " + sql);
        return (E)null;
    }

    // **************************************************************
    // 查询相关
    //

    // SQL执行函数
    private static final PreparedStatementSetter blankSetter = new PreparedStatementSetter()
    {
        @Override
        public void setValues(PreparedStatement ps) throws SQLException
        {
            // do nothing here
        }
    };

    @Override
    public <E> E queryOne(String sql, Class type)
    {
        List<E> list = query(sql, type);
        if (list.size() > 0) return list.get(0);
        else return null;
    }

    @Override
    public <E> List<E> query(String sql, Class type, Object... values)
    {
        return (List<E>)jdbcTemplate.query(sql, blankSetter, new BeanPropertyRowMapper(type));
    }

    @Override
    public <E> E queryForValue(String sql, Class type, Object... values)
    {
        E value = null;
        try
        {
            value = (E) jdbcTemplate.queryForObject(sql, type);
        }
        catch(EmptyResultDataAccessException e)
        {
            // ..
        }
        return value;
    }
}
