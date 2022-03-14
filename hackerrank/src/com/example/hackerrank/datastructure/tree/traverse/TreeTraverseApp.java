package com.example.hackerrank.datastructure.tree.traverse;

/*              3
             /    \
           4        5
         /    \    /  \
       6      7   8    9

PreOrder (N;L;R)  - > 3, 4, 6, 7, 5, 8, 9
InOrder  (L;N;R)  - > 6, 4, 7, 3, 8, 5, 9
PostOrder (L;R;N) - > 6, 7, 4, 8, 9, 5 ,3

*/

import com.example.hackerrank.datastructure.tree.traverse.dfs.*;

public class TreeTraverseApp {

    public static void main(String[] args) {
        TreeNode binaryTree = createBinaryTree();

        System.out.println("\n ============= traverseRecursively ===========");
        traverseRecursively(binaryTree);

        System.out.println("\n ============= traverseIteratively ===========");
        traverseIteratively(binaryTree);
    }

    private static void traverseIteratively(TreeNode binaryTree) {
        DFSIterative dfsIterative = new DFSIterative();

        System.out.print("Iteratively DFS PreOrder : ");
        dfsIterative.traverseTreeDFSPreOrder(binaryTree);

        System.out.println();

        System.out.print("Iteratively DFS InOrder : ");
        dfsIterative.traverseTreeDFSInOrder(binaryTree);

        System.out.println();

        System.out.print("Iteratively DFS PostOrder : ");
        dfsIterative.traverseTreeDFSPostOrder(binaryTree);
    }

    private static void traverseRecursively(TreeNode binaryTree) {
        DFSRecursion dfsRecursion = new DFSRecursion();

        System.out.print("DFS PreOrder : ");
        dfsRecursion.traverseTreeDFSPreOrder(binaryTree);

        System.out.println();

        System.out.print("DFS InOrder : ");
        dfsRecursion.traverseTreeDFSInOrder(binaryTree);

        System.out.println();

        System.out.print("DFS PostOrder : ");
        dfsRecursion.traverseTreeDFSPostOrder(binaryTree);
    }

    public static  TreeNode createBinaryTree ()
    {
        TreeNode root = new TreeNode (3);
        root.setLeft(new TreeNode (4));
        root.setRight(new TreeNode (5));

        root.getLeft().setLeft(new TreeNode (6));
        root.getLeft().setRight(new TreeNode (7));

        root.getRight().setLeft(new TreeNode (8));
        root.getRight().setRight(new TreeNode (9));
        return root;
    }
}
