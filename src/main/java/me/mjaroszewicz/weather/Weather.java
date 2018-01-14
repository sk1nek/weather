package me.mjaroszewicz.weather;

/**
 * Created by admin on 1/14/18.
 */

public class Weather {

    private int id;

    private String description;

    private String icon;

    private double temperature;

    private double pressure;

    private int humidity;

    private double temperatureMin;

    private double temperatureMax;

    private double seaLevel;

    private double groundLevel;

    private double windSpeed;

    private double windDegrees;

    private int clouds;

    public Weather(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(double groundLevel) {
        this.groundLevel = groundLevel;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegrees() {
        return windDegrees;
    }

    public void setWindDegrees(double windDegrees) {
        this.windDegrees = windDegrees;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                ", seaLevel=" + seaLevel +
                ", groundLevel=" + groundLevel +
                ", windSpeed=" + windSpeed +
                ", windDegrees=" + windDegrees +
                ", clouds=" + clouds +
                '}';
    }
}
