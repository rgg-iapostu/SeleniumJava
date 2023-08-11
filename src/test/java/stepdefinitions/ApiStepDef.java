package stepdefinitions;

import io.cucumber.java.en.Given;
import io.restassured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class ApiStepDef {
    @Given("I am sending a request to register API")
    public void iAmSendingARequestToRegisterAPI() {
        RestAssured.baseURI = "https://perf.ruelala.com/api/";
        Response response = given()
                .contentType(ContentType.URLENC)
                .body("{\"username\":\"user@example.com\"}")
                .post("v3/register/lookup")
                .then()
                .extract().response().prettyPeek();
    }
}
