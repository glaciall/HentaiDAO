package cn.org.hentai.dao;

import cn.org.hentai.dao.model.Type;
import cn.org.hentai.dao.model.TypeField;
import cn.org.hentai.dao.util.ClassStructures;
import cn.org.hentai.dao.util.DbUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by matrixy on 2017-03-06.
 */
public class InsertSQL extends DBSQL
{
    String tableName;
    String primaryKey;
    private ArrayList<Field> fields;
    private Object[] values;
    public InsertSQL(JDBCBridge jdbcBridge)
    {
        super(jdbcBridge);
        this.fields = new ArrayList<Field>();
    }

    public InsertSQL setTableName(String tableName)
    {
        this.tableName = tableName;
        return this;
    }

    public InsertSQL setPrimaryKey(String pk)
    {
        this.primaryKey = pk;
        return this;
    }

    public InsertSQL valueWith(Object bean) throws RuntimeException
    {
        try
        {
            Type type = ClassStructures.get(bean.getClass());
            this.setTableName(type.getName());
            this.setPrimaryKey(type.getPrimaryKey());
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

    public InsertSQL valueWith(String fieldName, Object value)
    {
        if (!fieldName.matches("^\\w+$")) throw new RuntimeException("invalid field name: " + fieldName);
        this.fields.add(new Field(fieldName, value));
        return this;
    }

    public String toSQL()
    {
        StringBuffer sql = new StringBuffer(1024);
        sql.append("insert into " + tableName + " (");
        int fieldCount = 0;
        for (int i = 0, l = fields.size(); i < l; i++)
        {
            Field field = fields.get(i);
            if (field.name.equals(primaryKey)) continue;
            sql.append(field.name);
            if (i < l - 1) sql.append(',');
            fieldCount += 1;
        }
        this.values = new Object[fieldCount];
        sql.append(") values (");
        for (int i = 0, s = 0, l = fields.size(); i < l; i++)
        {
            Field field = fields.get(i);
            if (field.name.equals(this.primaryKey)) continue;
            else sql.append('?');
            if (i < l - 1) sql.append(',');
            this.values[s++] = field.value;
        }
        sql.append(")");
        return sql.toString();
    }

    public long execute()
    {
        return this.getJdbcBridge().update(toSQL(), this.values);
    }

    public <E> E save()
    {
        return getJdbcBridge().insert(toSQL(), this.values);
    }

    public String toString()
    {
        return toSQL();
    }
}
