package hammurabi;               // package declaration 
import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    String newLine = System.getProperty("line.separator");

    
    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
    // Game Variables
    /*   100 people
    *   2800 bushels of grain in storage
    *   1000 acres of land
    *   Land value is 19 bushels/acre
    */
    int people = 100;
    int grainStored = 2800;
    int landHeld = 1000;
    int landValue = 19;
    int yearsToReign = 10;

    int currentYear = 0;
    int landToBuy = 0;
    int landToSell = 0;
    int feedGrain = 0;
    int acresToPlant = 0;

    while (currentYear < yearsToReign) {
        printSummary();

        landToBuy = askHowManyAcresToBuy(landValue, grainStored);
        if (landToBuy == 0) {
            landToSell = askHowManyAcresToSell(landHeld);
        }
        feedGrain = askHowMuchGrainToFeedPeople(grainStored);
        acresToPlant = askHowManyAcresToPlant(landHeld, people, grainStored);
    }
    }

    void printSummary() {
        // O great Hammurabi!
        // You are in year 1 of your ten year rule.
        // In the previous year 0 people starved to death.
        // In the previous year 5 people entered the kingdom.
        // The population is now 100.
        // We harvested 3000 bushels at 3 bushels per acre.
        // Rats destroyed 200 bushels, leaving 2800 bushels in storage.
        // The city owns 1000 acres of land.
        // Land is currently worth 19 bushels per acre.
    
    }

    void finalSummary() {

    }

    public int plagueDeaths(int i) {
        return 0;
    }

    public int starvationDeaths(int i, int j) {
        return 0;
    }

    public int immigrants(int i, int j, int k) {
        return 0;
    }

    public int harvest(int i) {
        return 0;
    }

    public int grainEatenByRats(int i) {
        return 0;
    }

    public int newCostOfLand() {
        return 0;
    }

    //other methods go here
    int askHowManyAcresToBuy(int price, int bushels) {return 0;}
    int askHowManyAcresToSell(int acresOwned) {return 0;}
    int askHowMuchGrainToFeedPeople(int bushels) {return 0;}
    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {return 0;}
    /**
     * Prints the given message (which should ask the user for some integral
     * quantity), and returns the number entered by the user. If the user's
     * response isn't an integer, the question is repeated until the user
     * does give a integer response.
     * 
     * @param message The request to present to the user.
     * @return The user's numeric response.
     */
    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
    
    String summaryTemplate() {
        return  "    O great Hammurabi!" + newLine
    + "You are in year %d of your ten year rule." + newLine
    + "In the previous year %d people starved to death." + newLine
    + "In the previous year %d people entered the kingdom." + newLine
    + "The population is now %d." + newLine
    + "We harvested %d bushels at %d bushels per acre." + newLine
    + "Rats destroyed %d bushels, leaving %d bushels in storage." + newLine
    + "The city owns %d acres of land." + newLine
    + "Land is currently worth %d bushels per acre.";
    }


}