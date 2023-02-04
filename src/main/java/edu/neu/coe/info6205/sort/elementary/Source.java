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

    public static Integer[] randomArray(int n) {
        Random r= new Random();
        Integer[] array = new Integer[n];
        for(int i=0;i<n;i++)
        {
            array[i]=r.nextInt(n);
        }
        return array;
    }

    public static Integer[] orderedArray(int n) {
        Integer[] array = new Integer[n];
        for(int i=0;i<n;i++)
        {
            array[i]=i+1;
        }
        return array;
    }

    public static Integer[] partiallyOrderedArray(int n) {
        Random r = new Random();
        Integer[] array = new Integer[n];
        for (int i = 0; i < n / 4; i++) {
            array[i] = i + 1;
        }
        for (int i = n / 4; i < n * 3 / 4; i++) {
            array[i] = r.nextInt(n);
        }
        for (int i = n * 3 / 4; i < n; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    public static Integer[] reverseOrderedArray(int n) {
        Integer[] array = new Integer[n];
        int count =1;
        for(int i=0;i<n;i++)
        {
            array[i]=n+2-count;
            count++;
        }
        return array;
    }

    private final int n;
    private final int m;
    private final Random random;
}
