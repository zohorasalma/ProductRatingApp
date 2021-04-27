package org.tensorflow.lite.examples.classification.model;

public class SensorDataProvider
{
    private int id;
    private String timestamp;
    private String xAccelerometer,yAccelerometer,zAccelerometer;
    private String xGyroscope,yGyroscope,zGyroscope;
    private String xMagnetometer,yMagnetometer,zMagnetometer;
    private String light;
    private String longitude,latitude,address;

    public SensorDataProvider()
    {
    }

    public SensorDataProvider(
            int id,
            String timestamp,
            String xAccelerometer,
            String yAccelerometer,
            String zAccelerometer,
            String xGyroscope,
            String yGyroscope,
            String zGyroscope,
            String xMagnetometer,
            String yMagnetometer,
            String zMagnetometer,
            String light,
            String longitude,
            String latitude,
            String address)
    {
        this.id = id;
        this.timestamp = timestamp;
        this.xAccelerometer = xAccelerometer;
        this.yAccelerometer = yAccelerometer;
        this.zAccelerometer = zAccelerometer;
        this.xGyroscope = xGyroscope;
        this.yGyroscope = yGyroscope;
        this.zGyroscope = zGyroscope;
        this.xMagnetometer = xMagnetometer;
        this.yMagnetometer = yMagnetometer;
        this.zMagnetometer = zMagnetometer;
        this.light = light;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getxAccelerometer()
    {
        return xAccelerometer;
    }

    public void setxAccelerometer(String xAccelerometer)
    {
        this.xAccelerometer = xAccelerometer;
    }

    public String getyAccelerometer()
    {
        return yAccelerometer;
    }

    public void setyAccelerometer(String yAccelerometer)
    {
        this.yAccelerometer = yAccelerometer;
    }

    public String getzAccelerometer()
    {
        return zAccelerometer;
    }

    public void setzAccelerometer(String zAccelerometer)
    {
        this.zAccelerometer = zAccelerometer;
    }

    public String getxGyroscope()
    {
        return xGyroscope;
    }

    public void setxGyroscope(String xGyroscope)
    {
        this.xGyroscope = xGyroscope;
    }

    public String getyGyroscope()
    {
        return yGyroscope;
    }

    public void setyGyroscope(String yGyroscope)
    {
        this.yGyroscope = yGyroscope;
    }

    public String getzGyroscope()
    {
        return zGyroscope;
    }

    public void setzGyroscope(String zGyroscope)
    {
        this.zGyroscope = zGyroscope;
    }

    public String getxMagnetometer()
    {
        return xMagnetometer;
    }

    public void setxMagnetometer(String xMagnetometer)
    {
        this.xMagnetometer = xMagnetometer;
    }

    public String getyMagnetometer()
    {
        return yMagnetometer;
    }

    public void setyMagnetometer(String yMagnetometer)
    {
        this.yMagnetometer = yMagnetometer;
    }

    public String getzMagnetometer()
    {
        return zMagnetometer;
    }

    public void setzMagnetometer(String zMagnetometer)
    {
        this.zMagnetometer = zMagnetometer;
    }

    public String getLight()
    {
        return light;
    }

    public void setLight(String light)
    {
        this.light = light;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "SensorDataProvider{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", xAccelerometer='" + xAccelerometer + '\'' +
                ", yAccelerometer='" + yAccelerometer + '\'' +
                ", zAccelerometer='" + zAccelerometer + '\'' +
                ", xGyroscope='" + xGyroscope + '\'' +
                ", yGyroscope='" + yGyroscope + '\'' +
                ", zGyroscope='" + zGyroscope + '\'' +
                ", xMagnetometer='" + xMagnetometer + '\'' +
                ", yMagnetometer='" + yMagnetometer + '\'' +
                ", zMagnetometer='" + zMagnetometer + '\'' +
                ", light='" + light + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
