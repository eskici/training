package com.example.hackerrank.datastructure.linkedlist.reverse;

public class LinkNode {

    public LinkNode(Integer value, LinkNode next) {
        this.value = value;
        this.next = next;
    }

    public Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LinkNode getNext() {
        return next;
    }

    public void setNext(LinkNode next) {
        this.next = next;
    }

    public LinkNode next;

}
