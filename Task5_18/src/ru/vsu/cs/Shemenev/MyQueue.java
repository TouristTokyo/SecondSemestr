package ru.vsu.cs.Shemenev;

import java.util.Iterator;

public interface MyQueue<T>{
    void addFirst(T val); // Добавляет элемент в начало очереди
    void addLast(T val); // Добавляет элемент в конец очереди
    boolean isEmpty(); // Проверяет есть ли элементы в очереди
    T pollFirst(); // Возвращает с удалением первый элемент в очереди
    T pollLast(); // Возвращает с удалением последний элемент в очереди
    void clear(); // Очищает всю очередь
    T peekFirst(); // Возвращает без удаления первый элемент в очереди
    T peekLast(); // Возвращает без удаленеи последний элемент в очереди
    int size(); // Возвращает размер очереди
}
