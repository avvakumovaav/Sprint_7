package orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import praktikum.courier.Courier;
import praktikum.courier.CourierChecks;
import praktikum.courier.CourierClient;
import praktikum.courier.CourierCredentials;
import praktikum.order.OrderChecks;
import praktikum.order.OrderClient;
import praktikum.order.OrderList;

public class GetOrderListTest {
    private final OrderClient orderClient = new OrderClient();
    private final OrderChecks orderChecks = new OrderChecks();
    private final CourierClient courierClient = new CourierClient();
    private final CourierChecks courierChecks = new CourierChecks();


    @Test
    @DisplayName("Успешное получение списка заказов")
    public void successGetOrderList() {
        var courier = Courier.random();
        courierClient.createCourier(courier);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = courierClient.logIn(creds);
        int courierId = courierChecks.checkLoggedIn(loginResponse);

        OrderList orderList = new OrderList(courierId);
        ValidatableResponse getResponse = orderClient.getOrderList(orderList);
        orderChecks.checkGetOrderList(getResponse);
    }
}
