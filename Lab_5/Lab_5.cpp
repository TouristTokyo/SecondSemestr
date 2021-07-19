// Lab_5.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include<windows.h>
#include<string>
#include<limits>
#include<regex>
using namespace std;

struct person {
    int id=-1; //ID
    string name=""; // ФИО
    int age=-1; // Возраст
    char gender=-1; // Пол
    string sw=""; // Переключатель
    union {
        struct {
            int numCourse; // Номер курса
            int numGroop; // Номер группы
        }student;
        struct {
            int countSubjects; // Кол-во предметов, которые преподаёт
            int countCoorse; // Кол-во курсов на которых преподаёт
        }teacher;
    };
};

void printMenu() { // Вывод меню
    cout << "Выберите цифру (действие), которое хотите осуществить: " << endl;
    cout << "1-Добавить в базу данных;" << endl;
    cout << "2-Удалить с базы данных по id;" << endl;
    cout << "3-Вывести всю базу данных;" << endl;
    cout << "4-Вывести информацию по id пользователя базы данных;" << endl;
    cout << "0-Выйти из программы." << endl;
    cout << "-----------------------------" << endl;
}

boolean addBD(person array[], int ind) {// Добавить в базу данных
    if (ind > 9) {
        cout << "Базза данных заполнена!";
        return false;
    }
    array[ind].id = ind + 1;
    cout << "Введите имя: ";
    cin >> array[ind].name;
    cout << "Введите возраст: ";
    cin >> array[ind].age;
    cout << "Введите пол: ";
    cin >> array[ind].gender;
    cout << "Введите должность (student/teacher): ";
    cin >> array[ind].sw;
    if (array[ind].sw == "student") {
        cout << "Введите номер курса: ";
        cin >> array[ind].student.numCourse;
        cout << "Введите номер группы: ";
        cin >> array[ind].student.numGroop;
    }
    else {
        cout << "Введите кол-во преподоваемых учебных дисциплин: ";
        cin >> array[ind].teacher.countSubjects;
        cout << "Введите кол-во курсов, которым читаются учебные дисциплины: ";
        cin >> array[ind].teacher.countCoorse;
    }
    cout << "Пользователь успешно добавлен!";
    return true;
}

void printOfId(person array[], int ind) { // Вывод по ID
    if (array[ind - 1].id==-1) {
        cout << "Такого пользователя не существует!" << endl;
        return;
    }
    cout << "ID: " << array[ind - 1].id<<endl;
    cout << "Имя:" << array[ind-1].name<<endl;
    cout << "Возраст: " << array[ind-1].age << endl;
    cout << "Пол: " << array[ind-1].gender << endl;
    cout << "Должность: " << array[ind-1].sw << endl;
    if (array[ind-1].sw == "student") {
        cout << "Номер курса: " << array[ind-1].student.numCourse << endl;
        cout << "Номер группы: " << array[ind-1].student.numGroop<<endl;
    }
    else {
        cout << "Кол-во читаемых учебных дисциплин: " << array[ind-1].teacher.countSubjects << endl;
        cout << "Кол-во курсов, которым читаются учебные дисциплины: " << array[ind-1].teacher.countCoorse << endl;
    }
}

void printAllBD(person array[]) { // Вывод всей базы данных
    for (int i = 1; i <=10; i++) {
        if (array[i - 1].id == -1) {
            cout << "NULL" << endl;
        }
        else {
            printOfId(array, i);
        }
        cout << "-----------------------------" << endl;
    }
}

boolean removeBD(person array[], int ind) { // Удалить по ID
    if(array[ind-1].id==-1){
        cout << "NULL! Пользователя не существует!";
        return false;
    }
    while (ind<10 && array[ind].id != -1) {
        array[ind - 1] = array[ind];
        array[ind - 1].id--;
        ind++;
    }
    array[ind - 1].id = -1;
    array[ind - 1].name = "";
    array[ind - 1].age = -1;
    array[ind - 1].gender = -1;
    array[ind - 1].sw = "";
    cout << "Удаление завершено успешно!";
    return true;
}

int main()
{
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);
    system("color a");

    int numDo = 10;
    person array[10]; // Массив на 10 человек (ниже (в цикле) есть проверки, чтобы не выйти за пределы массива)
    int currIndex = 0;
    int id;
    while (numDo) { // Пока не введем 0, то не выйдем из цикла (цикл бесконечный)
        printMenu();
        again: cout << "Ваш выбор: ";
        cin >> numDo;
        if (numDo < 0 || numDo>4) {
            cout << "Введите корректные данные!" << endl;
            goto again;
        }
        switch (numDo) {
        case 1:
            system("cls");
            if (addBD(array, currIndex)) {
                currIndex++;
            }
            break;
        case 2:
            system("cls");
            again2: cout << "Введите id пользователя: ";
            cin >> id;
            if (id < 1 || id>10) {
                cout << "Введите коректный id (1-10)!" << endl;
                goto again2;
            }
            cout << "-----------------------------" << endl;
            if (removeBD(array, id)) {
                currIndex--;
            }
            break;
        case 3:
            system("cls");
            printAllBD(array);
            break;
        case 4:
            system("cls");
            cin >> id;
            again3: cout << "Введите id пользователя: ";
            cin >> id;
            if (id < 1 || id>10) {
                cout << "Введите коректный id (1-10)!" << endl;
                goto again3;
            }
            cout << "-----------------------------" << endl;
            printOfId(array, id);
            break;
        }
        cout << endl;
        system("pause");
        system("cls");
    }
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
