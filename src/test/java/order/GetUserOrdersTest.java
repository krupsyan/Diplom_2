package order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetUserOrdersTest {
    private OrderClient orderClient;
    private UserClient userClient;

    @Before
    public void setup() {
        orderClient = new OrderClient();
        userClient = new UserClient();
    }

    //Timeout to avoid error 429
    @After
    public void waitOneSec() throws InterruptedException {
        Thread.sleep(1000);
    };

    @Test
    @DisplayName("Check that orders can be received by an authorized user")
    public void receiveOrdersWithAuthSuccess() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        orderClient.createOrder(accessToken)
                .assertThat()
                .statusCode(SC_OK);

        boolean isOrderReceived = orderClient.receiveOrders(accessToken)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");

        assertTrue(isOrderReceived);

        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("Check that orders cannot be received by a not authorized user")
    public void receiveOrdersWithoutAuthError() {

        orderClient.createOrder()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");

        orderClient.createOrder()
                .assertThat()
                .statusCode(SC_OK);

        String unauthorizedError = orderClient.receiveOrders()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("You should be authorised", unauthorizedError);
    }

}
