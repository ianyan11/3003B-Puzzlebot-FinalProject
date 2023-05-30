/**
 * @file multiply.h
 * @brief Archivo de cabecera para la función multiplicar.
 */

#ifdef __cplusplus
extern "C" {
#endif
/**
 * @brief Multiplica dos enteros por 100.
 * 
 * Esta función es parte de una interfaz de C que puede ser usada por código C++.
 * La función toma dos punteros a enteros, los multiplica por 100,
 * y guarda el resultado en las ubicaciones de memoria originales.
 * 
 * @param a Un puntero al primer entero que se multiplicará por 100.
 * @param b Un puntero al segundo entero que se multiplicará por 100.
 */
void multiply(int* a, int* b);

#ifdef __cplusplus
}
#endif