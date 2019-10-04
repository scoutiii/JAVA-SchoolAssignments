import java.util.Scanner;

/**
 * Class which solves slide puzzles using either A* or brute force.
 * @author Scout Jarman
 */
class SliderGame {
    /**
     * Variables to keeps track of timeOut, and to get input.
     */
    private int timeOutSeconds = 15;
    private Scanner in = new Scanner(System.in);

    /**
     * This function doesn't work for some reason, I don't know why.
     * It is supposed to just change the timeOutSeconds, but it just doesn't.
     * Complexity: O(1) lol
     * @param seconds what you want to the time out to be.
     */
    void setTimeOutSeconds(int seconds) {
        System.out.println("Old timeOut: " + this.timeOutSeconds);
        this.timeOutSeconds = seconds;
        System.out.println("New timeOut: " + this.timeOutSeconds);
    }

    /**
     * This is where all the logic actually happens, using an A* with AVL tree.
     * Complexity: O(log(n)) I am actually not sure, n for number of moves needed to solve?
     * @param b the board to be solved.
     */
    private void aStarSolve(GameState b) {
        System.out.println("********** A* start **********");
        long timeStart = System.currentTimeMillis();
        // String which holds all the moves
        String moves = "RULD";

        // A board which is complete which we will compare against
        GameState complete = new GameState();
        complete.makeBoard(0);
        System.out.println("Starting algorithm...");

        // List (or queue) which will hold all the possible boards
        AVLTree<GameState> queue = new AVLTree<>(b);
        boolean solutionFound = queue.findMin().equals(complete);
        GameState Solution = new GameState();
        if(solutionFound) {
            Solution = queue.findMin();
        }

        // Checks if the head is solved, continues if not
        while(!solutionFound && !queue.findMin().equals(complete)) {
            GameState next = new GameState(queue.findMin());
            queue.deleteMin();
            // Goes through each possible move, adding to queue if valid
            for(int i = 0; i < moves.length() && !solutionFound; i++) {
                if(next.checkMoves(moves.charAt(i), next.getLastMove()) != ' ') {
                    GameState newGameState = new GameState(next);
                    newGameState.makeMove(moves.charAt(i), newGameState.getLastMove());
                    queue.insert(newGameState);
                    // This checks if most recent one is correct, saves on time
                    if(newGameState.equals(complete)) {
                        solutionFound = true;
                        Solution = newGameState;
                    }
                }
            }
            //Time out condition, stops after the predefined timeout period
            if((System.currentTimeMillis() - timeStart) > this.timeOutSeconds*1000) {
                break;
            }
        }

        // This is only reached when the puzzle is effectively unsolvable
        if(!solutionFound) {
            System.out.println("This algorithm times out at " + this.timeOutSeconds + " seconds");
            System.out.println("TIMEOUT: puzzle took too long, probably unsolvable");
            System.out.println("\n\n********** A* end **********\n\n");
            return;
        }

        // Prints out all the information
        float totalTime = System.currentTimeMillis() - timeStart;
        System.out.println("Here is the solution...");
        String movesList = Solution.getPrevMoves();
        Solution.showMe(b, movesList);
        System.out.println("Moves required : " + movesList.length());
        System.out.println("Queue   added  : " + queue.getRunningLength());
        System.out.println("Queue   Removed: " + (queue.getRunningLength() - queue.getLength()));
        System.out.println("Queue   Size   : " + queue.getLength());
        System.out.println("Time in milliS : " + totalTime);
        System.out.println("\n\n********** A* end **********\n\n");
    }

    /**
     * This solves the board using brute force with a linked list.
     * Complexity: O(log(n)) I am actually not sure, n for number of moves needed to solve?
     * @param b the board to be solved.
     */
    private void bruteForceSolve(GameState b) {
        System.out.println("********** Brute Force start **********");
        long timeStart = System.currentTimeMillis();
        // String which holds all the moves
        String moves = "RULD";

        // A board which is complete which we will compare against
        GameState complete = new GameState();
        complete.makeBoard(0);
        System.out.println("Starting algorithm...");

        // List (or queue) which will hold all the possible boards
        linkedList<GameState> queue = new linkedList<>(b);
        boolean solutionFound = queue.getHead().equals(complete);
        GameState Solution = new GameState();
        if(solutionFound) {
            Solution = queue.getHead();
        }

        // Checks if the head is solved, continues if not
        while(!queue.getHead().equals(complete) && !solutionFound) {
            GameState next = new GameState(queue.getHead());
            // Goes through each possible move, adding to queue if valid
            for(int i = 0; i < moves.length() && !solutionFound; i++) {
                if(next.checkMoves(moves.charAt(i), next.getLastMove()) != ' ') {
                    GameState newGameState = new GameState(next);
                    newGameState.makeMove(moves.charAt(i), newGameState.getLastMove());
                    queue.insert(newGameState);
                    // This checks if most recent one is correct, saves on time
                    if(newGameState.equals(complete)) {
                        solutionFound = true;
                        Solution = newGameState;
                    }
                }
            }
            queue.removeFront();
            //Time out condition, stops after the predefined timeout period
            if((System.currentTimeMillis() - timeStart) > this.timeOutSeconds*1000) {
                break;
            }
        }

        // This is only reached when the puzzle is effectively unsolvable
        if(!solutionFound) {
            System.out.println("This algorithm times out at " + this.timeOutSeconds + " seconds");
            System.out.println("TIMEOUT: puzzle took too long, probably unsolvable");
            System.out.println("\n\n********** Brute Force end **********\n\n");
            return;
        }

        // Prints out all the information
        float totalTime = System.currentTimeMillis() - timeStart;
        System.out.println("Here is the solution...");
        String movesList = Solution.getPrevMoves();
        Solution.showMe(b, movesList);
        System.out.println("Moves required : " + movesList.length());
        System.out.println("Queue   added  : " + queue.getRunningLength());
        System.out.println("Queue   Removed: " + (queue.getRunningLength() - queue.getLength()));
        System.out.println("Queue   Size   : " + queue.getLength());
        System.out.println("Time in milliS : " + totalTime);
        System.out.println("\n\n********** Brute Force end **********\n\n");
    }

    /**
     * This function will just take a pre made board and solve it.
     * Asks user if they want to use A* or brute force, or both.
     * Complexity: O(1) lol
     * @param label the given label to print.
     * @param b the board to be solved.
     */
    void playGiven(String label, GameState b){
        System.out.println(label + "\n"+  b);

        System.out.println("Do you want to A*: y for yes");
        String aStarInput = in.nextLine();
        while(aStarInput.length() <= 0) {
            System.out.println("actually enter somethings mofo: ");
            aStarInput = in.nextLine();
        }

        System.out.println("Do you want to use brute force: y for yes");
        String bruteForceInput = in.nextLine();
        while(bruteForceInput.length() <= 0) {
            System.out.println("Nice try but plz enter something: ");
            bruteForceInput = in.nextLine();
        }

        if(aStarInput.toLowerCase().charAt(0) == 'y') {
            aStarSolve(b);
        }
        if(bruteForceInput.toLowerCase().charAt(0) == 'y') {
            bruteForceSolve(b);
        }
    }

    /**
     * Like the one above, but takes a jumble count which will scramble a board and solve that.
     * Asks user if they want to use A*, brute force, or both.
     * Complexity: O(1) lol
     * @param label the label printed at the beginning.
     * @param jumbleCount the number of random moves to make.
     */
    void playRandom(String label, int jumbleCount){
        System.out.println("Starting to jumble the board " + jumbleCount + " times...");
        GameState b = new GameState();
        b.makeBoard(jumbleCount);

        System.out.println(label + "\n"+  b);

        System.out.println("Do you want to use the AVL tree: y for yes");
        String aStarInput = in.nextLine();
        while(aStarInput.length() <= 0) {
            System.out.println("actually enter somethings mofo: ");
            aStarInput = in.nextLine();
        }

        System.out.println("Do you want to use the linked list: y for yes");
        String bruteForceInput = in.nextLine();
        while(bruteForceInput.length() <= 0) {
            System.out.println("Nice try but plz enter something: ");
            bruteForceInput = in.nextLine();
        }


        if(aStarInput.toLowerCase().charAt(0) == 'y') {
            aStarSolve(b);
        }
        if(bruteForceInput.toLowerCase().charAt(0) == 'y') {
            bruteForceSolve(b);
        }


    }

    /**
     * This is the old main, reused as an example for the user in UserInterface.
     * Complexity: O(1) lol
     */
    void example() {
        SliderGame g = new SliderGame();
        Scanner in = new Scanner(System.in);

        int [] game0 = { 1, 2, 3, 7, 4, 0, 6, 5, 8 };
        GameState b = new GameState();
        b.makeBoard(game0);
        g.playGiven("game 0", b);
        System.out.println("Click any key to continue\n");
        String resp;
        in.nextLine();

        int []game1 = { 1, 3, 2, 4, 5, 6, 8, 7, 0 };
        b.makeBoard(game1);
        g.playGiven("game 1", b);
        System.out.println("Click any key to continue\n");
        in.nextLine();

        int []game2 = { 1, 3, 8, 6, 2, 0, 5, 4, 7 };
        b.makeBoard(game2);
        g.playGiven("game 2", b);
        System.out.println("Click any key to continue\n");
        in.nextLine();

        int []game3 = { 4, 0, 1, 3, 5, 2, 6, 8, 7 };
        b.makeBoard(game3);
        g.playGiven("game 3", b);
        System.out.println("Click any key to continue\n");
        in.nextLine();

        // Warning slow to solve
        int []game4 = { 7, 6, 4, 0, 8, 1, 2, 3, 5 };
        b.makeBoard(game4);
        g.playGiven("game 4, 'slow' to solve", b);
        System.out.println("Click any key to continue\n");
        in.nextLine();

        // Warning unsolvable
        int []game5 = { 1, 2, 3, 4, 5, 6, 8, 7, 0 };
        b.makeBoard(game5);
        g.playGiven("game 5, unsolvable", b);
        System.out.println("Click any key to continue\n");
        in.nextLine();

        boolean playAgain = true;

        // how much jumbling to do in random board
        int JUMBLECT = 4;
        while (playAgain)
        {
            g.playRandom("Random GameState", JUMBLECT);

            System.out.println("Play Again?  Answer Y for yes\n");
            resp= in.nextLine().toUpperCase();
            while(resp.length() < 1) {
                System.out.println("Enter something");
                resp = in.nextLine().toUpperCase();
            }
            playAgain = (resp.charAt(0) == 'Y');
        }
    }
}
