package com.example.weather;

public class Rvmodel {
    private String time;
    private String temperature;
    private String icon;

    public Rvmodel(String time, String temperature, String icon) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
