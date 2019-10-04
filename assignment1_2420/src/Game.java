import java.util.Scanner;

class Game {
    private int timeOutSeconds = 15;

    void setTimeOutSeconds(int seconds) {
        System.out.println("Old timeOut: " + this.timeOutSeconds);
        this.timeOutSeconds = seconds;
        System.out.println("New timeOut: " + this.timeOutSeconds);
    }

    // This is where all the logic actually happens
    private void solve(Board b) {
        long timeStart = System.currentTimeMillis();

        // A board which is complete which we will compare against
        Board complete = new Board();
        complete.makeBoard(0);
        System.out.println("Starting algorithm...");

        // String which holds all the moves
        String moves = "RULD";

        // List (or queue) which will hold all the possible boards
        linkedList<Board> queue = new linkedList<>(b);
        boolean solutionFound = queue.getHead().equals(complete);
        Board Solution = new Board();
        if(solutionFound) {
           Solution = queue.getHead();
        }

        // Checks if the head is solved, continues if not
        while(!queue.getHead().equals(complete) && !solutionFound) {
            Board next = new Board(queue.getHead());
            // Goes through each possible move, adding to queue if valid
            for(int i = 0; i < moves.length() && !solutionFound; i++) {
                if(next.checkMoves(moves.charAt(i), next.getLastMove()) != ' ') {
                    Board newBoard = new Board(next);
                    newBoard.makeMove(moves.charAt(i), newBoard.getLastMove());
                    queue.insert(newBoard);
                    // This checks if most recent one is correct, saves on time
                    if(newBoard.equals(complete)) {
                        solutionFound = true;
                        Solution = newBoard;
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
    }

    void playGiven(String label, Board b){
        System.out.println(label + "\n"+  b);
        solve(b);
    }

    void playRandom( String label, int jumbleCount){
        System.out.println("Starting to jumble the board " + jumbleCount + " times...");
        Board b = new Board();
        b.makeBoard(jumbleCount);
        System.out.println(label + "\n" + b);
        solve(b);
    }

    // This is the old main, reused as an example for the user in UserInterface
    void example() {
        Game g = new Game();
        Scanner in = new Scanner(System.in);

        int [] game0 = { 1, 2, 3, 7, 4, 0, 6, 5, 8 };
        Board b = new Board();
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
            g.playRandom("Random Board", JUMBLECT);

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
