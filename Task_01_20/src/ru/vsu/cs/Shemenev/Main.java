package ru.vsu.cs.Shemenev;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    protected static final Scanner in = new Scanner(System.in);
    private static final Pattern regex = Pattern.compile("ДИФ||АНУЕТ");

    public static void main(String[] args) {
        System.out.println("Что будем делать?\n 1-Считать сумму платежей в n-ый по счету месяц.\n 2-" +
                "Считать общую сумму платежей по кредиту.");
        int num = in.nextInt();
        if(num!= 1 && num!=2){
            System.out.println("Ошибка.\nВведите корректные данные.");
            return;
        }
        System.out.print("Введите сумму кредита: ");
        double sum = in.nextDouble();
        if(sum<0){
            System.out.println("Ошибка.\nВведите корректные данные.");
            return;
        }
        System.out.print("Введите число месяцов: ");
        int n = in.nextInt();
        if(n<=0){
            System.out.println("Ошибка.\nВведите корректные данные.");
            return;
        }
        System.out.print("Введите процентную ставку: ");
        double r = in.nextDouble();
        if(r<0){
            System.out.print("Ошибка.\nВведите корректные данные.");
            return;
        }
        System.out.print("Введите способ погашения(ДИФ/АНУЕТ): ");
        String name = in.next();
        Matcher matcher = regex.matcher(name);
        if(!matcher.matches()){
            System.out.println("Ошибка.\nВведите корректные данные.");
            return;
        }
        KreditCallculator krCall = new KreditCallculator(sum,n,r,name);
        if(num==1){
            System.out.println("Ответ: "+krCall.getSumMoth());
            krCall.setMoth(2);
        } else{
            System.out.println("Ответ: "+krCall.getSum());
        }
    }
}
