package hw2;
import java.util.Random;

public class MatchCardGame {

  //public fields
  public final int n; //the size of the game set by the constructor

  //private fields
  private final char[] CardValues; //array containing the char values of n cards of the game
  private static int flips = 0; //amount of flips performed
  private int matches = 0; //number of matches found
  private int[] flipcardIndex = {-1,-1}; //array to store the index of flipped cards
  private boolean[] cardStatus; //boolean array to store the status of each card
  private int counter; //counter to display how many 
  
  /**
   * Initializes the card game with a total of n cards
   * @param n the amount of cards, assuming n is a multiple of 4
   */
  public MatchCardGame(int n) {  

    this.n = n;
    this.CardValues = new char[n];
    this.cardStatus = new boolean[n];
    this.counter = 0;

    //for loop to initialize each value of the array
    for (int i = 0; i < n; i++) {
      //if statement to check when to increase counter variable
      if (i % 4 == 0 && i > 0) { //increase when i is a multiple of 4 and greater than 0
        counter++; //increase counter by one
      }
      //if statement to check if i/4 is equal to the counter
      if (i / 4 == counter) {
        this.CardValues[i] = (char)(counter+'a'); //initialize CardValues array with the letter
      }
    }
  }
  
  /**
   * shuffles the cards from CardValues using the Fisher-Yates shuffle method
   */
  public void shuffleCards() {

    Random r = new Random(); //create a random object

    //for loop to go through each value in CardValues
    for (int i = n-1; i > 0; i--) {
      int j = r.nextInt(i+1); //generates random new number
      char temp = CardValues[i]; //stores the initial value into temp
      //swaps the CardValue positions with each other
      CardValues[i] = CardValues[j]; 
      CardValues[j] = temp; 
    }
  }
  
  /**
   * Checks if all of the cards have been matched
   * @return true if all cards are matched and false if not all cards have been matched
   */
  public boolean gameOver() {

    //if statement to check if matches is equal to n
    if (matches == n) {
      return true; //returns true
    }
    else { //if matches is not equal to n
      return false; //return false
    }
  }
  
  /**
   * To retrieve the number of card flips performed
   * @return flips, the number of card flips performed
   */
  public int getFlips() {
    return flips;
  }
  
  /**
   * Plays the card number i
   * @param i the card to be played
   * @return true if the card can be played, false if not
   */
  public boolean flip(int i) {

    flips++; //counts the number of flips

    //if statement to check if a card can be played
    if (cardStatus[i] == false) { //if the card has not yet been played,
      cardStatus[i] = true; //we can play it
      //if statement to store the index of the current flip
      if (flips % 2 == 0) { //if flips is even
        flipcardIndex[1] = i; //store the index in position 1
      }
      else { //if flips is odd
        flipcardIndex[0] = i; //store the index in position 0
      }
      return true; //returns true
    }
    else if (cardStatus[i] == true) { //if the card has already been played
      flips--; //the flip does not count because it has already been flipped
      return false; //returns false
    }
    else if (i < 0 || i >= n) { //if i is an invalid number
      return false; //returns false
    }
    else {
      return false; 
    }
  }
 
  /**
   * Checks if there was a match between two previous cards
   * @return true if there is a match, false if not
   */
  public boolean wasMatch() {

    //if statement to check if there is an even number of flips 
    if (flips % 2 == 0) {
      //if statement to check if the previous two cards match
      if (CardValues[flipcardIndex[0]] == CardValues[flipcardIndex[1]]) {
        cardStatus[flipcardIndex[0]] = true; //to keep track of cards that are already played
        cardStatus[flipcardIndex[1]] = true; //to keep track of cards that are already played
        matches += 2; //adds 2 to matches
        return true; //returns true
      }
      else { //if there is no match
        return false; //returns false
      }
    }
    else { //if flips is not even, we cannot compare the cards
      return false; //returns false
    }
  }

  /**
   * Gets the value of the previous flip performed
   * @return the character of the previous card flipped
   */
  public char previousFlipIdentity(){

    //to check if flips is even or not
    if (flips % 2 == 0) {
      return CardValues[flipcardIndex[1]]; //returns the CardValue from the previous flip
    }
    else {
      return CardValues[flipcardIndex[0]]; //returns the CardValue from the previous flip
    }
  }

  /**
   * Reverts the mismatched pair back to face-down position
   */
  public void flipMismatch() {

    //check if the cards are mismatched
    if (CardValues[flipcardIndex[0]] != CardValues[flipcardIndex[1]]) {
      cardStatus[flipcardIndex[0]] = false; //changes status back to unflipped
      cardStatus[flipcardIndex[1]] = false; //changes status back to unflipped
    }
  }
    
  /**
   * Converts the state of the board to a string representation
   * @return s the string that represents the board of the game
   */
  public String boardToString() {

    String s = ""; //initializes an empty string

    //for loop to go through each value of CardValues
    for (int i = 0; i < n; i++) {
      //to check if the card is flipped or not
      if (cardStatus[i]==true) {
        s += CardValues[i] + " (" + i + ")  "; //adds the card value to string
      }
      else { //if not flipped yet
        s += "X (" + i + ") "; //adds an X to the string
      }

      //to add a | between each value
      if (i % 4 != 3) {
        s += "|  ";
      }
      //to add a new line after every 4 values
      if (i % 4 == 3) {
        s += "\n";
      }
    }
    return s; //returns the string s
  }
}