package com.example.mamanguo.chooseServices.helperClasses;

import android.widget.Toast;

import com.example.mamanguo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bill {
    //Bill calculation
    public static int billTotal;
    public static String[] orderItems;
    public static ArrayList<Integer> orderQuantity;
    public static ArrayList<Integer> unitPrice;
    public static ArrayList<Integer> orderSubtotal;
    //Data structures to hold the activity data
    public static Map<String, Integer> serviceCount = new HashMap<>();
    public static Map<String, Integer> serviceTotal = new HashMap<>();
    public static Map<String, Integer> serviceUnitPrice= new HashMap<>();



    public static int getBillTotal() {
        return billTotal;
    }

    public static void setBillTotal(int billTotal) {
        Bill.billTotal = billTotal;
    }


    private void setCategoryTotal(String item, int total) {

    }

    public static String[] mapKeyToArray(Map<String, Integer> map) {
        int size = map.size();
        String[] keys = map.keySet().toArray(new String[size]);
        return keys;
    }

    public static ArrayList<Integer> mapValueToArray(Map<String, Integer> map) {
        ArrayList<Integer> arrayList = new ArrayList<>(map.values());
        return arrayList;
    }


}
