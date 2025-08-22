package ai.qodo.pojo;

import java.sql.Timestamp;

public class Order {
    private int id;
    private String orderNumber;
    private int customerId;
    private Timestamp orderDate;

    public Order() {
    }

    public Order(int id, String orderNumber, int customerId, Timestamp orderDate) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                '}';
    }
}