package cn.org.hentai.test;

import cn.org.hentai.dao.model.Type;
import cn.org.hentai.dao.util.ClassStructures;
import cn.org.hentai.test.model.User;

/**
 * Created by matrixy on 2018/4/5.
 */
public class StructureTest
{
    public static void main(String[] args) throws Exception
    {
        User user = new User();
        Type type = ClassStructures.get(User.class);
        System.out.println(type);
    }
}
