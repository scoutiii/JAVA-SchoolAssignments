/**
 * Class for holding a word and their rank.
 * @author Scout Jarman
 */
public class Term implements Comparable {
    /**
     * Variable which hold the word and its rank.
     */
    private String word;
    private int rank;

    /**
     * Constructor which sets the word and rank.
     * @param word that you want to store
     * @param rank for the word given.
     */
    Term(String word, int rank) {
        this.word = word;
        this.rank = rank;
    }

    /**
     * So that it will print the word and its rank.
     * @return string of word and rank.
     */
    @Override
    public String toString() {
        return word /*+ " " + rank*/;
    }

    /**
     * Just uses compareRank because this is just for making the heap.
     * @param o is the object to compare to.
     * @return 1 if I am greater, -1 if I am less, 0 if we are equal.
     */
    @Override
    public int compareTo(Object o) {
        return compareRank((Term) o);
    }

    /**
     * Compares objects by the word, just uses the string compare.
     * @param o is the other word you are comparing to.
     * @return + if you are greater, - if you are less, 0 if you are equal.
     */
    int compareWord(Term o) {
        return word.compareTo(o.word);
    }

    /**
     * So that you can compare the object by rank.
     * @param o is the word you are comparing to.
     * @return 1 if you are greater, -1 if they are greater, 0 if equal.
     */
    private int compareRank(Term o) {
        if(this.rank > o.rank) {
            return 1;
        }
        else if(this.rank < o.rank) {
            return -1;
        }
        return 0;
    }


}
