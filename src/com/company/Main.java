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
        DisplayPanel displayPanel = new DisplayPanel();
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
        DecodeValue decodeValue = new DecodeValue();
        ArrayList<CarAttributes> attributeCollectionList = decodeValue.getDecodedValues(attributeCollection);

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

            attributeCollectionList.add(carAttribute);
            timer += 1000;
        }

        //Sorting the values of objects in attributeCollection according to time offset using custom comparator.
        Collections.sort(attributeCollectionList, new CustomComparator());

        displayPanel.display(attributeCollectionList);
//        System.out.format("%16s%40s%25s", "Time Offset", "Sensor Name", "Value");
//        System.out.println();
//
//        //Printing out the values of collection and formatting
//        for (CarAttributes attributes : attributeCollectionList) {
//            System.out.format("%16s%40s%25s", attributes.getTimeOffset(), attributes.getSensorName(), attributes.getValue());
//            System.out.println();
//        }
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
