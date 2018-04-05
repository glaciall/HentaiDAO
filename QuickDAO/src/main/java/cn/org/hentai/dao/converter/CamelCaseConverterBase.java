package cn.org.hentai.dao.converter;

import cn.org.hentai.dao.util.DbUtil;

/**
 * Created by matrixy on 2018/4/5.
 * 当数据库使用下划线，类成员使用驼峰式时适用
 */
public class CamelCaseConverterBase implements BaseFieldNameConverter
{
    public String fromDatabase(String dbfieldName)
    {
        return DbUtil.formatFieldName(dbfieldName);
    }

    public String fromProperty(String propertyName)
    {
        return DbUtil.toDBName(propertyName);
    }
}
