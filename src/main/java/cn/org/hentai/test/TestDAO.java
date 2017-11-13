package cn.org.hentai.test;

import cn.org.hentai.db.HentaiDAO;
import cn.org.hentai.db.JDBCBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
@Repository
public class TestDAO extends HentaiDAO
{
    @Autowired
    SpringJDBCTemplateBridge testBridge;

    public JDBCBridge jdbcBridge()
    {
        return testBridge;
    }

    public List<TestModel> find(int id, String name, int pageIndex, int pageSize)
    {
        return select()
                .where(clause("name like ?", like(name)).and("id = ?", gtz(12)))
                .queryForPaginate(TestModel.class, pageIndex, pageSize);
    }

    public long save(TestModel model)
    {
        return insertInto().valueWith(model).save();
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
