package merge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort<T> extends RecursiveAction {
    private final T[] array;
    private final Comparator<? super T> comp;

    public ParallelMergeSort(T[] array, Comparator<? super T> comp) {
        this.array = array;
        this.comp = comp;
    }

    @Override
    protected void compute() {
        int n = array.length;

        if (n < 2) {
            return;
        }

        int mid = n / 2;
        T[] left = Arrays.copyOfRange(array, 0, mid);
        T[] right = Arrays.copyOfRange(array, mid, n);

        ParallelMergeSort<T> leftTask = new ParallelMergeSort<>(left, comp);
        ParallelMergeSort<T> rightTask = new ParallelMergeSort<>(right, comp);

        invokeAll(leftTask, rightTask);

        merge(left, right, array, comp);
    }

    private static <T> void merge(T[] left, T[] right, T[] result, Comparator<? super T> comp) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (comp.compare(left[i], right[j]) <= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }
    }

    public static <T> void parallelMergeSort(T[] array, Comparator<? super T> comp) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ParallelMergeSort<>(array, comp));
    }
}
