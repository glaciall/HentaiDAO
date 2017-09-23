package cn.org.hentai.quickdao.manage.dao;

import cn.org.hentai.db.DBAccess;
import cn.org.hentai.quickdao.manage.model.App;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by matrixy on 2017/8/26.
 */
@Repository
public class AppDAO extends DBAccess
{
    public List<App> list()
    {
        return select().orderBy("id", "asc").queryForList(App.class);
    }

    public String configureTableName()
    {
        return "sys_apps";
    }

    public String[] configureFields()
    {
        return new String[] { "id", "name", "icon" };
    }

    public String primaryKey()
    {
        return "id";
    }
}
