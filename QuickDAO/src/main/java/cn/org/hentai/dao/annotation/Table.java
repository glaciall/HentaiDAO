package cn.org.hentai.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by matrixy on 2018/4/5.
 * 表名注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface Table
{
    public String value();
}
