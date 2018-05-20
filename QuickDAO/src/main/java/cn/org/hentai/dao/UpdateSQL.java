package cn.org.hentai.dao;

import cn.org.hentai.dao.model.Type;
import cn.org.hentai.dao.model.TypeField;
import cn.org.hentai.dao.util.ClassStructures;
import cn.org.hentai.dao.util.DbUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matrixy on 2017-03-06.
 */
public class UpdateSQL extends DBSQL
{
    String tableName = null;
    String primaryKey = null;
    ArrayList<Field> fields = null;
    HashMap<String, String> skipFields = null;
    HashMap<String, String> storeFields = null;
    Clause clause = null;
    Object bean = null;
    ArrayList values;

    protected UpdateSQL(JDBCBridge jdbcBridge)
    {
        super(jdbcBridge);
        this.fields = new ArrayList<Field>();
        this.skipFields = new HashMap<String, String>();
        this.storeFields = new HashMap<String, String>();
    }

    public UpdateSQL valueWith(Object bean)
    {
        return with(bean);
    }

    public UpdateSQL valueWith(String fieldName, Object value)
    {
        if (!fieldName.matches("^\\w+$")) throw new RuntimeException("invalid field name: " + fieldName);
        this.fields.add(new Field(fieldName, value));
        return this;
    }

    private UpdateSQL with(String name, Object value)
    {
        return valueWith(name, value);
    }

    public UpdateSQL with(Object bean)
    {
        this.bean = bean;
        try
        {
            Type type = ClassStructures.get(bean.getClass());
            this.setPrimaryKey(type.getPrimaryKey());
            this.setTableName(type.getName());
            TypeField[] fields = type.getFields();
            for (int i = 0; i < fields.length; i++)
            {
                TypeField field = fields[i];
                this.fields.add(new Field(field.getName(), field.getGetter().invoke(bean)));
            }
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
        return this;
    }

    public UpdateSQL skip(String...fields)
    {
        for (int i = 0; i < fields.length; i++) this.skipFields.put(fields[i], null);
        return this;
    }

    public UpdateSQL only(String...fields)
    {
        for (int i = 0; i < fields.length; i++) this.storeFields.put(fields[i], null);
        return this;
    }

    public UpdateSQL byId()
    {
        this.clause = new Clause().and(this.primaryKey + " = ?", getPKValue());
        return this;
    }

    public UpdateSQL by(Clause clause)
    {
        this.clause = clause;
        return this;
    }

    protected UpdateSQL setTableName(String tableName)
    {
        this.tableName = tableName;
        return this;
    }

    protected UpdateSQL setPrimaryKey(String pk)
    {
        this.primaryKey = pk;
        return this;
    }

    private Object getPKValue()
    {
        if (this.bean == null) throw new RuntimeException("You should call valueWith(Object bean) before method by(...)");
        try
        {
            // System.out.println(formatFieldName("get_" + this.primaryKey));
            Method method = this.bean.getClass().getDeclaredMethod(DbUtil.formatFieldName("get_" + this.primaryKey));
            return method.invoke(this.bean);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public String toSQL(boolean merged)
    {
        StringBuffer sql = new StringBuffer(1024);
        // update xxx set a = 1, b = 2, c = 3 where id = ?
        sql.append("update ");
        sql.append(this.tableName);
        sql.append(" set ");
        int fieldCount = 0;
        if (!merged) this.values = new ArrayList();
        for (int i = 0, l = fields.size(); i < l; i++)
        {
            Field field = fields.get(i);
            if (this.primaryKey != null && field.name.equals(this.primaryKey)) continue;
            if (this.skipFields.containsKey(field.name)) continue;
            if (this.storeFields.size() > 0 && !this.storeFields.containsKey(field.name)) continue;
            sql.append(field.name);
            sql.append(" = ");
            fieldCount += 1;
            if (merged) sql.append(DbUtil.valueLiteral(field.value));
            else
            {
                sql.append('?');
                this.values.add(field.value);
            }
            if (i < l - 1) sql.append(',');
        }
        if (null == clause) throw new RuntimeException("missing where clause");
        String where = merged ? clause.toWhereClause() : clause.toWhere();
        if (where != null)
        {
            sql.append(" where ");
            sql.append(where);
            if (!merged)
            {
                this.values.addAll(Arrays.asList(clause.getValues()));
            }
        }
        else throw new RuntimeException("missing where clause: " + sql);
        return sql.toString().replaceAll(",\\s+where", " where");
    }

    public long execute()
    {
        return getJdbcBridge().update(toSQL(false), this.values);
    }

    public String toString()
    {
        return toSQL();
    }
}
