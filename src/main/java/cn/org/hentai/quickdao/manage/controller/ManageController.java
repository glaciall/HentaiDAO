package cn.org.hentai.quickdao.manage.controller;

import cn.org.hentai.quickdao.common.controller.BaseController;
import cn.org.hentai.quickdao.common.exception.Problem;
import cn.org.hentai.quickdao.common.model.Result;
import cn.org.hentai.quickdao.manage.model.Admin;
import cn.org.hentai.quickdao.manage.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by matrixy on 2017/8/26.
 */
@Controller
public class ManageController extends BaseController
{
    @Autowired
    AdminService adminService;

    @RequestMapping({ "/", "/index/signin" })
    public String index()
    {
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login()
    {
        return "/manage/user/login";
    }

    @RequestMapping("/signin")
    @ResponseBody
    public Result signin(@RequestParam(required = false) String username,
                         @RequestParam(required = false) String password,
                         HttpServletResponse response)
    {
        if (StringUtils.isEmpty(username)) throw new Problem("请填写用户名");
        if (StringUtils.isEmpty(password)) throw new Problem("请填写登陆密码");

        Result result = new Result();
        Admin admin = adminService.login(username, password);

        Cookie aid = new Cookie("admin_id", String.valueOf(admin.getId()));
        aid.setMaxAge((int)((admin.getTokenExpireTime().getTime() - System.currentTimeMillis()) / 1000));
        response.addCookie(aid);

        Cookie token = new Cookie("token", admin.getToken());
        token.setMaxAge((int)((admin.getTokenExpireTime().getTime() - System.currentTimeMillis()) / 1000));
        response.addCookie(token);

        return result;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response)
    {
        Cookie aid = new Cookie("admin_id", null);
        aid.setMaxAge(0);
        response.addCookie(aid);

        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/";
    }

    @RequestMapping("/manage/")
    public String home()
    {
        return "/manage/index/home";
    }
}
