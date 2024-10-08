package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;
import java.util.Set;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class CourierChecks {
    @Step("Проверка успешного добавления курьера")
    public void checkCreated(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("Проверка удаления курьера")
    public void checkDeleted(ValidatableResponse response) {
        boolean deleted = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("ok");
        assertTrue(deleted);
    }

    @Step("Проверка неуспешного добавления курьера без логина или пароля")
    public void checkCreateWithoutRequiredFieldsFailed(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        assertEquals("Недостаточно данных для создания учетной записи", body.get("message"));
        assertEquals(Set.of("code", "message"), body.keySet());
    }

    @Step("Проверка неуспешного добавления курьера с повторяющимся логином")
    public void checkDuplicateLoginFailed(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .extract()
                .body().as(Map.class);

        assertEquals("Этот логин уже используется", body.get("message"));
        assertEquals(Set.of("code", "message"), body.keySet());
    }

    @Step("Проверка логина курьера")
    public int checkLoggedIn(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("id");

        assertNotEquals(0, id);

        return id;
    }

    @Step("Проверка неуспешного логина курьера с несуществующей парой логин-пароль")
    public void checkLoginWithNotExistedUserFailed(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .extract()
                .body().as(Map.class);

        assertEquals("Учетная запись не найдена", body.get("message"));
        assertEquals(Set.of("code","message"), body.keySet());
    }

    @Step("Проверка неуспешного логина курьера при отсутствии логина или пароля в запросе")
    public void checkLoginWithNullLoginOrPasswordFailed(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        assertEquals("Недостаточно данных для входа", body.get("message"));
        assertEquals(Set.of("code", "message"), body.keySet());
    }
    }
