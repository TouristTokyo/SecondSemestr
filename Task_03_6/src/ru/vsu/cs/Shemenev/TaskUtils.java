package ru.vsu.cs.Shemenev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskUtils {
    protected static String[] readFile(File filename) throws FileNotFoundException {
        String[] input = new String[0];
        Scanner scanner = new Scanner(filename);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            input = line.split(", ");
        }
        return input;
    }

    protected static boolean check(String[] array) {
        Pattern pattern = Pattern.compile("(-)?[0-9]+");
        for (int i = 0; i < array.length; i++) {
            Matcher matcher = pattern.matcher(array[i]);
            if (!matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    protected static String[][] arrayDeckOfCards(MySimpleDeque<String> deque) {
        String[][] arrayDeckOfCards = new String[deque.size()][1];
        int i = 0;
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()){
            arrayDeckOfCards[i][0] = iterator.next();
            i++;
        }
        return arrayDeckOfCards;
    }
}
