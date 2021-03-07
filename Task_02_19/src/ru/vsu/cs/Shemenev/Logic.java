package ru.vsu.cs.Shemenev;

public class Logic {

    protected static MyLinkedList<String> newList(MyLinkedList<String> list) {
        list.toString(list);
        int indexMin = 0;
        int min = Integer.parseInt(list.get(indexMin));
        int indexMax = list.size() - 1;
        int max = Integer.parseInt(list.get(indexMax));
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i)) < min) {
                min = Integer.parseInt(list.get(i));
                indexMin = i;
            } else if (Integer.parseInt(list.get(i)) >= max) {
                max = Integer.parseInt(list.get(i));
                indexMax = i;
            }
        }
        list.replace(indexMin, indexMax);
        list.toString(list);
        System.out.println("-------------------------------------------------------------------------------------");
        return list;
    }
}
