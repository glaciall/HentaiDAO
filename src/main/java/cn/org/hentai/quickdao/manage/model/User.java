package cn.org.hentai.quickdao.manage.model;

import cn.org.hentai.db.annotation.DBField;
import cn.org.hentai.db.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by matrixy on 2017/8/27.
 */
public class User implements Serializable {
    // id
    @DBField(name = "id")
    private Long id;

    // username
    @DBField(name = "username")
    private String username;

    // password
    @DBField(name = "password")
    private String password;

    // salt
    @DBField(name = "salt")
    private String salt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
