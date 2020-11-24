package com.company;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class DecodeValue {
    /**
     * Function to convert hexadecimal value to binary.
     *
     * @return String
     */
    static String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }

    /**
     * Value calculation for Lateral acceleration
     *
     * @return double
     */
    private static double calculateValuesForLateralAcceleration(String binaryCanData) {

        //0245; By6Bi7-By6Bi0; 8 bit; Vehicle lateral acceleration (+ means left); FFh; -10,24 - +10,08 m/s²; 0 - 254; 0,08 m/s²

        String relevantBinaryString = binaryCanData.substring(40, 48);
        int integerValue = Integer.parseInt(relevantBinaryString, 2);

        return integerValue * 0.08 - 10.24;

    }

    /**
     * Value calculation for Longitudinal acceleration
     *
     * @return double
     */
    private static double calculateValuesForLongAcceleration(String binaryCanData) {
        //  0245; By5Bi7-By5Bi0; 8 bit; Vehicle longitudinal acceleration (+ means forward); FFh; -10,24 - +10,08 m/s²; 0 - 254; 0,08 m/s²

        String relevantBinaryString = binaryCanData.substring(32, 40);
        int integerValue = Integer.parseInt(relevantBinaryString, 2);

        return integerValue * 0.08 - 10.24;

    }

    /**
     * Value calculation for Yaw rate of vehicle
     *
     * @return double
     */
    private static double calculateValuesForYawRate(String binaryCanData) {

        //0245; By1Bi7-By2Bi0; 16 bit; Vehicle yaw rate; FFFFh; -327,68 - +327,66 °/s; 0 - 65534; 0,01 °/s


        String relevantBinaryString = binaryCanData.substring(0, 16);
        int integerValue = Integer.parseInt(relevantBinaryString, 2);

        return integerValue * 0.01 - 327.68;

    }

    /**
     * Value calculations for steering wheel angle.
     *
     * @return double
     */
    private static double calculateValuesForSteeringWheelAngle(String binaryCanData) {
        //0003; By1Bi5-By2Bi0; 14 bit; Steering wheel angle; 3FFFh;  -2048 - +2047°;  0 - 8190; 0,5 °

        int integerValue = (((Character.digit(binaryCanData.charAt(0),16) & ((1<<2)-1))<<12) |
                (Character.digit(binaryCanData.charAt(1),16)<<8) |
                (Character.digit(binaryCanData.charAt(2),16)<<4) |
                (Character.digit(binaryCanData.charAt(3),16)));

        return integerValue * 0.5 - 2048;
    }

    /**
     * Value calculation for Vehicle speed
     *
     * @return double
     */
    private static double calculateValuesForVehicleSpeed(String binaryCanData) {

        //019F; By1Bi3-By2Bi0; 12 bit; Displayed vehicle speed; FFFh; 0 - 409,4 km/h; 0 - 4094; 0,1 km/h

        String relevantBinaryString = binaryCanData.substring(4, 16);
        int integerValue = Integer.parseInt(relevantBinaryString, 2);

        return integerValue * 0.1;
    }

    public ArrayList<CarAttributes> getDecodedValues(ArrayList<CarAttributes> attributeCollection) {
        DecimalFormat df = new DecimalFormat("###.##");

        for (CarAttributes attribute : attributeCollection) {
            String canData = attribute.data;
            String binaryCanData = hexToBin(canData);
            String trailingZeroes = "";
            if (binaryCanData.length() % 4 != 0) {
                for (int i = 0; i < (canData.length() * 4 - binaryCanData.length()); i++) {
                    trailingZeroes += "0";
                }
                binaryCanData = trailingZeroes + binaryCanData;
            }

            //decoding value for Vehicle speed
            if (attribute.getFrameID().equals("019F")) {
                double value = calculateValuesForVehicleSpeed(binaryCanData);
                attribute.setValue(String.valueOf(df.format(value)));
            }

            //decoding value for Steering Wheel
            if (attribute.getFrameID().equals("0003")) {
                double value = calculateValuesForSteeringWheelAngle(binaryCanData);
                attribute.setValue(String.valueOf(df.format(value)));

            }

            if (attribute.getFrameID().equals("0245")) {
                //decoding value for YAW rate
                if (attribute.getSensorName().equals("Yaw rate(degrees/second)")) {
                    double yawRateValue = calculateValuesForYawRate(binaryCanData);
                    attribute.setValue(String.valueOf(df.format(yawRateValue)));
                }

                //decoding value for longitudinal acceleration
                if (attribute.getSensorName().equals("Longitudinal acceleration(metres/s^2)")) {
                    double longitudinalAccelerationValue = calculateValuesForLongAcceleration(binaryCanData);
                    attribute.setValue(String.valueOf(df.format(longitudinalAccelerationValue)));
                }

                //decoding value for lateral acceleration
                if (attribute.getSensorName().equals("lateral acceleration(metres/s^2)")) {
                    double lateralAcceleration = calculateValuesForLateralAcceleration(binaryCanData);
                    attribute.setValue(String.valueOf(df.format(lateralAcceleration)));
                }

            }
        }

        return attributeCollection;
    }
}
