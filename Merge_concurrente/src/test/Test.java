package test;

import merge.GfG;
import merge.ParallelMergeSort;
import java.util.Arrays;
import java.util.Comparator;

public class Test {

    // Inicia la ejecución del programa
    public static void main(String[] args) {

        // Crea el peor caso: un arreglo ordenado en orden inverso
        Integer[] worstCase = new Integer[10000000];
        for (int i = 0; i < worstCase.length; i++) {
            worstCase[i] = worstCase.length - i; // Llena el arreglo con valores decrecientes
        }

        // Crea el caso promedio: un arreglo con números aleatorios
        Integer[] averageCase = new Integer[10000000];
        for (int i = 0; i < averageCase.length; i++) {
            averageCase[i] = (int) (Math.random() * 10000); // Llena con valores aleatorios
        }

        // Crea el mejor caso: un arreglo ya ordenado
        Integer[] bestCase = new Integer[10000000];
        for (int i = 0; i < bestCase.length; i++) {
            bestCase[i] = i; // Llena el arreglo con valores crecientes
        }

        // Ejecuta pruebas para el peor caso
        runTest("Peor Caso", worstCase);
        // Ejecuta pruebas para el caso promedio
        runTest("Caso Promedio", averageCase);
        // Ejecuta pruebas para el mejor caso
        runTest("Mejor Caso", bestCase);
    }

    // Función para ejecutar pruebas de rendimiento
    private static void runTest(String caseType, Integer[] array) {
        // Imprime el tipo de caso y el tamaño del arreglo
        System.out.println("\n" + caseType + " (Tamaño: " + array.length + ")");

        // Define el número de repeticiones para las pruebas
        int runs = 100;
        // Acumuladores para los tiempos de ejecución
        long totalTimeSequential = 0;
        long totalTimeParallel = 0;

        // Ejecuta las pruebas para el número definido de repeticiones
        for (int i = 0; i < runs; i++) {
            // Clona el arreglo original para la prueba secuencial
            Integer[] sequentialArray = array.clone();
            // Clona el arreglo original para la prueba paralela
            Integer[] parallelArray = array.clone();

            // Mide el tiempo de la versión secuencial
            long start = System.nanoTime(); // Toma el tiempo de inicio
            GfG.sort(Arrays.stream(sequentialArray).mapToInt(Integer::intValue).toArray(), 0, sequentialArray.length - 1);
            long end = System.nanoTime(); // Toma el tiempo de finalización
            totalTimeSequential += (end - start); // Acumula el tiempo transcurrido

            // Mide el tiempo de la versión paralela
            start = System.nanoTime(); // Toma el tiempo de inicio
            ParallelMergeSort.parallelMergeSort(parallelArray, Comparator.naturalOrder());
            end = System.nanoTime(); // Toma el tiempo de finalización
            totalTimeParallel += (end - start); // Acumula el tiempo transcurrido
        }

        // Imprime el tiempo promedio de la versión secuencial
        System.out.println("Tiempo Secuencial promedio (" + runs + " corridas): " + (totalTimeSequential / runs / 1_000_000) + " ms");
        // Imprime el tiempo promedio de la versión paralela
        System.out.println("Tiempo Paralelo promedio (" + runs + " corridas): " + (totalTimeParallel / runs / 1_000_000) + " ms");
    }
}
