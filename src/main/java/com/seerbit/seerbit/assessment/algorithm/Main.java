package com.seerbit.seerbit.assessment.algorithm;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AlgorithmSolutions solutions = new AlgorithmSolutions();

        // Test mergeIntervals method
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        List<int[]> merged = solutions.mergeIntervals(intervals);
        System.out.println("Merged Intervals:");
        for (int[] interval : merged) {
            System.out.println(Arrays.toString(interval));
        }

        // Test findMaxXORSubarray method
        int[] arr = {1, 2, 3, 4};
        int maxXOR = solutions.findMaxXORSubarray(arr, arr.length);
        System.out.println("Maximum XOR Subarray Value: " + maxXOR);
    }

}
