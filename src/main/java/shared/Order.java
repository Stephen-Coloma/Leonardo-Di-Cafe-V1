package shared;

import java.util.List;

/**This method represents an order where it contains the customer, his orders and the time he pushed the order*/
public class Order {
    private Customer customer;
    private List<Product> orders;
    private String timeStamp;

    public Order(Customer customer, List<Product> orders, String timeStamp) {
        this.customer = customer;
        this.orders = orders;
        this.timeStamp = timeStamp;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getOrders() {
        return orders;
    }

    public void setOrders(List<Product> orders) {
        this.orders = orders;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", orders=" + orders +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
