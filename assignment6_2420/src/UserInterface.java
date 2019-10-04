import java.util.Scanner;

/**
 * A user interface to facilitate using the percolation stuff.
 * @author Scout Jarman
 */
public class UserInterface {
    /**
     * Variable which hold current size of board, and input scanner.
     */
    private int size;
    private Scanner input = new Scanner(System.in);

    /**
     * Default constructor which sets the size to 20.
     */
    private UserInterface() {
        this.size = 20;
        System.out.println("Default board size = " + this.size);
    }

    /**
     * Functions which changes the board size.
     */
    private void option1_setSize() {
        System.out.println("\nCurrent size = " + this.size);
        System.out.println("Enter a positive integer: ");
        int newSize = getUserInput();
        while(newSize <= 0) {
            System.out.println("Enter a number greater than 0: ");
            newSize = getUserInput();
        }
        this.size = newSize;
        System.out.println("New size = " + this.size + "\n\n");
    }

    /**
     * Runs a random percolation of board of current size.
     */
    private void option2_percolate() {
        System.out.println("Percolating board of size " + this.size + "x" + this.size + "\n\n");
        Percolation board = new Percolation(this.size, ("Size: " + this.size + "x" + this.size));
        board.randomGeneration(true);
        System.out.println();
    }

    /**
     * Prints out the options menu.
     */
    private void option3_menu() {
        System.out.println("\n1: set/change board size");
        System.out.println("2: run random percolation");
        System.out.println("3: print these options");
        System.out.println("4: runs demonstration of union find");
        System.out.println("5: runs demonstration of percolation");
        System.out.println("6: take average percolation rate");
        System.out.println("7: quit\n\n");
    }

    /**
     * Function which prints out the average percolation threshold for how ever many trials they want to check.
     */
    private void option6_average() {
        System.out.println("Enter the number of percolation you want to average over: ");
        int num = getUserInput();
        while (num <= 0) {
            System.out.println("Do a positive number: ");
            num = getUserInput();
        }
        System.out.println("Calculating...");
        int total = 0;
        for(int i = 0; i < num; i++) {
            Percolation board = new Percolation(this.size, ("Size: " + this.size + "x" + this.size));
            total += board.randomGeneration(false);
        }
        System.out.println("The average percolation threshold for " + num + " random boards is " + ((total * 1.0)/num));
        System.out.println("That is a percent of " + (100 * (total * 1.0)/num) / (this.size*this.size) + "\n");
    }

    /**
     * Function to get an integer from the user.
     * @return the users integer.
     */
    private int getUserInput() {
        System.out.print("\nInput (not Enter key): ");
        while(!input.hasNextInt()) {
            System.out.println("Please enter an integer.");
            input.nextLine();
        }
        return input.nextInt();
    }

    /**
     * Main function which allows user to change board size and run random percolation.
     * @param args are not used.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the percolation program!\n\n");
        UserInterface UI = new UserInterface();

        int input = 3;
        while(input != 7) {
            UI.option3_menu();
            input = UI.getUserInput();

            switch (input) {
                case 1: {
                    UI.option1_setSize();
                    break;
                }
                case 2: {
                    UI.option2_percolate();
                    break;
                }
//                case 3: {
//                    UI.option3_menu();
//                    break;
//                }
                case 4: {
                    UnionFind test = new UnionFind(5, "test");
                    test.oldMain();
                    break;
                }
                case 5: {
                    Percolation test = new Percolation(5, "test");
                    test.oldMain();
                    break;
                }
                case 6: {
                    UI.option6_average();
                    break;
                }
            }
        }
        System.out.println("\nThanks for using this program plz give gud grade (cool unicode ->) \u2620 \u2622");
    }
}
