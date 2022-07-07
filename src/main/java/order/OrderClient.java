package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import root.RestAssuredClient;

public class OrderClient extends RestAssuredClient {

    private final String INGREDIENTS = "/ingredients";
    private final String ORDERS = "/orders";

    @Step("Receive order of user with authorization")
    public ValidatableResponse receiveOrders(String accessToken) {
        return reqSpec
                .when()
                .get(ORDERS)
                .then().log().all();
    }

    @Step("Receive order of user with no authorization")
    public ValidatableResponse receiveOrders() {
        return reqSpec
                .when()
                .get(ORDERS)
                .then().log().all();
    }

    @Step("Create a new order with authorization")
    public ValidatableResponse createOrder(String accessToken) {
        Order order = Order.setCorrectIngredients();
        return reqSpec
                .header("authorization", accessToken)
                .when()
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Create a new order without authorization")
    public ValidatableResponse createOrder() {
        Order order = Order.setCorrectIngredients();
        return reqSpec
                .when()
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Create a new order without ingredients")
    public ValidatableResponse createOrderNoIngredients(String accessToken) {
        Order order = Order.setEmptyIngredients();
        return reqSpec
                .header("authorization", accessToken)
                .when()
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Create a new order incorrect ingredient hash")
    public ValidatableResponse createOrderIncorrectIngredientHash(String accessToken) {
        Order order = Order.setIncorrectIngredients();
        return reqSpec
                .header("authorization", accessToken)
                .when()
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }
}
