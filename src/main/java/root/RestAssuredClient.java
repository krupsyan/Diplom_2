package root;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredClient {
    final String URL = "https://stellarburgers.nomoreparties.site/api";

    protected final RequestSpecification reqSpec = given()
            .header("Content-type", "application/json")
            .baseUri(URL);

}
