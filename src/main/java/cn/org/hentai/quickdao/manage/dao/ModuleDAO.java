package cn.org.hentai.quickdao.manage.dao;

import cn.org.hentai.db.DBAccess;
import cn.org.hentai.quickdao.manage.model.Module;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by matrixy on 2017/8/26.
 */
@Repository
public class ModuleDAO extends DBAccess
{
    public List<Module> list()
    {
        return select().orderBy("id", "asc").queryForList(Module.class);
    }

    public List<Module> findByAppId(long appId)
    {
        return select().where(clause("app_id = ?", appId)).orderBy("id", "asc").queryForList(Module.class);
    }

    public String configureTableName()
    {
        return "sys_modules";
    }

    public String[] configureFields()
    {
        return new String[] { "id", "app_id", "name", "icon", "target", "url" };
    }

    public String primaryKey()
    {
        return "id";
    }
}
