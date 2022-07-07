package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class EditUserInfoTest {

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
    @DisplayName("Check editing user details")
    public void editUserSuccess() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        user = User.getRandom();

        boolean areDetailsChanged = userClient.editUser(accessToken, user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");

        assertTrue(areDetailsChanged);
    }

    @Test
    @DisplayName("Check that user email can't be edited without authorization")
    public void editUserEmailNoAuth() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        UserEmail userEmail = UserEmail.getRandomEmail();

        String errorMessage = userClient.editUser(userEmail)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("You should be authorised", errorMessage);
    }

    @Test
    @DisplayName("Check that user name can't be edited without authorization")
    public void editUserNameNoAuth() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        UserName userName = UserName.getRandomName();

        String errorMessage = userClient.editUser(userName)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("You should be authorised", errorMessage);

        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("Check that user password can't be edited without authorization")
    public void editUserPasswordNoAuth() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        UserPassword userPassword = UserPassword.getRandomPassword();

        String errorMessage = userClient.editUser(userPassword)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("You should be authorised", errorMessage);

        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }
}
