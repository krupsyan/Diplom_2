package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginTest {

    private UserClient userClient;

    private String accessToken;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @After
    public void shutDown() throws InterruptedException{
        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);

        //Timeout to avoid error 429
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Check that an existing user can login")
    public void loginUserSuccess() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        UserCredentials creds = UserCredentials.from(user);
        boolean isSuccess = userClient.login(creds)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");

        assertTrue(isSuccess);
    }

    @Test
    @DisplayName("Check that a user can't login with incorrect password")
    public void loginUserIncorrectPassword() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        User courierIncorrectPassword = User.getRandomNamePassword(user);
        UserCredentials incorrectCreds =  UserCredentials.from(courierIncorrectPassword);
        String incorrectPasswordError = userClient.login(incorrectCreds)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("email or password are incorrect", incorrectPasswordError);
    }
}