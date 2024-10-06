package praktikum.order;

import java.util.List;

public class ReturnOrderList {
    private List<Order> orders;

    public ReturnOrderList(List<Order> orders) {
        this.orders = orders;
    }

    public ReturnOrderList() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
