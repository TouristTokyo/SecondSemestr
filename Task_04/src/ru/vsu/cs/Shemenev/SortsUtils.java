package ru.vsu.cs.Shemenev;

public class SortsUtils {
    /**
     * Здесь собраны некоторые сортировки для массивов целых чисел Java.
     * Сортровки O(N^2): bubbleSort; selectionSort; insertSort.
     * Сортировки O(N*log(N)): quickSort; mergeSort, heapSort.
     * Сортировки O(Nk): countingSort, radixSort.
     * N - кол-во входных данных.
     * k - максимальное значение элемента (countingSort, radixSort).
     */
    public static void bubbleSort(int[] arr) { // Сортировка пузырьком
        boolean isSort = false;
        while (!isSort) {
            isSort = true;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] < arr[i - 1]) {
                    swap(arr, i - 1, i);
                    isSort = false;
                }
            }
        }
    }

    public static void quickSort(int[] arr, int from, int to) { // Быстрая сортировка
        if (from < to) {
            int part = partition(arr, from, to);
            quickSort(arr, from, part - 1);
            quickSort(arr, part, to);
        }

    }

    private static int partition(int[] arr, int from, int to) {
        int leftIndex = from;
        int rightIndex = to;
        int pilot = arr[from];
        while (leftIndex <= rightIndex) {
            while (arr[leftIndex] < pilot) {
                leftIndex++;
            }
            while (arr[rightIndex] > pilot) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                swap(arr, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    public static void selectionSort(int[] arr) { // Сортировка выбором
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    public static void insertSort(int[] arr) { // Сортировка вставками
        for (int i = 1; i < arr.length; i++) {
            int value = arr[i];
            int j = i - 1;
            while (j >= 0 && value < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = value;
        }
    }

    public static int[] mergeSort(int[] arr) { // Сортировка слиянием
        if (arr.length < 2) {
            return arr;
        }
        int[] arrayLeft = new int[arr.length / 2];
        System.arraycopy(arr, 0, arrayLeft, 0, arrayLeft.length);
        int[] arrayRight = new int[arr.length - arr.length / 2];
        System.arraycopy(arr, arr.length / 2, arrayRight, 0, arrayRight.length);
        arrayLeft = mergeSort(arrayLeft);
        arrayRight = mergeSort(arrayRight);
        return merge(arrayLeft, arrayRight);
    }

    private static int[] merge(int[] arr1, int[] arr2) {
        int i = 0, j = 0, k = 0;
        int[] newArr = new int[arr1.length + arr2.length];
        while (i < arr1.length && j < arr2.length) {
            newArr[k++] = (arr1[i] > arr2[j]) ? arr2[j++] : arr1[i++];
        }
        while (i < arr1.length) {
            newArr[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            newArr[k++] = arr2[j++];
        }
        return newArr;
    }

    public static void shellSort(int[] arr) { // Сортировка Шелла
        int partition = arr.length / 2;
        while (partition >= 1) {
            for (int right = 0; right < arr.length; right++) {
                for (int i = right - partition; i >= 0; i -= partition) {
                    if (arr[i + partition] < arr[i]) {
                        swap(arr, i, i + partition);
                    }
                }
            }
            partition /= 2;
        }
    }

    public static void countingSort(int[] arr) { // Сортировка подсчётом (Поразрядная сортировка)
        int maxValue = searchMax(arr);
        int[] countArray = new int[maxValue + 1];
        for (int val : arr) {
            countArray[val]++;
        }
        int index = 0;
        for (int i = 0; i < countArray.length; i++) {
            for (int j = 0; j < countArray[i]; j++) {
                arr[index++] = i;
            }
        }
    }
    public static void RadixSort(int[] arr, int digitBased){ // Сортировка подсчётом (Поразрядная сортировка) №2
        int max = searchMax(arr);
        for(int exp = 1; max/exp >0; exp*=digitBased){
            countSort(arr,exp,digitBased);
        }
    }
    private static void countSort(int[] arr,int exp, int digitBased){
        int[] output = new int[arr.length];
        int[] count = new int[digitBased];
        for(int i =0; i< arr.length;i++){
            int index = (arr[i]/exp)%digitBased;
            count[index]++;
        }
        for (int i = 1; i<count.length;i++){
            count[i]+=count[i-1];
        }
        for(int i = arr.length-1; i>-1; i--){
            int index = (arr[i]/exp)%digitBased;
            output[count[index]-1] = arr[i];
            count[index]--;
        }
        System.arraycopy(output,0,arr,0,arr.length);
    }

    public static void gnomeSort(int[] arr) { // Гномья сортировка
        int index = 0;
        int nextIndex = 1;
        while (index < arr.length - 1) {
            if (arr[index] > arr[index + 1]) {
                swap(arr, index, index + 1);
                index--;
                if (index < 0) {
                    index = nextIndex;
                    nextIndex++;
                }
            } else {
                index = nextIndex;
                nextIndex++;
            }
        }
    }

    public static <T> void heapSort(int[] arr /*T[] data*/) { // Сортировка кучей (Пирамидальная сортировка)
        int heapSize = arr.length;
        for (int i = heapSize / 2; i >= 0; i--) {
            siftDown(arr, i, heapSize);
        }
        while (heapSize > 1) {
            swap(arr, heapSize - 1, 0);
            heapSize--;
            siftDown(arr, 0, heapSize);
        }
    }

    private static <T>  void siftDown(int[] arr, int siftIndex, int size) {
        int value = arr[siftIndex];
        while (true) {
            int childIndex = 2 * siftIndex + 1;
            if (childIndex >= size) {
                break;
            }
            if (childIndex + 1 < size && arr[childIndex + 1] > arr[childIndex]) {
                childIndex++;
            }
            if (value > arr[childIndex]) {
                break;
            }
            arr[siftIndex] = arr[childIndex];

            siftIndex = childIndex;
        }
        arr[siftIndex] = value;

    }

    private static <T>  void swap(int[] arr, int index1, int index2) { // Обмен элементов
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    private static int searchMax(int[] arr){
        int max = arr[0];
        for(int i = 0; i< arr.length; i++){
            if(arr[i]>max){
                max = arr[i];
            }
        }
        return max;
    }
}
