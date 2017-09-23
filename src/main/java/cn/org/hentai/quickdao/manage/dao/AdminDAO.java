package cn.org.hentai.quickdao.manage.dao;

import cn.org.hentai.db.DBAccess;
import cn.org.hentai.quickdao.manage.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by matrixy on 2017/8/26.
 */
@Repository
public class AdminDAO extends DBAccess
{
    public List<Admin> find(int pageIndex, int pageSize)
    {
        return select().queryForPaginate(Admin.class, pageIndex, pageSize);
    }

    public Admin getById(long id)
    {
        return select().byId(id).query(Admin.class);
    }

    public Admin getByName(String username)
    {
        return select().where(clause("name = ?", username)).query(Admin.class);
    }

    public int login(Admin admin)
    {
        return update()
                .valueWith("rand", admin.getRand())
                .valueWith("token", admin.getToken())
                .valueWith("last_login", admin.getLastLogin())
                .valueWith("token_expire_time", admin.getTokenExpireTime())
                .by(clause("id = ?", admin.getId()))
                .execute();
    }

    public String configureTableName()
    {
        return "sys_admins";
    }

    public String[] configureFields()
    {
        return new String[] { "id", "role_id", "name", "password", "salt", "rand", "token", "last_login", "create_time", "state", "token_expire_time" };
    }

    public String primaryKey()
    {
        return "id";
    }
}
