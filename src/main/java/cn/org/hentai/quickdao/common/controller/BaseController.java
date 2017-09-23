package cn.org.hentai.quickdao.common.controller;

import cn.org.hentai.quickdao.common.model.Result;
import cn.org.hentai.quickdao.manage.model.Admin;
import cn.org.hentai.quickdao.util.CustomDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by matrixy on 2016/12/30.
 */
public abstract class BaseController
{
    @Autowired
    HttpServletRequest request;


    @Autowired
    HttpSession session;

    public void setAttribute(String key, Object value)
    {
        request.setAttribute(key, value);
    }

    // 获取登陆用户
    public final Admin getLoginAdmin()
    {
        return (Admin)request.getAttribute("login_admin");
    }

    protected final int getInt(String name, int defaultVal)
    {
        String val = request.getParameter(name);
        if (val == null || !val.matches("^-?\\d+$")) return defaultVal;
        return Integer.parseInt(val);
    }

    protected final float getFloat(String name, float defaultVal)
    {
        String val = request.getParameter(name);
        if (val == null || !val.matches("^-?\\d+(\\.\\d+)?$")) return defaultVal;
        return Float.parseFloat(val);
    }

    protected final long getLong(String name, long defaultVal)
    {
        String val = request.getParameter(name);
        if (val == null || !val.matches("^-?\\d+$")) return defaultVal;
        return Long.parseLong(val);
    }

    protected final String getString(String name)
    {
        String val = request.getParameter(name);
        if ("".equals(val)) return null;
        return val == null ? val : val.trim();
    }

    protected final String getString(String name, String format, String defaultVal)
    {
        String value = this.getString(name);
        if (null == value) return defaultVal;
        if (!value.matches(format)) return defaultVal;
        return value == null ? null : value.trim();
    }

    public final String getIP()
    {
        String addr = request.getHeader("X-Forwarded-For");
        if (null == addr) return request.getRemoteAddr();
        if (addr.indexOf(',') == -1) return addr;
        else return addr.substring(0, addr.indexOf(',')).trim();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex)
    {
        System.err.println("Controller异常：" + ex.getClass().getName());
        ex.printStackTrace();
        return new Result().setError(ex);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        CustomDateFormat dateFormat = new CustomDateFormat();
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
