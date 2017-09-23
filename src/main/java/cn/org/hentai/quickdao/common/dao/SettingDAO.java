package cn.org.hentai.quickdao.common.dao;

import cn.org.hentai.db.DBAccess;
import cn.org.hentai.quickdao.common.model.Setting;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by matrixy on 2017/8/26.
 */
@Repository
public class SettingDAO extends DBAccess
{
    public Setting getByName(String name)
    {
        List<Setting> settings = select().where(clause("name = ?", name)).queryForLimit(Setting.class, 1);
        if (settings.size() > 0) return settings.get(0);
        else return null;
    }

    public List<Setting> find()
    {
        return select().queryForList(Setting.class);
    }

    public long save(Setting setting)
    {
        long id = insertInto().valueWith(setting).save();
        setting.setId(id);
        return id;
    }

    public int update(Setting setting)
    {
        return update().valueWith(setting).byId().execute();
    }

    public int delete(long id)
    {
        return execute("delete from settings where id = ?", id);
    }

    public String configureTableName()
    {
        return "settings";
    }

    public String[] configureFields()
    {
        return new String[] { "id", "name", "cname", "val" };
    }

    public String primaryKey()
    {
        return "id";
    }

    public Setting getById(long settingId)
    {
        return select()
                .where(clause("id = ?", settingId))
                .query(Setting.class);
    }
}
