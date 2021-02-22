package com.novang.anisched.tool;

/**
 * Levenshtein Distance
 * https://en.wikipedia.org/wiki/Levenshtein_distance
 */
public class Levenshtein {

    public static double getDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();

        if (a.length() < b.length()) {
            String tmp = a;
            a = b;
            b = tmp;
        }

        int aLength = a.length();
        if (aLength == 0) {
            return 1.0;
        }

        int [] costs = new int [b.length() + 1];

        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }

        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;

            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }

        return (aLength - costs[b.length()]) / (double) aLength;
    }

}