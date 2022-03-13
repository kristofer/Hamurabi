package src.main;

import java.util.InputMismatchException;
import java.util.Random; // imports go here
import java.util.Scanner;

import static java.lang.System.exit;

public class Hammurabi { // must save in a file named Hammurabi.java
    Random rand = new Random(); // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    String newLine = System.getProperty("line.separator");

    // Game Variables
    /*
     * 100 people
     * 2800 bushels of grain in storage
     * 1000 acres of land
     * Land value is 19 bushels/acre
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

        System.out.println("\t\t\t\tHammurabi\n\t" +
                "Welcome, New Magnificence!\nLet us all hope you are better ruler than The Former Knucklehead!");

        while (currentYear < yearsToReign) {
            printSummary();

            // Ask the user for 4 input parameters.
            {
                landToBuy = askHowManyAcresToBuy(landValue, grainStored);
                landHeld += landToBuy;
                grainStored -= (landValue + landToBuy);
            }

            if (landToBuy == 0) {
                landToSell = askHowManyAcresToSell(landHeld);
                landHeld -= landToSell;
                grainStored += (landValue + landToSell);
            }

            {
                bushelsFedToPeople = askHowMuchGrainToFeedPeople(grainStored);
                grainStored -= bushelsFedToPeople;
            }

            acresToPlant = askHowManyAcresToPlant(landHeld, people, grainStored);

            // calculate the game vars.
            {
                plague = plagueDeaths(people);
                people -= plague;
            }

            {
                starved = starvationDeaths(people, bushelsFedToPeople);
                people -= starved;
            }
            if (uprising(people, starved)) {
                System.out.println("\n\nYour Great Ridiculessness!\nThere has been a terrible uprising;"
                        + "\nYou have been deposed as ruler. Run!\n");
                fail(false);
                break;
            }

            if (starved <= 0) {
                immigrants = immigrants(people, landHeld, grainStored);
                people += immigrants;
            }

            {
                bushelsPerAcre = harvestYield();

                newGrain = harvest(acresToPlant);
                grainStored += newGrain;

                grainDestroyed = grainEatenByRats(grainStored);
                grainStored -= grainDestroyed;
            }
            landValue = newCostOfLand();
            currentYear++;
        }
        finalSummary();
    }

    void printSummary() {
        String summaryTemplate = "\n\n    O great Hammurabi! I beg to report to your majesty:" + newLine
                + "You are in year %d of your ten year rule." + newLine
                + "In the previous year %d people starved to death." + newLine
                + "In the previous year %d people entered the kingdom." + newLine
                + "The population is now %d." + newLine
                + "We harvested %d bushels at %d bushels per acre." + newLine
                + "Rats destroyed %d bushels, leaving %d bushels in storage." + newLine
                + "The city owns %d acres of land." + newLine
                + "Land is currently worth %d bushels per acre." + newLine;
        System.out.printf(summaryTemplate, currentYear,
                starved, immigrants, people, newGrain, bushelsPerAcre,
                grainDestroyed, grainStored, landHeld, landValue);
    }

    void fail(boolean iQuit) {
        if (iQuit) {
            System.out.println(
                    "Hammurabi! Your humble servant can no longer be you Grand Vizier! I QUIT You miserable ruler!");
            exit(1);
        } else {
            System.out.println(
                    "\n\nTraitor! You are banished by our people;\nMay the fleas of a thousand camels infest your blanket!");
        }
    }

    void finalSummary() {
        printSummary();
        System.out.println("You're done.");
    }

    public int plagueDeaths(int population) {
        if (rand.nextInt(100) < 15) {
            return population / 2;
        }
        return 0;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) {
        int fedpeople = (int) Math.floor(bushelsFedToPeople / 20.0);
        return (fedpeople > population) ? population : population - fedpeople;
    }

    public boolean uprising(int population, int howManyPeopleStarved) {
        if (howManyPeopleStarved / (population / 1.0) > 0.45) {
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
            int eatenBushels = (int) Math.floor((bushels / 1.0 * eatenPct));
            return eatenPct;
        }
        return 0;
    }

    public int newCostOfLand() {
        int min = 17;
        int max = 23;
        return rand.nextInt(max - min + 1) + min;
    }

    // other methods go here
    int askHowManyAcresToBuy(int price, int bushels) {
        int toBuy = 0;
        do {
            toBuy = getNumber("How many acres do you wish to buy? ");
            if (toBuy < 0)
                fail(true);
            if (toBuy * price > bushels)
                System.out.println("O great Hammurabi:  I beg you to reconsider, You only have\n" +
                        bushels + " bushels of grain. So I ask you again, your Enormousness, ");
        } while (toBuy * price > bushels);
        return toBuy;
    }

    int askHowManyAcresToSell(int acresOwned) {
        int toSell = 0;
        do {
            toSell = getNumber("How many acres do you wish to sell? ");

            if (toSell < 0)
                fail(true);
            if (toSell > acresOwned)
                System.out.println("O great Hammurabi: Math is clearly not your strength, you only have "
                        + acresOwned + " acres. So your Gracious Mathlessness, pray inform me, ");
        } while (toSell > acresOwned);

        return toSell;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        int toFeed = 0;
        do {
            toFeed = getNumber("Your people are hungry!\n" +
                    " How many bushels do you wish to feed them? ");
            if (toFeed < 0)
                fail(true);
            if (toFeed > bushels)
                System.out.println("O great Hammurabi: Thank the gods you have me " +
                        "to do your math for your Most Cluelessness\n" +
                        "I am clearly unable to understand your thinking. You only have " +
                        bushels + " bushels of grain, So I beg Your Obtuseness");
        } while (toFeed > bushels);
        return toFeed;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        int toPlant;
        do {
            toPlant = getNumber("A great ruler such as Your Worshipfuleness must think to the future!\n" +
                    " How many acres do you wish to plant with seed? ");
            if (toPlant < 0)
                fail(true);
            if (toPlant > acresOwned)
                System.out.println("Yo Not-so-Smart:  THINK AGAIN. You only have " + acresOwned
                        + " acres. So yeah, I'm gonna ask again,");
            if (toPlant / 2 > bushels)
                System.out.println("Your Forgetfulness:  Reconsider, You only have\n" +
                        bushels + " bushels of grain. So while my patience is wearing thin,");
            if (toPlant > population * 10)
                System.out.println("Ack! No Your Confusedness, You only have" + population
                        + "people to tend the fields. So whith great humility, I pray you to tell me,");
        } while (toPlant > acresOwned || toPlant / 2 > bushels || toPlant > population * 10);

        return toPlant;
    }

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
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

}