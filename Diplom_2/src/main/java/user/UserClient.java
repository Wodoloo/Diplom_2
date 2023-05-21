package user;
import client.Client;
import io.qameta.allure.Step;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    private static final String PATH_CREATE = "/api/auth/register";
    private static final String PATH_LOGIN = "/api/auth/login";
    private static final String PATH_CHANGE = "/api/auth/user";
    private static final String PATH_DELETE = "/api/auth/user";
    private final io.restassured.filter.Filter requestFilter = new RequestLoggingFilter();
    private final Filter responseFiler = new ResponseLoggingFilter();

    @Step("Create new user")
    public ValidatableResponse createUser(User user) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH_CREATE)
                .then();
    }

    @Step("Login new user")
    public ValidatableResponse loginUser(Credentials credential) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(credential)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    @Step("Change user")
    public ValidatableResponse changeUserWithToken(User user, String token) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(user)
                .when()
                .auth().oauth2(token)
                .patch(PATH_CHANGE)
                .then();
    }

    @Step("Change user without token")
    public ValidatableResponse changeUserWithoutToken(User user) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(user)
                .when()
                .patch(PATH_CHANGE)
                .then();
    }


    @Step("Delete user")
    public ValidatableResponse deleteUser(String token) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .when()
                .auth().oauth2(token)
                .delete(PATH_DELETE)
                .then();
    }
}