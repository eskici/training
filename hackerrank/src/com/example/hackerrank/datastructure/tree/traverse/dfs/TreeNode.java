package com.example.hackerrank.datastructure.tree.traverse.dfs;

public class TreeNode {

    TreeNode left;
    TreeNode right;
    private final Integer value;

    public Integer getValue() {
        return value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public TreeNode(Integer value) {
        this.value = value;
    }
}
