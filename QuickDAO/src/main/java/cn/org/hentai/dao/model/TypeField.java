package cn.org.hentai.dao.model;

import java.lang.reflect.Method;

/**
 * Created by matrixy on 2018/4/5.
 */
public class TypeField
{
    private String name;
    private Class type;
    private Method getter;
    private Method setter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    @Override
    public String toString() {
        return "TypeField{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", getter=" + getter.toString() +
                ", setter=" + setter.toString() +
                '}';
    }
}
