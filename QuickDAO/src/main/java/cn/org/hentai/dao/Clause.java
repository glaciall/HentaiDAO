package cn.org.hentai.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by matrixy on 2017-03-06.
 */
public class Clause
{
    private List<Block> conditions = null;

    public Clause()
    {
        this.conditions = new ArrayList<Block>();
    }

    public Clause(String sql, Object value)
    {
        this();
        and(sql, value);
    }

    // clause("id = ?", 0).and("name like ?", name)
    // TODO: 需要考虑一下如何支持多级条件分组
    public Clause and(String sql, Object value)
    {
        conditions.add(new Block(sql, value));
        return this;
    }

    public Clause and(String sql)
    {
        return and(sql, null);
    }

    public Object[] getValues()
    {
        ArrayList values = new ArrayList((int)(conditions.size() * 1.5f));
        for (int i = 0; i < conditions.size(); i++)
        {
            Block block = conditions.get(i);
            if (block.test() == null) continue;
            if (block.data instanceof Concatenation)
            {
                values.add(((Concatenation)block.data).data);
            }
            else values.add(block.data);
        }
        return values.toArray();
    }

    public String toWhere()
    {
        StringBuffer sql = new StringBuffer(1024);
        for (int i = 0, l = conditions.size(); i < l; i++)
        {
            Block block = conditions.get(i).test();
            if (null == block) continue;
            sql.append(block.sql);
            if (i < l - 1) sql.append(" and ");
        }
        return sql.length() == 0 ? null : sql.toString().replaceAll("\\s+and\\s+$", "");
    }

    public String toWhereClause()
    {
        return toWhere();
    }
}
