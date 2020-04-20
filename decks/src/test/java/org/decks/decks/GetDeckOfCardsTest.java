package org.decks.decks;

import org.junit.Assert;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
 
public class GetDeckOfCardsTest {
 
	
    @Test
    public void testGetDeckOfCardsSuccess_JokersAdded() {
    	 
        //The base URI to be used
        RestAssured.baseURI = "https://deckofcardsapi.com/api/deck/new/";
 
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.queryParam("jokers_enabled", true).request(Method.GET);;
        httpRequest.header("Content-Type", "application/json");
       
        int statusCode = response.getStatusCode();
        JsonPath jsonResponse = response.jsonPath();
        String deck_id_new = jsonResponse.get("deck_id");
        int remainingNumber = jsonResponse.get("remaining");
        boolean shuffledStatus = jsonResponse.get("shuffled");
        
        //Assert checks
        Assert.assertEquals(statusCode, 200); 
        Assert.assertNotNull(deck_id_new);
        Assert.assertEquals(remainingNumber, 54);
        Assert.assertEquals(shuffledStatus, false);
    }
    
    @Test
    public void testGetDeckOfCardsSuccess_JokersNotAdded() {
    	 
        //The base URI to be used
        RestAssured.baseURI = "http://deckofcardsapi.com/api/deck/new/";
 
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.queryParam("jokers_enabled", false).request(Method.GET);
        
        int statusCode = response.getStatusCode();
        JsonPath jsonResponse = response.jsonPath();
        String deck_id_new = jsonResponse.get("deck_id");
        int remainingNumber = jsonResponse.get("remaining");
        boolean shuffledStatus = jsonResponse.get("shuffled");
        
        //Assert checks
        Assert.assertEquals(statusCode, 200); 
        Assert.assertNotNull(deck_id_new);
        Assert.assertEquals(remainingNumber, 52);
        Assert.assertEquals(shuffledStatus, false);
    }
}