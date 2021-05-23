package com.aegisql;

import com.aegisql.memeter.MeasuringBox;
import com.aegisql.memeter.MeasuringMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    String test = "SOME TEST";

    public static void main( String[] args )
    {
        App a = new App();
        MeasuringBox<App> appMeasuringBox = new MeasuringBox<>(a);
        System.out.println( "App size "+appMeasuringBox.totalSize());
        a.test = "SOME MUCH LONGER TEST!!!";
        appMeasuringBox.invalidate();
        System.out.println( "App size "+appMeasuringBox.totalSize());

        MeasuringMap<String, List<String>> mm = new MeasuringMap<>();

        List<String> l1 = mm.computeIfAbsent("k1", k -> new ArrayList<>());
        l1.add("s11");
        l1.add("s12");

    }
}
