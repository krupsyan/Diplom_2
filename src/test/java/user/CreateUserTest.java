package user;


import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class CreateUserTest {
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
    @DisplayName("Check that a new user can be created")
    public void createUserSuccess() {
        User user = User.getRandom();
        String accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        assertNotNull(accessToken);

        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Check that the same user cannot be created again")
    public void createSameUserError() {
        User user = User.getRandom();
        userClient.createUser(user);

        User sameUser = User.getRandomNamePassword(user);
        String sameLoginError = userClient.createUser(sameUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .extract()
                .path("message");

        assertEquals("User already exists", sameLoginError);
    }

    @Test
    @DisplayName("Check that a user cannot be created with no email")
    public void createUserNoLoginError() {
        User user = User.getRandomNoEmail();
        String noLoginError = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract()
                .path("message");

        assertEquals("Email, password and name are required fields", noLoginError);
    }

    @Test
    @DisplayName("Check that a user cannot be created with no password")
    public void createUserNoPasswordError() {
        UserNoPassword user = UserNoPassword.getRandomNoPassword();
        String noPasswordError = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract()
                .path("message");

        assertEquals("Email, password and name are required fields", noPasswordError);
    }

    @Test
    @DisplayName("Check that a user cannot be created with no name")
    public void createUserNoNameError() {
        UserNoName user = UserNoName.getRandomNoName();
        String noNameError = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract()
                .path("message");

        assertEquals("Email, password and name are required fields", noNameError);
    }
}