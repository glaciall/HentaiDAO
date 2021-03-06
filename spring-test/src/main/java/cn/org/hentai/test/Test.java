package cn.org.hentai.test;

import cn.org.hentai.dao.HentaiDAO;
import cn.org.hentai.test.model.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by matrixy on 2017/11/10.
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        HentaiDAO.setupJDBCBridge(new TestBridge());

        User user = new User();
        user.setId(1122L);
        user.setName("matrixy");
        user.setDelete(true);
        user.setEncryptedPassword("fake-encrypted-password-123456");
        user.setIgnoreField("hahahaha~~~");
        user.setBalance(new BigDecimal("3.14159265358979"));
        user.setCreateTime(new Date());

        TestDAO testDAO = new TestDAO();

        // 主键查询
        testDAO.getById(12);

        // in (*)查询
        testDAO.find(new int[] { 1, 3, 5, 7, 9 });

        // 获取单列的值
        testDAO.getName(33);

        // insert
        testDAO.save(user);

        // update
        testDAO.update(user);
    }
}
