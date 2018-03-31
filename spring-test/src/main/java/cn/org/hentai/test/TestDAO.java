package cn.org.hentai.test;

import cn.org.hentai.dao.HentaiDAO;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public class TestDAO extends HentaiDAO
{
    public List<TestModel> find()
    {
        return select()
                .where(clause("id = ?", gtz(12)))
                .queryForList(TestModel.class);
    }

    @Override
    public String[] configureFields()
    {
        return new String[] { "id", "name" };
    }

    @Override
    public String configureTableName()
    {
        return "test";
    }
}
