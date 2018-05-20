package cn.org.hentai.dao;

import cn.org.hentai.dao.util.DbUtil;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * Created by matrixy on 2017-03-06.
 */
public class Block
{
    public String sql;
    public Object data;

    public Block(String sql, Object data)
    {
        this.sql = sql;
        this.data = data;
    }

    public Block(String sql)
    {
        this.sql = sql;
        this.data = null;
    }

    // 条件测试，如果不符合联合条件则返回null
    public Block test()
    {
        String wildcard = "\\?";
        if (this.sql.indexOf('*') > -1) wildcard = "\\*";

        Concatenation.Concate concateType = Concatenation.Concate.direct;
        if (this.data instanceof Concatenation)
        {
            concateType = ((Concatenation)this.data).concateType;
            this.data = ((Concatenation)this.data).data;
        }

        // date
        if (this.data instanceof java.util.Date)
        {
            String val = DbUtil.formatDate((java.util.Date)this.data);
            return this;
        }

        // number
        if (this.data instanceof Integer)
        {
            int val = (Integer)this.data;
            if (concateType == Concatenation.Concate.gtz) return val > 0 ? this : null;
            else return this;
        }
        if (this.data instanceof Long)
        {
            long val = (Long)this.data;
            if (concateType == Concatenation.Concate.gtz) return val > 0 ? this : null;
            else return this;
        }
        if (this.data instanceof Float)
        {
            float val = (Float)this.data;
            if (concateType == Concatenation.Concate.gtz) return val > 0 ? this : null;
            else return this;
        }
        if (this.data instanceof Double)
        {
            double val = (Double)this.data;
            if (concateType == Concatenation.Concate.gtz) return val > 0 ? this : null;
            else return this;
        }
        if (this.data instanceof Byte)
        {
            byte val = (Byte)this.data;
            if (concateType == Concatenation.Concate.gtz) return val > 0 ? this : null;
            else return this;
        }
        // bool
        if (this.data instanceof Boolean)
        {
            // boolean val = (Boolean)this.data;
            return this;
        }
        // bigdecimal
        if (this.data instanceof BigDecimal)
        {
            BigDecimal val = (BigDecimal)this.data;
            if (concateType == Concatenation.Concate.gtz)
            {
                if (val.compareTo(BigDecimal.ZERO) > 0) return this;
                else return null;
            }
            else return this;
        }
        // array, in (*)
        if (this.data.getClass().isArray())
        {
            StringBuffer params = new StringBuffer(64);
            for (int i = 0, l = Array.getLength(this.data); i < l; i++)
            {
                params.append('?');
                if (i < l - 1) params.append(',');
            }
            this.sql = this.sql.replaceAll(wildcard, params.toString());
            return this;
        }

        // string，应该放在最后且必然执行，因为null与String傻傻分不清，null instanceof everything
        {
            String val = DbUtil.charQuote((String)this.data);
            if (concateType == Concatenation.Concate.direct)
            {
                // TODO: 没有判断!=的情况
                // TODO: 也没有判断>=、<=等情况
                if (null == val)
                {
                    this.sql = this.sql.replaceAll("(?is)(=\\s*\\?)|(like\\s*\\?)", " is null");
                    return this;
                }
                return this;
            }
            if (concateType == Concatenation.Concate.notnull)
            {
                if (null == val || val.trim().length() == 0) return null;
                else return this;
            }
            if (concateType == Concatenation.Concate.like)
            {
                if (null == val || val.trim().length() == 0) return null;
                else
                {
                    this.sql = this.sql.replace(wildcard, "'%" + val + "%'").replace("=", " like ");
                    return this;
                }
            }
        }
        return null;
    }
}
