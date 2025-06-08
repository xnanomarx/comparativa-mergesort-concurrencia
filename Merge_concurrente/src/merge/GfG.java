package merge;

public class GfG {

    // Fusiona dos subarreglos de arr[].
    static void merge(int arr[], int l, int m, int r) {
        // Calcula el tamaño de los dos subarreglos a fusionar.
        int n1 = m - l + 1; // Tamaño del subarreglo izquierdo.
        int n2 = r - m; // Tamaño del subarreglo derecho.

        // Crea arreglos temporales para los subarreglos izquierdo y derecho.
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copia los datos al subarreglo izquierdo.
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];

        // Copia los datos al subarreglo derecho.
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        // Inicializa índices para iterar sobre los subarreglos izquierdo (L), derecho (R),
        // y el arreglo combinado (arr).
        int i = 0, j = 0, k = l;

        // Fusiona los dos subarreglos ordenados en un solo arreglo.
        while (i < n1 && j < n2) {
            // Si el elemento actual de L es menor o igual al de R,
            // se copia al arreglo original y se incrementa el índice de L.
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } 
            // Si el elemento actual de R es menor, se copia al arreglo original
            // y se incrementa el índice de R.
            else {
                arr[k] = R[j];
                j++;
            }
            // Avanza el índice del arreglo combinado.
            k++;
        }

        // Copia los elementos restantes de L al arreglo original, si quedan.
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copia los elementos restantes de R al arreglo original, si quedan.
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Ordena arr[l..r] usando el método merge().
    public static void sort(int arr[], int l, int r) {
        // Comprueba si el índice izquierdo es menor que el derecho para continuar.
        if (l < r) {
            // Encuentra el punto medio del arreglo.
            int m = l + (r - l) / 2;

            // Ordena recursivamente la mitad izquierda.
            sort(arr, l, m);

            // Ordena recursivamente la mitad derecha.
            sort(arr, m + 1, r);

            // Fusiona las dos mitades ordenadas.
            merge(arr, l, m, r);
        }
    }

    // Imprime los elementos de un arreglo.
    public static void printArray(int arr[]) {
        // Recorre el arreglo e imprime cada elemento.
        for (int i = 0; i < arr.length; ++i)
            System.out.print(arr[i] + " ");
        // Agrega una nueva línea al final de la impresión.
        System.out.println();
    }
}
