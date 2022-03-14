package com.example.hackerrank.datastructure.linkedlist.reverse;

public class ReverseLinkedListApp {

    public static void main(String[] args) {
        ReverseLinkedListApp app = new ReverseLinkedListApp();
        final LinkNode linkedList = app.createReverLinkedList();

        System.out.println("\n============= before reversion ===========");
        app.printLinkedList(linkedList);

        System.out.println("\n============= reverse Recursively ===========");
        //reverseRecursively(linkedList);

        System.out.println("\n============= reverse Iteratively ===========");
        LinkNode reverseLinkedList = app.reverseIteratively(linkedList);

        System.out.println("\n============= reverse LinkedListStack ===========");
        app.reverseLinkedListStack(reverseLinkedList);
    }

    private static LinkNode reverseIteratively(LinkNode linkedList) {
        RLIterative iterative = new RLIterative();
        LinkNode reverseLinkedList = iterative.reverseLinkedList(linkedList);

        printLinkedList(reverseLinkedList);
        return reverseLinkedList;
    }

    private static void reverseLinkedListStack(LinkNode linkedList) {
        RLIterative iterative = new RLIterative();
        LinkNode reverseLinkedList = iterative.reverseLinkedListStack(linkedList);

        printLinkedList(reverseLinkedList);
    }

    private static void reverseRecursively(LinkNode linkedList) {
        RLRecoursion iterative = new RLRecoursion();
        LinkNode reverseLinkedList = iterative.reverseLinkedList(linkedList);

        printLinkedList(reverseLinkedList);
    }

    private static void printLinkedList(LinkNode reverseLinkedList) {

        while(reverseLinkedList != null) {
            System.out.print(reverseLinkedList.getValue() + " -> ");
            reverseLinkedList = reverseLinkedList.getNext();
        }
        System.out.println("null");
    }

    private LinkNode createReverLinkedList() {
        LinkNode head = new LinkNode(1,
                new LinkNode(2,
                        new LinkNode(3,
                                new LinkNode(4, null
                                ))));
        return head;

    }
}
