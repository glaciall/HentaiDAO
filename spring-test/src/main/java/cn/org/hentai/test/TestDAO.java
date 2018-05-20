package cn.org.hentai.test;

import cn.org.hentai.dao.HentaiDAO;
import cn.org.hentai.test.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by matrixy on 2017/11/10.
 */
public class TestDAO extends HentaiDAO
{
    public User getById(int id)
    {
        return select()
                .from(User.class)
                .where(
                        clause("id = ?", gtz(id))
                        .and("balance > ?", 3.14)
                        .and("create_time < ?", today())
                )
                .query();
    }

    public Long save(User user)
    {
        return insertInto().valueWith(user).save();
    }

    public Long update(User user)
    {
        return update().valueWith(user).byId().execute();
    }
}
