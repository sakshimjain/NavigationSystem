package com.company;

/**
 * Definitions attributes and measurements of curve with respective getter and setter
 */

public class CurveDetails {

    public double timeOffset;
    public String startPoint = "--";
    public String endPoint = "--";
    public String direction = "--";
    public String speedWarning = "--";
    public Double avgSpeed = 0.00;

    public double getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(double timeOffset) {
        this.timeOffset = timeOffset;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeedWarning() {
        return speedWarning;
    }

    public void setSpeedWarning(String speedWarning) {
        this.speedWarning = speedWarning;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }


}
