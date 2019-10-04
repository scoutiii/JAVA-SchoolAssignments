

public class Recursion {
    public static void main(String[] args) {

        int[] sumMe = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };
        System.out.printf("Array Sum: %d\n", arraySum(sumMe, 0));

        int[] minMe = { 0, 1, 1, 2, 3, 5, 8, -42, 13, 21, 34, 55, 89 };
        System.out.printf("Array Min: %d\n", arrayMin(minMe, 0));

        String[] amISymmetric =  {
                "You can cage a swallow can't you but you can't swallow a cage can you",
                "I still say cS 1410 is my favorite class"
        };
        for (String test : amISymmetric) {
            String[] words = test.toLowerCase().split(" ");
            System.out.println();
            System.out.println(test);
            System.out.printf("Is word symmetric: %b\n", isWordSymmetric(words, 0, words.length - 1));
        }

        double weights[][] = {
                { 51.18 },
                { 55.90, 131.25 },
                { 69.05, 133.66, 132.82 },
                { 53.43, 139.61, 134.06, 121.63 }
        };
        System.out.println();
        System.out.println("--- Weight Pyramid ---");
        for (int row = 0; row < weights.length; row++) {
            for (int column = 0; column < weights[row].length; column++) {
                double weight = computePyramidWeights(weights, row, column);
                System.out.printf("%.2f ", weight);
            }
            System.out.println();
        }

        char image[][] = {
                {'*','*',' ',' ',' ',' ',' ',' ','*',' '},
                {' ','*',' ',' ',' ',' ',' ',' ','*',' '},
                {' ',' ',' ',' ',' ',' ','*','*',' ',' '},
                {' ','*',' ',' ','*','*','*',' ',' ',' '},
                {' ','*','*',' ','*',' ','*',' ','*',' '},
                {' ','*','*',' ','*','*','*','*','*','*'},
                {' ',' ',' ',' ',' ',' ',' ',' ','*',' '},
                {' ',' ',' ',' ',' ',' ',' ',' ','*',' '},
                {' ',' ',' ','*','*','*',' ',' ','*',' '},
                {' ',' ',' ',' ',' ','*',' ',' ','*',' '}
        };
        int howMany = howManyOrganisms(image);
        System.out.println();
        System.out.println("--- Labeled Organism Image ---");
        for (char[] line : image) {
            for (char item : line) {
                System.out.print(item);
            }
            System.out.println();
        }
        System.out.printf("There are %d organisms in the image.\n", howMany);
    }

    //checks to see if a sentence is word symmetric
    public static boolean isWordSymmetric(String[] words, int start, int end) {
        //null/size 1 base cases
        if(words.length <= 1) {
            return true;
        }
        //base case,
        else if(end <= start) {
            return true;
        }
        //if there is one word for a middle
        else if(words[end].equalsIgnoreCase(words[start]) == false) {
            return false;
        }
        else {
            return isWordSymmetric(words, start + 1, end - 1);
        }
    }

    //finds the sum of an array
    public static long arraySum(int[] data, int position) {
        //checks for empty array
        if(data.length == 0) {
            return 0;
        }
        //checks for bad input(ie position > length of array)
        else if(position >= data.length) {
            return 0;
        }

        //base case, if at end of array
        if(position == data.length - 1) {
            return data[position];
        }
        //recursive call
        return data[position] + arraySum(data, position + 1);
    }

    //finds the minimum of an array
    public static int arrayMin(int[] data, int position) {
        //checks for empty array
        if(data.length == 0) {
            return 0;
        }
        //checks for bad input(ie position > length of array)
        else if(position >= data.length) {
            return 0;
        }

        if(position == data.length - 1) {
            return data[position];
        }

        if(data[position] < arrayMin(data, position + 1)) {
            return data[position];
        }
        return arrayMin(data, position + 1);
    }

    //finds the weights of elements in a pyramid
    public static double computePyramidWeights(double [][] weights, int row, int column) {
        //checks for empty array (which apparently is size 1)
        if(weights.length <= 1 ) {
            return 0.0;
        }
        //checks for negative row/col
        else if(row < 0 || column < 0) {
            return 0.0;
        }
        //checks if row is out of bounds
        else if(row > weights.length - 1) {
            return 0.0;
        }
        //checks if col is out of bounds
        else if(column > weights[row].length) {
            return 0.0;
        }

        //base case/once at top of pyramid
        if(row == 0 && column == 0) {
            return weights[row][column];
        }

        //checks to see if at far left side of pyramid
        if(column - 1 < 0 ) {
            return weights[row][column] + (.5 * computePyramidWeights(weights, row - 1, column));
        }
        //checks to see if at far right side of pyramid
        else if(column == weights[row - 1].length) {
            return weights[row][column] + (.5 * computePyramidWeights(weights, row-1, column - 1));
        }
        //if in between (ie has stuff above on both sides)
        return weights[row][column]
                + (.5 * computePyramidWeights(weights, row - 1, column)
                + (.5 * computePyramidWeights(weights, row - 1, column - 1)));
    }

    //the function which will call the recursive solution
    public static int howManyOrganisms(char[][] image) {
        //the starting letter
        char startLetter = 'a';
        char letter = startLetter;
        //goes through the whole array and changes/labels the image
        for(int row = 0; row < image.length; ++row) {
            for(int col = 0; col < image[row].length; ++col) {
                if(image[row][col] == '*') {
                    traverseOrganism(image, row, col, 0, letter);
                    letter++;
                }
            }
        }
        //sees how many letters have been used, thus how many organisms there are
        return letter - startLetter;
    }

    //the recursive solution
    private static void traverseOrganism(char[][] image, int row, int col, int previousDirection, char letter) {
        //these are to keep you from trying to access out of array bounds
        boolean dontCheckUp = false;
        boolean dontCheckDown = false;
        boolean dontCheckLeft = false;
        boolean dontCheckRight = false;
        /**
         * standard cases for square array
         */
        //if you are at the top of the array(square), don't check up
        if(row - 1 < 0) {
            dontCheckUp = true;
        }
        //if you are at the bottom of the array, don't check down
        if(row + 1 >= image.length){
            dontCheckDown = true;
        }
        //if you are at the far left of the array, don't check to the left
        if(col - 1 < 0) {
            dontCheckLeft = true;
        }
        //if you are at the far right of the array, don't check to the right
        if(col + 1 >= image[row].length) {
            dontCheckRight = true;
        }
        /**
         * other cases for ragged arrays and such
         */
        //if you are in a ragged array, check above you and make sure stuff is up there basically
        if(row > 0) {
            if(col >= image[row - 1].length) {
                dontCheckUp = true;
            }
        }

        /**
         * starts checking and doing recursion
         */
        //if you can check above you...
        if(dontCheckUp == false) {
            //if there is a part above you, and its not where you previously were, check it out
            if(image[row - 1][col] == '*' && previousDirection != 1) {
                image[row][col] = letter;
                traverseOrganism(image, row - 1, col, 3, letter);
            }
        }
        //if you can check to your right...
        if(dontCheckRight == false) {
            //if there is a part to the right of you, and its not where you previously were, check it out
            if(image[row][col + 1] == '*' && previousDirection != 2) {
                image[row][col] = letter;
                traverseOrganism(image, row, col + 1, 4, letter);
            }
        }
        //if you can check bellow you...
        if(dontCheckDown == false) {
            //if there is a part bellow you, and its not where you previously were, check it out
            if(image[row + 1][col] == '*' && previousDirection != 3) {
                image[row][col] = letter;
                traverseOrganism(image, row + 1, col, 1, letter);
            }
        }
        //if you can check to your left...
        if(dontCheckLeft == false) {
            //if there is a part to the left of you, and its not where you previously were, check it out
            if(image[row][col - 1] == '*' && previousDirection != 4) {
                image[row][col] = letter;
                traverseOrganism(image, row, col - 1, 2, letter);
            }
        }
        //once you are at an end point, make that spot the given letter
        image[row][col] = letter;
    }
}
