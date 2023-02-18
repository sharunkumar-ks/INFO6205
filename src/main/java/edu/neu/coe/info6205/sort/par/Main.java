package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {
    private static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
//        processArgs(args);
        System.out.println("Machine has " + Runtime.getRuntime().availableProcessors() + " available processors.");
        for(int threads = 1; threads <= Runtime.getRuntime().availableProcessors(); threads++) {
            for(int runs = 1, array_size = 500_000; runs <= 5; runs++, array_size *= 2) {
                runBenchmark(array_size, threads);
            }
        }
    }

    private static void runBenchmark(int array_size, int thread_count) {
        System.out.println("Array size: " + array_size + "\t\tThreads: " + thread_count);
        Random random = new Random();
        int[] array = new int[array_size];
        ArrayList<Long> timeList = new ArrayList<>();
        for (double cutoff_ratio = 0.05; cutoff_ratio < 1; cutoff_ratio += 0.1) {
            ParSort.cutoff = (int)(array_size * cutoff_ratio);
            ParSort.pool = new ForkJoinPool(thread_count);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);

            System.out.println("cutoff-ratio：" + (df.format(cutoff_ratio)) + "\t\t10times Time:" + time + "ms");

        }
//        try {
//            final String file_name = "./src/result-array-" + array_size + "-thread-" + thread_count + ".csv";
//            FileOutputStream fis = new FileOutputStream(file_name);
//            OutputStreamWriter isr = new OutputStreamWriter(fis);
//            BufferedWriter bw = new BufferedWriter(isr);
//            int j = 0;
//            for (long i : timeList) {
//                String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i / 10 + "\n";
//                j++;
//                bw.write(content);
//                bw.flush();
//            }
//            bw.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
