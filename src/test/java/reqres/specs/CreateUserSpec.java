package reqres.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class CreateUserSpec {

    public static RequestSpecification createUserRequestSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(new AllureRestAssured())
            .contentType(JSON);
    public static ResponseSpecification createUserSpecification201 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .build();
}