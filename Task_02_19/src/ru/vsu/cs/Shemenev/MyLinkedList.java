package ru.vsu.cs.Shemenev;


public class MyLinkedList<T> {
    private class HelpList<T> {
        public T value;
        public HelpList<T> next;

        public HelpList(T value, HelpList<T> next) {
            this.value = value;
            this.next = next;
        }

        public HelpList(T value) {
            this(value, null);
        }
    }

    private HelpList<T> start = null;
    private HelpList<T> end = null;
    private int count = 0;

    public void addFirst(T value) {
        start = new HelpList<>(value, start);
        if (count == 0) {
            end = start;
        }
        count++;
    }

    public void addLast(T value) {
        HelpList<T> temp = new HelpList<>(value);
        if (count > 0) {
            end.next = temp;
        } else {
            start = temp;
        }
        end = temp;
        count++;
    }

    public boolean isEmpty() {
        if (count == 0) {
            return true;
        }
        return false;
    }

    public void removeFirst() {
        if (!isEmpty()) {
            start = start.next;
            count--;
            if (count == 0) {
                end = null;
            }
        }
    }

    public void removeLast() {
        if (!isEmpty()) {
            count--;
            if (count == 0) {
                start = end = null;
            } else {
                end = getIndex(count - 1);
                end.next = null;
            }
        }
    }

    public void clear() {
        start = end = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    public T get(int index) {
        return getIndex(index).value;
    }

    private HelpList<T> getIndex(int index) {
        HelpList<T> curr = start;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    public void set(T value, int index) {
        if (!isEmpty()) {
            HelpList<T> curr = getIndex(index);
            curr.value = value;
        }
    }

    public void replace(int ind1, int ind2) {
        int index1 = Math.min(ind1, ind2);
        int index2 = Math.max(ind1, ind2);
        if (!isEmpty() && index1 > -1 && index1 < count && index2 < count && count != 1 && index1 != index2) {
            HelpList<T> valLeft1 = getIndex(index1 - 1);
            HelpList<T> val1 = getIndex(index1);
            HelpList<T> valRight1 = getIndex(index1 + 1);
            HelpList<T> valLeft2 = getIndex(index2 - 1);
            HelpList<T> val2 = getIndex(index2);
            HelpList<T> valRight2 = getIndex(index2 + 1);
            if (index1 != 0) {
                valLeft1.next = val2;
            } else {
                start = val2;
            }
            if (valRight1.equals(val2)) {
                valRight1 = val1;
            } else {
                valLeft2.next = val1;
            }
            val2.next = valRight1;
            if (index2 == count - 1) {
                end = val1;
                val1.next = null;
            } else {
                val1.next = valRight2;
            }
        }
    }

    public void toString(MyLinkedList<T> list) {
        for (int i = 0; i < count; i++) {
            System.out.print((i != count - 1) ? list.get(i) + " ( " + list.getIndex(i).next.value + " ) " + " --> " :
                    list.get(i) + " ( " + list.getIndex(i).next + " ) ");
        }
        System.out.println();
    }
}