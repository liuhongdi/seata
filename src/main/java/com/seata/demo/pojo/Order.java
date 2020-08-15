package com.seata.demo.pojo;

import java.math.BigDecimal;

//订单模型
public class Order {
    //订单id
    Long orderId;
    public Long getOrderId() {
        return this.orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    //订单编号
    private String orderSn;
    public String getOrderSn() {
        return this.orderSn;
    }
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    //下单时间
    private String orderTime;
    public String getOrderTime() {
        return this.orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    //订单状态
    int orderStatus;
    public int getOrderStatus() {
        return this.orderStatus;
    }
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    //订单状态
    int userId;
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    //订单价格
    private BigDecimal price;
    public BigDecimal getPrice() {
        return this.price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String toString(){
        return " Order:orderId=" + orderId +" orderSn=" + orderSn+" orderTime=" + orderTime+" orderStatus:"+orderStatus+" userId:"+userId+" price=" + price;
    }
}
