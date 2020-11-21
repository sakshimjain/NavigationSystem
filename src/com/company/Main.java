package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    private static final ArrayList<CarAttributes> attributeCollection = new ArrayList<CarAttributes>();

    public static void main(String[] args) throws IOException {

        String PathOfCANFile = args[0];
        String PathOfGPSFile = args[1];
        CarAttributes carAttribute;
        CarAttributes carAttribute1;
        CarAttributes carAttribute2;
        CarAttributes carAttribute3;
        File GPS = new File(PathOfGPSFile);
        BufferedReader br1 = new BufferedReader(new FileReader(GPS));
        String st1;
        String[] GPSTokens;
        int lineCount = 0;
        double timer = 0.0;


        File file = new File(PathOfCANFile);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;

        String[] tokens;

        //parsing of CAN message file and storing data in objects
        while ((st = br.readLine()) != null) {
            tokens = st.split("\\s+");

            if (tokens.length >= 6) {

                //Parsing values of CAN messages corresponding to frame 0195
                if (tokens[4].equals("019F")) {
                    carAttribute = new CarAttributes();
                    carAttribute.setTimeOffset(Double.parseDouble(tokens[2]));
                    carAttribute.setSensorName("Vehicle Speed(km/h)");
                    carAttribute.setFrameID("019F");
                    StringBuilder data = new StringBuilder();
                    for (int i = 6; i < tokens.length; i++) {

                        data.append(tokens[i]);
                    }
                    carAttribute.setData(data.toString());
                    attributeCollection.add(carAttribute);
                }
                //Parsing values of CAN messages corresponding to frame 0003
                if (tokens[4].equals("0003")) {
                    carAttribute = new CarAttributes();
                    carAttribute.setTimeOffset(Double.parseDouble(tokens[2]));
                    carAttribute.setSensorName("Steering wheel angle(degrees)");
                    carAttribute.setFrameID("0003");
                    StringBuilder data = new StringBuilder();
                    for (int i = 6; i < tokens.length; i++) {
                        data.append(tokens[i]);
                    }
                    carAttribute.setData(data.toString());
                    attributeCollection.add(carAttribute);
                }
                //Parsing values of CAN messages corresponding to frame 0245 for calculation of YAW rate,
                // longitudinal acceleration and lateral acceleration
                if (tokens[4].equals("0245")) {
                    carAttribute1 = new CarAttributes();
                    carAttribute2 = new CarAttributes();
                    carAttribute3 = new CarAttributes();

                    carAttribute1.setTimeOffset(Double.parseDouble(tokens[2]));
                    carAttribute2.setTimeOffset(Double.parseDouble(tokens[2]));
                    carAttribute3.setTimeOffset(Double.parseDouble(tokens[2]));

                    carAttribute1.setFrameID("0245");
                    carAttribute2.setFrameID("0245");
                    carAttribute3.setFrameID("0245");

                    StringBuilder data = new StringBuilder();
                    for (int i = 6; i < tokens.length; i++) {

                        data.append(tokens[i]);
                    }
                    carAttribute1.setData(data.toString());
                    carAttribute2.setData(data.toString());
                    carAttribute3.setData(data.toString());


                    carAttribute1.setSensorName("Yaw rate(degrees/second)");
                    carAttribute2.setSensorName("Longitudinal acceleration(metres/s^2)");
                    carAttribute3.setSensorName("lateral acceleration(metres/s^2)");
                    attributeCollection.add(carAttribute1);
                    attributeCollection.add(carAttribute2);
                    attributeCollection.add(carAttribute3);

                }

            }

        }

        //this method decodes the value of the respective frames by calling respective functions
        decodeValue();

        //This while loop is parsing the GPS file data and storing it in object
        while ((st1 = br1.readLine()) != null) {
            lineCount++;
            if (lineCount < 51) continue;
            if (lineCount > 91) continue;

            GPSTokens = st1.split("\\s+");
            int i = 0;

            while (i < GPSTokens.length && !GPSTokens[i].equals("GLatLng(")) {
                i++;
            }

            carAttribute = new CarAttributes();
            carAttribute.setSensorName("GPS(degrees)");
            carAttribute.setTimeOffset(timer);
            String str = GPSTokens[i + 1] + GPSTokens[i + 2];
            carAttribute.setValue(str.substring(0, str.indexOf(")")));

            attributeCollection.add(carAttribute);
            timer += 1000;
        }

        //Sorting the values of objects in attributeCollection according to time offset using custom comparator.
        Collections.sort(attributeCollection, new CustomComparator());

        System.out.format("%16s%40s%25s", "Time Offset", "Sensor Name", "Value");
        System.out.println();

        //Printing out the values of collection and formatting
        for (CarAttributes attributes : attributeCollection) {
            System.out.format("%16s%40s%25s", attributes.getTimeOffset(), attributes.getSensorName(), attributes.getValue());
            System.out.println();
        }
    }

    /**
     * Function to convert hexadecimal value to binary.
     *
     * @return String
     */
    static String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }

    /**
     * Values for each object is calculated and saved
     */
    private static void decodeValue() {
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

        String relevantBinaryString = binaryCanData.substring(2, 16);
        int integerValue = Integer.parseInt(relevantBinaryString, 2);

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

    /**
     * Custom sort function to sort objects of list according to time offset value.
     */
    public static class CustomComparator implements Comparator<CarAttributes> {
        @Override
        public int compare(CarAttributes o1, CarAttributes o2) {
            return Double.compare(o1.getTimeOffset(), o2.getTimeOffset());
        }
    }
}
