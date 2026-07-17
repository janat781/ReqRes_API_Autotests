package auth;

import base.BaseTest;
import io.restassured.response.Response;
import models.ErrorResponse;
import models.LoginRequest;
import models.LoginResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginApiTests extends BaseTest {

    @Test
    public void successfulLoginTest(){
        LoginRequest request = new LoginRequest("eve.holt@reqres.in", "cityslicka");

        Response response = given()
                .spec(requestSpecification)
                .body(request)
                .when()
                .post("/api/login");

        response.then()
                .spec(responseSpecification)
                .statusCode(200);

        LoginResponse user = response.as(LoginResponse.class);

        assertFalse(user.getToken().isEmpty());
    }

    @Test
    public void loginWithoutPasswordTest() {
        LoginRequest request = new LoginRequest("eve.holt@reqres.in", null);

            Response response = given()
                    .spec(requestSpecification)
                    .body(request)
                    .when()
                    .post("/api/login");

            response.then()
                    .spec(responseSpecification)
                    .statusCode(400);

            ErrorResponse user =
                    response.as(ErrorResponse.class);

            assertEquals("Missing password", user.getError());
    }
    }