package order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateOrderTest {
    private OrderClient orderClient;
    private UserClient userClient;

    private String accessToken;

    @Before
    public void setup() {
        orderClient = new OrderClient();
        userClient = new UserClient();
    }

    @After
    public void shutDown() throws InterruptedException {
        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);

        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Check that a new order can be created with authorization")
    public void createOrderWithAuthSuccess() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        boolean isOrderCreated = orderClient.createOrder(accessToken)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");

        assertTrue(isOrderCreated);
    }

    @Test
    @DisplayName("Check that a new order cannot be created without authorization")
    public void createOrderWithoutAuthSuccess() {

        boolean isOrderCreated = orderClient.createOrder()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
//баг -  неавторизованные тоже могут делать заказы
        assertFalse(isOrderCreated);
    }

    @Test
    @DisplayName("Check that a new order without ingredients cannot be created")
    public void createOrderWithoutIngredientsError() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        String emptyIngredientsError = orderClient.createOrderNoIngredients(accessToken)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        assertEquals("Ingredient ids must be provided", emptyIngredientsError);
    }

    @Test
    @DisplayName("Check that a new order with incorrect ingredient hash cannot be created")
    public void createOrderIncorrectIngredientHashError() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        orderClient.createOrderIncorrectIngredientHash(accessToken)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
