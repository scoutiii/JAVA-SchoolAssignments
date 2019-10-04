import java.util.Random;

/**
 * This is the class GameState, which is the slider puzzle kind of board.
 * Any functions that are commented out (regular gray comments) still work, but are just unused.
 * @author Viki Allan, Scout Jarman altered and added to original.
 */

public class GameState implements Comparable<GameState> {
    /**
     * These comments are in their respective order going down.
     * Constant which holds the size of the board.
     * Values of board.
     * Row location of blank.
     * Column location of blank.
     * String which contains a list of previous moves.
     * Int which holds the comparable rank.
     */
    private static final int SIZE = 3;
    private int[][] board;
    private int blankRow;
    private int blankCol;
    private String prevMoves;
    private Integer rank;

    /**
     * Function which compares the ranks of the two boards.
     * Complexity: O(1) lol
     * @param b2 The other board which will be compared to this board.
     * @return the comparison of the boards ranks.
     */
    public int compareTo(GameState b2) {
        return this.rank.compareTo(b2.rank);
    }

    /**
     * This function determines the rank of the board, uses Hamming distance.
     * Complexity: O(n) it has to go through all squares of the board, so maybe its actually constant.
     * @return The new rank of the board, also sets the rank.
     */
    private int setRank() {
        this.rank = 0;
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++){
                int currentBoardNumber = row * 3 + col + 1;
                if(row == 2 && col == 2) {
                    currentBoardNumber = 0;
                }
                if(this.board[row][col] != currentBoardNumber) {
                    this.rank += 1;
                }
            }
        }
        this.rank += this.prevMoves.length();
        return this.rank;
    }

    /**
     * Default constructor.
     * Complexity: O(1) lol
     */
    GameState() {
        this.board = new int[SIZE][SIZE];
        this.prevMoves = "";
        this.rank = setRank();
    }

    /**
     * Copy constructor.
     * Complexity: O(1) or O(n), n cuz it goes through all cube, 1 because it is always 9 squares.
     * @param b the board that will be copied.
     */
    GameState(GameState b) {
        //System.out.println( "Just Copied GameState\n" + b.toString() +"\n");
        board = new int[SIZE][];
        for (int i = 0; i < SIZE; i++) {
            this.board[i] = b.board[i].clone();
        }
        this.blankRow = b.blankRow;
        this.blankCol = b.blankCol;

        this.prevMoves = b.prevMoves;
        this.rank = setRank();
    }

    /**
     * The function which converts the board to a string
     * Complexity: O(1)/O(n) n because it goes through 2 for loops, 1 because it is always 9 squares.
     * @return the board as a string.
     */
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

    /**
     * Return true if board is identical to b.
     * Complexity: O(1)/O(n) n for all squares, 1 for there always being 9 squares lol.
     * @param b the board which will be compared to.
     * @return true if the boards are equal, otherwise false.
     */
    boolean equals(GameState b) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != b.board[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Create a board by performing legal moves on a board.
     * jumbleCT indicates the number of moves to make.
     * if jumbleCt == 0, return the winning board.
     * Complexity: O(n) where n is the number of moves to be made.
     * @param jumbleCt the number of moves to make.
     */
    void makeBoard(int jumbleCt) {
        int val = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = val++;
            }
        }
        blankRow = SIZE - 1;
        blankCol = SIZE - 1;
        board[blankRow][blankCol] = 0;
        jumble(jumbleCt);
        deletePrevMoves();
        this.rank = setRank();
    }

    /**
     * Randomly apply ct moves to the board, making sure they are legal and don't undo the previous move
     * Complexity: O(n) where n is the number of moves to make.
     * @param ct the number of moves to make.
     */
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

    /**
     * Create a board from a given set of values.
     * Complexity: O(1) lol
     * @param values array of value which will be put in the board.
     */
    void makeBoard(int[] values) {
        int c = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (values[c] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
                board[i][j] = values[c++];
            }
        }
        this.rank = setRank();
    }

    /**
     * Make the move indicated by m (L Left, R Right, U Up, D Down) if it is legal and if it doesn't undo the move specified by lastMove
     * Return a blank if the move could not be made, otherwise return the move
     * Complexity: O(1) lol
     * @param m the move which is wanting to be made.
     * @param lastMove the move which was moved last on the board.
     * @return ' ' if no moves made, or the move which is made, so m.
     */
    char makeMove(char m, char lastMove) {
        //System.out.println( "makeMove " + m + lastMove +"\n");
        boolean moved = false;
        switch (m) {
            case 'R':
                if (lastMove != 'L') {
                    moved = slideRight();
                }
                break;
            case 'L':
                if (lastMove != 'R') {
                    moved = slideLeft();
                }
                break;
            case 'D':
                if (lastMove != 'U') {
                    moved = slideDown();
                }
                break;
            case 'U':
                if (lastMove != 'D') {
                    moved = slideUp();
                }
                break;
        }
        if (!moved) {
            return ' ';
        }
        addToMoves(m);
        setRank();
        return m;
    }

    /**
     * If possible, slides a tile up into the blank position.
     * Complexity: O(1) lol
     * @return the success of operation.
     */
    private boolean slideUp()
    {
        if (blankRow == SIZE - 1) {
            return false;
        }
        board[blankRow][blankCol] = board[blankRow + 1][blankCol];
        board[blankRow + 1][blankCol] = 0;
        blankRow += 1;
        return true;
    }

    /**
     * If possible, slides a tile down into the blank position.
     * Complexity: O(1) lol
     * @return success of operation.
     */
    private boolean slideDown()
    {
        if (blankRow == 0) {
            return false;
        }
        board[blankRow][blankCol] = board[blankRow - 1][blankCol];
        board[blankRow - 1][blankCol] = 0;
        blankRow -= 1;
        return true;
    }

    /**
     * If possible, slides a tile left into the blank position.
     * Complexity: O(1) lol
     * @return success of operation.
     */
    private boolean slideLeft()
    {
        if (blankCol == SIZE - 1) {
            return false;
        }
        board[blankRow][blankCol] = board[blankRow][blankCol + 1];
        board[blankRow][blankCol + 1] = 0;
        blankCol += 1;
        return true;
    }

    /**
     * If possible, slides a tile right into the blank position.
     * Complexity: O(1) lol
     * @return success of operation.
     */
    private boolean slideRight()
    {
        if (blankCol == 0) {
            return false;
        }
        board[blankRow][blankCol] = board[blankRow][blankCol - 1];
        board[blankRow][blankCol - 1] = 0;
        blankCol -= 1;
        return true;
    }

    /**
     * Show me method which takes a board and moves, and shows the outcomes of each move.
     * Complexity: O(n) where n is the number of moves to be made lol.
     * @param b the starting board.
     * @param moves string of moves which are to made on b.
     */
    void showMe(GameState b, String moves) {
        // prints out title and original board
        System.out.println("Moves list: " + moves);
        GameState temp = new GameState(b);
        System.out.println("Original GameState");
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

    /**
     * Takes a string (just a char) and adds it to the end of previous moves.
     * Complexity: O(1) lol
     * @param move the move which is to be added to list of moves made.
     */
    private void addToMoves(char move) {
        this.prevMoves += move;
    }

    /**
     * To make the jumble thing easier.
     * Complexity: O(1) lol
     */
    private void deletePrevMoves() {
        this.prevMoves = "";
    }

    /**
     * Returns the moves list.
     * Complexity: O(1) lol
     * @return the string which is the list of moves.
     */
    String getPrevMoves() {
        return this.prevMoves;
    }

    /**
     * Returns the last move made.
     * Complexity: O(1) lol
     * @return the last move made.
     */
    char getLastMove() {
        int length = this.prevMoves.length();
        if (length > 0) {
            return prevMoves.charAt(length - 1);
        }
        return ' ';
    }

    /**
     * Like make move, but just checks to see if the move is legal.
     * Complexity: O(1) lol
     * @param curr the move that wants to be made/checked if it is legal to make this move.
     * @param last the last move made on the board.
     * @return ' ' if move is not made, otherwise the move that can be made, so curr.
     */
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

    /*
     * This is the old main function, can be used to test GameState, but not needed.
     * Complexity: O(1) lol
     * @param args frankly not sure, it is not used.
     */
//    public static void oldMain (String[] args) {
//        GameState b = new GameState();
//        int[] values = {1,2,3,4,5,6,8,7,0};
//        GameState c = new GameState();
//        c.makeBoard(values);
//        b.makeBoard(2);
//        System.out.println(b);
//        System.out.println(c);
//
//        GameState test = new GameState();
//        int[] testVal = {1, 2, 3, 4, 6, 8, 7, 0, 5};
//        test.makeBoard(testVal);
//        test.showMe(test, "LDRULL");
//
//        b.setRank();
//    }
}
