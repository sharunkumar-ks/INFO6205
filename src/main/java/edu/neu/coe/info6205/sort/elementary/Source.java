package edu.neu.coe.info6205.sort.elementary;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

/**
 * A source of random information.
 */
class Source {
    public Source(int N, int M, Random random) {
        n = N;
        m = M;
        this.random = random;
    }

    public Source(int N, int M, long seed) {
        this(N, M, new Random(seed));
    }

    public Source(int N, int M) {
        this(N, M, new Random());
    }

    public Supplier<int[]> intsSupplierRandom() {
        return () -> {
            int[] ints = (int[]) Array.newInstance(int.class, n);
            for (int i = 0; i < ints.length; i++) ints[i] = random.nextInt(m) - m / 2;
            return ints;
        };
    }

    public Supplier<int[]> intsSupplierOrdered() {
        return () -> {
            int[] ints = intsSupplierRandom().get();
            Arrays.sort(ints);
            return ints;
        };
    }

    public Supplier<int[]> intsSupplierPartiallyOrdered() {
        return () -> {
            int[] ints = intsSupplierOrdered().get();
            Arrays.sort(ints, 0, ints.length / 2);
            return ints;
        };
    }

    public Supplier<int[]> intSupplierReverseOrdered() {
        return () -> {
            int[] ints = intSupplierReverseOrdered().get();
            int[] result = (int[]) Array.newInstance(int.class, n);
            for (int i = 0; i < n; i++) result[i] = ints[n - i - 1];
            return result;
        };
    }

    private final int n;
    private final int m;
    private final Random random;
}
