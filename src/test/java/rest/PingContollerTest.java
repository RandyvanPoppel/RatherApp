package rest;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.matchers.JUnitMatchers.containsString;

public class PingContollerTest {

    // Before ALL tests
    @BeforeClass
    public void setUp() {
        RestAssured.port = 51572;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/RatherApp-1.0/rest/";
    }

    @Test
    public void nonExistantRouteTest() {
        given().when().get("/nonExistingRoute").then().statusCode(404);
    }

    @Test
    public void pingRouteTest() {
        given().when().get("/ping").then().statusCode(200).body(containsString("Pinged successfully"));
    }
}
