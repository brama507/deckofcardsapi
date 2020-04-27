package com.deckofcardsapi.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeckOfCardsApiTests {

	private String deckIdIncludeJoker;
	private String deckIdExcludeJoker;

	@BeforeTest
	public void getBaseUri() {
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = "http://deckofcardsapi.com/api/deck";
	}

	/**
	 * This is the test case to create new deck of cards that includes Jokers.
	 * 
	 */
	@Test(priority = 1)
	public void createNewDeckOfCardsWithJoker() {   
		createNewDeckOfCards(true);
	}
	
	/**
	 * This is the test case to create new deck of cards that excludes Jokers.
	 * 
	 */
	@Test(priority = 3)
	public void createNewDeckOfCardsWithoutJoker() {   
		createNewDeckOfCards(false);
	}

	/**
	 * This test us to draw two cards from deck with joker.
	 * 
	 */
	@Test(dependsOnMethods={"createNewDeckOfCardsWithJoker"}, priority = 2)
	public void drawTwoCardsFromDeckWithJoker() {
		int cardsToDraw = 2;
		drawCardsFromDeck(deckIdIncludeJoker, cardsToDraw);
	}
	
	/**
	 * This test us to draw four cards from deck without joker.
	 * 
	 */
	@Test(dependsOnMethods={"createNewDeckOfCardsWithoutJoker"}, priority = 4)
	public void drawFourCardsFromDeckWithoutJoker() {
		int cardsToDraw = 4;
		drawCardsFromDeck(deckIdExcludeJoker, cardsToDraw);
	}
	
	/**
	 * This test us to draw no cards from deck with joker.
	 * 
	 */
	@Test(dependsOnMethods={"createNewDeckOfCardsWithJoker"}, priority = 5)
	public void drawNoCardsFromDeckWithJoker() {
		int cardsToDraw = 0;
		drawCardsFromDeck(deckIdIncludeJoker, cardsToDraw);
	}
	
	/**
	 * This test us to draw five cards from deck without joker.
	 * 
	 */
	@Test(dependsOnMethods={"createNewDeckOfCardsWithoutJoker"}, priority = 6)
	public void drawFiveCardsFromDeckWithJoker() {
		int cardsToDraw = 5;
		drawCardsFromDeck(deckIdExcludeJoker, cardsToDraw);
	}

	private void createNewDeckOfCards(boolean jokersEnabled) {   

		// Get the RequestSpecification of the request to sent to the server. 
		RequestSpecification httpRequest = RestAssured.given();

		//Enabled Jokers in the request parameter.
		httpRequest.param("jokers_enabled", jokersEnabled);

		// Specifying the method Type and the method URL.
		// This will return the Response from the server. Store the response in a variable.
		Response response = httpRequest.request(Method.GET, "/new/");

		// Check if the response status is 200.
		int responseStatusCode = response.getStatusCode();
		Assert.assertEquals(responseStatusCode, 200, "Valid response.");

		// Print the body of the message to see the response we have received from the server.
		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);

		JsonPath jsonPath = response.jsonPath();

		// Validate New deck of cards created by checking the response field "success" : true
		boolean status = jsonPath.getBoolean("success");
		Assert.assertEquals(status, true, "New Deck of cards created.");

		int totalCards = jsonPath.getInt("remaining");
		if(jokersEnabled) {
			// Assigning the deck_id from the response to the deckId variable.
			deckIdIncludeJoker = jsonPath.getString("deck_id");
			Assert.assertEquals(totalCards, 54, "Two Joker cards are included.");		
		} else {
			deckIdExcludeJoker = jsonPath.getString("deck_id");
			Assert.assertEquals(totalCards, 52,  "Two Joker cards are not included.");
		}
	}
	
	private void drawCardsFromDeck(String deckId, int count) {
		// Get the RequestSpecification of the request to sent to the server. 
		RequestSpecification httpRequest = RestAssured.given();
		
		//Specifying the number of cards in the request parameter.
		httpRequest.param("count", count);
		
		Response response = httpRequest.pathParam("deck_id", deckId).when().get("/{deck_id}/draw");

		// Check if the response status is 200.
		int responseStatusCode = response.getStatusCode();
		Assert.assertEquals(responseStatusCode, 200, "Valid response.");
		
		// Print the body of the message to see the response we have received from the server.
		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);
		
		JsonPath jsonPath = response.jsonPath();
		
		List<Object> cardsJson = jsonPath.getList("cards");
		
		//Validate no of cards drawn from response are the same as requested input card count.
		Assert.assertEquals(cardsJson.size(), count);
	}
}
