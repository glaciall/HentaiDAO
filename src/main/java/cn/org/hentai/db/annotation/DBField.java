package cn.org.hentai.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface DBField
{
    String name();
    Class type() default Object.class;
}
