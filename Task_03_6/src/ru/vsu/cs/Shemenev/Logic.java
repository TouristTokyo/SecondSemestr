package ru.vsu.cs.Shemenev;

public class Logic {
    protected static MySimpleDeque<String> deque (String[] cards) {
        MySimpleDeque<String> deckOfCards = new MySimpleDeque<>();
        for (int i = cards.length - 1; i >= 0; i--) {
            if (!deckOfCards.isEmpty()) {
                if (i == 0) {
                    deckOfCards.addFirst(cards[i]);
                    break;
                }
                String card = deckOfCards.pollLast();
                deckOfCards.addFirst(cards[i]);
                deckOfCards.addFirst(card);
            } else {
                deckOfCards.addFirst(cards[i]);
            }
        }
        return deckOfCards;
    }
}
