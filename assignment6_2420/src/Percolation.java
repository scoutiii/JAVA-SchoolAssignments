import java.util.Random;

/**
 * Percolation using union find, randomly opens sites till percolated.
 * @author Scout Jarman
 */
class Percolation {
    /**
     * Variables for union find group, array of open sites,
     * size, size*size, and a title.
     */
    private UnionFind groups;
    private int[][] array;
    private int size;
    private int totalSize;
    private String title;

    /**
     * Constructor which makes a size*size board, initiates array to 0 (closed),
     * Also creates edge groups which are above and bellow the array, for determining percolation.
     * @param size of one edge of the board you want to percolate.
     * @param title of this particular board.
     */
    Percolation(int size, String title){
        this.groups = new UnionFind(size*size + 2*size, title);
        this.size = size;
        this.totalSize = size * size;
        this.array = new int[size][size];
        this.title = title;

        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                this.array[y][x] = 0;
            }
        }
        // Makes the edge group at "top" (y = -1)
        for(int i = 0; i < size; i++) {
            this.groups.union(0, i);
        }
        // Makes the edge group at "bottom" (y = size)
        for(int i = size*size + size; i < size*size + 2*size; i++) {
            this.groups.union(size*size + size, i);
        }
    }

    /**
     * Prints out the board and title.
     */
    private void print() {
        System.out.println(this.title + "\n");
        for(int y = 0; y < this.size; y++) {
            for(int x = 0; x < this.size; x++) {
                if(this.array[y][x] == 1) {
                    System.out.print('\u2593');
                }
                else {
                    System.out.print('\u2591');
                }

//                if(this.array[y][x] == 1) {
//                    System.out.print('\u25a9');
//                }
//                else {
//                    System.out.print('\u25a2');
//                }

                /*
                Uncomment if you dare (comment above lines 53-65)
                 */
//                if(this.array[y][x] == 0) {
//                    System.out.print('\u2540');
//                    continue;
//                }
//                String code = "";
//                if(y - 1 >= 0 && this.array[y - 1][x] == 1) {
//                    code += "1";
//                }
//                if(x + 1 < this.size && this.array[y][x + 1] == 1) {
//                    code += "2";
//                }
//                if(y + 1 < this.size && this.array[y + 1][x] == 1) {
//                    code += "3";
//                }
//                if(x - 1 >= 0 && this.array[y][x - 1] == 1) {
//                    code += "4";
//                }
//
//                switch (code){
//                    case "": {
//                        System.out.print('\u2575');
//                        break;
//                    }
//                    case "1": {
//                        System.out.print('\u2559');
//                        break;
//                    }
//                    case "2": {
//                        System.out.print('\u2553');
//                        break;
//                    }
//                    case "3": {
//                        System.out.print('\u2556');
//                        break;
//                    }
//                    case "4": {
//                        System.out.print('\u255b');
//                        break;
//                    }
//                    case "12": {
//                        System.out.print('\u255a');
//                        break;
//                    }
//                    case "13": {
//                        System.out.print('\u2551');
//                        break;
//                    }
//                    case "14": {
//                        System.out.print('\u255d');
//                        break;
//                    }
//                    case "23": {
//                        System.out.print('\u2554');
//                        break;
//                    }
//                    case "24": {
//                        System.out.print('\u2550');
//                        break;
//                    }
//                    case "34": {
//                        System.out.print('\u2557');
//                        break;
//                    }
//                    case "123": {
//                        System.out.print('\u2560');
//                        break;
//                    }
//                    case "124": {
//                        System.out.print('\u2569');
//                        break;
//                    }
//                    case "1234": {
//                        System.out.print('\u256c');
//                        break;
//                    }
//                }

            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Takes an x and y coordinate and opens the site if unopened.
     * Also unions with adjacent sites and with edge groups is needed.
     * @param x coordinate of a site.
     * @param y coordinate of a site.
     * @return 0 if open, 1 if closed, -1 if out of bounds (ERROR).
     */
    private int open(int x, int y) {
        // If out of bounds, returns -1
        if(x < 0 || x >= this.size || y < 0 || y >= this.size) {
            return -1;
        }
        // If already filled, returns 1
        if(this.array[y][x] == 1) {
            return 0;
        }
        // Sets (x,y) to 1
        this.array[y][x] = 1;
        // Converts to corresponding number for the groups
        int first = this.size * y + x + this.size;
        // Unions with the edge groups for checking with percolation
        if(y == 0) {
            this.groups.union(first, first - this.size);
        }
        else if(y == this.size - 1) {
            this.groups.union(first, first + this.size);
        }
        // Checks for other open squares near by to union with
        if(y - 1 >= 0 && this.array[y - 1][x] == 1) {
            this.groups.union(first, first - this.size);
        }
        if(y + 1 < this.size && this.array[y + 1][x] == 1) {
            this.groups.union(first, first + this.size);
        }
        if(x - 1 >= 0 && this.array[y][x - 1] == 1) {
            this.groups.union(first, first - 1);
        }
        if(x + 1 < this.size && this.array[y][x + 1] == 1) {
            this.groups.union(first, first + 1);
        }
        return 1;
    }

    /**
     * Randomly generates x,y until the board is percolated, prints every 50 opened.
     */
    int randomGeneration(boolean print) {
        Random random = new Random();
        if(print) {
            System.out.println("\u2593 means open, \u2591 means closed.");
        }

        int count = 0;
        int randX = random.nextInt(this.size);
        int randY = random.nextInt(this.size);
        count += open(randX, randY);

        while(!percolated()) {
            if(print && count % 50 == 0) {
                print();
                System.out.println(count + " open sites \n\n");
            }

            while(open(randX, randY) == 0) {
                randX = random.nextInt(this.size);
                randY = random.nextInt(this.size);
            }

            count += 1;
        }
        if(print) {
            print();
            System.out.println(count + " open sites \n\n");
            System.out.println("Percolation Threshold = " +(count * 1.0) / (this.totalSize));
        }
        return count;
    }

    /**
     * Checks if the two edge groups are connected, which means percolation.
     * @return true if top and bottom group are connected.
     */
    private boolean percolated() {
        return this.groups.find(0) == this.groups.find(this.size * this.size + this.size);
    }

    /**
     * old main which tests out the 20x20 case.
     */
    void oldMain() {
        Percolation test = new Percolation(20, "Demo 20x20");
        test.randomGeneration(true);
    }
}
