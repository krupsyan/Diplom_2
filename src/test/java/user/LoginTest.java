package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginTest {

    private UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    //Timeout to avoid error 429
    @After
    public void waitOneSec() throws InterruptedException {
        Thread.sleep(1000);
    };

    @Test
    @DisplayName("Check that an existing user can login")
    public void loginUserSuccess() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
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
        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("Check that a user can't login with incorrect password")
    public void loginUserIncorrectPassword() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        User courierIncorrectPassword = User.getRandomPasswordFirstname(user);
        UserCredentials incorrectCreds =  UserCredentials.from(courierIncorrectPassword);
        String incorrectPasswordError = userClient.login(incorrectCreds)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("email or password are incorrect", incorrectPasswordError);
        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }
}