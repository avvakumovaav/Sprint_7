package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.*;

public class OrderChecks {
    @Step("Проверка успешного создания заказа")
    public Integer checkCreated(ValidatableResponse response) {
        Integer track = response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("track");

        assertNotNull(track);
        return track;
    }

    @Step("Проверка получения списка заказов")
    public void checkGetOrderList(ValidatableResponse response) {
        var getOrderList = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract().body().as(ReturnOrderList.class);

        assertNotNull(getOrderList);
    }
}
