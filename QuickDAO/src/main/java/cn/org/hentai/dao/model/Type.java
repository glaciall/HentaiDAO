package cn.org.hentai.dao.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by matrixy on 2018/4/5.
 * 数据库表结构与实体类的映射结构
 */
public class Type
{
    // 自实体类注解上得到的表名
    private String name;

    // 主键名
    private String primaryKey;

    // 自实体类成员注解上得到的字段名
    private TypeField[] fields;

    private HashMap<String, Integer> fieldNameIndexes = new HashMap<String, Integer>();

    public Type()
    {
        // ...
    }

    public Type(String name, TypeField[] fields)
    {
        this.name = name;
        this.fields = fields;
    }

    public void callSetter(Object bean, String fieldName, Object value) throws InvocationTargetException, IllegalAccessException
    {
        if (!fieldNameIndexes.containsKey(fieldName)) throw new RuntimeException("No such field declared: " + fieldName + " in " + bean.getClass().getName());
        this.fields[fieldNameIndexes.get(fieldName)].getSetter().invoke(bean, value);
    }

    public Object callGetter(Object bean, String fieldName) throws InvocationTargetException, IllegalAccessException
    {
        if (!fieldNameIndexes.containsKey(fieldName)) throw new RuntimeException("No such field declared: " + fieldName + " in " + bean.getClass().getName());
        return this.fields[fieldNameIndexes.get(fieldName)].getSetter().invoke(bean);
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeField[] getFields() {
        return fields;
    }

    public void setFields(TypeField[] fields)
    {
        this.fields = fields;
        for (int i = 0; i < fields.length; i++) fieldNameIndexes.put(fields[i].getName(), i);
    }

    @Override
    public String toString() {
        String text = "";
        text += "name = " + this.name + ",\n";
        text += "fields:\n";
        for (int i = 0; i < fields.length; i++)
        {
            text += "\t" + fields[i].toString() + "\n";
        }
        return text;
    }
}
