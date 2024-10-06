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
    @DisplayName("Успешный логин курьера")
    public void successLoginCourier() {
        var courier = Courier.random();
        courierClient.createCourier(courier);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierId = courierChecks.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("Логин под несуществующим пользователем")
    public void cannotLoginWithNotExistedUser() {
        var courier = Courier.withoutPassword();
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierChecks.checkLoginWithNotExistedUserFailed(loginResponse);
    }

    @Test
    @DisplayName("Логин с некорретным паролем пользователем")
    public void cannotLoginWithIncorrectPassword() {
        var courier = Courier.random();
        courierClient.createCourier(courier);
        var creds = CourierCredentials.fromCourier(courier);
        courier.setPassword("11111");

        ValidatableResponse loginResponse = courierClient.logIn(creds);
        courierChecks.checkLoginWithNotExistedUserFailed(loginResponse);
    }

    @Test
    @DisplayName("Создание курьера без пароля из объекта класса Courier")
    public void cannotCreateWithoutPasswordFromClassObject() {
        var courier = Courier.withoutPassword();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        courierChecks.checkLoginWithNullUserFailed(createResponse);
    }

    @Test
    @DisplayName("Создание курьера без пароля из json-файла")
    public void cannotCreateWithoutPasswordFromJsonFile() {
        File json = new File("src/test/resources/brokenCourier.json");
        ValidatableResponse createResponse = courierClient.createCourier(json);
        courierChecks.checkCreateWithoutRequiredFieldsFailed(createResponse);
    }

    @Test
    @DisplayName("Создание курьера без логина из объекта класса Courier")
    public void cannotCreateWithoutLoginFromClassObject() {
        var courier = Courier.withoutLogin();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        courierChecks.checkCreateWithoutRequiredFieldsFailed(createResponse);
    }


}
