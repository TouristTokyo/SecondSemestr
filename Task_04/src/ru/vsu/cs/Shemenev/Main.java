package ru.vsu.cs.Shemenev;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.print("Введите длину массивов: ");
        int n = scanner.nextInt();
        int[] array = new int[n];
        boolean[] fixed = new boolean[n];
        for(int i = 0; i< n; i++){
            array[i] = scanner.nextInt();
            fixed[i] = scanner.nextBoolean();
        }
        System.out.println(Arrays.toString(array));
        quickSortTask(array,0,array.length-1,fixed);
        System.out.println(Arrays.toString(array));
    }

    private static void quickSortTask(int[] arr, int from, int to, boolean[] fixed ){
        if(from<to){
            int part = partition(arr,from,to,fixed);
            if(part==-1){
                return;
            }
            quickSortTask(arr,from,part-1,fixed);
            quickSortTask(arr,part,to,fixed);
        }
    }
    private static int partition(int[] arr, int from, int to, boolean[] fixed){
        int rightIndex = to;
        while (fixed[from]){
            from++;
            if(from==to){
                return -1;
            }
        }
        int leftIndex = from;
        int element = arr[from];
        while (leftIndex<=rightIndex){
            while (leftIndex<arr.length && arr[leftIndex]<element && !fixed[leftIndex]){
                leftIndex++;
            }
            while (rightIndex>-1 && arr[rightIndex]>element && !fixed[rightIndex]){
                rightIndex--;
            }
            if(leftIndex<=rightIndex){
                if(!fixed[leftIndex] && !fixed[rightIndex]){
                    swap(arr,leftIndex,rightIndex);
                    leftIndex++;
                    rightIndex--;
                }
                else {
                    if(fixed[leftIndex]){
                        leftIndex++;
                    }
                    if(fixed[rightIndex]){
                        rightIndex--;
                    }
                }
            }
        }
        if(leftIndex==to+1){
            return -1;
        }
        return leftIndex;
    }
    private static void swap(int[] arr, int index1, int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
