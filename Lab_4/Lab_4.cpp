// Lab_4.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include<Windows.h>
using namespace std;

int **createMatrix(int **matrix, int const row, int const col) {
    int** newMatrix = new int* [row / 2];
    int index = -1;
    for (int i = 0; i < row && index<row/2; i++) {
        if (i % 2 != 0) {
            index++;
            newMatrix[index] = new int[col];
            for (int j = 0; j < col; j++) {
                newMatrix[index][j] = matrix[i][j];
            }
        }
    }
    return newMatrix;
}
int main()
{   
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);
    int row, col, newRow, val, index;
    cout << "Введите кол-во строк в матрице: ";
    cin >> row;
    cout << "Введите ко-во столбцов: ";
    cin >> col;
    int** matrix = new int* [row];
    cout << "Введите матрицу"<<endl;
    for (int i = 0; i < row; i++) {
        matrix[i] = new int[col];
        for (int j = 0; j < col; j++) {
            cin >> val;
            matrix[i][j] = val;
        }
    }
    cout << "----------Результат----------" << endl;
    int** newMatrix = createMatrix(matrix, row, col);
    for (int i = 0; i < row/2; i++) {
        for (int j = 0; j < col; j++) {
            cout << newMatrix[i][j]<<" ";
        }
        cout << endl;
    }
    for (int i = 0; i < row; i++) {
        delete matrix[i];
        if (i < row/2) {
            delete newMatrix[i];
        }
    }
    //printMatrix(matrix, row, col);
    delete matrix;
    delete newMatrix;
    cout << endl << endl;
    system("pause");
}

// Запуск программы: CTRL+F5 или меню "Отладка" > "Запуск без отладки"
// Отладка программы: F5 или меню "Отладка" > "Запустить отладку"

// Советы по началу работы 
//   1. В окне обозревателя решений можно добавлять файлы и управлять ими.
//   2. В окне Team Explorer можно подключиться к системе управления версиями.
//   3. В окне "Выходные данные" можно просматривать выходные данные сборки и другие сообщения.
//   4. В окне "Список ошибок" можно просматривать ошибки.
//   5. Последовательно выберите пункты меню "Проект" > "Добавить новый элемент", чтобы создать файлы кода, или "Проект" > "Добавить существующий элемент", чтобы добавить в проект существующие файлы кода.
//   6. Чтобы снова открыть этот проект позже, выберите пункты меню "Файл" > "Открыть" > "Проект" и выберите SLN-файл.
