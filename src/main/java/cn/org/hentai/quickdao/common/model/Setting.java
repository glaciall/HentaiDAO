package cn.org.hentai.quickdao.common.model;

import cn.org.hentai.db.annotation.DBField;

import java.io.Serializable;

/**
 * Created by matrixy on 2017/8/26.
 */
public class Setting implements Serializable {
    // id
    @DBField(name = "id")
    private Long id;

    // name
    @DBField(name = "name")
    private String name;

    // cname
    @DBField(name = "cname")
    private String cname;

    // val
    @DBField(name = "val")
    private String val;

    public Long getId() {
        return id;
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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cname='" + cname + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
