package order;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient extends Client {

    private static final String PATH_ORDER = "/api/orders";


    private final io.restassured.filter.Filter requestFilter = new RequestLoggingFilter();
    private final Filter responseFiler = new ResponseLoggingFilter();

    @Step("Create new order with token")
    public ValidatableResponse createOrderWithToken(Order order, String token) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(order)
                .when()
                .auth().oauth2(token)
                .post(PATH_ORDER)
                .then();
    }

    @Step("Create new order without token")
    public ValidatableResponse createOrderWithoutToken(Order order) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_ORDER)
                .then();
    }

    @Step("Get orders user with token")
    public ValidatableResponse getOrdersUserWithToken(Order order, String token) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(order)
                .when()
                .auth().oauth2(token)
                .get(PATH_ORDER)
                .then();
    }

    @Step("Get orders user without token")
    public ValidatableResponse getOrdersUserWithoutToken(Order order) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(order)
                .when()
                .get(PATH_ORDER)
                .then();
    }
}