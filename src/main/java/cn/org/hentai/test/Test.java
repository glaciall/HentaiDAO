package cn.org.hentai.test;

import cn.org.hentai.db.HentaiDAO;

/**
 * Created by matrixy on 2017/11/10.
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        TestDAO testDAO = new TestDAO();
        testDAO.find(12, "abc", 1, 10);
        testDAO.findCount(12, "abc");

        TestModel model = new TestModel();
        model.setId(12);
        model.setName("test");
        testDAO.save(model);

        testDAO.update(model);
    }
}
