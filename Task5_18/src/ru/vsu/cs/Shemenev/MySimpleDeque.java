package ru.vsu.cs.Shemenev;

import java.util.Iterator;

public class MySimpleDeque<T> implements MyQueue<T> {
    private class ListNode<T> {
        public T value;
        public ListNode next;

        public ListNode(T value, ListNode next) {
            this.value = value;
            this.next = next;
        }

        public ListNode(T value) {
            this(value, null);
        }
    }

    private ListNode<T> head = null;
    private ListNode<T> tail = null;
    private int count = 0;

    @Override
    public void addFirst(T val) {
        ListNode<T> temp = new ListNode<T>(val, head);
        if (count == 0) {
            tail = temp;
        }
        head = temp;
        count++;
    }

    @Override
    public void addLast(T val) {
        ListNode<T> temp = new ListNode<T>(val);
        if (count > 0) {
            tail.next = temp;
        } else {
            head = temp;
        }
        tail = temp;
        count++;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public T pollFirst() {
        ListNode<T> val = head;
        count--;
        if (count == 0) {
            tail = null;
        }
        head = head.next;
        return val.value;
    }

    @Override
    public T pollLast() {
        ListNode<T> val = tail;
        count--;
        if (count == 0) {
            tail = head = null;
        } else {
            tail = getIndex(count - 1);
            tail.next = null;
        }
        return val.value;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    @Override
    public T peekFirst() {
        return head.value;
    }

    @Override
    public T peekLast() {
        return tail.value;
    }

    @Override
    public int size() {
        return count;
    }

    private ListNode<T> getIndex(int index) {
        ListNode<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }
    public Iterator<T> iterator() {
        class LinkedListIterator implements Iterator<T> {
            ListNode<T> curr;

            public LinkedListIterator(ListNode<T> head) {
                curr = head;
            }

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T result = curr.value;
                curr = curr.next;
                return result;
            }
        }

        return new LinkedListIterator(head);
    }
}
