package cn.org.hentai.quickdao.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matrixy on 2016/12/29.
 */
public final class LBS
{
    public static final int POLICY_AVOID_HIGHWAY = 10;
    public static final int POLICY_GENERAL = 11;    //默认策略
    public static final int POLICY_TRAFFIC_SENSITIVE = 12;
    public static final int POLICY_LEAST_DISTANCE = 13;

    private static final String ACCESSKEY = "Lx7lVCPY7y4DwNc1vqYc7INYa6FAh9vU";

    public static String getAddress(Position position)
    {
        try
        {
            JsonObject result = request("http://api.map.baidu.com/geocoder/v2/?location=" + position.getLatitude() + "," + position.getLongitude() + "&output=json&pois=0&ak=" + ACCESSKEY);
            if (result.get("status").getAsInt() != 0) throw new RuntimeException("位置转换失败: " + result.get("status").getAsInt());
            return result.get("result").getAsJsonObject().get("formatted_address").getAsString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Position> translate(List<Position> positions)
    {
        try
        {
            List<Position> ps = new ArrayList<Position>(positions.size());
            for (int x = 0, f = (int)Math.ceil(positions.size() / 100f); x < f; x++)
            {
                StringBuilder coords = new StringBuilder(1024);
                int i = (x) * 100;
                int l = (x + 1) * 100;
                l = l > positions.size() ? positions.size() : l;
                for (; i < l; i++)
                {
                    Position p = positions.get(i);
                    coords.append(p.getLongitude());
                    coords.append(',');
                    coords.append(p.getLatitude());
                    if (i < l - 1) coords.append(';');
                }
                System.err.println("Coords: " + coords.toString());
                ps.addAll(translate(coords.toString()));
            }

            return ps;
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private static List<Position> translate(String coords)
    {
        try
        {
            JsonObject result = request("http://api.map.baidu.com/geoconv/v1/?coords=" + coords + "&ak=" + ACCESSKEY + "&from=1&to=5");
            if (result.get("status").getAsInt() != 0) throw new RuntimeException("位置转换失败: " + result.get("status").getAsInt());
            JsonArray list = result.get("result").getAsJsonArray();
            List<Position> ps = new ArrayList<Position>(130);
            for (int i = 0; i < list.size(); i++)
            {
                JsonObject item = list.get(i).getAsJsonObject();
                Position p = new Position();
                p.setLongitude(item.get("x").getAsDouble());
                p.setLatitude(item.get("y").getAsDouble());
                ps.add(p);
            }
            return ps;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Position translate(Position position, int fromType)
    {
        try
        {
            JsonObject result = request("http://api.map.baidu.com/geoconv/v1/?coords=" + position.getLongitude() + "," + position.getLatitude() + "&ak=" + ACCESSKEY + "&from=" + fromType + "&to=5");
            if (result.get("status").getAsInt() != 0) throw new RuntimeException("位置转换失败: " + result.get("status").getAsInt());
            JsonArray list = result.get("result").getAsJsonArray();
            List<Position> ps = new ArrayList<Position>(130);
            for (int i = 0; i < list.size(); i++)
            {
                JsonObject item = list.get(i).getAsJsonObject();
                Position p = new Position();
                p.setLongitude(item.get("x").getAsDouble());
                p.setLatitude(item.get("y").getAsDouble());
                return p;
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Position getPositionByAddress(String address)
    {
        try
        {
            JsonObject result = request("http://api.map.baidu.com/geocoder/v2/?address=" + java.net.URLEncoder.encode(address, "UTF-8") + "&output=json&ak=" + ACCESSKEY);

            if (result.get("status").getAsInt() > 0)
            {
                showError(result.get("status").getAsInt(), address);
                return null;
            }

            Position position = new Position();
            position.setLongitude(result.getAsJsonObject("result").getAsJsonObject("location").get("lng").getAsDouble());
            position.setLatitude(result.getAsJsonObject("result").getAsJsonObject("location").get("lat").getAsDouble());
            // position.setAddress(address);
            return position;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private static JsonObject request(String url) throws Exception
    {
        String result = Http.get(url);
        return new JsonParser().parse(result).getAsJsonObject();
    }

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    // 计算两点间直线距离
    public static int directDistance(Position p1, Position p2){
        Double lat1 = p1.getLatitude();
        Double lng1 = p1.getLongitude();
        Double lat2 = p2.getLatitude();
        Double lng2 = p2.getLongitude();

        return directDistance(lng1, lat1, lng2, lat2);
    }

    public static int directDistance(double lng1, double lat1, double lng2, double lat2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = (distance * 10000) / 10000 * 1000;
        return (int)distance;
    }

    public static int directDistance(String address, String addressT){
        Position p1 = getPositionByAddress(address);
        Position p2 = getPositionByAddress(addressT);
        return directDistance(p1,p2);
    }



    //两点间行驶距离
    public static int driverDistance(Position p1,Position p2,int tactics){
        String url = "http://api.map.baidu.com/routematrix/v2/driving?";
        url += "output=json&origins=" + p1.getLatitude() +  "," + p1.getLongitude() + "&destinations=" + p2.getLatitude() + "," + p2.getLongitude() + "&tactics=" + tactics +"&ak="+ACCESSKEY;
        try {
            JsonObject result = request(url);
            if (result.get("status").getAsInt() > 0)
            {
                showError(result.get("status").getAsInt(), null);
                return -1;
            }
            return ((result.getAsJsonArray("result").get(0)).getAsJsonObject().get("distance").getAsJsonObject().get("value")).getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static int driverDistance(Position p1,Position p2){
        return driverDistance(p1, p2,POLICY_GENERAL);
    }


    private static void showError(int code, Object parameters)
    {
        System.err.println("LBS Status: " + code);
        System.err.println("LBS Error: " + error(code));
        System.err.println("LBS Parameters: " + parameters);
    }

    private static String error(int code)
    {
        if (code == 1) return "服务器内部错误";
        if (code == 2) return "请求参数非法";
        if (code == 3) return "权限校验失败";
        if (code == 4) return "配额校验失败";
        if (code == 5) return "ak不存在或非法";
        if (code == 101) return "服务禁用";
        if (code == 102) return "不通过白名单或者安全码不对";
        if (code >= 200 && code < 300) return "无权限：" + code;
        if (code >= 300 && code < 400) return "配额错误：" + code;
        return "未知错误";
    }
}
