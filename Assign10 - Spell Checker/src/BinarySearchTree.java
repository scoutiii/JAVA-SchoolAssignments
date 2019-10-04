public class  BinarySearchTree <E extends Comparable<E>>{
    //node class for the binary search tree
    private class treeNode <E> {
        public E value;
        public treeNode<E> left;
        public treeNode<E> right;

        public treeNode(E value) {
            this.value = value;
        }
    }

    //start of binary search tree class stuff
    private treeNode<E> rootNode;

    //will add a value to the tree, in the proper search tree style
    public boolean insert(E value) {
        treeNode<E> insertValue = new treeNode<>(value);
        //checks to see if the value is in the tree already
        if(search(insertValue.value)) {
            return false;
        }
        //checks if there is nothing in the tree, makes this the first node
        else if(rootNode == null) {
            rootNode = insertValue;
        }
        //goes through and finds the proper place to put the value
        else {
            treeNode<E> parent = null;
            treeNode<E> current = rootNode;
            while(current != null) {
                parent = current;
                if(current.value.compareTo(value) < 0) {
                    current = current.right;
                }
                else {
                    current = current.left;
                }
            }
            if(parent.value.compareTo(value) < 0) {
                parent.right = insertValue;
            }
            else {
                parent.left = insertValue;
            }
        }
        return true;
    }

    /**
     * NOTE: I had made this function on my own,
     * but it didn't work %100 correctly,
     * so I followed what Dean did in class,
     * and now it works.
     * I also went along and made some edits to my
     * original function to follow Deans code
     */
    //this function removes a value from the tree
    public boolean remove(E value) {
        //checks to see if the value is in the tree
        if(!search(value)) {
            return false;
        }
        //keeps track of parent and current nodes
        treeNode<E> parent = null;
        treeNode<E> current = rootNode;
        boolean done = false;
        //traverses tree until the value to delete is found
        while(!done) {
            if(current.value.compareTo(value) < 0) {
                parent = current;
                current = current.right;
            }
            else if (current.value.compareTo(value) > 0){
                parent = current;
                current = current.left;
            }
            else {
                done = true;
            }
        }
        //checks for case: if current has no left child
        if(current.left == null) {
            //if you are removing a root note with no left child
            if(parent == null) {
                rootNode = current.right;
            }
            //regular removal, checks if current was the parents right or left and works accordingly
            else {
                if(parent.value.compareTo(value) < 0) {
                    parent.right = current.right;
                }
                else {
                    parent.left = current.right;
                }
            }
        }
        //does case 2: if current has a left child
        else {
            treeNode<E> rightMost = current.left;
            treeNode<E> parentRightMost = current;
            while(rightMost.right != null) {
                parentRightMost = rightMost;
                rightMost = rightMost.right;
            }
            current.value = rightMost.value;
            /**
             * this is where I went wrong before, thanks Dean
             */
            if(parentRightMost.right == rightMost) {
                parentRightMost.right = rightMost.left;
            }
            else {
                parentRightMost.left = rightMost.left;
            }
        }
        return true;
    }

    //searches the value in the tree
    public boolean search(E value) {
        boolean found = false;
        treeNode<E> current = rootNode;
        while(current != null && !found) {
            if(current.value.equals(value)) {
                found = true;
            }
            else if(current.value.compareTo(value) < 0) {
                current = current.right;
            }
            else {
                current = current.left;
            }
        }
        return found;
    }

    //method which displays everything in the tree in order
    public void display(String message) {
        System.out.println();
        System.out.println(message);
        inOrderTraverse(rootNode);
        System.out.println();
    }
    //recursively prints out the tree in order
    private void inOrderTraverse(treeNode<E> node) {
        if (node != null) {
            inOrderTraverse(node.left);
            System.out.println(node.value);
            inOrderTraverse(node.right);
        }
    }

    //helper function for counting number of nodes
    public int numberNodes() {
        return numNodRec(rootNode);
    }
    //the recursive function which finds how many
    private int numNodRec(treeNode<E> node) {
        //base case: if the called node is null
        if(node == null) {
            return 0;
        }
        int totalLeft = 0;
        totalLeft = 1 + numNodRec(node.left);
        int toalRight = 0;
        toalRight = 1 + numNodRec(node.right);
        return (toalRight + totalLeft - 1);
    }

    //the helper function for counting the number of leaves
    public int numberLeafNodes() {
        return leavesRec(rootNode);
    }
    //the recursive call for counting leaves
    private int leavesRec(treeNode<E> node) {
        if(node.left == null && node.right == null) {
            return 1;
        }
        int leftSide = 0;
        int rightSide = 0;
        if(node.left != null) {
            leftSide += leavesRec(node.left);
        }
        if(node.right != null) {
            rightSide += leavesRec(node.right);
        }
        return leftSide + rightSide;
    }

    //the helper function for counting the height
    public int height() {
        if(rootNode == null) {
            return -1;
        }
        else if(rootNode.left == null && rootNode.right == null) {
            return 0;
        }
        return heightRec(rootNode);
    }
    //the recursive call
    private int heightRec(treeNode<E> node) {
        //base case: basically at a leaf
        if(node.right == null && node.left == null) {
            return 0;
        }

        int depthLeft = 0;
        int depthRight = 0;

        if(node.right != null) {
            depthRight += heightRec(node.right);
        }
        if(node.left != null) {
            depthLeft += heightRec(node.left);
        }

        //returns the larger of the two
        if(depthLeft >= depthRight) {
            return depthLeft + 1;
        }
        else return depthRight + 1;
    }
    
}