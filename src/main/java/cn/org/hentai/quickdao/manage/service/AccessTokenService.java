package cn.org.hentai.quickdao.manage.service;

import cn.org.hentai.quickdao.manage.dao.AccessTokenDAO;
import cn.org.hentai.quickdao.manage.model.AccessToken;
import cn.org.hentai.quickdao.manage.model.User;
import cn.org.hentai.quickdao.util.Configs;
import cn.org.hentai.quickdao.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by matrixy on 2017/8/26.
 */
@Service
public class AccessTokenService
{
    @Resource(name = "accessTokenDAO")
    @Autowired
    AccessTokenDAO accessTokenDAO;

    /**
     * 为user创建accesstoken
     * @param user 待创建accesstoken的用户
     * @return
     */
    public AccessToken create(User user)
    {
        AccessToken token = new AccessToken();
        token.setUserId(user.getId());
        token.setRand(getRand());
        token.setToken(MD5.encode(Configs.get("bms.user.token.key") + ":::" + user.getId() + ":::" + token.getRand()));
        token.setCreateTime(new Date());
        // 与cookie有效期保持一致
        token.setExpireTime(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30));
        return token;
    }

    public boolean check(User user, String token, AccessToken accessToken)
    {
        if (null == token) return false;
        if (accessToken.getExpireTime().getTime() < System.currentTimeMillis()) return false;
        if (!accessToken.getToken().equals(token)) return false;
        return true;
    }

    public AccessToken getByToken(long uid, String token)
    {
        return accessTokenDAO.getByToken(uid, token);
    }

    public long save(AccessToken token)
    {
        return accessTokenDAO.save(token);
    }

    public String getRand()
    {
        return getRand(32);
    }

    public String getRand(int length)
    {
        String[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".split("");
        StringBuffer sb = new StringBuffer((int)(length * 1.5));
        for (int i = 0; i < length; i++)
        {
            sb.append(chars[(int)(Math.random() * chars.length)]);
        }
        return sb.toString();
    }
}
