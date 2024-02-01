package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeClass;
import utils.ApiExtendReport;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class GetRequestForFact extends ApiExtendReport {

    private static Logger log  = Logger.getLogger(GetRequestForFact.class);
    protected RequestSpecification spec;

    @Before
    public void setUp(){
        spec=new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("baseURL")).build();
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }
    @BeforeClass
    public static void logData(){

        log.info("Test başladı.");
        log.trace("Logger message TRACE");
        log.debug("Logger message DEBUG");
        log.info("Logger message INFO");
        log.warn("Logger message WARM");
        log.error("Logger message ERROR");
        log.fatal("Logger message FATAL");

    }


    @Test
    public void verifyingThatTheLengthOfTheReceivedFactIsLessByEnteringARandomNumber(){

        //Random bir sayı yaz ve istediğin sayıdan az olduğunu doğrula

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThatTheLengthOfTheReceivedFactIsLessByEnteringARandomNumber");

        Random r=new Random();
        int randomNumber=r.nextInt(500);

        spec.pathParam("first","fact").
         queryParam("max_length", randomNumber);

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("length", Matchers.lessThan(randomNumber));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();
    }


    @Test
    public void verifyThatBodyReturnsEmptyIfLengthIsLessThan20(){

        //length i 20 den az girildiğinde body nin boş döndüğünü doğrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyThatBodyReturnsEmptyIfLengthIsLessThan20");

        Random r=new Random();
        int randomNumber=r.nextInt(19);

        spec.pathParam("first","fact").
                queryParam("max_length", randomNumber);

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        HashMap<String ,Object> actualData=response.as(HashMap.class);
        assertTrue(actualData.isEmpty());

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }
    @Test
    public void verifyingThatBodyIsEmptyWheANegativeNumberIsEntered(){

        //negatif sayı girildğinde body nin bos geldigini dogrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThatBodyIsEmptyWheANegativeNumberIsEntered");

        spec.pathParam("first","fact").
                queryParam("max_length", -3);

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        HashMap<String ,Object> actualData=response.as(HashMap.class);
        assertTrue(actualData.isEmpty());

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }

    @Test
    public void verifyingLengthOfRandomFact(){

        //random gelen fact in length ini doğrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingLengthOfRandomFact");

        spec.pathParam("first","fact");

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        HashMap<String ,Object> actualData=response.as(HashMap.class);
        String factText=actualData.get("fact").toString();
        assertEquals(factText.length(),actualData.get("length"));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }

    @AfterClass
    public static void afterClass() throws Exception {
        log.info("Test bitti.");
    }
}
