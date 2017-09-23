package cn.org.hentai.quickdao.manage.controller;

import cn.org.hentai.db.model.Page;
import cn.org.hentai.quickdao.common.controller.BaseController;
import cn.org.hentai.quickdao.common.exception.Problem;
import cn.org.hentai.quickdao.common.model.Result;
import cn.org.hentai.quickdao.common.model.Setting;
import cn.org.hentai.quickdao.common.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by matrixy on 2017/9/11.
 */
@RequestMapping("/manage/setting")
@Controller
public class SettingController extends BaseController
{
    @Autowired
    SettingService settingService;

    @RequestMapping("/index")
    public String index()
    {
        return "/manage/setting/index";
    }

    @RequestMapping("/list/json")
    @ResponseBody
    public Result listJosn()
    {
        Result result = new Result();
        Page<Setting> page = new Page<Setting>(1, 100000);
        page.setList(settingService.list());
        result.setData(page);
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@RequestParam long settingId,
                         @RequestParam String cname,
                         @RequestParam String val)
    {
        Result result = new Result();
        Setting setting = settingService.getById(settingId);
        if (null == setting) throw new Problem("查无此设置项");
        setting.setCname(cname);
        setting.setVal(val);
        result.setData(settingService.update(setting));
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result save(@RequestParam String name,
                       @RequestParam String cname,
                       @RequestParam String val)
    {
        Result result = new Result();
        Setting setting = new Setting();
        setting.setName(name);
        setting.setCname(cname);
        setting.setVal(val);
        result.setData(settingService.save(setting));
        return result;
    }
}
