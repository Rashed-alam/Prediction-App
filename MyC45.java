package com.example.rashedalam.callpredictor;

import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

import static java.text.AttributedCharacterIterator.*;

public class MyC45 {

   public static int c45 = Integer.valueOf((int) Math.random());

    public static void main(String[] args) throws IOException {
        // .csv data sets
        String files[] = {"data_sets/firebase_server.csv"};
        Scanner scan;

        // start loop for all files HERE
        scan = new Scanner(new File(files[0]));
        String headerLine = scan.nextLine();
        String headers[]  = headerLine.split(",");

        // class index is assumed to be the last column
        int classIndex    = headers.length - 1;
        int numAttributes = headers.length - 1;

        // store data set attributes
        Attribute attributes[] = new Attribute[numAttributes];
        for(int x = 0; x < numAttributes; x++) {
        }

        // for storing classes and class count
        List<String>  classes      = new ArrayList<String>();
        List<Integer> classesCount = new ArrayList<Integer>();

        // store are values into respected attributes
        // along with respected classes
        while(scan.hasNextLine()){
            String data = null;
            String inLine = scan.nextLine();
            String lineData[] = inLine.split(",");

            // insert class into classes List
            if(classes.isEmpty()){
                classes.add(lineData[classIndex]);
                classesCount.add(classes.indexOf(lineData[classIndex]), 1);
            }
            else{
                if(!classes.contains(lineData[classIndex])){
                    classes.add(lineData[classIndex]);
                    classesCount.add(classes.indexOf(lineData[classIndex]), 1);
                }
                else {
                    classesCount.set(classes.indexOf(lineData[classIndex]),
                            classesCount.get(classes.indexOf(lineData[classIndex])) + 1);
                }
            }

            // insert data into attributes
            for(int x = 0; x < numAttributes; x++){
               // data = new (lineData[x], lineData[classIndex]);
                Attribute attribute = attributes[x];
            }
        }
        int totalNumClasses = 0;
        for(int i : classesCount){
            totalNumClasses += i;
        }
        double IofD = calcIofD(classesCount); // Set information criteria

        List<Integer> testCount = new ArrayList<Integer>();
        testCount.add(9);
        testCount.add(5);

        double testIofD = calcIofD(testCount);

        System.out.println("I of D: " + testIofD);

		/*
		for(Attribute a : attributes){
			System.out.println(a.toString());
		}
		*/

    }

    public static double calcIofD(List<Integer> classesCount){
        double IofD = 0.0;
        double tem = 0.0;

        int totalNumClasses = 0;
        for(int i : classesCount){
            totalNumClasses += i;
        }

        for(double d : classesCount){
            tem = (-1 * (d/totalNumClasses)) * (Math.log((d/totalNumClasses)) / Math.log(2));
            IofD += tem;
        }
        return IofD;
    }

    public static int getC45(){
        return c45;
    }
}
