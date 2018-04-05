package cn.org.hentai.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface Field
{
    // 字段名，数据库格式
    String name() default "";

    // 数据类型
    Class type() default Object.class;

    // 是否主键
    boolean pk() default false;
}
