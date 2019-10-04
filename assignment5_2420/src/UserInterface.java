import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the user interface for the auto complete program
 * @author Scout Jarman
 */
public class UserInterface {
    /**
     * Variables that store the array of words,
     * and creates an input.
     */
    private Term[] dict;
    private Scanner input = new Scanner(System.in);

    /**
     * Constructor which takes a file name.
     * @param fileName of the file that has all the words.
     * @throws FileNotFoundException because its reading files.
     */
    private UserInterface(String fileName) throws FileNotFoundException {
        ArrayFromFile make = new ArrayFromFile(fileName);
        dict = make.makeArray();
    }

    /**
     * For a given word, gives the index of where the given word ends.
     * @param target word.
     * @return the ending index.
     */
    private int lastIndexOf(String target) {
        // Makes sure there is actually a word.
        if(target.length() == 0) {
            return -1;
        }
        // Changes the last letter to last letter + 1
        char lastLetter = target.charAt(target.length() - 1);
        target = target.substring(0, target.length() - 1);
        target += String.valueOf((char)(lastLetter + 1));

        // Starts a binary search for the word.
        Term targetObject = new Term(target, 0);
        int leftIndex = 0;
        int rightIndex = dict.length - 1 ;

        while(rightIndex >= leftIndex) {
            int mid = (leftIndex + rightIndex) / 2;

            if(dict[mid].compareWord(targetObject) > 0) {
                rightIndex = mid - 1;
            }
            else if(dict[mid].compareWord(targetObject) < 0) {
                leftIndex = mid + 1;
            }
            else {
                return mid;
            }
        }
        // These are to prevent null pointer stuff.
        if(leftIndex >= dict.length) {
            --leftIndex;
        }
        else if(dict[leftIndex] == null) {
            return rightIndex - 1;
        }
        else if(dict[leftIndex].compareWord(targetObject) > 0) {
            return leftIndex - 1;
        }
        return rightIndex;
    }

    /**
     * Finds the index of where the word starts.
     * @param target word.
     * @return the index of where that word starts.
     */
    private int firstIndexOf(String target) {
        // Makes sure there is a word.
        if(target.length() == 0) {
            return -1;
        }
        // Starts binary search.
        Term targetObject = new Term(target, 0);
        int leftIndex = 0;
        int rightIndex = dict.length - 1 ;

        while(rightIndex >= leftIndex) {
            int mid = (leftIndex + rightIndex) / 2;

            if(dict[mid].compareWord(targetObject) > 0) {
                rightIndex = mid - 1;
            }
            else if(dict[mid].compareWord(targetObject) < 0) {
                leftIndex = mid + 1;
            }
            else {
                return mid;
            }
        }
        // To prevent null pointer problems.
        if(leftIndex >= dict.length) {
            --leftIndex;
        }
        else if(dict[leftIndex] == null) {
            return rightIndex;
        }
        else if(dict[leftIndex].compareWord(targetObject) > 0) {
            return leftIndex;
        }
        return rightIndex;
    }

    /**
     * Takes some bounds, creates a priority cue of the words in that range.
     * @param lowIndex to check in the array.
     * @param highIndex to check in the array.
     * @return a priority cue with all the words in that range.
     */
    private LeftHeap<Term> createHeap(int lowIndex, int highIndex) {
        if(lowIndex < 0 || highIndex < 0) {
            return new LeftHeap<>("EMPTY TREE!!!");
        }
        LeftHeap<Term> heap = new LeftHeap<>("Heap from " + dict[lowIndex] + " to " + dict[highIndex]);
        for(int i = lowIndex; i <= highIndex; i++) {
            heap.insert(dict[i]);
        }
        return heap;
    }

    /**
     * Takes a priority cue, and a number, prints the top count elements.
     * @param heap that stores the words you want.
     * @param count is the number of words you want to print.
     * @return a string with all the words needed.
     */
    private String topCountWords(LeftHeap<Term> heap, int count) {
        StringBuilder list = new StringBuilder();
        list.append("--------------------\n");
        int ct = 0;
        while(ct < count && heap.isNotEmpty()) {
            list.append(heap.deleteMax());
            list.append("\n");
            ct++;

        }
        list.append("--------------------\n");
        return list.toString();
    }

    /**
     * Just prints the options.
     */
    private void printMenu() {
        System.out.println("0: End the program.");
        System.out.println("1: Enter single word.");
        System.out.println("2: Enter two words.");
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
     * Autocomplete for just one word.
     */
    private void option1SW() {
        System.out.println("\nEnter part of one word (no spaces): ");
        while(!input.hasNext()) {
            System.out.println("Bad input, try again.");
            input.nextLine();
        }
        String word = input.next();
        System.out.println("\nHow many words would you like to see: ");
        int count = getUserInput();

        int lowIndex = firstIndexOf(word);
        int highIndex = lastIndexOf(word);
        LeftHeap<Term> heap = createHeap(lowIndex, highIndex);
        System.out.println("\nSuggestions for: " + word);
        System.out.println(topCountWords(heap, count));
    }

    /**
     * Autocomplete for two words.
     */
    private void option2TW() {
        System.out.println("\nEnter one word, then enter, then another word: ");

        String[] words = new String[2];
        words[0] = "";
        words[1] = "";
        boolean firstReceived = false;
        boolean allReceived = false;
        while(!allReceived && input.hasNext()) {
            if(!firstReceived) {
                words[0] = input.next();
                firstReceived = true;
            }
            else {
                words[1] = input.next();
                allReceived = true;
            }
        }

        System.out.println("\nHow many words would you like to see: ");
        int count = getUserInput();

        int lowIndexFirst = firstIndexOf(words[0]);
        int highIndexFirst = lastIndexOf(words[0]);
        LeftHeap<Term> heapFirst = createHeap(lowIndexFirst, highIndexFirst);

        int lowIndexSecond = firstIndexOf(words[1]);
        int highIndexSecond = lastIndexOf(words[1]);
        LeftHeap<Term> heapSecond = createHeap(lowIndexSecond, highIndexSecond);

        heapFirst.merge(heapSecond);
        System.out.println("\nSuggestions for: " + words[0] + " , " + words[1]);
        System.out.println(topCountWords(heapFirst, count));
    }

    /**
     * This is the main loop, you can autocomplete 1 or 2 words, or quite.
     * @param args are not used.
     * @throws FileNotFoundException because we are reading files.
     */
    public static void main(String[] args) throws FileNotFoundException {
        UserInterface UI = new UserInterface("SortedWords.txt");
        System.out.println("Welcome to the auto complete program!!! \n\n");
        int choice = 1;
        while(choice > 0) {
            UI.printMenu();
            choice = UI.getUserInput();
            switch (choice) {
                case 1: {
                    UI.option1SW();
                    break;
                }
                case 2: {
                    UI.option2TW();
                    break;
                }
            }
        }
        System.out.println("\nThanks for using!!!");
    }
}
