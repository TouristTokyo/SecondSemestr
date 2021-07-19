// Lab_6.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include<windows.h>
#include<fstream>
#include <string>
using namespace std;

struct Student {
    string name;
    Student* next;
    
    Student(string nameSt) : name(nameSt), next(nullptr) {};
};

struct List {
    Student* head = NULL;
    Student* tail = NULL;
    int size = 0;

    void addLast(string val) {
        Student* st = new Student(val);
        if (isEmpty()) {
            head = st;
            tail = st;
        }
        else {
            tail->next = st;
            tail = st;
        }
        size++;
    }

    void printAll() {
        if (isEmpty()) {
            cout << "Структура пуста"<<endl;
            return;
        }
        Student* curr = head;
        while (curr!= NULL) {
            cout << curr->name << endl;
            curr = curr->next;
        }
    }

    void addFirst(string val) {
        Student* st = new Student(val);
        if (isEmpty()) {
            head = st;
            tail = st;
        }
        else {
            st->next = head;
            head = st;
        }
        size++;
    }

    bool isEmpty() {
        return size == 0;
    }

    void removeAll() {
        size = 0;
        head = NULL;
        tail = NULL;
    }

    void removeFirst() {
        if (isEmpty()) {
            cout << "Структура пуста" << endl;
            return;
        }
        Student* st = head;
        head = st->next;
        if (head == NULL) {
            tail = NULL;
        }
        cout << st->name << " успешно удален(а) из начала структуры" << endl;
        delete st;
        size--;
    }

    void removeLast() {
        if (isEmpty()) {
            cout << "Структура пуста" << endl;
            return;
        }
        if (head == tail) {
            removeFirst();
            return;
        }
        Student* curr = head;
        while(curr->next!=tail){
            curr = curr->next;
        }
        curr->next = NULL;
        cout << tail->name << " успешно удален(а) из конца структуры" << endl;
        delete tail;
        tail = curr;
        size--;
    }

    void remove(string val) {
        if (isEmpty()) {
            cout << "Структура пуста" << endl;
            return;
        }
        if (head->name == val) {
            removeFirst();
        }
        else if (tail->name == val) {
            removeLast();
        }
        else {
            Student* curr = head->next;
            Student* parrent = head;
            if (curr == NULL) {
                cout << "Такого студента в структуре нет" << endl;
                return;
            }
            while (curr != tail) {
                if (curr->name == val) {
                    break;
                }
                parrent = curr;
                curr = curr->next;
            }
            if (curr == tail) {
                cout << "Такого студента в структуре нет" << endl;
                return;
            }
            parrent->next = curr->next;
            cout << val << " успешно удален(а) из структуры" << endl;
            delete curr;
            size--;
        }
    }

    void writeOutputFile() {
        ofstream out;
        out.open("C:\\Users\\пользователь\\Desktop\\Labs\\Lab_6\\students.txt");
        if (out.is_open()) {
            Student* curr = head;
            while (curr != NULL) {
                out << curr->name << endl;
                curr = curr->next;
            }
            cout << "Запись в файл прошла успешна" << endl;
        }
        else {
            cout << "Ошибка при записи в файл" << endl;
        }
        out.close();
    }
    
    void readInputFile(string fileName) {
        string line;
        ifstream in("C:\\Users\\пользователь\\Desktop\\Labs\\Lab_6\\"+fileName);
        if (in.is_open()) {
            while (getline(in, line)) {
                addLast(line);
            }
            cout << "Запись в структуру из файла просшла успешна" << endl;
        }
        else {
            cout << "Ошибка при открытии файла" << endl;
        }
        in.close();
    }
};

void printMenu() {
    cout << "Выберите цифру (действие), которое хотите осуществить: " << endl;
    cout << "1-Добавить в начало структуры;" << endl;
    cout << "2-Добавить в конец структуры;" << endl;
    cout << "3-Вывести всю структуру;" << endl;
    cout << "4-Удалить из струтктуры по имени;" << endl;
    cout << "5-Удалить из начала структуры;" << endl;
    cout << "6-Удалить из конца структуры;" << endl;
    cout << "7-Удалить всю структуру" << endl;
    cout << "8-Записать в файл (students.txt) всю структуру" << endl;
    cout << "9-Записать в структуру данные из файла" << endl;
    cout << "0-Выйти из программы." << endl;
    cout << "-----------------------------" << endl;
}

int main()
{   
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);
    system("color a");

    List* list = new List();
    int numDo = 1;
    string name;
    while (numDo) {
        printMenu();
        again: cout << "Ваш выбор: ";
        cin >> numDo;
        if (numDo < 0 || numDo>9) {
            cout << "Введите корректные данные!" << endl;
            goto again;
        }
        switch (numDo) {
        case 1:
            system("cls");
            cout << "Введите имя студента: ";
            cin >> name;
            cout << endl;
            list->addFirst(name);
            cout << name << " успешно добавлен(a) в начало структуры" << endl;
            break;
        case 2:
            system("cls");
            cout << "Введите имя студента: ";
            cin >> name;
            cout << endl;
            list->addLast(name);
            cout << name << " успешно добавлен(a) в конец структуры" << endl;
            break;
        case 3:
            system("cls");
            list->printAll();
            break;
        case 4:
            system("cls");
            cout << "Введите имя студента: ";
            cin >> name;
            cout << endl;
            list->remove(name);
            break;
        case 5:
            system("cls");
            cout << endl;
            list->removeFirst();
            break;
        case 6:
            system("cls");
            cout << endl;
            list->removeLast();
            break;
        case 7:
            system("cls");
            list->removeAll();
            cout << endl;
            cout << "Cтруктура успешно очищена" << endl;
            break;
        case 8:
            system("cls");
            list->writeOutputFile();
            break;
        case 9:
            system("cls");
            cout << "Введите название файла: ";
            cin >> name;
            list->readInputFile(name);
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
