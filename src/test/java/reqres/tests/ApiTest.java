package reqres.tests;

import org.junit.jupiter.api.Test;
import reqres.TestBase;
import reqres.models.*;
import reqres.specs.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest extends TestBase {


    @Test
    void singleUserReqres() {

        SingleUserDto singleUser = step("Send request", () ->
        given(ReqresSpec.userRequestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(ReqresSpec.userResponseSpecification200)
                .extract().as(SingleUserDto.class));

            step("Check Response", () -> {
                assertEquals(2, singleUser.getData().getId());
                assertEquals("janet.weaver@reqres.in", singleUser.getData().getEmail());
                assertEquals("Janet", singleUser.getData().getFirst_name());
                assertEquals("Weaver", singleUser.getData().getLast_name());
                assertEquals(baseURI + "/img/faces/2-image.jpg", singleUser.getData().getAvatar());

                assertEquals(baseURI + "/#support-heading", singleUser.getSupport().getUrl());
                assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", singleUser.getSupport().getText());
            });
    }

    @Test
    void createUserInReqres() {

        CreateUserBodyModel createUser = new CreateUserBodyModel();
        createUser.setName("morpheus");
        createUser.setJob("leader");

        CreateUserBodyModel checkUserBody = step("Send request", () ->
                given(ReqresSpec.userRequestSpec)
                        .body(createUser)
                        .when()
                        .post("/api/users")
                        .then()
                        .spec(ReqresSpec.createSpecification201)
                        .extract().as(CreateUserBodyModel.class));

        step("Check Response", () -> {
            assertEquals("morpheus", checkUserBody.getName());
            assertEquals("leader", checkUserBody.getJob());
        });
    }

    @Test
    void updateUserInReqres() {

        CreateUserBodyModel updateUser = new CreateUserBodyModel();
        updateUser.setName("morpheus");
        updateUser.setJob("zion resident");

        CreateUserBodyModel checkUserBody = step("Send request", () ->
                given(ReqresSpec.userRequestSpec)
                .body(updateUser)
                .when()
                .put("/api/users/2")
                .then()
                .spec(ReqresSpec.userResponseSpecification200)
                .extract().as(CreateUserBodyModel.class));

        step("Check Response", () -> {
            assertEquals("morpheus", checkUserBody.getName());
            assertEquals("zion resident", checkUserBody.getJob());
        });
    }


    @Test
    void deleteUserInReqres() {
        step("Send request", () ->
        given(ReqresSpec.userRequestSpec)
                .when()
                .delete("/api/users/2")
                .then()
                .spec(ReqresSpec.deleteSpecification204));

    }

    @Test
    void registerUserInReqres() {

        RegisterDto registerUser = new RegisterDto();
        registerUser.setEmail("eve.holt@reqres.in");
        registerUser.setPassword("pistol");


    RegisterResponseDto registerResponse = step("Send request", () ->
            given(ReqresSpec.userRequestSpec)
                .body(registerUser)
                .when()
                .post("/register")
                .then()
                .spec(ReqresSpec.userResponseSpecification200)
                .extract().as(RegisterResponseDto.class));;

        step("Check Response", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", registerResponse.getToken());
            assertEquals(4, registerResponse.getId());
        });
    }


    @Test
    void loginUnsuccessfulUserInReqres() {

        RegisterDto login = new RegisterDto();
        login.setEmail("peter@klaven");

        LoginResponseErrorDto loginError = step("Send request", () ->
                given(ReqresSpec.userRequestSpec)
                .body(login)
                .when()
                .post("/login")
                .then()
                .spec(ReqresSpec.loginErrorResponseSpecification400)
                .extract().as(LoginResponseErrorDto.class));

        step("Check Response", () -> {
            assertEquals("Missing password", loginError.getError());
        });
    }

    @Test
    void getUsersListInReqresStatus200() {

        ListUsersDto usersList = step("Send request", () ->
                given(ReqresSpec.userRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(ReqresSpec.userResponseSpecification200)
                        .extract().as(ListUsersDto.class));

        step("Check Response", () -> {
            assertEquals(2, usersList.getPage());
            assertEquals(6, usersList.getPer_page());
            assertEquals(12, usersList.getTotal());
            assertEquals(2, usersList.getTotal_pages());

            assertEquals(7, usersList.getData().get(0).getId());
            assertEquals("michael.lawson@reqres.in", usersList.getData().get(0).getEmail());
            assertEquals("Michael", usersList.getData().get(0).getFirst_name());
            assertEquals("Lawson", usersList.getData().get(0).getLast_name());
            assertEquals(baseURI + "/img/faces/7-image.jpg", usersList.getData().get(0).getAvatar());

            assertEquals(8, usersList.getData().get(1).getId());
            assertEquals("lindsay.ferguson@reqres.in", usersList.getData().get(1).getEmail());
            assertEquals("Lindsay", usersList.getData().get(1).getFirst_name());
            assertEquals("Ferguson", usersList.getData().get(1).getLast_name());
            assertEquals(baseURI + "/img/faces/8-image.jpg", usersList.getData().get(1).getAvatar());

            assertEquals("https://reqres.in/#support-heading", usersList.getSupport().getUrl());
            assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", usersList.getSupport().getText());
        });
    }

    @Test
    void getUsersListInReqresStatus200CheckBodyWithSchema() {

         step("Send request", () ->
                given(ReqresSpec.userRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(ReqresSpec.userResponseSpecification200)
                        .body(matchesJsonSchemaInClasspath("schemas/user-schema.json")));
    }
}