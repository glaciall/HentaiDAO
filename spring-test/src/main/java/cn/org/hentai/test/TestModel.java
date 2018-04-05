package cn.org.hentai.test;

import cn.org.hentai.dao.annotation.Field;
import cn.org.hentai.dao.annotation.Table;

/**
 * Created by matrixy on 2017/11/10.
 */
@Table("sys_test")
public class TestModel
{
    @Field
    private int id;

    @Field
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
