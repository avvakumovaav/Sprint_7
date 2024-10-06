package praktikum.order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;
import praktikum.courier.Courier;

public class OrderClient extends Client {
    private static final String ORDER_PATH = "orders";

    @Step("Создать заказ из объекта класса Order")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }
}
