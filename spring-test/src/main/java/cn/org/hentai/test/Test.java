package cn.org.hentai.test;

import cn.org.hentai.dao.HentaiDAO;

/**
 * Created by matrixy on 2017/11/10.
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        HentaiDAO.registerJDBCBridge(new TestBridge());

        TestDAO testDAO = new TestDAO();
        testDAO.find();
    }
}
