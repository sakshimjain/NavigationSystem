package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DisplayPanel extends JFrame {

    public void display(ArrayList<CarAttributes> attributesArrayList) {
        setSize(1000, 1000);
        setVisible(false);

        JFrame frame2 = new JFrame("NAVIGATION SYSTEM");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel frame1 = new JPanel();
        JPanel time = new JPanel();
        JPanel dashboard = new JPanel( new FlowLayout(FlowLayout.CENTER, 0, 0) );
        JPanel curve = new JPanel(new GridLayout(1, 2, 2, 2));
        JPanel sensors = new JPanel(new GridLayout(3, 2, 2, 2));
        curve.setPreferredSize( new Dimension(700, 100) );
        sensors.setPreferredSize( new Dimension(700, 290) );

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

        JLabel curveDetection = new JLabel("Curve Detection: ");
        curveDetection.setForeground(Color.WHITE);
        curve.add(curveDetection);
        curveDetection.setSize(100, 50);
        JLabel curveDetection_value = new JLabel();
        curveDetection_value.setForeground(Color.WHITE);
        curveDetection_value.setText("No curve");
        curve.add(curveDetection_value);

        JLabel curvePosition = new JLabel("Curve Position: ");
        curvePosition.setForeground(Color.WHITE);
        curve.add(curvePosition);
        curvePosition.setSize(100, 50);
        JLabel curvePosition_value = new JLabel();
        curvePosition_value.setForeground(Color.WHITE);
        curvePosition_value.setText("0");
        curve.add(curvePosition_value);

        JLabel vehicleSpeed = new JLabel("Vehicle Speed: ");
        vehicleSpeed.setForeground(Color.WHITE);
        sensors.add(vehicleSpeed);
        vehicleSpeed.setSize(80, 50);
        JLabel vehicleSpeed_value = new JLabel();
        vehicleSpeed_value.setForeground(Color.WHITE);
        vehicleSpeed_value.setText("-- mph");
        String vehicleSpeedConsole = "-- mph";
        sensors.add(vehicleSpeed_value);

        JLabel steerAngle = new JLabel("Steering Angle: ");
        steerAngle.setForeground(Color.WHITE);
        sensors.add(steerAngle);
        steerAngle.setSize(80, 50);
        JLabel steerAngle_value = new JLabel();
        steerAngle_value.setForeground(Color.WHITE);
        steerAngle_value.setText("0°");
        String steerAngleConsole = "-- mph";
        sensors.add(steerAngle_value);

        JLabel yawRate = new JLabel("Yaw Rate: ");
        yawRate.setForeground(Color.WHITE);
        sensors.add(yawRate);
        yawRate.setSize(80, 50);
        JLabel yawRate_value = new JLabel();
        yawRate_value.setForeground(Color.WHITE);
        yawRate_value.setText("0 °/s");
        String yawRateConsole = "-- mph";
        sensors.add(yawRate_value);

        JLabel latAcceleration = new JLabel("Lateral Acceleration: ");
        latAcceleration.setForeground(Color.WHITE);
        sensors.add(latAcceleration);
        latAcceleration.setSize(80, 50);
        JLabel latAcceleration_value = new JLabel();
        latAcceleration_value.setForeground(Color.WHITE);
        latAcceleration_value.setSize(80, 50);
        latAcceleration_value.setText("0 m/s²");
        String latAccelerationConsole = "-- mph";
        sensors.add(latAcceleration_value);

        JLabel longAcceleration = new JLabel("Longitudinal Acceleration: ");
        longAcceleration.setForeground(Color.WHITE);
        sensors.add(longAcceleration);
        longAcceleration.setSize(80, 50);
        JLabel longAcceleration_value = new JLabel();
        longAcceleration_value.setForeground(Color.WHITE);
        longAcceleration_value.setSize(80, 50);
        longAcceleration_value.setText("0 m/s²");
        String longitudinalAcceleration = "-- mph";
        sensors.add(longAcceleration_value);

        JLabel gps = new JLabel("GPS Coordinates: ");
        gps.setForeground(Color.WHITE);
        sensors.add(gps);
        gps.setSize(80, 50);
        JLabel gps_value = new JLabel();
        gps_value.setForeground(Color.WHITE);
        gps_value.setSize(80, 50);
        gps_value.setText("0");
        String gpsValues = "-- mph";
        sensors.add(gps_value);

        sensors.setBackground(Color.BLACK);
        sensors.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(
                                EtchedBorder.RAISED, Color.GRAY
                                , Color.DARK_GRAY), "Sensor Data"));
        ((javax.swing.border.TitledBorder)sensors.getBorder()).setTitleColor(Color.WHITE);

        curve.setBackground(Color.BLACK);
        curve.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(
                                EtchedBorder.RAISED, Color.GRAY
                                , Color.DARK_GRAY), "Curve"));
        ((javax.swing.border.TitledBorder)curve.getBorder()).setTitleColor(Color.WHITE);

        dashboard.add(curve, BorderLayout.NORTH);
        dashboard.add(sensors, BorderLayout.SOUTH);
        frame2.add(frame1, BorderLayout.NORTH);
        frame2.add(time, BorderLayout.SOUTH);
        frame2.add(dashboard, BorderLayout.CENTER);
        frame2.setSize(700, 500);
        frame2.setVisible(true);
        System.out.println("\t\tTime \t SteerAngle \t LatAcceleration \t LongAcceleration \t\t\t GPS \t\t\t YawRate \t VehSpeed");
        CurveDetails curveDetails = new CurveDetails();
        CurveDetails curveDetails1 = new CurveDetails();

        final boolean[] flag = {false};

        while (true) {
            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flag[0] = true;
                    System.out.print("\r");
                }
            });

            if (flag[0]) {

                double steeringAngle = 0;
                double yawRate2 = 0.00;
                double speed = 0;
                String direction = "";
                String position = "";
                boolean flag1 = true;
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
                            position = attribute.getValue();
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "Yaw rate(degrees/second)" -> {
                            yawRate_value.setText(attribute.getValue() + "°/s");
                            time_value.setText(Double.toString(attribute.getTimeOffset()));
                            yawRateConsole = attribute.getValue() + "°/s";
                            yawRate2 = Double.parseDouble(attribute.getValue());
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "Steering wheel angle(degrees)" -> {
                            steerAngle_value.setText(attribute.getValue() + "°");
                            steeringAngle = Double.parseDouble(attribute.getValue());
                            time_value.setText(Double.toString(attribute.getTimeOffset()));
                            steerAngleConsole = attribute.getValue() + "°";
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "Vehicle Speed(km/h)" -> {
                            vehicleSpeed_value.setText(attribute.getValue() + "km/h");
                            time_value.setText(Double.toString(attribute.getTimeOffset()));
                            vehicleSpeedConsole = attribute.getValue() + "km/h";
                            speed = Double.parseDouble(attribute.getValue());
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    curveDetails.speedWarning = "Low speed";
                    if (steeringAngle > 1000) {
                        if (yawRate2 > 0) {
                            curveDetails.direction = "Left Curve";
                            if (flag1) {
                                curveDetails.startPoint = position;
                                curvePosition_value.setText(curveDetails.startPoint + "  to   " + curveDetails.endPoint);
                                flag1 = false;
                            }
                        }

                        if (speed < 50) curveDetails.speedWarning = "Low Speed";
                        if (speed > 100) curveDetails.speedWarning = "High Speed";
                    } else {
                        if (curveDetails.startPoint != null) curveDetails.endPoint = position;
                        curvePosition_value.setText(curveDetails.startPoint + "  to   " + curveDetails.endPoint);
                        curveDetails.direction = "No curve";
                        flag1 = true;
                    }

                    curveDetection_value.setText(curveDetails.direction + " with " + curveDetails.speedWarning);

                    System.out.format("\r\t\t%s\t%10s\t\t%10s\t\t%20s\t%20s\t%10s\t\t%5s", timeValue, steerAngleConsole, latAccelerationConsole, longitudinalAcceleration, gpsValues, yawRateConsole, vehicleSpeedConsole);
                    flag[0] = false;
                }
            }
        }
    }
}
