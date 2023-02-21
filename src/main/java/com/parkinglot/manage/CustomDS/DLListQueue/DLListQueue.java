package com.parkinglot.manage.CustomDS.DLListQueue;

public class DLListQueue<T> {
    private Node<T> head;
    private Node<T> tail;

    public DLListQueue() {
        head.prev = null;
        head.next = tail;
        tail.next = null;
        tail.prev = head;
    }

    public void add(Node<T> element) {
        /*
         * This method adds element from the front as enqueue operation
         */

        element.next = head.next;
        element.prev = head;
        head.next.prev = element;

        head.next = element;
    }

    public void remove(Node<T> element) {
        /*
         * This method removes specific element from anywhere in list
         */
        element.prev.next = element.next;
        element.next.prev = element.prev;
        element.prev = null;
        element.next = null;
    }

    public Node<T> removeLast() {
        /*
         * This method removes the last element or first added element of list like
         * dequeue operation
         */
        if (tail.prev == head)
            return null;
        Node<T> temp = tail.prev;
        remove(temp);
        return temp;
    }
}
