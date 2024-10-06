package orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.order.Order;
import praktikum.order.OrderChecks;
import praktikum.order.OrderClient;

import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final OrderChecks orderChecks = new OrderChecks();

    private String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "color {0}")
    public static Object[][] dataGenerator() {
        return new Object[][]{
                {new String[]{}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"GREY"}},
        };
    }

    @Test
    @DisplayName("Успешное создание заказа с вариантами цвета")
    public void successCreateOrder() {
        var order = Order.random(color);
        ValidatableResponse createResponse = orderClient.createOrder(order);
        orderChecks.checkCreated(createResponse);
    }
}
