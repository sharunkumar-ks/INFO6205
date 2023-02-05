/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        for (int outer = from + 1; outer < to; outer++) {
            int inner = outer;
            while (inner > from) {
                if (helper.compare(xs[inner], xs[inner - 1]) < 0) {
                    helper.swap(xs, inner - 1, inner);
                } else {
                    break;
                }
                inner--;
            }
        }
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    private static <T extends Comparable<T>> T[] sortConsumer(T[] ts) {
        sort(ts);
        return ts;
    }

    public static void main(String[] args) {

        final int start_n = 100;
        final int number_of_tests = 5;

        for (int i = 0, n = start_n; i < number_of_tests; i++, n *= 2) {
            final int n_num = n;

            Map<String, Supplier<Integer[]>> supplierMap = new HashMap<>();

            supplierMap.put("random", () -> Source.randomArray(n_num));
            supplierMap.put("ordered", () -> Source.orderedArray(n_num));
            supplierMap.put("partially-ordered", () -> Source.partiallyOrderedArray(n_num));
            supplierMap.put("reverse-ordered", () -> Source.reverseOrderedArray(n_num));

            Benchmark_Timer<Integer[]> bt = new Benchmark_Timer<>("InsertionSort", InsertionSort::sortConsumer);

            for (Map.Entry<String, Supplier<Integer[]>> entry : supplierMap.entrySet()) {
                System.out.println("n = " + n_num + ", " + entry.getKey() + " array: " + bt.runFromSupplier(entry.getValue(), 100));
            }
        }

    }
}
