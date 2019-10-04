import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class SpellChecker {
    public static void main(String[] args) {
        //makes the dictionary
        BinarySearchTree<String> dictionary = readDictionary();
        reportTreeStats(dictionary);
        //checks the spelling of a given file
        checkSpeelling("Letter.txt", dictionary);
        //the test function
        testTree();
    }

    //function which takes a file name and BST and checks the file contents against the BST
    //         get it? its funny cuz its misspelled... plz don't dock points
    public static void checkSpeelling(String fileName, BinarySearchTree<String> dictionary) {
        System.out.println();
        try (Scanner input = new Scanner(new File(fileName))){
            System.out.println("--- Misspelled Words ---");
            while (input.hasNext()) {
                String word = input.next();
                word = word.toLowerCase();
                word = word.replaceAll("[^A-Za-z0-9']", "");
                if(!dictionary.search(word)) {
                    System.out.println(word);
                }
            }
            System.out.println();
        }
        catch (Exception ex) {
            System.out.println("Error in the file");
        }
    }

    //not made by me, just does a test of the BST function
    public static void testTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();


        // Add a bunch of values to the tree
        tree.insert("Olga");
        tree.insert("Tomeka");
        tree.insert("Benjamin");
        tree.insert("Ulysses");
        tree.insert("Tanesha");
        tree.insert("Judie");
        tree.insert("Tisa");
        tree.insert("Santiago");
        tree.insert("Chia");
        tree.insert("Arden");


        //Make sure it displays in sorted order
        tree.display("--- Initial Tree State ---");
        reportTreeStats(tree);


        // Try to add a duplicate
        if (tree.insert("Tomeka")) {
            System.out.println("oops, shouldn't have returned true from the insert");
        }
        tree.display("--- After Adding Duplicate ---");
        reportTreeStats(tree);


        // Remove some existing values from the tree
        tree.remove("Olga");    // Root node
        tree.remove("Arden");   // a leaf node
        tree.display("--- Removing Existing Values ---");
        reportTreeStats(tree);


        // Remove a value that was never in the tree, hope it doesn't crash!
        tree.remove("Karl");
        tree.display("--- Removing A Non-Existent Value ---");
        reportTreeStats(tree);
    }

    public static void reportTreeStats(BinarySearchTree<String> tree) {
        System.out.println("--- Tree Stats ---");
        System.out.printf("Total Nodes : %d\n", tree.numberNodes());
        System.out.printf("Leaf Nodes  : %d\n", tree.numberLeafNodes());
        System.out.printf("Tree Height : %d\n", tree.height());
    }

    public static BinarySearchTree<String> readDictionary() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        //array list that hold the dictionary so that it can be easily shuffled
        ArrayList<String> words = new ArrayList<>();
        //note: this particular set up of the try block is from Dean from in class
        try (Scanner input = new Scanner(new File("Dictionary.txt"))){
            while (input.hasNext()) {
                words.add(input.next());
            }
        }
        catch (Exception ex) {
            System.out.println("Error in the file");
        }

        //got this from the hw description
        java.util.Collections.shuffle(words, new java.util.Random(System.currentTimeMillis()));

        for(int count = 0; count < words.size(); ++count) {
            tree.insert(words.get(count));
        }

        return tree;
    }
}
