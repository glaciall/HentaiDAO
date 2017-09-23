package cn.org.hentai.quickdao.util;

/**
 * Created by matrixy on 2016/12/29.
 */
public class Position
{
    private Double longitude = 0.0d;
    private Double latitude = 0.0d;
    private Double altitude = 0.0d;

    private String address;
    public Position(){}
    public Position(Double longitude, Double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Position{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", address='" + address + '\'' +
                '}';
    }
}
