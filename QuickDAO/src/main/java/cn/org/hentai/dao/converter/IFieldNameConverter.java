package cn.org.hentai.dao.converter;

/**
 * Created by matrixy on 2018/4/5.
 */
public interface IFieldNameConverter
{
    /**
     * 从数据库字段名映射到类成员名称
     * @param dbfieldName
     * @return
     */
    public String fromDatabase(String dbfieldName);

    /**
     * 从类成员名称映射到数据库字段名
     * @param propertyName
     * @return
     */
    public String fromProperty(String propertyName);
}
