import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This is the game class which is a word game. Each word gets a score based on letters,
 * length, and repeats. This game takes in a file with a bunch of words, and adds the scores.
 * @author Scout Jarman (modified from given code)
 */
public class Game {
    /**
     * Variables that hold the name of the file used.
     * The hash table which holds the words.
     * The total score of the game.
     */
    private String name;
    private DoubleHashTable<WordInfo> H;
    private long totalScore;

    /**
     * Starts the game, just makes a new hash table.
     */
    private Game(){
        H = new DoubleHashTable<>();
    }

    /**
     * Computes the score for a given WordInfo.
     * @param wi the WordInfo object.
     * @return the score for the given WordInfo.
     */
    private long computeScore(WordInfo wi) {
        return wi.getScore();
    }

    /**
     * This is where the magic happens.
     * Takes in a file, reads in the words into a hash table.
     * Then calculates the score of the words in the hash table.
     * @param filename the file which is to be used
     * @throws FileNotFoundException needed for the Scanner object.
     */
    private void playGame(String filename) throws FileNotFoundException {
        this.name = filename;
        File file = new File(filename);
        Scanner fileOut = new Scanner(file);
        // Reads the values into the hash table
        while(fileOut.hasNextLine()) {
            WordInfo newWord = new WordInfo(fileOut.nextLine(), 0);
            WordInfo foundWord = H.find(newWord);
            if(foundWord == null) {
                H.insert(newWord);
                this.totalScore += computeScore(newWord);
            }
            else {
                foundWord.increaseCount();
                this.totalScore += computeScore(foundWord);
            }
        }
    }

    /**
     * The to string function.
     * Takes all the statics from the game.
     * @return all the data for the game.
     */
    @Override
    public String toString() {
        System.out.println("Here are the games statistics for " + this.name + ":");
        System.out.println("Total score              = " + this.totalScore);
        System.out.println("Total number of finds    = " + this.H.getNumFinds());
        System.out.println("Total number of probes   = " + this.H.getNumProbes());
        System.out.println("Total number of items    = " + this.H.getSize());
        System.out.println("Total size of hash table = " + this.H.getCapacity());
        System.out.println("The first 20 elements in the table...");
        int LIMIT = 20;
        return this.name + "\n"+ this.H.toString(LIMIT) + "\n\n";
    }

    /**
     * The main function which plays a couple of games.
     * @param args is never used.
     * @throws FileNotFoundException so it can play the game.
     */
    public static void main(String [] args) throws FileNotFoundException {
        Game g0 = new Game();
        g0.playGame("game0.txt" );
        System.out.println(g0);
        Game g1 = new Game(  );
        g1.playGame("game1.txt" );
        System.out.println(g1);
        Game g2 = new Game(  );
        g2.playGame("game2.txt" );
        System.out.println(g2);
        Game g3 = new Game(  );
        g3.playGame("game3.txt" );
        System.out.println(g3);
        Game g4 = new Game(  );
        g4.playGame("game4.txt" );
        System.out.println(g4);
    }

}
