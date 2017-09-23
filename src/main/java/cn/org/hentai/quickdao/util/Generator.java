package cn.org.hentai.quickdao.util;

import java.io.DataInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by matrixy on 2017/1/6.
 */
// TODO: 需要删除
public class Generator
{
    public static void main(String[] args) throws Exception
    {
        DataInputStream dis = new DataInputStream(System.in);
        System.out.print("Enter Table Name: ");
        String tableName = dis.readLine();
        if (!tableName.matches("^\\w{3,}$"))
        {
            System.err.println("请输入正确的表名");
            return;
        }
        System.out.println("Connecting to database and obtain columns...");
        System.out.println("Table: " + tableName);

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery("select column_name,column_default,is_nullable,data_type,column_key,extra,column_comment from information_schema.columns where table_schema = 'bms' and table_name = '" + tableName + "' order by ordinal_position asc");

        String pk = null;

        StringBuffer provider = new StringBuffer(4096);
        provider.append("public class XXSQLProvider extends DBAccess\r\n");
        provider.append("{\r\n");
        provider.append("   public String configureTableName()\r\n");
        provider.append("   {\r\n");
        provider.append("       return \"" + tableName + "\";\r\n");
        provider.append("   }\r\n\r\n");

        StringBuffer buff = new StringBuffer(4096);
        buff.append("public class UserBean extends BaseModel\r\n");
        buff.append("{\r\n");
        ArrayList<String> columns = new ArrayList<String>();
        for (int i = 0; rst.next(); i++)
        {
            String name = rst.getString("column_name");
            String defaultValue = rst.getString("column_default");
            boolean nullable = "YES".equals(rst.getString("is_nullable"));
            String type = rst.getString("data_type");
            String key = rst.getString("column_key");
            String extra = rst.getString("extra");
            String comment = rst.getString("column_comment");

            String javaType = "Long";
            if ("bigint".equals(type)) javaType = "Long";
            else if ("varchar".equals(type)) javaType = "String";
            else if ("int".equals(type)) javaType = "Integer";
            else if ("tinyint".equals(type)) javaType = "Integer";
            else if ("bit".equals(type)) javaType = "Boolean";
            else if ("decimal".equals(type)) javaType = "java.math.BigDecimal";
            else if ("date".equals(type)) javaType = "java.util.Date";
            else if ("datetime".equals(type)) javaType = "java.util.Date";
            else if ("double".equals(type)) javaType = "Double";
            else
            {
                System.err.println("Unknown column type: " + type);
            }

            if ("PRI".equals(key)) pk = name;

            buff.append("    // " + ("".equals(comment) ? name : comment) + "\r\n");
            buff.append("    @DBField(name = \"" + name + "\")\r\n");
            buff.append("    private " + javaType + " " + formatFieldName(name) + ";\r\n");
            buff.append("\r\n");

            columns.add(name);
        }

        buff.append("}\r\n");

        provider.append("    public String[] configureFields()\r\n");
        provider.append("    {\r\n");
        provider.append("        return new String[] {");
        for (int i = 0, l = columns.size(); i < l; i++)
        {
            provider.append(" ");
            provider.append('"');
            provider.append(columns.get(i));
            provider.append('"');
            if (i < l - 1) provider.append(",");
        }
        provider.append(" };\r\n");
        provider.append("   }\r\n\r\n");

        provider.append("    public String primaryKey()\r\n");
        provider.append("    {\r\n");
        provider.append("        return \"" + pk + "\";\r\n");
        provider.append("    }\r\n");
        provider.append("}\r\n");

        System.out.println("*********************************************************************");
        System.out.println(buff);
        System.out.println("*********************************************************************");
        System.out.println(provider);
    }

    private static String formatFieldName(String name)
    {
        String newName = "";
        boolean found = false;
        if (name.indexOf('.') > -1) name = name.replaceAll("`?\\w+`?\\.", "");
        for (int i = 0; i < name.length(); i++)
        {
            char chr = name.charAt(i);
            if (chr == '_')
            {
                found = true;
                continue;
            }
            newName += found ? Character.toUpperCase(chr) : chr;
            found = false;
        }
        return newName;
    }

    private static Connection getConnection() throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://rm-m5e47529pvjcoeetso.mysql.rds.aliyuncs.com:3306/bms?autoReconnect=true&characterEncoding=utf-8&useSSL=false", "lqf", "sdut*2756977");
    }
}