package src.main.java.hammurabi;               // package declaration
import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    String newLine = System.getProperty("line.separator");

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
    int bushelsFedToPeople = 0;
    int acresToPlant = 0;

    int bushelsPerAcre = 0;
    int newGrain = 0;
    int immigrants = 0;
    int starved = 0;
    int plague = 0;
    int grainDestroyed = 0;
    
    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {

        while (currentYear < yearsToReign) {
            printSummary();

            landToBuy = askHowManyAcresToBuy(landValue, grainStored);
            if (landToBuy == 0) {
                landToSell = askHowManyAcresToSell(landHeld);
            }
            bushelsFedToPeople = askHowMuchGrainToFeedPeople(grainStored);
            acresToPlant = askHowManyAcresToPlant(landHeld, people, grainStored);

            // calculate the game vars.
            plague = plagueDeaths(people);
            starved =  starvationDeaths(people, bushelsFedToPeople);
            if (uprising(people, starved)) {
                break;
            }
            if (starved <= 0) {
                immigrants = immigrants(people, landHeld, grainStored);
            }
            bushelsPerAcre = harvestYield();
            
            newGrain = harvest(acresToPlant);
            grainDestroyed = grainEatenByRats(grainStored);

            landValue = newCostOfLand();
            currentYear++;
        }
        finalSummary();
    }


    void printSummary() {
        String summaryTemplate = "    O great Hammurabi!" + newLine
        + "You are in year %d of your ten year rule." + newLine
        + "In the previous year %d people starved to death." + newLine
        + "In the previous year %d people entered the kingdom." + newLine
        + "The population is now %d." + newLine
        + "We harvested %d bushels at %d bushels per acre." + newLine
        + "Rats destroyed %d bushels, leaving %d bushels in storage." + newLine
        + "The city owns %d acres of land." + newLine
        + "Land is currently worth %d bushels per acre."+ newLine;
        System.out.printf(summaryTemplate, currentYear,
        starved, immigrants, people, newGrain, bushelsPerAcre,
        grainDestroyed, grainStored, landHeld, landValue );
    }

    void finalSummary() {
        System.out.println("You're done.");
    }

    public int plagueDeaths(int population) {
        if (rand.nextInt(100) < 15) {
            return population/2;
        }
        return 0;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) {
        int fedpeople = (int) Math.floor(bushelsFedToPeople/20.0);
        return (fedpeople > population) ? population : population - fedpeople ;
    }

    public boolean uprising(int population, int howManyPeopleStarved) {
        if (howManyPeopleStarved/(population/1.0) > 0.45) {
            return true;
        }
        return false;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
        return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
    }

    private int harvestYield() {
        int min = 1;
        int max = 6;
        return rand.nextInt(max - min + 1) + min;
    }

    int bushelsUsedAsSeed = 0;
    public int harvest(int acres) {
        int yield = harvestYield();

        return acres * yield;
    }

    public int grainEatenByRats(int bushels) {

        if (rand.nextInt(100) < 40) {
            int eatenPct = rand.nextInt(30 - 10 + 1) + 10;
            int eatenBushels = (int) Math.floor((bushels/1.0 * eatenPct));
            return eatenPct;
        }
        return 0;
    }

    public int newCostOfLand() {
        int min = 17;
        int max = 23;
        return rand.nextInt(max - min + 1) + min;
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
    

}