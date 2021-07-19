#include<iostream>
#include <string>
#include <windows.h>
#include <regex>
using namespace std;

void paintField(string field[3][7]) {
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 7; j++) {
			cout << field[i][j];
		}
		cout << endl;
	}
	cout << endl;
}

int searchRow(string field[3][7], int n) {
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 7; j++) {
			if (atoi(field[i][j].c_str()) == n) {
				return i;
			}
		}
	}
	return -1;
}

int searchCol(string field[3][7], int n) {
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 7; j++) {
			if (atoi(field[i][j].c_str()) == n) {
				return j;
			}
		}
	}
	return -1;
}

bool checkWinPlayer1(string field[3][7]) {//Победа крестиков
	for (int i = 0; i < 3; i++) {//Проверка по горизонтали
		if (field[i][1] == field[i][3] && field[i][5] == field[i][1] && field[i][1]=="X") {
			return true;
		}
	}
	for (int i = 1; i < 7; i+=2) {//Проверка  по вертикали
		if (field[0][i] == field[1][i] && field[2][i] == field[0][i] && field[0][i] == "X") {
			return true;
		}
	}
	if ((field[0][1] == field[1][3] && field[1][3] == field[2][5] && field[0][1] == "X") ||
	(field[0][5] == field[1][3] && field[1][3] == field[2][1] && field[0][5] == "X")) {//Победа по диагонали
		return true;
	}
	return false;
}

bool checkWinPlayer2(string field[3][7]) {//Победа ноликов
	for (int i = 0; i < 3; i++) {//Проверка по горизонтали
		if (field[i][1] == field[i][3] && field[i][5] == field[i][1] && field[i][1] == "O") {
			return true;
		}
	}
	for (int i = 1; i < 7; i += 2) {//Проверка  по вертикали
		if (field[0][i] == field[1][i] && field[2][i] == field[0][i] && field[0][i] == "O") {
			return true;
		}
	}
	if ((field[0][1] == field[1][3] && field[1][3] == field[2][5] && field[0][1] == "O") ||
		(field[0][5] == field[1][3] && field[1][3] == field[2][1] && field[0][5] == "O")) {//Победа по диагонали
		return true;
	}
	return false;
}

bool checkFullField(string field[3][7]) {//Победила дружба:) (Ничья)
	regex regular("[0-9]");
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 7; j++) {
			if (regex_match(field[i][j], regular)) {
				return false;
			}
		}
	}
	return true;
}

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);
	string  player1, player2;
	cout << "Имя первого игрока: ";
	cin >> player1;
	cout << "Имя второго игрока: ";
	cin >> player2;
	string field[3][7] = {
		{"|","1","|","2","|","3","|"},
		{"|","4","|","5","|","6","|"},
		{"|","7","|","8","|","9","|"}
	};
	for (int i = 0;; i++) {
		int num_1, num_2;
		paintField(field);
		label1:
		cout << player1 << ", введите цифру куда хотите сходить: ";
		cin >> num_1;
		if (searchRow(field, num_1) == -1) {
			cout << "Введите цифру, которая доступна на игровом поле";
			cout << endl;
			goto label1;
		}
		field[searchRow(field, num_1)][searchCol(field, num_1)] = "X";
		paintField(field);
		if (checkWinPlayer1(field)) {
			cout << "Победил " << player1;
			cout << endl << endl;
			break;
		}
		else if (checkFullField(field)) {
			cout << "Ничья";
			cout << endl << endl;
			break;
		}
		label2:
		cout << player2 << ", введите цифру куда хотите сходить: ";
		cin >> num_2;
		if (searchRow(field, num_2) == -1) {
			cout << "Введите цифру, которая доступна на игровом поле";
			cout << endl;
			goto label2;
		}
		field[searchRow(field, num_2)][searchCol(field, num_2)] = "O";
		if (checkWinPlayer2(field)) {
			cout << "Победил " << player2;
			cout << endl << endl;
			break;
		}
	}
}