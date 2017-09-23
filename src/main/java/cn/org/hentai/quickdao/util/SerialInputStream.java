package cn.org.hentai.quickdao.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by matrixy on 2017/9/21.
 * 串联流，把字节数组跟输入流拼接到一起，当作一个完整的输入流
 */
public class SerialInputStream extends InputStream
{
    byte[] prefix = null;
    InputStream inputStream = null;
    int offset = 0;
    public SerialInputStream(byte[] prefix, InputStream is)
    {
        this.prefix = prefix;
        this.inputStream = is;
    }

    @Override
    public int read() throws IOException
    {
        if (offset < prefix.length) return prefix[offset++];
        else return inputStream.read();
    }

    @Override
    public int read(byte[] buff) throws IOException
    {
        return read(buff, 0, buff.length);
    }

    @Override
    public int read(byte[] buff, int ost, int length) throws IOException
    {
        // 先从prefix里尽量读
        int len = Math.min(Math.min(prefix.length, prefix.length - offset), length);
        if (len > 0) System.arraycopy(
                prefix,                 // 数据源
                offset,                 // 数据源的起始位置
                buff,                   // 写入目标数组
                ost,                    // 目标的起始位置
                // 整几个字节呢？不大于prefix和buff的剩余长度、length的长度
                len);
        if (len == length) return len;
        return len + inputStream.read(buff, ost + len, length - len);
    }

    public static void main(String[] args) throws Exception
    {
        byte[] buff = new byte[24];
        for (int i = 0; i < buff.length; i++) buff[i] = (byte)(i + 1);
        ByteArrayInputStream bais = new ByteArrayInputStream(buff);

        bais.read();
        bais.read();
        bais.read();

        SerialInputStream sis = new SerialInputStream(new byte[] { 0x11, 0x22, 0x33 }, bais);
        byte[] data = new byte[24];
        sis.read(data);
        System.out.println(ByteUtils.toString(data));
    }

    public static void mains(String[] args) throws Exception
    {
        byte[] prefix = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09 };
        byte[] buff = new byte[240];
        for (int i = 0; i < buff.length; i++) buff[i] = (byte)(i + 0x0a);

        SerialInputStream sis = new SerialInputStream(prefix, new ByteArrayInputStream(buff));
        byte[] data = new byte[512];
        sis.read();
        sis.read();
        sis.read();
        sis.read();
        sis.read();
        sis.read();
        sis.read();
        sis.read();
        sis.read();
        sis.read();
        int len = sis.read(data, 0, 10);
        System.out.println("Len: " + len);
        System.out.println(ByteUtils.toString(data));

        if (data != null) return;
        for (int i = 0; i < 250; i++)
        {
            int val = sis.read();
            if (val == -1) break;
            System.out.println(Integer.toHexString(val));
        }
    }
}
