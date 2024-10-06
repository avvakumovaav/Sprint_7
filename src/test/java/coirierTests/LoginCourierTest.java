package coirierTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.courier.Courier;
import praktikum.courier.CourierChecks;
import praktikum.courier.CourierClient;
import praktikum.courier.CourierCredentials;

import java.io.File;

public class LoginCourierTest {
    private final CourierClient courierClient = new CourierClient();
    private final CourierChecks courierChecks = new CourierChecks();

    int courierId;

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = courierClient.delete(courierId);
            courierChecks.checkDeleted(response);
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void successLoginCourier() {
        var courier = Courier.random();
        courierClient.createCourier(courier);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierId = courierChecks.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("Ошибка авторизации курьера без логина")
    public void cannotLoginWithoutLogin() {
        var courier = Courier.withoutLogin();
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierChecks.checkLoginWithNullLoginOrPasswordFailed(loginResponse);
    }

    @Test
    @DisplayName("Ошибка авторизации курьера без пароля")
    public void cannotLoginWithoutPassword() {
        var courier = Courier.withoutPassword();
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierChecks.checkLoginWithNullLoginOrPasswordFailed(loginResponse);
    }

    @Test
    @DisplayName("Логин под несуществующим пользователем")
    public void cannotLoginWithNotExistedUser() {
        // создать курьера
        var courier = Courier.random();
        courierClient.createCourier(courier);
        var creds = CourierCredentials.fromCourier(courier);
        // залогониться, получить id
        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierId = courierChecks.checkLoggedIn(loginResponse);
        // удалить курьера
        ValidatableResponse response = courierClient.delete(courierId);
        courierId = 0;

        // логин под удаленным курьером
        loginResponse = courierClient.logIn(creds);
        courierChecks.checkLoginWithNotExistedUserFailed(loginResponse);
    }

    @Test
    @DisplayName("Логин с некорретным паролем пользователем")
    public void cannotLoginWithIncorrectPassword() {
        var courier = Courier.random();
        courierClient.createCourier(courier);
        var creds = new CourierCredentials(courier.getLogin(), "11111");

        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierChecks.checkLoginWithNotExistedUserFailed(loginResponse);
    }
}
