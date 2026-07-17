package users;
import base.BaseTest;
import models.*;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiTests extends BaseTest {

    @ParameterizedTest(name = "Проверка пользователя с id = {0}")
    @ValueSource(ints = {1, 2, 3})
    public void getSingleUserTest(int userId) {

        given()
                .spec(requestSpecification)
                .when()
                .get("/api/users/" + userId)
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .body("data.id", equalTo(userId))
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue());
    }


    @Test
    public void getUsersListTest() {
         given()
                 .spec(requestSpecification)
                 .queryParam("page", 2)
                 .when()
                 .get("/api/users")
                 .then()
                 .spec(responseSpecification)
                 .statusCode(200)
                 .body("page", equalTo(2))
                 .body("per_page", equalTo(6))
                 .body("data.size()", equalTo(6));
    }

    @Test
    public void getUserNotFoundTest(){
        given()
                .spec(requestSpecification)
                .when()
                .get("/api/users/23")
                .then()
                .spec(responseSpecification)
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    public void createUserTest() {

        CreateUserRequest request =
                new CreateUserRequest("Lisa", "QA Engineer Auto");

        Response response = given()
                .spec(requestSpecification)
                .body(request)
                .when()
                .post("/api/users");

        response.then()
                .spec(responseSpecification)
                .statusCode(201);

        CreateUserResponse user =
                response.as(CreateUserResponse.class);

        assertEquals("Lisa", user.getName());
        assertEquals("QA Engineer Auto", user.getJob());

        assertNotNull(user.getId());
        assertNotNull(user.getCreatedAt());

    }

    @Test
    public void createUserWithNullDataTest() {

        CreateUserRequest request =
                new CreateUserRequest(null, null);

        Response response = given()
                .spec(requestSpecification)
                .body(request)
                .when()
                .post("/api/users");

        response.then()
                .spec(responseSpecification)
                .statusCode(201);

        CreateUserResponse user =
                response.as(CreateUserResponse.class);

        assertNull(user.getName());
        assertNull(user.getJob());

        assertNotNull(user.getId());
        assertNotNull(user.getCreatedAt());
    }


    @Test
    public void updateUserTest() {

        UpdateUserRequest request =
                new UpdateUserRequest("Kim", "Senior QA Engineer Auto");

        Response response = given()
                .spec(requestSpecification)
                .body(request)
                .when()
                .put("/api/users/2");

        response.then()
                .spec(responseSpecification)
                .statusCode(200);

        UpdateUserResponse user =
                response.as(UpdateUserResponse.class);

        assertEquals("Kim", user.getName());
        assertEquals("Senior QA Engineer Auto", user.getJob());

        assertNotNull(user.getUpdatedAt());

    }

    @Test
    public void patchUserTest() {

        PatchUserRequest request = new PatchUserRequest();
        request.setJob("Lead QA Engineer Auto");

        PatchUserResponse response = given()
                .spec(requestSpecification)
                .body(request)
                .when()
                .patch("/api/users/2")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract()
                .as(PatchUserResponse.class);

        assertEquals("Lead QA Engineer Auto", response.getJob());

        assertNotNull(response.getUpdatedAt());

    }

    @Test
    public void deleteUserTest(){
        given()
                .spec(requestSpecification)
                .when()
                .delete("/api/users/2")
                .then()
                .spec(responseSpecification)
                .statusCode(204);
    }
}