/**
 * This is the Word Info class.
 * Holds a word, a count, and a score.
 * @author Scout Jarman
 */
public class WordInfo {
    /**
     * Variable which hold the word, count, and score.
     * Array which holds the values for each letter.
     */
    private String word;
    private int count;
    private static final int letterValue [] = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};

    /**
     * Constructor which will set the word and count.
     * @param word to be stored, automatically set to lower case.
     * @param count the count to be stored (pretty much always 0).
     */
    WordInfo(String word, int count) {
        this.word = word.toLowerCase();
        this.count = count;
    }

//    /**
//     * Default constructor, just in case.
//     */
//    WordInfo() {
//        this.word = "";
//        this.count = 0;
//    }

    /**
     * Creates a score based of the letters, length, and count.
     * @return the (sum of letters) * length multiplier * repeat multiplier.
     */
    long getScore() {
        long score = 0;
        // Sums the value of the letter as defined above
        for(int i = 0; i < this.word.length(); i++) {
            score += letterValue[this.word.charAt(i) - 'a'];
        }
        // Applies length bonus
        if(this.word.length() - 2 <= 0) {
            return 0;
        }
        else if(this.word.length() < 8) {
            score *= (this.word.length() - 2);
        }
        else {
            score *= 6;
        }
        // Applies the count bonus
        if(this.count == 0) {
            score *= 5;
        }
        else if(this.count <= 15){
            score *= (4 - ((this.count - 1) / 5));
        }
        // Returns the final value for score
        return score;
    }

    /**
     * Increases the words count by how ever much given.
     */
    void increaseCount() {
        this.count += 1;
    }

    /**
     * An overridden method to compare to word objects, based on the word only.
     * Got code from Vicki Allan/the assignment write up.
     * @param w2 the object to compare with this
     * @return whether the words are the same or not.
     */
    @Override
    public boolean equals(Object w2) {
        if(w2 == this) {
            return true;
        }
        if(!(w2 instanceof WordInfo)){
            return false;
        }
        WordInfo w = (WordInfo)w2;
        return (this.word.compareTo(w.word) == 0);
    }

    /**
     * Overridden hash code function, just returns the words hash code.
     * @return the words hash code.
     */
    @Override
    public int hashCode() {
        return this.word.hashCode();
    }

    /**
     * Overridden to string method, gives count and word.
     * @return the count then the word.
     */
    @Override
    public String toString() {
        return this.count + " " + this.word;
    }

}