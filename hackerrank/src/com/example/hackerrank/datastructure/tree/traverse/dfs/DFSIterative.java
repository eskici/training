package com.example.hackerrank.datastructure.tree.traverse.dfs;

import java.util.*;

public class DFSIterative {

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

    public  void traverseTreeDFSInOrder (TreeNode root)
    {
        Stack<TreeNode> nodeStack = new Stack<>();

        while (true)
        {
            if(root != null) {
                nodeStack.push(root);
                root = root.left;
            } else {
                if(nodeStack.isEmpty())
                    break;

                root = nodeStack.pop();
                System.out.print (root.getValue() + " , ");
                    root = root.right;
            }
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
