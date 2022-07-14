package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class CreateUserTest {
    private UserClient userClient;

    private String accessToken;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @After
    public void shutDown() throws InterruptedException{
        //Timeout to avoid error 429
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Check that a new user can be created")
    public void createUserSuccess() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        assertNotNull(accessToken);

        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("Check that the same user cannot be created again")
    public void createSameUserError() {
        User user = User.getRandom();
        accessToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        User sameUser = User.getRandomNamePassword(user);
        String sameLoginError = userClient.createUser(sameUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .extract()
                .path("message");

        assertEquals("User already exists", sameLoginError);

        userClient.deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("Check that a user cannot be created with no email")
    public void createUserNoLoginError() {
        User user = User.getRandomPasswordName();
        ResponseBody noLoginError = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract()
                .body()
                .as(ResponseBody.class);

        if(noLoginError.message == null){
            userClient.deleteUser(noLoginError.accessToken)
                    .statusCode(SC_ACCEPTED);

        }
        assertEquals("Email, password and name are required fields", noLoginError.message);

    }

    @Test
    @DisplayName("Check that a user cannot be created with no password")
    public void createUserNoPasswordError() {
        User user = User.getRandomNoPassword();
        ResponseBody noPasswordError = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract()
                .body()
                .as(ResponseBody.class);

        if(noPasswordError.message == null){
            userClient.deleteUser(noPasswordError.accessToken)
                    .statusCode(SC_ACCEPTED);

        }
        assertEquals("Email, password and name are required fields", noPasswordError.message);
    }

    @Test
    @DisplayName("Check that a user cannot be created with no name")
    public void createUserNoNameError() {
        User user = User.getRandomEmailPassword();
        ResponseBody noPasswordError = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract()
                .body()
                .as(ResponseBody.class);

        if(noPasswordError.message == null){
            userClient.deleteUser(noPasswordError.accessToken)
                    .statusCode(SC_ACCEPTED);

        }
        assertEquals("Email, password and name are required fields", noPasswordError.message);
    }
}