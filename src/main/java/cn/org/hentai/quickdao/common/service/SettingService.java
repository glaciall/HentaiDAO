package cn.org.hentai.quickdao.common.service;

import cn.org.hentai.quickdao.common.dao.SettingDAO;
import cn.org.hentai.quickdao.common.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by matrixy on 2017/8/26.
 */
@Service
public class SettingService
{
    @Resource(name = "settingDAO")
    @Autowired
    SettingDAO settingDAO;

    public Setting getByName(String name)
    {
        return settingDAO.getByName(name);
    }

    public List<Setting> list()
    {
        return settingDAO.find();
    }

    public int delete(long id)
    {
        return settingDAO.delete(id);
    }

    public long save(Setting setting)
    {
        return settingDAO.save(setting);
    }

    public int update(Setting setting)
    {
        return settingDAO.update(setting);
    }

    public Setting getById(long settingId)
    {
        return settingDAO.getById(settingId);
    }
}
