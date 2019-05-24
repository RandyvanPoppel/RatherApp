package rest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@Stateless
public class ComparisonControllerTest {

    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    // Before ALL tests
    @BeforeClass
    public static void setUp() {
        RestAssured.port = 51572;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/RatherApp-1.0/rest/";
    }

    // Before EACH test
    @Before
    public void init() {
        System.out.println(em);
    }

    // After EACH test
    @After
    public void finalize() {
    }

    @Test
    public void addComparisonTest() {
        given().contentType("application/json")
                .queryParam("choiceDescriptions", "Choice A")
                .queryParam("choiceDescriptions", "Choice B")
                .post("/comparison/add")
                .then()
                .statusCode(200);
    }

    @Test
    public void getLatestComparisonsTest() {
        given().contentType("application/json")
                .queryParam("unixTimeStamp", 5246080000000L)
                .get("/comparison/getLatest")
                .then()
                .statusCode(200)
                .assertThat().body("size()", is(0));

        given().contentType("application/json")
                .queryParam("choiceDescriptions", "Choice A")
                .queryParam("choiceDescriptions", "Choice B")
                .post("/comparison/add")
                .then()
                .statusCode(200);

        given().contentType("application/json")
                .queryParam("unixTimeStamp", 5246080000000L)
                .get("/comparison/getLatest")
                .then()
                .statusCode(200)
                .assertThat().body("size()", is(1));
    }
}
