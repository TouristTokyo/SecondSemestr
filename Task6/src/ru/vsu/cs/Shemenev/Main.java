package ru.vsu.cs.Shemenev;

import java.util.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //System.out.println("--Реализация с помощью MySimpleHashMap--");
        //solve();
        System.out.println("--Реализация с помощью Map--");
        Map<String, StudentMarks> mapStudents = new HashMap<>();
        System.out.print("Введите кол-во студентов: ");
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            StudentMarks st = new StudentMarks();
            System.out.print("Введите имя студента: ");
            String name = scanner.next();
            System.out.print("Введите кол-во предметов у студента: ");
            int m = scanner.nextInt();
            for (int j = 0; j < m; j++) {
                System.out.print("Введите предмет: ");
                String subject = scanner.next();
                System.out.print("Ввдите оценку (зачёт/незачёт): ");
                String mark = scanner.next();
                st.map.put(subject, mark);
                mapStudents.put(name, st);
            }
        }
        Map<StudentMarks,List<String>> mapAns = new HashMap<>();
        for (Map.Entry<String, StudentMarks> item : mapStudents.entrySet()) {
            if (item.getValue().visited) {
                continue;
            }
            List<String> list = new ArrayList<>();
            list.add(item.getKey());
            for (Map.Entry<String, StudentMarks> item1 : mapStudents.entrySet()) {
                if (item.equals(item1) || item.getValue().map.size() != item1.getValue().map.size() || item1.getValue().visited) {
                    continue;
                }
                if (item.getValue().compareTo(item1.getValue())==0) {
                    item1.getValue().visited = true;
                    list.add(item1.getKey());
                }
            }
            mapAns.put(item.getValue(), list);
            item.getValue().visited = true;
        }
        System.out.println("--------------------------------");
        for (Map.Entry<StudentMarks, List<String>> curr : mapAns.entrySet()) {
            Set<String> subjects = curr.getKey().map.keySet();
            List<String> list = curr.getValue();
            for (String subject : subjects) {
                System.out.print(subject + " (" + curr.getKey().map.get(subject) + "); ");
            }
            System.out.println();
            for (String name:list){
                System.out.print(name+"; ");
            }
            System.out.println();
            System.out.println("--------------------------------");
        }
    }


    public static void solve() {
        MySimpleHashMap<String, StudentMarks> mySimpleHashMapStudents = new MySimpleHashMap<>(1000);
        System.out.print("Введите кол-во студентов: ");
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            StudentMarks st = new StudentMarks();
            System.out.print("Введите имя студента: ");
            String name = scanner.next();
            System.out.print("Введите кол-во предметов у студента: ");
            int m = scanner.nextInt();
            for (int j = 0; j < m; j++) {
                System.out.print("Введите предмет: ");
                String subject = scanner.next();
                System.out.print("Введите оценку (зачёт/незачёт): ");
                String mark = scanner.next();
                st.map.put(subject, mark);
                mySimpleHashMapStudents.put(name, st);
            }
        }
        MySimpleHashMap<StudentMarks, List<String>> mapAnswer = new MySimpleHashMap<>(1000);
        for (Map.Entry<String, StudentMarks> item : mySimpleHashMapStudents.entrySet()) {
            if (item.getValue().visited) {
                continue;
            }
            List<String> list = new ArrayList<>();
            list.add(item.getKey());
            for (Map.Entry<String, StudentMarks> item1 : mySimpleHashMapStudents.entrySet()) {
                if (item.equals(item1) || item.getValue().map.size() != item1.getValue().map.size() || item1.getValue().visited) {
                    continue;
                }
                if (item.getValue().compareTo(item1.getValue())==0) {
                    item1.getValue().visited = true;
                    list.add(item1.getKey());
                }
            }
            mapAnswer.put(item.getValue(), list);
            item.getValue().visited = true;
        }
        System.out.println("--------------------------------");
        for (Map.Entry<StudentMarks, List<String>> curr : mapAnswer.entrySet()) {
            Set<String> subjects = curr.getKey().map.keySet();
            List<String> list = curr.getValue();
            for (String subject : subjects) {
                System.out.print(subject + " (" + curr.getKey().map.get(subject) + "); ");
            }
            System.out.println();
            for (String name:list){
                System.out.print(name+"; ");
            }
            System.out.println();
            System.out.println("--------------------------------");
        }
    }
}
