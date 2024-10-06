package coirierTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.courier.Courier;
import praktikum.courier.CourierClient;
import praktikum.courier.CourierChecks;

import java.io.File;

public class CreateCourierTest {
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
    @DisplayName("Успешное создание курьера")
    public void successCreateCourier() {
        var courier = Courier.random();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        courierChecks.checkCreated(createResponse);
    }

    @Test
    @DisplayName("Создание курьера с дублирующим логином")
    public void cannotCreateWithDuplicateLogin() {
        var courier = Courier.random();
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        courierChecks.checkDuplicateLoginFailed(createResponse);
    }

    @Test
    @DisplayName("Создание курьера без пароля из объекта класса Courier")
    public void cannotCreateWithoutPasswordFromClassObject() {
        var courier = Courier.withoutPassword();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        courierChecks.checkCreateWithoutRequiredFieldsFailed(createResponse);
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
