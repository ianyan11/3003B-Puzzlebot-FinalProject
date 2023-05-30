/**
 * @file multiply.cpp
 * @brief Archivo que contiene la definición de la función multiplicar.
 */

/**
 * @brief Multiplica dos enteros por 100.
 * 
 * Esta es una función externa "C" que puede ser llamada desde otros lenguajes de 
 * programación. Toma dos punteros a enteros, multiplica los enteros por 100,
 * y almacena el resultado de nuevo en las ubicaciones de memoria originales a las que 
 * apuntan los punteros.
 * 
 * @param x Un puntero al primer entero a ser multiplicado por 100.
 * @param y Un puntero al segundo entero a ser multiplicado por 100.
 */
extern "C" {
    void multiply(int* x, int* y) {
        *x *= 100;
        *y *= 100;
    }
}