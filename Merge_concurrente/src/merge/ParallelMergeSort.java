package merge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort<T> extends RecursiveAction {
    // Declaración de variables: el arreglo que se ordenará y el comparador para definir el criterio de ordenación.
    private final T[] array;
    private final Comparator<? super T> comp;

    // Constructor que inicializa el arreglo y el comparador.
    public ParallelMergeSort(T[] array, Comparator<? super T> comp) {
        this.array = array;
        this.comp = comp;
    }

    @Override
    protected void compute() {
        // Longitud del arreglo actual.
        int n = array.length;

        // Si el tamaño del arreglo es menor a 2, no es necesario realizar ninguna acción.
        if (n < 2) {
            return;
        }

        // Encuentra el punto medio para dividir el arreglo.
        int mid = n / 2;

        // Divide el arreglo en dos subarreglos: izquierdo y derecho.
        T[] left = Arrays.copyOfRange(array, 0, mid);
        T[] right = Arrays.copyOfRange(array, mid, n);

        // Crea tareas paralelas para ordenar los subarreglos.
        ParallelMergeSort<T> leftTask = new ParallelMergeSort<>(left, comp);
        ParallelMergeSort<T> rightTask = new ParallelMergeSort<>(right, comp);

        // Ejecuta ambas tareas en paralelo.
        invokeAll(leftTask, rightTask);

        // Fusiona los subarreglos ordenados en el arreglo original.
        merge(left, right, array, comp);
    }

    // Método estático para fusionar dos subarreglos ordenados en un solo arreglo ordenado.
    private static <T> void merge(T[] left, T[] right, T[] result, Comparator<? super T> comp) {
        int i = 0, j = 0, k = 0; // Índices para recorrer los subarreglos izquierdo, derecho y el arreglo resultante.

        // Fusiona los elementos mientras haya elementos en ambos subarreglos.
        while (i < left.length && j < right.length) {
            // Compara los elementos y coloca el menor en el arreglo resultante.
            if (comp.compare(left[i], right[j]) <= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        // Agrega los elementos restantes del subarreglo izquierdo (si los hay).
        while (i < left.length) {
            result[k++] = left[i++];
        }

        // Agrega los elementos restantes del subarreglo derecho (si los hay).
        while (j < right.length) {
            result[k++] = right[j++];
        }
    }

    // Método público para iniciar la clasificación paralela de un arreglo.
    public static <T> void parallelMergeSort(T[] array, Comparator<? super T> comp) {
        // Crea un ForkJoinPool para gestionar la concurrencia.
        ForkJoinPool pool = new ForkJoinPool();

        // Inicia la tarea de clasificación paralela.
        pool.invoke(new ParallelMergeSort<>(array, comp));
    }
}
