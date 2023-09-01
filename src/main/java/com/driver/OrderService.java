package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
    @Autowired
    OrderRepository orderrepository;
    public void addOrder(Order order) {
        orderrepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderrepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderrepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderrepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderrepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderrepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderrepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderrepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderrepository.getCountOfUnassignedOrders();

    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] arr=time.split(":");
        String hh=arr[0];
        String mm=arr[1];
        int t=Integer.parseInt(hh)*60+Integer.parseInt(mm);
        return orderrepository.getOrdersLeftAfterGivenTimeByPartnerId(t,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
       int t= orderrepository.getLastDeliveryTimeByPartnerId(partnerId);
       String hh=String.valueOf(t/60);
       String mm=String.valueOf(t%60);
       if(hh.length()<2){
           hh="0"+hh;
       }
       if(mm.length()<2){
           mm="0"+mm;
       }
       return hh+":"+mm;
    }

    public void deletePartnerById(String partnerId) {
        orderrepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderrepository.deleteOrderById(orderId);
    }
}
