package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        String[] arr=deliveryTime.split(":");
        String hh=arr[0];
        String mm=arr[1];
        this.deliveryTime=Integer.parseInt(hh)*60+Integer.parseInt(mm);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
