# DeckOfCardsAPITest

## Introduction

This project is to implement automation tests to the following APIs in Java
1. Create a new deck of cards, GET https://deckofcardsapi.com/api/deck/new/.
Support adding Jokers with a POST
2. Draw one or more cards from a deck,
GET https://deckofcardsapi.com/api/deck/{deck_id}/draw/?count={count_value}.
 
    Parameters: 

        deck_id: ID of the deck.
        count_value: number of cards to draw.    

## Code Samples
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

## Installation

# Prerequisites:
>> Eclipse IDE, JAVA 1.8, Maven and TestNG.

# How to run:
>> Right click on pom.xml and Run As -> Clean Install. This will install all the dependencies and runs all the Tests.

## Output:
>> Once Maven build is completed, there will be test-output folder generated with UI interface for list of all test cases  and also have test-results.xml.
