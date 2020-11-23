package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayPanel extends JFrame {

    public void display(ArrayList<CarAttributes> attributesArrayList) {
        setSize(500, 500);
        setVisible(false);

        JFrame frame2 = new JFrame("NAVIGATION SYSTEM");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel frame1 = new JPanel();
        JPanel time = new JPanel();
        JPanel sensors = new JPanel(new GridLayout(3, 2, 4, 4));

        JButton start = new JButton("Start");
        frame1.add(start);
        frame1.setBackground(Color.BLACK);

        JLabel timeLabel = new JLabel("Current Time: ");
        timeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        time.add(timeLabel);
        timeLabel.setSize(100, 50);
        JLabel time_value = new JLabel();
        time_value.setForeground(Color.WHITE);
        time_value.setFont(new Font("Serif", Font.BOLD, 20));
        time_value.setText("-- s");
        String timeValue = "-- s";
        time.add(time_value);
        time_value.setSize(100, 50);
        time.setBackground(Color.BLACK);

        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setVerticalAlignment(JLabel.CENTER);


        JLabel vehicleSpeed = new JLabel("Vehicle Speed: ");
        vehicleSpeed.setForeground(Color.WHITE);
        sensors.add(vehicleSpeed);
        vehicleSpeed.setSize(100, 50);
        JLabel vehicleSpeed_value = new JLabel();
        vehicleSpeed_value.setForeground(Color.WHITE);
        vehicleSpeed_value.setText("-- mph");
        String vehicleSpeedConsole = "-- mph";
        sensors.add(vehicleSpeed_value);

        JLabel steerAngle = new JLabel("Steering Angle: ");
        steerAngle.setForeground(Color.WHITE);
        sensors.add(steerAngle);
        steerAngle.setSize(100, 50);
        JLabel steerAngle_value = new JLabel();
        steerAngle_value.setForeground(Color.WHITE);
        steerAngle_value.setText("0°");
        String steerAngleConsole = "-- mph";
        sensors.add(steerAngle_value);

        JLabel yawRate = new JLabel("Yaw Rate: ");
        yawRate.setForeground(Color.WHITE);
        sensors.add(yawRate);
        yawRate.setSize(100, 50);
        JLabel yawRate_value = new JLabel();
        yawRate_value.setForeground(Color.WHITE);
        yawRate_value.setText("0 °/s");
        String yawRateConsole = "-- mph";
        sensors.add(yawRate_value);

        JLabel latAcceleration = new JLabel("Lateral Acceleration: ");
        latAcceleration.setForeground(Color.WHITE);
        sensors.add(latAcceleration);
        latAcceleration.setSize(100, 50);
        JLabel latAcceleration_value = new JLabel();
        latAcceleration_value.setForeground(Color.WHITE);
        latAcceleration_value.setText("0 m/s²");
        String latAccelerationConsole = "-- mph";
        sensors.add(latAcceleration_value);

        JLabel longAcceleration = new JLabel("Longitudinal Acceleration: ");
        longAcceleration.setForeground(Color.WHITE);
        sensors.add(longAcceleration);
        longAcceleration.setSize(100, 50);
        JLabel longAcceleration_value = new JLabel();
        longAcceleration_value.setForeground(Color.WHITE);
        longAcceleration_value.setText("0 m/s²");
        String longitudinalAcceleration = "-- mph";
        sensors.add(longAcceleration_value);

        JLabel gps = new JLabel("GPS Coordinates: ");
        gps.setForeground(Color.WHITE);
        sensors.add(gps);
        gps.setSize(100, 50);
        JLabel gps_value = new JLabel();
        gps_value.setForeground(Color.WHITE);
        gps_value.setText("0");
        String gpsValues = "-- mph";
        sensors.add(gps_value);

        sensors.setBackground(Color.BLACK);

        frame2.add(frame1, BorderLayout.NORTH);
        frame2.add(time, BorderLayout.SOUTH);
        frame2.add(sensors, BorderLayout.CENTER);
        frame2.setSize(700, 500);
        frame2.setVisible(true);

        System.out.println("\t\tTime \t SteerAngle \t LatAcceleration \t LongAcceleration \t\t\t GPS \t\t\t YawRate \t VehSpeed");

        for (CarAttributes attribute : attributesArrayList) {

            switch (attribute.getSensorName()) {
                case "lateral acceleration(metres/s^2)" -> {
                    latAcceleration_value.setText(attribute.getValue() + "m/s²");
                    time_value.setText(Double.toString(attribute.getTimeOffset()));
                    latAccelerationConsole = attribute.getValue() + "m/s²";
                    timeValue = Double.toString(attribute.getTimeOffset());
                }
                case "Longitudinal acceleration(metres/s^2)" -> {
                    longAcceleration_value.setText(attribute.getValue() + "m/s²");
                    time_value.setText(Double.toString(attribute.getTimeOffset()));
                    longitudinalAcceleration = attribute.getValue() + "m/s²";
                    timeValue = Double.toString(attribute.getTimeOffset());
                }
                case "GPS(degrees)" -> {
                    gps_value.setText(attribute.getValue() + "m/s²");
                    time_value.setText(Double.toString(attribute.getTimeOffset()));
                    gpsValues = attribute.getValue() + "m/s²";
                    timeValue = Double.toString(attribute.getTimeOffset());
                }
                case "Yaw rate(degrees/second)" -> {
                    yawRate_value.setText(attribute.getValue() + "°/s");
                    time_value.setText(Double.toString(attribute.getTimeOffset()));
                    yawRateConsole = attribute.getValue() + "°/s";
                    timeValue = Double.toString(attribute.getTimeOffset());
                }
                case "Steering wheel angle(degrees)" -> {
                    steerAngle_value.setText(attribute.getValue() + "°");
                    time_value.setText(Double.toString(attribute.getTimeOffset()));
                    steerAngleConsole = attribute.getValue() + "°";
                    timeValue = Double.toString(attribute.getTimeOffset());
                }
                case "Vehicle Speed(km/h)" -> {
                    vehicleSpeed_value.setText(attribute.getValue() + "km/h");
                    time_value.setText(Double.toString(attribute.getTimeOffset()));
                    vehicleSpeedConsole = attribute.getValue() + "km/h";
                    timeValue = Double.toString(attribute.getTimeOffset());
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.out.format("\r\t\t%s\t%10s\t\t%10s\t\t%20s\t%20s\t%10s\t\t%5s", timeValue, steerAngleConsole, latAccelerationConsole, longitudinalAcceleration, gpsValues, yawRateConsole, vehicleSpeedConsole);
        }
    }
}
