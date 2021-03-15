package ru.vsu.cs.Shemenev;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameMain().setVisible(true);
            }
        });*/
        int N = input.nextInt();
        int[] cards = new int[N]; // Вводим кол-во карточек
        for (int i = 0; i < N; i++) {
            cards[i] = input.nextInt(); // Вводим сами карточки
        }
        Deque<Integer> deckOfCards = new LinkedList<>();
        for (int i = N - 1; i >= 0; i--) {
            if (!deckOfCards.isEmpty()) {
                if (i == 0) {
                    deckOfCards.addFirst(cards[i]);
                    break;
                }
                Integer card = deckOfCards.pollLast();  // Перекладываем карточку
                deckOfCards.addFirst(cards[i]);  // Перекладываем карточку
                deckOfCards.addFirst(card);  // Перекладываем карточку
            } else {
                deckOfCards.addFirst(cards[i]);
            }
        }
        System.out.println(deckOfCards);
        System.out.println("Начальное положение в колоде:");
        for (Integer card : deckOfCards) {
            System.out.println(card);
        } // Реализация задачи с помощью очереди из библиотеки Java
    }
}
