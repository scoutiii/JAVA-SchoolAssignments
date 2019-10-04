import java.util.Scanner;

/**
 * This class is the user interface.
 * It takes all the code in the other classes, and uses it.
 * @author Scout Jarman
 */
public class UserInterface {
    /**
     * This is where the magic happens, this is what should be run.
     * @param args  is never used from what I can tell, so lol.
     */
    public static void main(String[] args) {
        // Initialize some things
        Scanner in = new Scanner(System.in);
        SliderGame g = new SliderGame();

        // A little welcome message, and defaults the input to E, no particular reason for E
        System.out.println("Welcome to the slide puzzle solver!");
        printHelp();
        char input = 'E';

        // Starts the main loop which revolves around the user using Q to quit
        while(input != 'Q') {
            // Gets the users input
            System.out.print("Enter option: ");
            String temp = in.nextLine().toUpperCase();

            // This stops null pointer exceptions
            while(temp.length() < 1 || temp.length() > 1) {
                System.out.println("Invalid input, try again");
                temp = in.nextLine().toUpperCase();
            }
            input = temp.charAt(0);

            // Validates it, and keeps trying until it get a good response
            while(input != 'E' && input != 'C' && input != 'J' && input != 'H' && input != 'Q' && input!= 'T') {
                System.out.print("Invalid option, try again: ");
                String tempi = in.nextLine().toUpperCase();
                // This stops null pointer exceptions
                while(tempi.length() < 1 || tempi.length() > 1) {
                    System.out.println("Invalid input, try again");
                    tempi = in.nextLine();
                }
                input = tempi.charAt(0);
            }

            // Does what the user asks...
            switch (input) {
                case 'E':
                    System.out.println("Starting examples...");
                    g.example();
                    break;
                case 'C':
                    System.out.println("Starting custom...");
                    createCustom(in, g);
                    break;
                case 'J':
                    System.out.println("Starting jumble...");
                    createJumble(in, g);
                    break;

                case 'H':
                    System.out.println("Starting help...");
                    printHelp();
                    break;
                case 'T':
                    System.out.println("Starting timeOut...");
                    timeOut(in, g);
                    break;
                case 'Q':
                    System.out.println("Thanks for observing, plz give a gud grade.");
            }
        }
    }

    /**
     * This function allows the user to adjust the timeout length
     * @param in the scanner to get user input (mostly for validation).
     * @param g the game object which is able to solve the puzzles.
     */
    private static void timeOut(Scanner in, SliderGame g) {
        System.out.println("For what ever reason this function doesn't actually work...");
        System.out.println("Enter a positive number to set as the new time out limit: ");
        String input = in.nextLine();

        while(input.length() < 1) {
            System.out.println("You entered nothing, enter a number:");
            input = in.nextLine();
        }

        int result = checkForNumber(input);
        while(result < 0) {
            System.out.println("Enter a positive number: ");
            input = in.nextLine();
            result = checkForNumber(input);
        }
        g.setTimeOutSeconds(result);
    }

    /**
     * This just has some statements which tells the user what the commands are.
     */
    private static void printHelp() {
        System.out.println("The algorithm used can solve any puzzle in less than 10 seconds or quicker!");
        System.out.println("If it takes longer than 15 seconds, the puzzle is likely unsolvable.");
        System.out.println("If you would like to run some pre made examples, enter 'E'.");
        System.out.println("For entering in your own list of numbers, enter 'C'.");
        System.out.println("To make a randomized board, enter 'J'.");
        System.out.println("To change the timeOut length (how long before quiting a puzzle) enter 'T'");
        System.out.println("For help, enter 'H', to quit enter 'Q'.");
    }

    /**
     * Starts a custom puzzle, given by the user
     * @param in the scanner to get user input, data validation is included.
     * @param g the game object which holds all the code to solve the puzzle.
     */
    private static void createCustom(Scanner in, SliderGame g) {
        System.out.println("Enter in a 9 digit long sequence of numbers one at a time, enter after each");
        int[] sequence = new int[9];

        for(int i = 0; i < 9; i++) {
            System.out.print(i + ": ");
            String input = in.nextLine();

            // This stops null pointer exceptions
            while (input.length() < 1 || input.length() > 1) {
                System.out.print("Please enter something valid: ");
                input = in.nextLine();
            }

            char inC = input.charAt(0);
            while(inC < '0' || inC > '9') {
                System.out.print("Invalid number:");
                String temp = in.nextLine();
                // This stops null pointer exceptions
                while (temp.length() < 1 || temp.length() > 1) {
                    System.out.print("Please enter something valid: ");
                    temp = in.nextLine();
                }
                inC = temp.charAt(0);
            }
            String inS = "" + inC;
            sequence[i] = Integer.parseInt(inS);
        }

        // Solves the given board
        GameState b = new GameState();
        b.makeBoard(sequence);
        g.playGiven("Here is your board", b);
        System.out.println("Enter anything to continue");
        in.nextLine();
    }

    /**
     * Starts the jumbled version of a puzzle
     * @param in the scanner to get user input, data validation is included.
     * @param g the game object which holds all the code to solve the puzzle.
     */
    private static void createJumble(Scanner in, SliderGame g) {
        System.out.print("Enter a number of slides to apply to the puzzle: ");
        String input = in.nextLine();

        // This stops null pointer exceptions
        while(input.length() < 1) {
            System.out.println("Invalid input, try again: 1 ");
            input = in.nextLine();
        }

        int result = checkForNumber(input);
        while(result < 0) {
            System.out.println("Invalid input, try again: 2");
            input = in.nextLine();
            result = checkForNumber(input);
        }

        g.playRandom("Here is your jumbled board", result);
        System.out.println("Enter anything to continue");
        in.nextLine();
    }

    /**
     * Provides some input validation for the jumble option.
     * @param input the input which is checked for numbers.
     * @return -1 if input is invalid, other wise returns the first digit.
     */
    private static int checkForNumber(String input) {
        int result = -1;

        // This stops null pointer exceptions
        if(input.length() < 1) {
            return result;
        }

        for(int i = 0; i < input.length(); i++) {
            char inC = input.charAt(i);
            if (inC < '0' || inC > '9') {
                return result;
            }
        }

        result = Integer.parseInt(input);
        return result;
    }
}