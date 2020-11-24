package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.StrictMath.abs;

public class DisplayPanel extends JFrame {

    /**
     * Function to create GUI frame and update values
     *
     * @param attributesArrayList
     */
    public void display(ArrayList<CarAttributes> attributesArrayList) {
        setSize(1000, 1000);
        setVisible(false);

        //Setup of GUI Frame and Panel
        JFrame frame2 = new JFrame("NAVIGATION SYSTEM");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel frame1 = new JPanel();
        JPanel time = new JPanel();
        JPanel dashboard = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel curve = new JPanel(new GridLayout(2, 2, 2, 2));
        JPanel warning = new JPanel(new GridLayout(1, 2, 2, 2));
        JPanel sensors = new JPanel(new GridLayout(3, 2, 2, 2));
        curve.setPreferredSize(new Dimension(900, 100));
        warning.setPreferredSize(new Dimension(900, 100));
        sensors.setPreferredSize(new Dimension(700, 190));

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

        JLabel avgSpeedLabel = new JLabel("Avg Speed: ");
        avgSpeedLabel.setForeground(Color.WHITE);
        curve.add(avgSpeedLabel);
        avgSpeedLabel.setSize(100, 50);
        JLabel avgSpeed_value = new JLabel();
        avgSpeed_value.setForeground(Color.WHITE);
        avgSpeed_value.setText("0 km/h");
        curve.add(avgSpeed_value);

        JLabel empty = new JLabel();
        empty.setForeground(Color.WHITE);
        curve.add(empty);
        avgSpeedLabel.setSize(100, 50);
        JLabel empty_value = new JLabel();
        empty_value.setForeground(Color.WHITE);
        curve.add(empty_value);

        JLabel warningMsg = new JLabel("Warning: ");
        warningMsg.setFont(new Font("Serif", Font.BOLD, 20));
        warningMsg.setForeground(Color.RED);
        warning.add(warningMsg);
        warningMsg.setSize(100, 50);
        JLabel warningMsg_value = new JLabel();
        warningMsg_value.setFont(new Font("Serif", Font.BOLD, 20));
        warningMsg_value.setForeground(Color.RED);
        warningMsg_value.setText("--");
        warningMsg_value.setSize(100, 50);
        warning.add(warningMsg_value);

        JLabel warningAvgSpeed = new JLabel("Avg Speed: ");
        warningAvgSpeed.setFont(new Font("Serif", Font.BOLD, 20));
        warningAvgSpeed.setForeground(Color.RED);
        warning.add(warningAvgSpeed);
        warningAvgSpeed.setSize(100, 50);
        JLabel warningAvgSpeed_value = new JLabel();
        warningAvgSpeed_value.setFont(new Font("Serif", Font.BOLD, 20));
        warningAvgSpeed_value.setForeground(Color.RED);
        warningAvgSpeed_value.setText("-- km/h");
        warningAvgSpeed_value.setSize(100, 50);
        warning.add(warningAvgSpeed_value);

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
                                EtchedBorder.RAISED, Color.WHITE
                                , Color.DARK_GRAY), "Sensor Data"));
        ((javax.swing.border.TitledBorder) sensors.getBorder()).setTitleColor(Color.WHITE);

        warning.setBackground(Color.BLACK);
        warning.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(
                                EtchedBorder.RAISED, Color.RED
                                , Color.DARK_GRAY), "Warning"));
        ((javax.swing.border.TitledBorder) warning.getBorder()).setTitleColor(Color.RED);

        curve.setBackground(Color.BLACK);
        curve.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(
                                EtchedBorder.RAISED, Color.WHITE
                                , Color.DARK_GRAY), "Curve"));
        ((javax.swing.border.TitledBorder) curve.getBorder()).setTitleColor(Color.WHITE);

        dashboard.setBackground(Color.BLACK);
        dashboard.add(warning);
        dashboard.add(curve);
        dashboard.add(sensors);

        //Adding panels to GUI frame
        frame2.add(frame1, BorderLayout.NORTH);
        frame2.add(time, BorderLayout.SOUTH);
        frame2.add(dashboard, BorderLayout.CENTER);
        frame2.setSize(1000, 500);
        frame2.setVisible(true);

        System.out.println("\t\tTime \t SteerAngle \t LatAcceleration \t LongAcceleration \t\t\t GPS \t\t\t YawRate \t VehSpeed");

        //Initializing object to store curve details and a list to store each curve
        CurveDetails curveDetails = new CurveDetails();
        ArrayList<CurveDetails> curveList = new ArrayList<>();

        final boolean[] flag = {false};
        boolean warningFlag = false;
        while (true) {

            //Start button ActionListener with initialization of UI fields
            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flag[0] = true;
                    latAcceleration_value.setText("--");
                    longAcceleration_value.setText("--");
                    gps_value.setText("--");
                    time_value.setText("--");
                    steerAngle_value.setText("--");
                    yawRate_value.setText("--");
                    vehicleSpeed_value.setText("--");
                    curveDetection_value.setText("--");
                    curvePosition_value.setText("--");
                    avgSpeed_value.setText("--");
                    System.out.print("\r");
                }
            });

            if (flag[0]) {

                //initialization of variables required for getting sensor details
                double steeringAngle = 0;
                double speed = 0;
                String direction = "";
                String position = "";
                boolean flag1 = true;
                double lowSpeed = 0.00;
                double highSpeed = 0.00;
                int index = 0;

                // Loop to update the window with details of sensor and detected curves
                for (CarAttributes attribute : attributesArrayList) {

                    // Printing sensor data on GUI frame (Part 2)
                    switch (attribute.getSensorName()) {
                        case "lateral acceleration(metres/s^2)" -> {
                            latAcceleration_value.setText(attribute.getValue() + " m/s²");
                            time_value.setText(attribute.getTimeOffset() + " ms");
                            latAccelerationConsole = attribute.getValue() + " m/s²";
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "Longitudinal acceleration(metres/s^2)" -> {
                            longAcceleration_value.setText(attribute.getValue() + " m/s²");
                            time_value.setText(attribute.getTimeOffset() + " ms");
                            longitudinalAcceleration = attribute.getValue() + " m/s²";
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "GPS(degrees)" -> {
                            gps_value.setText(attribute.getValue());
                            time_value.setText(attribute.getTimeOffset() + " ms");
                            gpsValues = attribute.getValue();
                            position = attribute.getValue();
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "Yaw rate(degrees/second)" -> {
                            yawRate_value.setText(attribute.getValue() + " °/s");
                            time_value.setText(attribute.getTimeOffset() + " ms");
                            yawRateConsole = attribute.getValue() + " °/s";
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "Steering wheel angle(degrees)" -> {
                            steerAngle_value.setText(attribute.getValue() + " °");
                            steeringAngle = Double.parseDouble(attribute.getValue());
                            time_value.setText(attribute.getTimeOffset() + " ms");
                            steerAngleConsole = attribute.getValue() + " °";
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                        case "Vehicle Speed(km/h)" -> {
                            vehicleSpeed_value.setText(attribute.getValue() + " km/h");
                            time_value.setText(attribute.getTimeOffset() + " ms");
                            vehicleSpeedConsole = attribute.getValue() + " km/h";
                            speed = Double.parseDouble(attribute.getValue());
                            timeValue = Double.toString(attribute.getTimeOffset());
                        }
                    }
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    //Curve Detection (Part 3)
                    curveDetails.setSpeedWarning("Low speed");
                    if (abs(steeringAngle) > 15) {
                        if (steeringAngle < 0) direction = "Left Curve";
                        if (steeringAngle > 0) direction = "Right Curve";
                        if (flag1) {
                            curveDetails.setDirection(direction);
                            curveDetails.setStartPoint(position);
                            curveDetails.setTimeOffset(Double.parseDouble(timeValue));
                            curvePosition_value.setText(curveDetails.getStartPoint() + "  to   " + curveDetails.getEndPoint());
                            flag1 = false;
                        }
                        lowSpeed = Math.min(lowSpeed, speed);
                        highSpeed = Math.max(highSpeed, speed);
                        if (speed < 50) curveDetails.setSpeedWarning("Low Speed");
                        if (speed > 50) curveDetails.setSpeedWarning("High Speed");
                        curveDetection_value.setText(curveDetails.getDirection() + " with " + curveDetails.getSpeedWarning());
                    } else {
                        if (curveDetails.getStartPoint() != null && !flag1) {
                            curveDetails.setEndPoint(position);
                            curveDetails.setAvgSpeed((lowSpeed + highSpeed) / 2);
                            avgSpeed_value.setText((curveDetails.getAvgSpeed()) + " km/h");
                            if (!warningFlag) curveList.add(curveDetails);
                            curvePosition_value.setText(curveDetails.getStartPoint() + "  to   " + curveDetails.getEndPoint());
                            curveDetails = new CurveDetails();
                            curveDetails.setStartPoint(null);
                            curveDetails.setDirection("No curve");
                            flag1 = true;
                        }

                    }

                    //Printing Output in console (Part 1)
                    System.out.format("\r\t\t%s\t%10s\t\t%10s\t\t%20s\t%20s\t%10s\t\t%5s", timeValue, steerAngleConsole, latAccelerationConsole, longitudinalAcceleration, gpsValues, yawRateConsole, vehicleSpeedConsole);
                    flag[0] = false;

                    //Curve Warning (Part 4)
                    if (curveList.size() == 3 && warningFlag) {

                        if (Double.parseDouble(timeValue) - curveList.get(index).getTimeOffset() == 0.0 && index < 2)
                            index++;
                        if (Double.parseDouble(timeValue) <= curveList.get(index).getTimeOffset()) {
                            warningMsg_value.setText(curveList.get(index).getDirection() + " in " + abs((Double.parseDouble(timeValue) - curveList.get(index).getTimeOffset()) / 1000) + " s");
                            warningAvgSpeed_value.setText(curveList.get(index).getAvgSpeed().toString() + " km/h");
                        }
                    }
                }
                warningFlag = true;
            }
        }
    }
}
