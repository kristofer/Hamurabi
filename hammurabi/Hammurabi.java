package hammurabi;               // package declaration 
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

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

    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        // statements go after the declarations
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
}