package com.novang.anisched.tool;

import com.novang.anisched.tool.levenshtein.Levenshtein;

public class LevenshteinWrapper {

    public static double getDistance(String a, String b) {
        return Levenshtein.calculate(a, b);
    }

}