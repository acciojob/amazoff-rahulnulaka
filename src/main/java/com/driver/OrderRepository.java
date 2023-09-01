package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository
public class OrderRepository {
    HashMap<String,Order> orderdb=new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerdb=new HashMap<>();
    HashMap<String,List<Order>> partneroderpairdb=new HashMap<>();
    public void addOrder(Order order) {
        orderdb.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnerdb.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId,String partnerId) {
        if(orderdb.containsKey(orderId)&&deliveryPartnerdb.containsKey(partnerId)){
            if(partneroderpairdb.containsKey(partnerId)){
                partneroderpairdb.get(partnerId).add(orderdb.get(orderId));
            }
            else{
                partneroderpairdb.put(partnerId,new ArrayList<>());
                partneroderpairdb.get(partnerId).add(orderdb.get(orderId));
            }
            deliveryPartnerdb.get(partnerId).setNumberOfOrders(1);
        }
    }

    public Order getOrderById(String orderId) {
        if(orderdb.containsKey(orderId)){
            return orderdb.get(orderId);
        }
        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if(deliveryPartnerdb.containsKey(partnerId)){
            deliveryPartnerdb.get(partnerId);
        }
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        if(deliveryPartnerdb.containsKey(partnerId)){
            return deliveryPartnerdb.get(partnerId).getNumberOfOrders();
        }
        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> list=new ArrayList<>();
        if(partneroderpairdb.containsKey(partnerId)){
            for(Order order:partneroderpairdb.get(partnerId)){
                list.add(order.getId());
            }
        }
        return list;
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderdb.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        int total=orderdb.size();
        int assigned=0;
        for(String id:partneroderpairdb.keySet()){
            assigned+=partneroderpairdb.get(id).size();
        }
        return total-assigned;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {
        int undelivered=0;
        for(Order order:partneroderpairdb.get(partnerId)){
            if(order.getDeliveryTime() > time) undelivered++;
        }
        return undelivered;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int time=Integer.MIN_VALUE;
        for(Order order:partneroderpairdb.get(partnerId)){
            time=Math.max(time,order.getDeliveryTime());
        }
        return time;
    }

    public void deletePartnerById(String partnerId) {
        if(deliveryPartnerdb.containsKey(partnerId)&&partneroderpairdb.containsKey(partnerId)){
            deliveryPartnerdb.remove(partnerId);
            partneroderpairdb.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId) {
        if(orderdb.containsKey(orderId)) {
            orderdb.remove(orderId);
            for (String partnerId : partneroderpairdb.keySet()) {
                boolean orderdeleted = false;
                for (int i = 0; i < partneroderpairdb.get(partnerId).size(); i++) {
                    if (partneroderpairdb.get(partnerId).get(i).equals(orderId)) {
                        partneroderpairdb.get(partnerId).remove(i);
                        orderdeleted = true;
                        break;
                    }
                }
                if (orderdeleted == true) {
                    deliveryPartnerdb.get(partnerId).setNumberOfOrders(-1);
                }
            }
        }
    }
}
