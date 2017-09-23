package cn.org.hentai.quickdao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by matrixy on 2017/9/5.
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        Date now = new SimpleDateFormat("yyyy-MM-dd").parse("2017-2-1");
        now.setMonth(now.getMonth() + 1);
        System.out.println(now.toLocaleString());
    }
}
