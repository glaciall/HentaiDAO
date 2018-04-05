package cn.org.hentai.dao.converter;

/**
 * Created by matrixy on 2018/4/5.
 * 字段名与类成员名一模一样时适用
 */
public class EquivalentConverterBase implements BaseFieldNameConverter
{
    public String fromDatabase(String dbfieldName)
    {
        return dbfieldName;
    }

    public String fromProperty(String propertyName)
    {
        return propertyName;
    }
}
