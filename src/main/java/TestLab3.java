// Siaod_2.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <vector>
#include <chrono>
#include <Windows.h>
        using namespace std;
        using namespace std::chrono;

        vector <int> key_arr;

        bool delete_value_from_file(string value) {
        vector<string> buff;
        string str;
        ifstream fin("Test.txt");
        getline(fin, str);
        while (!fin.eof())
        {
        buff.push_back(str);
        getline(fin, str);
        }
        for (int i = 0; i < buff.size(); ++i) {
        if (buff[i] == value) {
        buff.erase(buff.begin() + i);
        }
        }
        fin.close();
        ofstream fout("Test.txt");
        for (int i = 0; i < buff.size(); ++i) {
        fout << buff[i] << endl;
        }
        fout.close();
        return true;
        }

        string getKey(string str) {
        int x = 0;
        string res;
        for (int i = 0; i < str.size(); ++i) {
        if (str[i] == '/') {
        x++;
        continue;
        }
        if (x == 1) {
        res += str[i];
        }
        }
        return res;
        }

        int HashFunction(const string& str, int size, int key) {
        int hash_res = 0;
        string s = getKey(str);// получения строки-ключа
        for (int i = 0; i < s.size(); ++i) {
        hash_res += (key * hash_res + s[i]) % size;
        }
        hash_res = (hash_res * 2 + 1) % size;
        return hash_res;
        }

        struct HashFunction1
        {
        int operator()(const string& str, int size)const {
        return(HashFunction(str, size, size - 1));
        }
        };

        struct HashFunction2
        {
        int operator()(const string& str, int size)const {
        return(HashFunction(str, size, size + 1));
        }
        };

        template<class T, class THash1 = HashFunction1, class THash2 = HashFunction2>
class HashTable
{
    public:
    HashTable() {
        buffer_size = table_size;
        size = 0;
        size_all = 0;
        arr = new Node * [buffer_size];
        for (int i = 0; i < buffer_size; ++i) {
            arr[i] = nullptr;
        }
    }
    ~HashTable() {
    for (int i = 0; i < buffer_size; ++i) {
        if (arr[i]) {
            delete arr[i];
        }
        delete[] arr;
    }
}

    void Resize() {
        key_arr.clear();
        int past_buffer_size = buffer_size;
        buffer_size *= 2;
        size_all = 0;
        size = 0;
        Node** arr2 = new Node * [buffer_size];
        for (int i = 0; i < buffer_size; ++i) {
            arr2[i] = nullptr;
        }
        swap(arr, arr2);

        for (int i = 0; i < past_buffer_size; ++i) {
            if (arr2[i] && arr2[i]->state) {
                Add(arr2[i]->value);
            }
        }
        for (int i = 0; i < past_buffer_size; ++i) {
            if (arr2[i]) {
                delete arr2[i];
            }
        }
        delete[] arr2;
    }

    void Rehash() {
        key_arr.clear();
        size_all = 0;
        size = 0;
        Node** arr2 = new Node * [buffer_size];
        for (int i = 0; i < buffer_size; ++i) {
            arr2[i] = nullptr;
        }
        swap(arr, arr2);

        for (int i = 0; i < buffer_size; ++i) {
            if (arr2[i] && arr2[i]->state) {
                Add(arr2[i]->value);
            }
        }
        for (int i = 0; i < buffer_size; ++i) {
            if (arr2[i])
                delete arr2[i];
        }
        delete[] arr2;
    }
    bool Find(T& value, const THash1& hash1 = THash1(), const THash2& hash2 = THash2()) {
    int h1 = hash1(value, buffer_size); // получения результата 1-ой функции
    int h2 = hash2(value, buffer_size);// получение резултата 2-ой функуции
    int i = 0;
    while (arr[h1] != nullptr && i < buffer_size)
    {
        if (arr[h1]->value == value && arr[h1]->state) {
        return true; //Такой элемент есть
    }
        h1 = (h1 + h2) % buffer_size; // след шаг
        ++i;
    }
    return false;
}

    bool getElementByKey(int key, string& str1, const THash1& hash1 = THash1(), const THash2& hash2 = THash2()) {
    if (arr[key]) {
        if ((arr[key]->state)) {
            str1 = arr[key]->value;
            return true;
        }
            else
        {
            return false;
        }
    }
    else
    {
        return false;
    }
}

    bool Remove(const T& value, const THash1& hash1 = THash1(), const THash2& hash2 = THash2()) {
    int h1 = hash1(value, buffer_size);
    int h2 = hash2(value, buffer_size);
    int i = 0;
    while (arr[h1] != nullptr && i < buffer_size)
    {
        if (arr[h1]->value == value && arr[h1]->state) {
        arr[h1]->state = false;
        --size;
        delete_value_from_file(value);
        return true;
    }
        h1 = (h1 + h2) % buffer_size;
        ++i;
    }
    return false;
}

    bool Add(const T& value, const THash1& hash1 = THash1(), const THash2& hash2 = THash2()) {
    if (size + 1 > int(rehash_size * buffer_size))// Проверка на то нужно ли увеличить место
    Resize();
        else if (size_all > 2 * size)// проверка на то не многовато ли удалённых элемнтов
        Rehash();
    int h1 = hash1(value, buffer_size);
    int h2 = hash2(value, buffer_size);
    bool flag = false;
    int first_deleted = -1, i = 0;
    while (arr[h1] != nullptr && i < buffer_size)
    {
        if (arr[h1]->value == value && arr[h1]->state) {
        return false;// элемент уже есть
    }
        flag = true;
        if (!arr[h1]->state && first_deleted == -1) { // проверка на занятость позиции удалённым элементом
        first_deleted = h1;
        flag = false;
    }
        h1 = (h1 + h2) % buffer_size;
        ++i;
    }
    if (first_deleted == -1) {// проверка новый элемент надо создать или же изменить удалённый
        arr[h1] = new Node(value);
        ++size_all;
        if (flag) {
            arr[h1]->second_hash = true;
        }
        key_arr.push_back(h1);
    }
    else {
        arr[first_deleted]->value = value;
        arr[first_deleted]->state = true;
        key_arr.push_back(first_deleted);
    }
    ++size;
    return true;
}
    bool Print() {
        for (int i = 0; i < buffer_size; ++i) {
            if (arr[i] != nullptr && arr[i]->state)
            cout << "[" << i << "] " << arr[i]->value << endl;
        }
        return true;
    }

    int getBufferSize() {
        return buffer_size;
    }
    private:
        const int table_size = 8; // изначальный размер
    const double rehash_size = 0.75;// коэффицент при котором долна увеличиваться таблица.
    struct Node
    {
        T value;
        bool state;
        bool second_hash;
        Node(const T& value_) : value(value_), state(true), second_hash(false) {}
    };
    Node** arr;//массив с структурами Nodе
    int size;// Размер реального кол-ва элементов
    int buffer_size;// размер массива
    int size_all;//кол-во всех элементов считая удалённые
};


int main()
        {
        setlocale(LC_ALL, "Russian");
        SetConsoleCP(1251);
        SetConsoleOutputCP(1251);
        ifstream fin("C:\Text.txt");
        string str;
        HashTable<string>* hashT = new HashTable<string>();
        getline(fin, str);
        while (!fin.eof())
        {
        hashT->Add(str);
        getline(fin, str);
        }
        if (str != "")
        hashT->Add(str);
        hashT->Print();
        string str1;
        cout << endl;
        auto begin1 = chrono::steady_clock::now();
        hashT->getElementByKey(0, str1);
        auto end1 = chrono::steady_clock::now();
        auto elapsed_ms1 = chrono::duration_cast<chrono::milliseconds>(end1 - begin1);
        cout << endl;
        cout << "The time of first elem: " << elapsed_ms1.count() << " ms\n";
        cout << endl;
        auto begin2 = chrono::steady_clock::now();
        hashT->getElementByKey(10, str1);
        auto end2 = chrono::steady_clock::now();
        auto elapsed_ms2 = chrono::duration_cast<chrono::milliseconds>(end2 - begin2);
        cout << endl;
        cout << "The time of first elem: " << elapsed_ms2.count() << " ms\n";
        cout << endl;
        auto begin3 = chrono::steady_clock::now();
        hashT->getElementByKey(5, str1);
        auto end3 = chrono::steady_clock::now();
        auto elapsed_ms3 = chrono::duration_cast<chrono::milliseconds>(end3 - begin3);
        cout << endl;
        cout << "The time of first elem: " << elapsed_ms3.count() << " ms\n";
        cout << endl;
        int flag = 0;
        for (int i = 0; i < hashT->getBufferSize(); ++i) {
        if (hashT->getElementByKey(i, str1) && flag < 6) {
        hashT->Remove(str1);
        flag++;
        }
        }
        hashT->Add("11.SSSSS/456788/gggg.");
        cout << endl << endl;
        hashT->Print();
        cout << endl;
        for (int i = 0; i < key_arr.size(); ++i) {
        cout << key_arr[i] << " ";
        }
        }


