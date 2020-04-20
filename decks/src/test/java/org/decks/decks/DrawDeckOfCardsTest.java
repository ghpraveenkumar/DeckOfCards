package org.decks.decks;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DrawDeckOfCardsTest {
	
	@Test
    public void testDrawDeckOfCardsSuccess_JokersNotAdded() {
    	 
        //The base URI to be used for getting the new deck of cards
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
        
        //The base URI to be used for drawing a card from fetched deck of cards
        RestAssured.baseURI = "http://deckofcardsapi.com/api/deck/" + deck_id_new + "/draw/";
        
        RequestSpecification httpRequest1 = RestAssured.given();
        Response response1 = httpRequest1.request(Method.GET); 
        
        int statusCode1 = response1.getStatusCode();
        JsonPath jsonResponse1 = response1.jsonPath();
        String deck_id_latest = jsonResponse1.get("deck_id");
        int remainingNumberLatest = jsonResponse1.get("remaining");
       
        Assert.assertEquals(statusCode1, 200); 
        Assert.assertEquals(deck_id_new, deck_id_latest);
      //Verify Remaining Cards after Picking 1 card
        Assert.assertEquals(remainingNumberLatest, remainingNumber - 1);
        
    }

	
	@Test
    public void testDrawDeckOfCardsSuccess_JokersAdded() {
    	 
        //The base URI to be used for getting the new deck of cards
        RestAssured.baseURI = "http://deckofcardsapi.com/api/deck/new/";
 
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.queryParam("jokers_enabled", true).request(Method.GET);
        
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
        
        //The base URI to be used for drawing a card from fetched deck of cards
        RestAssured.baseURI = "http://deckofcardsapi.com/api/deck/" + deck_id_new + "/draw/";
        
        RequestSpecification httpRequest1 = RestAssured.given();
        Response response1 = httpRequest1.request(Method.GET); 
        
        int statusCode1 = response1.getStatusCode();
        JsonPath jsonResponse1 = response1.jsonPath();
        String deck_id_latest = jsonResponse1.get("deck_id");
        int remainingNumberLatest = jsonResponse1.get("remaining");
       
        Assert.assertEquals(statusCode1, 200); 
        Assert.assertEquals(deck_id_new, deck_id_latest);
        //Verify Remaining Cards after Picking 1 card
        Assert.assertEquals(remainingNumberLatest, remainingNumber - 1);
        
    }
}
