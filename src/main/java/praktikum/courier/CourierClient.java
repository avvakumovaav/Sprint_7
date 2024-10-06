package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.util.Map;

import praktikum.Client;

public class CourierClient extends Client {
    private static final String COURIER_PATH = "courier";

    @Step("Авторизация курьера")
    public ValidatableResponse logIn(CourierCredentials courierCredentials) {
        return spec()
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Создать курьера из объекта класса Courier")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Удалить курьера")
    public ValidatableResponse delete(int id) {
        return spec()
                .body(Map.of("id", id))
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all();
    }

    @Step("Создать курьера из json")
    public ValidatableResponse createCourier(File json) {
        return spec()
                .body(json)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }
}
