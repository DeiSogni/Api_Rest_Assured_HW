package reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ApiTest extends TestBase {


    @Test
    void singleUserReqres() {

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .when()
                .get("users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data", everyItem(notNullValue()));

    }

    @Test
    void createUserInReqres() {

        String createUser = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .log().body()
                .log().method()
                .contentType(JSON)
                .body(createUser)
                .when()
                .post("/api/users")
                .then()
                .log().status()
                .statusCode(201)
                .body("name", is("morpheus"),
                        "job", is("leader"));
    }


    @Test
    void updateUserInReqres() {
        String updateUser = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .log().body()
                .log().method()
                .contentType(JSON)
                .body(updateUser)
                .when()
                .put("/api/users/2")
                .then()
                .log().status()
                .statusCode(200)
                .body("name", is("morpheus"),
                        "job", is("zion resident"));
    }


    @Test
    void deleteUserInReqres() {
        given()
                .log().uri()
                .log().body()
                .log().method()
                .contentType(JSON)
                .when()
                .delete("/api/users/2")
                .then()
                .log().status()
                .statusCode(204);

    }

    @Test
    void registerUserInReqres() {

        String registerUser = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().body()
                .log().method()
                .contentType(JSON)
                .body(registerUser)
                .when()
                .post("/register")
                .then()
                .log().status()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }


    @Test
    void loginUnsuccessfulUserInReqres() {

        String loginUser = "{ \"email\": \"peter@klaven\", \"error\": \"Missing password\"}";

        given()
                .log().uri()
                .log().body()
                .log().method()
                .contentType(JSON)
                .body(loginUser)
                .when()
                .post("/login")
                .then()
                .log().status()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void putUserInReqres() {

        String loginUser = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .log().body()
                .log().method()
                .contentType(JSON)
                .body(loginUser)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .statusCode(200)
                .body("name", is("morpheus"),
                        "job", is("zion resident"));
    }


}