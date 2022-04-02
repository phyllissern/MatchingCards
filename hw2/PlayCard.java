package hw2;
import java.util.Scanner;
import java.util.Random;

public class PlayCard {
  
  /**
   * Plays the game by flipping random cards
   * @param g the game
   * @return the total number of flips
   */
  public  static  int  playRandom(MatchCardGame g){

    Random r = new Random(); //creates a random object

    //while loop to play the game as long as it is not game over yet
    while(!g.gameOver()) {

      int rand = r.nextInt(g.n); //generates a random number
      g.flip(rand); //flips the card from the randomly generated number

      //if statement to check if there was a match
      if (g.getFlips() % 2 == 0 && g.wasMatch() == false) {
        g.flipMismatch(); //if there is no match, cards are flipped back down
      }
    }
    return g.getFlips(); //returns the number of flips
  }

  /**
   * Plays the game with perfect memory
   * @param g the game
   * @return the total number of flips
   */
  public  static  int  playGood(MatchCardGame g){

    char[] boardValues = new char[g.n]; //an array to keep track of board values flipped
    Random r = new Random(); //creates a random object

    //while loop to play the game as long as it is not game over yet
    while(!g.gameOver()) {

      int rand = r.nextInt(g.n); //generates a random number
      g.flip(rand); //flips the card from the randomly generated number
      boardValues[rand] = g.previousFlipIdentity(); //saves the previous flipped card in boardValues
      
      //if statement to check if there is an even number of flips
      if (g.getFlips() % 2 == 0) {
        //nested for loop to compare the values inside the array
        for (int i = 0; i < boardValues.length; i++) {
          for (int j = i + 1; j < boardValues.length; j++) {
            //if statement to make sure the value isn't null
            if ((int)boardValues[i]!=0) {
              //if there is a match between the previous flips
              if (boardValues[i] == boardValues[j]) {
                g.flip(i); //flips the card
                g.flip(j); //flips the card
              }
            }
          }
        }
      }
      else { //if there is not an even number of flips
        //for loop to check if there is a match from previous flips
        for (int i = 0; i < boardValues.length; i++) {
          //if statement to check if there is a previous match
          if (boardValues[i] == boardValues[rand]) {
            g.flip(i); //flips the card if there is
          }
        }
      }

      //if statement to check if there was a match
      if (g.getFlips() % 2 == 0 && g.wasMatch() == false) {
        g.flipMismatch(); //if there is no match, cards are flipped back down
      }
    }
    return g.getFlips(); //returns the number of flips
  }
  
  
  // Monte Carlo Method
  /**
   * Plays the shuffled MatchCardGames of size 32 a total of N times using playRandom
   * @param N the amount of times to play the game
   * @return average, the average number of flips to complete the games
   */
  public  static  double  randomMC(int N) {

    MatchCardGame g = new MatchCardGame(32); //creates the game with size 32
    g.shuffleCards(); //shuffles the cards
    int scores = 0; //initializes the scores of the games
    int average; //initializes the variable average

    //for loop to go through N times of playing the game
    for (int i = 0; i < N; i++) {
      scores += playRandom(g); //adds the amount of flips to score every time
    }

    average = scores / N; //calculates the average

    return average; //returns the average
  }

  /**
   * Plays the shuffled MatchCardGames of size 32 a total of N times using playGood
   * @param N the amount of times to play the game
   * @return average, the average number of flips to complete the games
   */
  public  static  double  goodMC(int N){

    MatchCardGame g = new MatchCardGame(32); //creates the game with size 32
    g.shuffleCards(); //shuffles the cards
    int scores = 0; //initializes the scores of the games
    int average; //initializes the variable average

    //for loop to go through N times of playing the game
    for (int i = 0; i < N; i++) {
      scores += playGood(g); //adds the amount of flips to score every time
    }

    average = scores / N; //calculates the average

    return average; //returns the average
  }
  

  public static void main(String[] args) {
    //set up reader to take inputs
    java.util.Scanner reader = new java.util.Scanner (System.in);
    
    int n = 16; //game size
    MatchCardGame g1 = new MatchCardGame(n);
    g1.shuffleCards();
    
    while(!g1.gameOver()) {
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      System.out.println("Which card to play?");
      while(!g1.flip(reader.nextInt())) {}
      
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      while(!g1.flip(reader.nextInt())) {} //flip card 15
      // previousflipidentity -> carvalues[15]
      //say whether the 2 cards were a match
      if(g1.wasMatch()) {
        System.out.println("Was a match!");
      } else {
        //print board to show mismatched cards
        System.out.println(g1.boardToString());		
        System.out.println("Was not a match.");
        //flip back the mismatched cards
        g1.flipMismatch();
      }
    }
    reader.close();

    //Report the score
    System.out.println("The game took " + g1.getFlips() + " flips.");
    
    //Using the AIs
    //int count;
    //MatchCardGame g2 = new MatchCardGame(n);
    //g2.shuffleCards();
    //count = playRandom(g2);
    //System.out.println("The bad AI took " + count + " flips.");
    //MatchCardGame g3 = new MatchCardGame(n);
    //g3.shuffleCards();
    //count = playGood(g3);
    //System.out.println("The good AI took " + count + " flips.");
    
    //Using MCs
    //int N = 1000;
    //System.out.println("The bad AI took " + randomMC(N) + " flips on average.");
    //System.out.println("The good AI took " + goodMC(N) + " flips on average.");
  }
}