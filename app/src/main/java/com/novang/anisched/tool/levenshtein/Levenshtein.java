package com.novang.anisched.tool.levenshtein;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Levenshtein {

    public static double calculate(String str1, String str2) {
        if (str1.length() < str2.length()) {
            return calculate(str2, str1);
        }

        if (str2.length() == 0) {
            return str1.length();
        }

        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();

        List<Double> previousRow = IntStream.range(0, str2.length() + 1).asDoubleStream().boxed().collect(Collectors.toList());
        for (int idx1 = 0; idx1 < charArray1.length; idx1++) {
            char char1 = charArray1[idx1];

            List<Double> currentRow = new ArrayList<>(List.of(idx1 + 1.0));
            for (int idx2 = 0; idx2 < charArray2.length; idx2++) {
                char char2 = charArray2[idx2];
                double substitutionCost = char1 == char2 ? 0 : calculate(
                        KoreanComposition.decomposeToString(char1),
                        KoreanComposition.decomposeToString(char2),
                        new HashMap<>()) / 3;

                double insertions = previousRow.get(idx2 + 1) + 1;
                double deletions = currentRow.get(idx2) + 1;
                double substitutions = previousRow.get(idx2) + substitutionCost;

                currentRow.add(Math.min(Math.min(insertions, deletions), substitutions));
            }

            previousRow = new ArrayList<>(currentRow);
        }

        return previousRow.get(previousRow.size() - 1);
    }

    public static double calculate(String str1, String str2, Map<String, Double> costs) {
        if (str1.length() < str2.length()) {
            return calculate(str2, str1, costs);
        }

        if (str2.length() == 0) {
            return str1.length();
        }

        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();

        List<Double> previousRow = IntStream.range(0, str2.length() + 1).asDoubleStream().boxed().collect(Collectors.toList());
        for (int idx1 = 0; idx1 < charArray1.length; idx1++) {
            char char1 = charArray1[idx1];

            List<Double> currentRow = new ArrayList<>(List.of(idx1 + 1.0));
            for (int idx2 = 0; idx2 < charArray2.length; idx2++) {
                char char2 = charArray2[idx2];
                double substitutionCost = char1 == char2 ? 0 : costs.getOrDefault(String.format("%s>%s", char1, char2), 1.0);

                double insertions = previousRow.get(idx2 + 1) + 1;
                double deletions = currentRow.get(idx2) + 1;
                double substitutions = previousRow.get(idx2) + substitutionCost;

                currentRow.add(Math.min(Math.min(insertions, deletions), substitutions));
            }

            previousRow = new ArrayList<>(currentRow);
        }

        return previousRow.get(previousRow.size() - 1);
    }

}
