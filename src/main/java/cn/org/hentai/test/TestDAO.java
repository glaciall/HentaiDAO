package cn.org.hentai.test;

import cn.org.hentai.db.HentaiDAO;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public class TestDAO extends HentaiDAO
{
    public List<TestModel> find(int id, String name, int pageIndex, int pageSize)
    {
        return select()
                .where(clause("name like ?", like(name)).and("id = ?", gtz(12)))
                .queryForPaginate(TestModel.class, pageIndex, pageSize);
    }

    public int save(TestModel model)
    {
        return (Integer)insertInto().valueWith(model).save();
    }

    public Long update(TestModel model)
    {
        return update().valueWith(model).byId().execute();
    }

    public Long findCount(int id, String name)
    {
        return select()
                .where(clause("name like ?", like(name)).and("id = ?", gtz(id)))
                .queryForCount();
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
