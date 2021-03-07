package ru.vsu.cs.Shemenev;

import java.io.File;
import java.io.FileNotFoundException;
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

    protected static MyLinkedList<String> list(String[] inputArray) {
        MyLinkedList list = new MyLinkedList();
        for (int i = 0; i < inputArray.length; i++) {
            list.addLast(inputArray[i]);
        }
        return list;
    }

    protected static String[] newArray(MyLinkedList<String> list) {
        String[] newArray = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            newArray[i] = list.get(i);
        }
        return newArray;
    }

    protected static boolean check(String[] array){
        Pattern pattern = Pattern.compile("(-)?[0-9]+");
        for(int i = 0; i< array.length;i++){
            Matcher matcher = pattern.matcher(array[i]);
            if(!matcher.matches()){
                return true;
            }
        }
        return false;
    }

}
