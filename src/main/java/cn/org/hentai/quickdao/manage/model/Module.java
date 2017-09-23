package cn.org.hentai.quickdao.manage.model;

import cn.org.hentai.db.annotation.DBField;

import java.io.Serializable;

/**
 * Created by matrixy on 2017/8/26.
 */
public class Module implements Serializable {
    // id
    @DBField(name = "id")
    private Long id;

    // app_id
    @DBField(name = "app_id")
    private Long appId;

    // name
    @DBField(name = "name")
    private String name;

    // icon
    @DBField(name = "icon")
    private String icon;

    // target
    @DBField(name = "target")
    private String target;

    // url
    @DBField(name = "url")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
