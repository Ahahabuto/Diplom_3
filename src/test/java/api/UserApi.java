package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String USER_ENDPOINT = "/api/auth/user";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";

    @Step("Регистрация пользователя")
    public static Response register(UserData user) {
        return given()
                .spec(Specifications.requestSpec())
                .body(user)
                .when()
                .post(REGISTER_ENDPOINT);
    }

    @Step("Удаление пользователя")
    public static Response delete(String accessToken) {
        return given()
                .spec(Specifications.requestSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_ENDPOINT);
    }

    public static Response login(UserData user) {
        return given()
                .spec(Specifications.requestSpec())
                .body(user)
                .when()
                .post(LOGIN_ENDPOINT);
    }
}