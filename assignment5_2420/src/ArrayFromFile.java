import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class which is for creating an array of Words and Ranks from a file.
 * NOT GENERIC
 * @author Scout Jarman
 */
class ArrayFromFile {
    /**
     * Holds the name of the file to get the words from.
     */
    private String fileName;

    /**
     * Constructor which gets the file name.
     * @param fileName of the file you want to use.
     */
    ArrayFromFile(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads the file, and makes an array of word and rank objects.
     * @return the array with all the words from the file.
     * @throws FileNotFoundException because it wants to.
     */
    Term[] makeArray() throws FileNotFoundException {
        File file = new File(this.fileName);
        Scanner fileOut = new Scanner(file);
        int size = fileOut.nextInt();

        Term[] dict = new Term[size];
        int i = 0;
        while(fileOut.hasNext()) {
            String word = fileOut.next();
            int rank = fileOut.nextInt();
            dict[i] = new Term(word, rank);
            i++;
        }
        return dict;
    }

//    /**
//     * Really just a test program.
//     * @param args are not used.
//     * @throws FileNotFoundException because of the file reading.
//     */
//    public static void oldMain(String[] args) throws FileNotFoundException {
//        System.out.println("test");
//        ArrayFromFile test = new ArrayFromFile("SortedWords.txt");
//        System.out.println(test.fileName);
//        Term[] dict = test.makeArray();
//        for (Term w: dict) {
//            System.out.println(w);
//        }
//    }
}
