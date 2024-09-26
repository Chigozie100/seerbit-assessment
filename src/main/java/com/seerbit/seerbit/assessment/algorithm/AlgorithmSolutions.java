package com.seerbit.seerbit.assessment.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AlgorithmSolutions {

    public List<int[]> mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new ArrayList<>();
        }

        // Sort the intervals by the start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> mergedIntervals = new ArrayList<>();
        int[] currentInterval = intervals[0]; // Initialize with the first interval
        mergedIntervals.add(currentInterval);

        // Traverse through all intervals
        for (int[] interval : intervals) {
            int currentEnd = currentInterval[1];
            int nextStart = interval[0];
            int nextEnd = interval[1];

            // If the intervals overlap, merge them by extending the current end time
            if (currentEnd >= nextStart) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // No overlap, add the new interval and move currentInterval pointer
                currentInterval = interval;
                mergedIntervals.add(currentInterval);
            }
        }

        return mergedIntervals;
    }

    /**
     * Finds the subarray with the maximum XOR value.
     *
     * @param arr the input array
     * @param N the size of the array
     * @return the maximum XOR value of any subarray.
     */
    public int findMaxXORSubarray(int[] arr, int N) {
        // Prefix XOR array
        int[] prefixXOR = new int[N + 1];
        prefixXOR[0] = 0;

        // Build prefix XOR for the array
        for (int i = 1; i <= N; i++) {
            prefixXOR[i] = prefixXOR[i - 1] ^ arr[i - 1];
        }

        int maxXOR = 0;

        // We use a Trie data structure to optimize the XOR query
        TrieNode root = new TrieNode();

        for (int i = 0; i <= N; i++) {
            // Insert the current prefix XOR in the Trie
            insert(root, prefixXOR[i]);

            // Query the maximum XOR for the current prefix XOR
            int currentMaxXOR = query(root, prefixXOR[i]);
            maxXOR = Math.max(maxXOR, currentMaxXOR);
        }

        return maxXOR;
    }

    /**
     * Trie Node class to store binary representations of numbers for XOR calculation.
     */
    class TrieNode {
        TrieNode[] children = new TrieNode[2]; // Since we are working with binary numbers (0 and 1)
    }

    /**
     * Inserts a number in the Trie as a binary representation.
     *
     * @param root the root of the Trie
     * @param num the number to be inserted
     */
    private void insert(TrieNode root, int num) {
        TrieNode node = root;

        // Insert the number in the Trie by its binary representation (31 bits for integer)
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1; // Get the ith bit of the number
            if (node.children[bit] == null) {
                node.children[bit] = new TrieNode();
            }
            node = node.children[bit];
        }
    }

    /**
     * Queries the Trie for the maximum XOR value possible with the current prefix XOR.
     *
     * @param root the root of the Trie
     * @param num the current prefix XOR number
     * @return the maximum XOR value possible
     */
    private int query(TrieNode root, int num) {
        TrieNode node = root;
        int maxXOR = 0;

        // Traverse the Trie and find the path that maximizes the XOR result
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1; // Get the ith bit of the number
            int oppositeBit = 1 - bit; // We want to maximize XOR, so we take the opposite bit if possible

            if (node.children[oppositeBit] != null) {
                maxXOR |= (1 << i); // Set the ith bit in the result
                node = node.children[oppositeBit]; // Follow the opposite path
            } else {
                node = node.children[bit]; // Follow the same bit if the opposite path is not available
            }
        }

        return maxXOR;
    }

}
