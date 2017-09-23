package cn.org.hentai.quickdao.util;

import java.security.MessageDigest;

/**
 * Created by matrixy on 2017/8/31.
 */
public final class SHA1
{
    public static String encrypt(String str)
    {
        if (null == str || 0 == str.length())
        {
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try
        {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
