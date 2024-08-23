package userManagement;

import core.statusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.SoftAssertionUtil;
import util.jsonReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.Headers.headers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.http.Cookies.*;
import static org.testng.AssertJUnit.assertEquals;
import org.json.simple.parser.ParseException;
import util.propertyReader;


public class getUserData {


    static SoftAssertionUtil softAssertionUtil =new SoftAssertionUtil();
    @Test
    public static void getUserData(){
         given().
                 when().get("https://reqres.in/api/users?page=2").
        then().assertThat().statusCode(200);


    }
    @Test
    public static void validateGetResponseBody(){
        // Set the base URI for Rest Assured
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Given, When, Then
        given().

    when().get("/todos/1")
                .then()
                .statusCode(200)
                .body(not(isEmptyString()))
                .body("title",equalTo("delectus aut autem"));


    }
@Test
    public static  void validateResponseHasItems(){
    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    Response response= given()
            .when()
            .get("posts")
            .then()
            .extract().response();

    assertThat(response.jsonPath().getList("title"),hasItems("qu" +
            "i est esse"));
}
@Test
public static  void validateResponseHasSize(){
    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    Response response= given()
            .when()
            .get("comments")
            .then()
            .extract().response();

    assertThat(response.jsonPath().getList(""), hasSize(500));
}
@Test
    public static void Contains(){
    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    Response response =given()
            .when()
            .get("comments")
            .then()
            .extract().response();


    List<String> emails= Arrays.asList("Eliseo@gardner.biz");
    assertThat(response.jsonPath().getList("email"),contains(emails.toArray(new String[0])));
}
// query paramters
    @Test
    public static void queryParameter()
    {
     //   RestAssured.baseURI = "https://reqres.in/api/users";

        Response response=given()
                .queryParam("page",2)

                // .queryParam("page",2)  multiple qyery parameter add one more
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .extract().response();
        assertThat(response.statusCode(),is(200));
    }
    @Test   //path paramter
    public static void pathPParamterss(){
        Response response=given()
                .pathParam("raceseason",2017)
                .when()
                .get("http://ergast.com/api/f1/{raceseason}/circuits.json")
                .then()
                .extract().response();
        assertThat(response.statusCode(),is(200));
        //print response body in console
        System.out.println(response.body().asString());



    }
    @Test   //passing single header
    public static void userTestHeaders(){
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);


    }

    //passing multiple header
    @Test
    public  static void multipleTestHeaders()
    {
        given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer jkjjksjksjksjksjksjksjksjksjksjksjks")
            .when()
            .get("https://reqres.in/api/users?page=2")
            .then()
            .statusCode(200);

    }
// header using an hashmap
    @Test
    public static void headerUsingMap()

        {
            Map<String,String> headers= new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer jkjjksjksjksjksjksjksjksjksjksjksjks");

            given()
            .headers(headers)
                    //   headers(headers)
                    .when()
                    .get("https://reqres.in/api/users?page=2")
                    .then()
                    .statusCode(200);



        }
        // getting cokkie
        @Test
        public  static void multipleTestCokkie()
        {
            given()
                    .cookie("CookieKey1", "value1")
                    .cookie("CookieKey2", "value2")
                    .when()
                    .get("https://reqres.in/api/users?page=2")
                    .then()
                    .statusCode(200);

        }

        // cookie with cookie builder
        @Test
        public static void cookieBuilderTest() {

            // Build the cookie
            Cookie cookies = new Cookie.Builder("CookieKey1", "value1")
                    .setComment("using cookie name")
                    .build();

            // Use the cookie in the API request
            given()
                    .cookie(cookies)  // Set the cookie
                    .when()
                    .get("https://reqres.in/api/users?page=2")  // Specify the endpoint
                    .then()
                    .statusCode(200);  // Validate the status code
        }


        //basic auth
    @Test
    public static void basicAuth()
    {
        Response response=given()
                .auth()
                .basic("postman","password")
                .when()
                .get("https://postman-echo.com/basic-auth");
                assertThat(response.statusCode(),is(200));
                System.out.println(response.body().asString());
    }
    //Digest Auth

    @Test
    public static void digestAuth()
    {
        Response response=given()
                .auth()
                .digest("postman","password")
                .when()
                .get("https://postman-echo.com/digest-auth");

                assertThat(response.statusCode(),is(200));
                System.out.println(response.body().asString());
    }

    //delete method

    @Test
    public  static  void deleteValidate()
    {
        Response response=given().delete("https://reqres.in/api/users/2");
        assertThat(response.statusCode(),is(statusCode.UPDATED.code));
    }


    //validate the basic auth by data from json file

    @Test
    public  void validateWithTestDataFromJson() throws IOException, ParseException {

        String username = jsonReader.getTestData("username");
        String password = jsonReader.getTestData("password");
        System.out.println("username from json is: " + username +
                "***password from json is:" + password);
        Response resp = given()
                .auth()
                .basic(username, password)
                .when()
                .get("https://postman-echo.com/basic-auth"); //RestAssured
        int actualStatusCode = resp.statusCode(); //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithTestDataFromJson executed successfully");
    }

    //property reader

    @Test
    public  void validateWithTestDataFromPropertyFile() throws IOException,ParseException {

        String serverAddress = propertyReader.propertyReader("config.properties","server");
        String username = jsonReader.getTestData("username");
        String password = jsonReader.getTestData("password");
       System.out.println( serverAddress);

        Response resp = given()
                .auth()
                .basic(username, password)
                .when()
                .get(serverAddress); //RestAssured
        int actualStatusCode = resp.statusCode(); //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithTestDataFromJson executed successfully");
    }

// property with testdata

    @Test
    public  void validateWithTestDataFromPropertyFileTestData() throws IOException,ParseException {

        String serverAddress = propertyReader.propertyReader("config.properties","serverAddress");
        String username = jsonReader.getTestData("username");
        String password = jsonReader.getTestData("password");
        String endpoint= jsonReader.getTestData("endpoint");
        System.out.println( serverAddress+endpoint);

        Response resp = given()
                .auth()
                .basic(username, password)
                .when()
                .get(serverAddress+endpoint); //RestAssured
        int actualStatusCode = resp.statusCode(); //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithTestDataFromJson executed successfully");
    }


    //validate with soft assert util

    @Test
    public static void validateWithSoftAssertUtil()  throws IOException, ParseException
    {


        String username = jsonReader.getTestData("username");
        String password = jsonReader.getTestData("password");
        System.out.println("username from json is: " + username +
                "***password from json is:" + password);
        Response resp = given()
                .auth()
                .basic(username, password)
                .when()
                .get("https://postman-echo.com/basic-auth"); //RestAssured
        int actualStatusCode = resp.statusCode(); //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithTestDataFromJson executed successfully");
        softAssertionUtil.assertEquals(resp.getStatusCode(),statusCode.UPDATED.code,"status code matched");
softAssertionUtil.assertAll();
    }

    @DataProvider(name = "testdata")
    return new ojbect[][] {
            
    }
}

