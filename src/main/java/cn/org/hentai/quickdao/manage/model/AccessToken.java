package cn.org.hentai.quickdao.manage.model;

import cn.org.hentai.db.annotation.DBField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by matrixy on 2017/8/27.
 */
public class AccessToken implements Serializable {
    // id
    @DBField(name = "id")
    private Long id;

    // user_id
    @DBField(name = "user_id")
    private Long userId;

    // token
    @DBField(name = "token")
    private String token;

    // rand
    @DBField(name = "rand")
    private String rand;

    // expire_time
    @DBField(name = "expire_time")
    private Date expireTime;

    // create_time
    @DBField(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
