package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class EditUserInfoTest {

    private UserClient userClient;
    private String accessToken;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    //Timeout to avoid error 429
    @After
    public void shutDown() throws InterruptedException{
        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);

        //Timeout to avoid error 429
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Check editing user details")
    public void editUserSuccess() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

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
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        User userEmail = User.getRandomEmail();

        String errorMessage = userClient.editUserEmail(userEmail)
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
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        User userName = User.getRandomName();

        String errorMessage = userClient.editUserName(userName)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("You should be authorised", errorMessage);
    }

    @Test
    @DisplayName("Check that user password can't be edited without authorization")
    public void editUserPasswordNoAuth() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        User userPassword = User.getRandomPassword();

        String errorMessage = userClient.editUserPassword(userPassword)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("message");

        assertEquals("You should be authorised", errorMessage);
    }
}
