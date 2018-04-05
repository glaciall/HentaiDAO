package cn.org.hentai.test.model;

import cn.org.hentai.dao.annotation.Field;
import cn.org.hentai.dao.annotation.Table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by matrixy on 2018/4/5.
 */
@Table("sys_users")
public class User
{
    @Field(pk = true)
    private Long id;

    @Field
    private String name;

    @Field
    private Boolean isDelete;

    @Field
    private Date createTime;

    @Field
    private BigDecimal balance;

    @Field
    private String encryptedPassword;

    private String ignoreField;

    public String getIgnoreField() {
        return ignoreField;
    }

    public void setIgnoreField(String ignoreField) {
        this.ignoreField = ignoreField;
    }

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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", balance=" + balance +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                '}';
    }
}
