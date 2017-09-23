package cn.org.hentai.quickdao.manage.model;

import cn.org.hentai.db.annotation.DBField;
import cn.org.hentai.db.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by matrixy on 2017/8/26.
 */
public class App implements Serializable {
    // id
    @DBField(name = "id")
    private Long id;

    // name
    @DBField(name = "name")
    private String name;

    // icon
    @DBField(name = "icon")
    private String icon;

    public Long getId() {
        return id;
    }

    @Transient
    List<Module> modules;

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void setId(Long id) {
        this.id = id;
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
}
