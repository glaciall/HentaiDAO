package cn.org.hentai.dao.util;

import cn.org.hentai.dao.HentaiDAO;
import cn.org.hentai.dao.annotation.Field;
import cn.org.hentai.dao.annotation.Table;
import cn.org.hentai.dao.model.Type;
import cn.org.hentai.dao.model.TypeField;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by matrixy on 2018/4/5.
 * 缓存及获取类注解
 */
public final class ClassStructures
{
    private static ConcurrentHashMap<String, Type> types = new ConcurrentHashMap<String, Type>();

    /**
     * 从缓存中获取类结构，如果缓存未命中，则通过反射获取
     * @param typeClass
     * @return
     */
    public static Type get(Class typeClass)
    {
        if (types.containsKey(typeClass.getName())) return types.get(typeClass.getName());
        Type type = types.get(typeClass.getName());
        if (null == type)
        {
            try
            {
                type = reflect(typeClass);
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
            types.put(typeClass.getName(), type);
        }
        return type;
    }

    private static Type reflect(Class typeClass) throws Exception
    {
        Type type = new Type();

        // 获取@Table
        Table typeAnno = (Table)typeClass.getAnnotation(Table.class);
        if (null == typeAnno) throw new RuntimeException("No @Table annotation declared: " + typeClass.getName());
        type.setName(typeAnno.value());

        int fieldCount = 0;
        java.lang.reflect.Field[] clsFields = typeClass.getDeclaredFields();
        LinkedList<TypeField> typeFields = new LinkedList<TypeField>();
        String pk = null;
        for (int i = 0; i < clsFields.length; i++)
        {
            java.lang.reflect.Field field = clsFields[i];
            // 跳过静态成员
            if (Modifier.isStatic(field.getModifiers())) continue;

            // 跳过无@Field注解的成员
            Field attr = (Field)field.getAnnotation(cn.org.hentai.dao.annotation.Field.class);
            if (null == attr) continue;

            String fName = field.getName();
            if (field.getType().isAssignableFrom(Boolean.class)) fName = field.getName().replaceAll("^(is)?", "");

            String getterName = HentaiDAO.getFieldNameConverter().fromDatabase("get_" + fName);
            String setterName = HentaiDAO.getFieldNameConverter().fromDatabase("set_" + fName);

            String fieldName = null;
            if (attr != null && attr.name().trim().length() > 0) fieldName = attr.name();
            else fieldName = HentaiDAO.getFieldNameConverter().fromProperty(field.getName());

            Class varType = field.getType();
            if (!attr.type().equals(Object.class)) varType = attr.type();

            Method getter = typeClass.getDeclaredMethod(getterName);
            Method setter = typeClass.getDeclaredMethod(setterName, field.getType());

            if (attr.pk()) pk = fieldName;

            TypeField typeField = new TypeField();
            typeField.setName(fieldName);
            typeField.setType(varType);
            typeField.setGetter(getter);
            typeField.setSetter(setter);

            typeFields.add(typeField);
            fieldCount++;
        }
        if (null == pk) throw new RuntimeException("No primary key declared: " + typeClass.getName());
        type.setPrimaryKey(pk);
        type.setFields(typeFields.toArray(new TypeField[fieldCount]));
        return type;
    }
}
