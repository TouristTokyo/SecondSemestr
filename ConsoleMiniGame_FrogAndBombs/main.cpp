#include<iostream>
#include<Windows.h>
#include<string>
using namespace std;

void paintField(string field[10][6]) {
	cout << "\t       FINISH"<<endl;
	for (int i = 0; i < 10; i++) {
		for (int j = 0; j < 6; j++) {
			cout << "| " << field[i][j] << " |"<<" ";
		}
		if (i == 9) {
			cout << endl;
			cout << "\t       START" << endl;
		}
		cout << endl<<endl;
	}
}

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);
	string field[10][6];
	for (int i = 9; i >= 0; i--) {
		int val = 1;
		for (int j = 0; j < 6; j++) {
			field[i][j] = to_string(val);
			val++;
		}
	}
	for (int countRun = 9; countRun >= 0; countRun--) {
		paintField(field);
		int num1, num2, num3, supRand, numUser;
		srand(time(NULL));
		supRand = 6;
		num1 = rand() % supRand + 1;
	over1:
		num2 = rand() % supRand + 1;
		if (num2 == num1) {
			goto over1;
		}
	over2:
		num3 = rand() % supRand + 1;
		if (num3 == num1 || num3 == num2) {
			goto over2;
		}
	start:
		cout << "Ââåäèòå öèôðó, êóäà õîòèòå ïðûãíóòü: ";
		cin >> numUser;
		if (numUser > 6 || numUser < 1) {
			if (numUser == 23072020) {
				cout << num1 << endl << num2 << endl << num3 << endl;
			}
			goto start;
		}
		else if (numUser == num1 || numUser == num2 || numUser == num3) {
			field[countRun][numUser - 1] = "X_X";
			if (countRun != 9) {
				for (int j = 0; j < 6; j++) {
					field[countRun + 1][j] = "+";
				}
			}
			paintField(field);
			cout << "ÂÛ ÏÐÎÈÃÐÀËÈ!" << endl;
			break;
		}
		else {
			field[countRun][numUser - 1] = "#";
			if (countRun != 9) {
				for (int j = 0; j < 6; j++) {
					field[countRun + 1][j] = "+";
				}
			}
			paintField(field);
			if (countRun == 0) {
				cout << "ÂÛ ÂÛÈÃÐÀËÈ!" << endl;
			}
		}
	}
}