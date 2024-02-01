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
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;


public class GetRequestForFacts extends ApiExtendReport {

    private static Logger log  = Logger.getLogger(GetRequestForFacts.class);
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
    public void verifyingTotalFactCount(){

        //toplam facts sayısının 332 olduğunu doğrula

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingTotalFactCount");

        Integer totalFacts=332;
        spec.pathParam("first","facts");

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("total", equalTo(totalFacts));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();
    }

    @Test
    public void verifyingThereAre34Pages(){
        //34 sayfa olduğunu doğrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThereAre34Pages");

        spec.pathParam("first","facts");

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("last_page", equalTo(34));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }

    @Test
    public void verifyingThatTheRandomlyEnteredNumberOfPagesLeadsToTheCorrectPage(){

        //random girilen sayfa sayısının doğru sayfaya götürdüğünü doğrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThatTheRandomlyEnteredNumberOfPagesLeadsToTheCorrectPage");

        Random random=new Random();
        int randomPageCount= random.nextInt(34);
        spec.pathParam("first","facts").queryParam("page",randomPageCount);

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        HashMap<String ,Object> actualData=response.as(HashMap.class);

        System.out.println("randomSayı"+randomPageCount);
       response.then().assertThat().statusCode(200);
       assertEquals(randomPageCount,actualData.get("current_page"));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();
    }

    @Test
    public void verifyingThatTheNumberOfLimitsEnteredIsFact(){

        //girilen limit sayısınca fact döndüğünü doğrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThatTheNumberOfLimitsEnteredIsFact");


        Random random=new Random();
        int randomLimitCount= random.nextInt(100);
        spec.pathParam("first","facts").queryParam("limit",randomLimitCount);

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        System.out.println("randomSayı"+randomLimitCount);
        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("data",Matchers.hasSize(randomLimitCount));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }
    @Test
    public void verifyingThatTheBodyIsFullAsAresultOfTheEnteredMaxLengthEndLimit(){

        //girilen max length ve limit sonucunda body nin dolu geldiğini doğrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThatTheBodyIsFullAsAresultOfTheEnteredMaxLengthEndLimit");

        spec.pathParam("first","facts").queryParams("max_length",50,"limit",10);

        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("data",Matchers.notNullValue());

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }



    @AfterClass
    public static void afterClass() throws Exception {
        log.info("Test bitti.");
    }
}
