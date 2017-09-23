package cn.org.hentai.quickdao.manage.service;

import cn.org.hentai.db.model.Page;
import cn.org.hentai.quickdao.common.exception.Problem;
import cn.org.hentai.quickdao.manage.dao.AdminDAO;
import cn.org.hentai.quickdao.manage.model.Admin;
import cn.org.hentai.quickdao.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by matrixy on 2017/8/26.
 */
@Service
public class AdminService
{
    @Resource(name = "adminDAO")
    @Autowired
    AdminDAO adminDAO;

    @Autowired
    AccessTokenService accessTokenService;

    public Admin getById(long id)
    {
        return adminDAO.getById(id);
    }

    public Admin getByName(String username)
    {
        return adminDAO.getByName(username);
    }

    public int updateLoginInfo(Admin admin)
    {
        return adminDAO.login(admin);
    }

    public Page<Admin> find(int pageIndex, int pageSize)
    {
        Page<Admin> page = new Page<Admin>(pageIndex, pageSize);
        page.setList(adminDAO.find(pageIndex, pageSize));
        return page;
    }

    public Admin login(String username, String password)
    {
        Admin admin = adminDAO.getByName(username);
        if (null == admin) throw new Problem(1, "无此管理员账号");

        if (admin.getState() != 1) throw new Problem(2, "账号己禁用");

        String pwd = MD5.encode(password + ":::" + admin.getSalt());
        if (!pwd.equals(admin.getPassword())) throw new Problem(3, "密码错误");

        admin.setRand(accessTokenService.getRand());
        admin.setToken(MD5.encode(admin.getRand() + ":::" + admin.getId() + ":::" + admin.getName()));
        admin.setTokenExpireTime(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24));
        admin.setLastLogin(new Date());

        adminDAO.login(admin);

        return admin;
    }
}
