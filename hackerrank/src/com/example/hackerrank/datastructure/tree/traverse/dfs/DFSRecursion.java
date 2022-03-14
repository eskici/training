package com.example.hackerrank.datastructure.tree.traverse.dfs;

public class DFSRecursion {

    public  void traverseTreeDFSPreOrder (TreeNode currentNode)
    {
        System.out.print (currentNode.getValue() + " , ");
        if (currentNode.left != null)
        {
            traverseTreeDFSPreOrder (currentNode.left);
        }

        if (currentNode.right != null)
        {
            traverseTreeDFSPreOrder (currentNode.right);
        }
    }

    public  void traverseTreeDFSInOrder (TreeNode currentNode)
    {

        if (currentNode.left != null)
        {
            traverseTreeDFSInOrder (currentNode.left);
        }

        System.out.print (currentNode.getValue() + " , ");

        if (currentNode.right != null)
        {
            traverseTreeDFSInOrder (currentNode.right);
        }
    }

    public  void traverseTreeDFSPostOrder (TreeNode currentNode)
    {
        if (currentNode.left != null)
        {
            traverseTreeDFSPostOrder (currentNode.left);
        }

        if (currentNode.right != null)
        {
            traverseTreeDFSPostOrder (currentNode.right);
        }
        System.out.print (currentNode.getValue() + " , ");
    }
}
