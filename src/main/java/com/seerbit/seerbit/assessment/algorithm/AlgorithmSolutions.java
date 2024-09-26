package com.seerbit.seerbit.assessment.algorithm;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmSolutions {

    public static void main(String[] args) {
        Interval[] intervals = { new Interval(1, 3), new Interval(2, 6), new Interval(8, 10), new Interval(15, 18) };
        Interval[] merged = mergeIntervals(intervals);

        System.out.println("Merged Intervals:");
        for (Interval interval : merged) {
            System.out.println("(" + interval.start + ", " + interval.end + ")");
        }
    }

    /**
     * Merge overlapping intervals in a sorted array of intervals.
     *
     * @param intervals Array of intervals
     * @return Merged intervals
     */
    public static Interval[] mergeIntervals(Interval[] intervals) {
        // If input array is empty, return empty array
        if (intervals.length == 0) {
            return new Interval[0];
        }

        // Sort intervals by start time (already sorted in problem statement)
        // Arrays.sort(intervals, Comparator.comparingInt(a -> a.start));

        List<Interval> merged = new ArrayList<>();
        merged.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            Interval current = intervals[i];
            Interval last = merged.get(merged.size() - 1);

            // Check if current interval overlaps with last merged interval
            if (current.start <= last.end) {
                // Merge current interval with last merged interval
                last.end = Math.max(last.end, current.end);
            } else {
                // Add current interval to merged list
                merged.add(current);
            }
        }

        // Convert merged list to array
        return merged.toArray(new Interval[0]);
    }


}
