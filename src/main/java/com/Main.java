package com;

import com.solution.Solution;
import com.util.SolutionSelector;

public class Main {
    public static void main(String[] args) {
        Solution latestSolution = SolutionSelector.findLatestSolution();
        latestSolution.solve();
    }
}