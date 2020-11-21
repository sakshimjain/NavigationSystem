package com.company;

/**
 * Definitions attributes and measurements of vehicle with respective getter and setter
 */
public class CarAttributes {

    public double timeOffset;
    public String frameID;
    public String data;
    public String sensorName;
    public String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(double timeOffset) {
        this.timeOffset = timeOffset;
    }

    public String getFrameID() {
        return frameID;
    }

    public void setFrameID(String frameID) {
        this.frameID = frameID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }


}
