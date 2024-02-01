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
import pojos.BreedDataPojo;
import pojos.BreedPojo;
import utils.ApiExtendReport;
import utils.ConfigReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;



public class GetRequestForBreeds extends ApiExtendReport {

    private static Logger log  = Logger.getLogger(GetRequestForBreeds.class);
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
    public void getBreeds() {

        //breeds listesinin dolu geldiğini doğrula

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("getBreeds");

        spec.pathParam("first","breeds");
        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("data",Matchers.notNullValue());

         ApiExtendReport.logPass("API test passed");
         ApiExtendReport.flushReport();


    }

    @Test
    public void getFirstBreedAndAssertTotalNumberOfBreeds() throws IOException {

        //ilk breed i getirme ve toplam breeds sayısını doğrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("getFirstBreedAndAssertTotalNumberOfBreeds");

        Integer totalBreeds=98;
        spec.pathParam("first","breeds");
        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        BreedDataPojo breedDataPojo=new BreedDataPojo("Abyssinian","Ethiopia","Natural/Standard","Short","Ticked");
        BreedPojo breedPojoExpected=new BreedPojo(1,breedDataPojo,98);

        HashMap<String ,Object> actualData=response.as(HashMap.class);
        System.out.println("actualData = " + actualData);

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("total",equalTo(totalBreeds));

        assertEquals(breedPojoExpected.getCurrent_page(),actualData.get("current_page"));
        assertEquals(breedPojoExpected.getTotal(),actualData.get("total"));
        assertEquals(breedPojoExpected.getData().getBreed(),((Map)((List)actualData.get("data")).get(0)).get("breed"));
        assertEquals(breedPojoExpected.getData().getCountry(),((Map)((List)actualData.get("data")).get(0)).get("country"));
        assertEquals(breedPojoExpected.getData().getOrigin(),((Map)((List)actualData.get("data")).get(0)).get("origin"));
        assertEquals(breedPojoExpected.getData().getCoat(),((Map)((List)actualData.get("data")).get(0)).get("coat"));
        assertEquals(breedPojoExpected.getData().getPattern(),((Map)((List)actualData.get("data")).get(0)).get("pattern"));


        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();
    }

    @Test
    public void verifyingNumberOfPagesInBreeds(){

        // 4 sayfa olduğunu doğrula
        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingNumberOfPagesInBreeds");

        spec.pathParam("first","breeds");
        Response response = given().spec(spec).when().get("/{first}");
        //  response.prettyPrint();
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("last_page",Matchers.equalTo(4));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }

    @Test
    public void verifyingThatThereAreAsManyBreedsAsTheEnteredLimitNumberWithHasSize(){

        // Girilen limit sayısı kadar breed oldugunu dogrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThatThereAreAsManyBreedsAsTheEnteredLimitNumberWithHasSize");

        spec.pathParam("first","breeds").queryParam("limit",10);
        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("data",Matchers.hasSize(10));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();
    }

    @Test
    public void verifyingThatThereAreAsManyBreedsAsTheEnteredLimitNumberWithToKey(){

        // Girilen limit sayısı kadar breed oldugunu dogrulama

        ApiExtendReport.initializeReport();
        ApiExtendReport.createTest("verifyingThatThereAreAsManyBreedsAsTheEnteredLimitNumberWithToKey");
        spec.pathParam("first","breeds").queryParam("limit",10);
        Response response = given().spec(spec).when().get("/{first}");
        log.info(response.then().extract().response().prettyPeek());

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON).body("to",Matchers.equalTo(10));

        ApiExtendReport.logPass("API test passed");
        ApiExtendReport.flushReport();

    }


    @AfterClass
    public static void afterClass() throws Exception {
        log.info("Test bitti.");
    }
}
