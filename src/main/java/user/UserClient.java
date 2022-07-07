package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import root.RestAssuredClient;

public class UserClient extends RestAssuredClient {

    private final String ROOT = "/auth";
    private final String REGISTER = ROOT + "/register";
    private final String LOGIN = ROOT + "/login";
    private final String USER = ROOT + "/user";

    @Step("Create a new user")
    public ValidatableResponse createUser(User user) {
        return reqSpec
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Create a new user")
    public ValidatableResponse createUser(UserNoPassword user) {
        return reqSpec
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Create a new user")
    public ValidatableResponse createUser(UserNoName user) {
        return reqSpec
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Login as a user")
    public ValidatableResponse login(UserCredentials creds) {
        return reqSpec
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Login as a user after email update")
    public ValidatableResponse loginAfterEmailUpdate(UserEmail userEmail, UserCredentials creds) {
        return reqSpec
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Edit user details")
        public ValidatableResponse editUser(String accessToken, User user) {
        return reqSpec
                .header("authorization", accessToken)
                .body(user)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Edit user details")
    public ValidatableResponse editUser(UserEmail userEmail) {
        return reqSpec
                .body(userEmail)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Edit user details")
    public ValidatableResponse editUser(UserPassword userPassword) {
        return reqSpec
                .body(userPassword)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Edit user details")
    public ValidatableResponse editUser(UserName userName) {
        return reqSpec
                .body(userName)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Delete a user")
    public ValidatableResponse deleteUser(String accessToken) {
        return reqSpec
                .header("authorization", accessToken)
                .when()
                .delete(USER)
                .then().log().all();
    }
}
