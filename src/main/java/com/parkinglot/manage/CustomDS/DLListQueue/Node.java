package com.parkinglot.manage.CustomDS.DLListQueue;

public class Node<T> {
    T val;
    Node<T> prev;
    Node<T> next;

    public Node() {
    }

    public Node(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }
}
