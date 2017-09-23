package cn.org.hentai.quickdao.common.interceptor;

import cn.org.hentai.quickdao.manage.dao.AppDAO;
import cn.org.hentai.quickdao.manage.dao.ModuleDAO;
import cn.org.hentai.quickdao.manage.model.Admin;
import cn.org.hentai.quickdao.manage.model.App;
import cn.org.hentai.quickdao.manage.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matrixy on 2017/8/26.
 */
public class AdminInterceptor extends HandlerInterceptorAdapter
{
    @Autowired
    AdminService adminService;

    @Resource(name = "appDAO")
    @Autowired
    AppDAO appDAO;

    @Resource(name = "moduleDAO")
    @Autowired
    ModuleDAO moduleDAO;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception
    {
        // 获取登陆的用户身份
        Map<String, String> cookies = new HashMap<String, String>();
        Cookie[] cks = request.getCookies();
        cks = cks == null ? new Cookie[0] : cks;
        for (int i = 0; i < cks.length; i++) cookies.put(cks[i].getName(), cks[i].getValue());

        if (!cookies.containsKey("admin_id") || !cookies.containsKey("token"))
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        long adminId = Long.parseLong(cookies.get("admin_id"));
        String token = cookies.get("token");

        Admin admin = adminService.getById(adminId);
        if (null == admin)
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (admin.getState() != 1)
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (!admin.getToken().equals(token))
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (admin.getTokenExpireTime().getTime() < System.currentTimeMillis())
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        request.setAttribute("login_admin", admin);

        // 侧边栏菜单
        List<App> apps = appDAO.list();
        for (int i = 0; i < apps.size(); i++)
        {
            App app = apps.get(i);
            app.setModules(moduleDAO.findByAppId(app.getId()));
        }
        request.setAttribute("apps", apps);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception
    {
        // do nothing here...
    }
}
