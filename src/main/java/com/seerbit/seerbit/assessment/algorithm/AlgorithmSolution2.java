package com.seerbit.seerbit.assessment.algorithm;

public class AlgorithmSolution2 {

    public static void main(String[] args) {
        AlgorithmSolution2 solutions = new AlgorithmSolution2();

        // Test for maxXORSubarray
        int[] arr = {1, 2, 3, 4};
        int maxXOR = solutions.maxXORSubarray(arr);
        System.out.println("Maximum XOR Subarray Value: " + maxXOR);
    }

    // Problem 2: Find the subarray with maximum XOR
    public int maxXORSubarray(int[] arr) {
        int n = arr.length;
        // Variable to store maximum XOR value
        int maxXOR = Integer.MIN_VALUE;
        // Initialize XOR of the current subarray
        int currentXOR = 0;

        // Create a Trie structure to store XOR prefixes
        Trie trie = new Trie();
        trie.insert(0);  // Insert 0 for cases where subarray starts from the beginning

        // Traverse the array to find the maximum XOR subarray
        for (int i = 0; i < n; i++) {
            // Calculate the XOR for subarray arr[0..i]
            currentXOR ^= arr[i];
            // Find the maximum XOR of any prefix with the current XOR
            maxXOR = Math.max(maxXOR, trie.getMaxXOR(currentXOR));
            // Insert the current XOR into the Trie for future prefixes
            trie.insert(currentXOR);
        }

        return maxXOR;
    }

    // Trie structure to store XOR prefixes
    static class TrieNode {
        TrieNode[] children = new TrieNode[2];
    }

    static class Trie {
        TrieNode root = new TrieNode();

        // Insert a number's binary representation into the Trie
        public void insert(int num) {
            TrieNode node = root;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;  // Get the ith bit of num
                if (node.children[bit] == null) {
                    node.children[bit] = new TrieNode();
                }
                node = node.children[bit];
            }
        }

        // Find the maximum XOR of any number in the Trie with the given num
        public int getMaxXOR(int num) {
            TrieNode node = root;
            int maxXOR = 0;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;  // Get the ith bit of num
                // Try to maximize XOR by choosing the opposite bit if possible
                if (node.children[1 - bit] != null) {
                    maxXOR |= (1 << i);  // Set the ith bit of the result
                    node = node.children[1 - bit];
                } else {
                    node = node.children[bit];
                }
            }
            return maxXOR;
        }
    }


}
