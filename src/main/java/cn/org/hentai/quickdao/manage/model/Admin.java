package cn.org.hentai.quickdao.manage.model;

import cn.org.hentai.db.annotation.DBField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by matrixy on 2017/8/26.
 */
public class Admin implements Serializable {
    // id
    @DBField(name = "id")
    private Long id;

    // role_id
    @DBField(name = "role_id")
    private Long roleId;

    // name
    @DBField(name = "name")
    private String name;

    // password
    @DBField(name = "password")
    private String password;

    // salt
    @DBField(name = "salt")
    private String salt;

    // rand
    @DBField(name = "rand")
    private String rand;

    // token
    @DBField(name = "token")
    private String token;

    // last_login
    @DBField(name = "last_login")
    private Date lastLogin;

    // create_time
    @DBField(name = "create_time")
    private Date createTime;

    // token expire time
    @DBField(name = "token_expire_time")
    private Date tokenExpireTime;

    @DBField(name = "state")
    private Integer state;

    public Date getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Date tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Admin safe()
    {
        this.setToken(null);
        this.setTokenExpireTime(null);
        this.setRand(null);
        this.setSalt(null);
        this.setPassword(null);
        return this;
    }
}
