package ru.vsu.cs.Shemenev;

/**
 Реализовать нерекурсивный метод, который будет сравнивать эквивалентность двух двоичных деревьев.
 Примечание: пользоваться представлением дерева в виде скобочной последовательности запрещено.
 Подсказка: вероятно, необходимо реализовать параллельный нерекурсивный обход деревьев с использованием стека или очереди.
 */

public class Main {

    public static void main(String[] args) {
        SimpleTree<Integer> tree1 = new SimpleTree<>();
        tree1.insertNode(14);
        tree1.insertNode(10);
        tree1.insertNode(19);
        tree1.insertNode(16);
        tree1.insertNode(11);
        tree1.insertNode(9);
        tree1.insertNode(20);
        tree1.printSimpleTree(tree1.getRoot(), 0);
        System.out.println(tree1.searchMinValue(17));
    }
}
