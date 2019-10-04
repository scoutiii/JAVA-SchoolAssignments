import java.util.Random;

public class Board {
    // Constant which holds the size of the board
    private static final int SIZE = 3;
    // Values of board
    private int[][] board;
    // Row location of blank
    private int blankRow;
    // Column location of blank
    private int blankCol;
    // String which contains a list of previous moves
    private String prevMoves;

    // Default constructor
    Board() {
        this.board = new int[SIZE][SIZE];
        this.prevMoves = "";
    }

    // Copy constructor
    Board(Board b) {
        //System.out.println( "Just Copied Board\n" + b.toString() +"\n");
        board = new int[SIZE][];
        for (int i = 0; i < SIZE; i++)
            this.board[i] = b.board[i].clone();
        this.blankRow = b.blankRow;
        this.blankCol = b.blankCol;

        this.prevMoves = b.prevMoves;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(board[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // return true if board is identical to b
    boolean equals(Board b) {

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] != b.board[i][j]) return false;
        return true;

    }

    // Create a board by performing legal moves on a board
    // jumbleCt indicates the number of moves to make
    // if jumbleCt == 0, return the winning board
    void makeBoard(int jumbleCt) {
        int val = 1;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = val++;
        blankRow = SIZE - 1;
        blankCol = SIZE - 1;
        board[blankRow][blankCol] = 0;
        jumble(jumbleCt);
        deletePrevMoves();
    }

    // Create a board from a given set of values
    void makeBoard(int[] values) {
        int c = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (values[c] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
                board[i][j] = values[c++];
            }
    }

    // If possible, slides a tile up into the blank position.  Returns success of operation.
    private boolean slideUp()
    {
        if (blankRow == SIZE - 1) return false;
        board[blankRow][blankCol] = board[blankRow + 1][blankCol];
        board[blankRow + 1][blankCol] = 0;
        blankRow += 1;
        return true;
    }

    // If possible, slides a tile down into the blank position.  Returns success of operation.
    private boolean slideDown()
    {
        if (blankRow == 0) return false;
        board[blankRow][blankCol] = board[blankRow - 1][blankCol];
        board[blankRow - 1][blankCol] = 0;
        blankRow -= 1;
        return true;
    }

    // If possible, slides a tile left into the blank position.  Returns success of operation.
    private boolean slideLeft()
    {
        if (blankCol == SIZE - 1) return false;
        board[blankRow][blankCol] = board[blankRow][blankCol + 1];
        board[blankRow][blankCol + 1] = 0;
        blankCol += 1;
        return true;
    }

    // If possible, slides a tile right into the blank position.  Returns success of operation.
    private boolean slideRight()
    {
        if (blankCol == 0) return false;
        board[blankRow][blankCol] = board[blankRow][blankCol - 1];
        board[blankRow][blankCol - 1] = 0;
        blankCol -= 1;
        return true;
    }

    // Randomly apply ct moves to the board, making sure they are legal and don't undo the previous move
    private void jumble(int ct) {
        Random rand = new Random();
        // Moves representing Up, Down, Left, Right
        String moveStr = "RULD";
        char lastMove = ' ';
        char thisMove;
        for (int i = 0; i < ct; i++) {
            thisMove = ' ';
            while (thisMove == ' ') {
                thisMove = moveStr.charAt(rand.nextInt(4));
                thisMove = makeMove(thisMove, lastMove);
            }
            lastMove = thisMove;
            //System.out.println("JUMBLE Moves" + thisMove + '\n');
        }
    }

    // Make the move indicated by m (L Left, R Right, U Up, D Down) if it is legal and if it doesn't undo the move specified by lastmove
    // Return a blank if the move could not be made, otherwise return the move
    char makeMove(char m, char lastmove) {
        //System.out.println( "makeMove " + m + lastmove +"\n");
        boolean moved = false;
        switch (m) {
            case 'R':
                if (lastmove != 'L') {
                    moved = slideRight();
                }
                break;
            case 'L':
                if (lastmove != 'R') {
                    moved = slideLeft();
                }
                break;
            case 'D':
                if (lastmove != 'U') {
                    moved = slideDown();
                }
                break;
            case 'U':
                if (lastmove != 'D') {
                    moved = slideUp();
                }
                break;
        }
        if (!moved)
            return ' ';
        addToMoves(m);
        return m;
    }

    // show me method which takes a board and moves, and shows the outcomes of each
    void showMe(Board b, String moves) {
        // prints out title and original board
        System.out.println("Moves list: " + moves);
        Board temp = new Board(b);
        System.out.println("Original Board");
        System.out.println(b);

        // loops through given string, moving what it says
        for(int i = 0; i < moves.length(); i++) {
            System.out.println(moves.charAt(i) + "==>");
            // tells user if the move doesn't work, doesn't changes anything
            if (temp.makeMove(moves.charAt(i), temp.getLastMove()) == ' ') {
                System.out.println("This is an illegal move, no changes made.");
            }
            System.out.print(temp);
        }
    }

    // Takes a string (just a char) and adds it to the end of previous moves
    private void addToMoves(char move) {
        this.prevMoves += move;
    }

    // To make the jumble thing easier
    private void deletePrevMoves() {
        this.prevMoves = "";
    }

    // Returns the moves list
    String getPrevMoves() {
        return this.prevMoves;
    }

    // Returns the last move made
    char getLastMove() {
        int length = this.prevMoves.length();
        if (length > 0) {
            return prevMoves.charAt(length - 1);
        }
        return ' ';
    }

    // Like make move, but just checks to see if the move is legal
    char checkMoves(char curr, char last) {
        boolean moved = false;
        switch (curr) {
            case 'R':
                if (last != 'L') {
                    if (blankCol != 0) {
                        moved = true;
                    }
                }
                break;
            case 'L':
                if (last != 'R') {
                    if (blankCol != SIZE-1) {
                        moved = true;
                    }
                }
                break;
            case 'U':
                if (last != 'D') {
                    if (blankRow != SIZE - 1) {
                        moved = true;
                    }
                }
                break;
            case 'D':
                if (last != 'U') {
                    if (blankRow != 0) {
                        moved = true;
                    }
                }
                break;
        }
        if (!moved) {
            return ' ';
        }
        return curr;
    }
    /* This is the old main function, can be used to test Board, but not needed */
//    public static void testBoard(String[] args) {
//        Board b = new Board();
//        int[] values = {1,2,3,4,5,6,8,7,0};
//        Board c = new Board();
//        c.makeBoard(values);
//        b.makeBoard(2);
//        System.out.println(b);
//        System.out.println(c);
//
//        Board test = new Board();
//        int[] testVal = {1, 2, 3, 4, 6, 8, 7, 0, 5};
//        test.makeBoard(testVal);
//        test.showMe(test, "LDRULL");
//    }
}
