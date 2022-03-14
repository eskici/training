package com.example.hackerrank.datastructure.linkedlist.reverse;

import java.util.*;

public class RLIterative {

    LinkNode reverseLinkedList(LinkNode curr) {
        LinkNode previous = null;
        LinkNode next;

        while (curr != null) {
            next = curr.next;
            curr.next = previous;
            previous = curr;
            curr = next;
        }

        return previous;
    }

    LinkNode reverseLinkedListStack(LinkNode head) {
        Stack<LinkNode> stack = new Stack<>();

        LinkNode tmp = head;
        while(tmp.getNext() != null) {
            stack.push(tmp);
            tmp = tmp.getNext();
        }

        head = tmp;

        while (!stack.isEmpty()) {
            LinkNode pop = stack.pop();
            tmp.next = pop;
            tmp = pop;
        }
        tmp.next = null;

        return head;
    }
}
