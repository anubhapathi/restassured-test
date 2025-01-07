package com.api.coindesk;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CoinDeskTest {

    public static void main(String[] args) {
        // Set the base URI for Rest Assured
        RestAssured.baseURI = "https://api.coindesk.com/v1/bpi";

        // Step 1:Send GET request to the endpoint and  Verify status code is 200
        Response response = given()
                .when()
                .get("/currentprice.json")
                .then()
                .statusCode(200) 
                .extract().response();

        // Step 2: Verify response contains 3 BPIs: USD, GBP, EUR
        response.then().assertThat()
                .body("bpi", allOf(
                        hasKey("USD"),
                        hasKey("GBP"),
                        hasKey("EUR")
                ));

        // Step 3: Verify GBP 'description' equals 'British Pound Sterling'
        response.then().assertThat()
                .body("bpi.GBP.description", equalTo("British Pound Sterling"));

        System.out.println("API Test Passed: All validations successful.");
    }
}
