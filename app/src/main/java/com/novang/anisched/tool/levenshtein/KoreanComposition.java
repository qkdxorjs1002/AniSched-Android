package com.novang.anisched.tool.levenshtein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KoreanComposition {
    private static final int korBegin = 44032;
    private static final int korEnd = 55203;
    private static final int chosungBase = 588;
    private static final int jungsungBase = 28;
    private static final int jaumBegin = 12593;
    private static final int jaumEnd = 12622;
    private static final int moumBegin = 12623;
    private static final int moumEnd = 12643;

    private static final List<Character> chosungList = Arrays.asList(
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ',
            'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ',
            'ㅌ', 'ㅍ', 'ㅎ'
    );

    private static final List<Character> jungsungList = Arrays.asList(
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ',
            'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ',
            'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    );

    private static final List<Character> jongsungList = Arrays.asList(
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ',
            'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ',
            'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ',
            'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    );

    private static final List<Character> jaumList = Arrays.asList(
            'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄸ',
            'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ',
            'ㅁ', 'ㅂ', 'ㅃ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ',
            'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    );

    private static final List<Character> moumList = Arrays.asList(
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ',
            'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ',
            'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    );

    public static char compose(char chosung, char jungsung, char jongsung) {
        return (char) (korBegin
                + chosungBase * chosungList.indexOf(chosung)
                + jungsungBase * jungsungList.indexOf(jungsung)
                + jongsungList.indexOf(jongsung));
    }

    public static List<Character> decompose(char c) {
        if (!isKoreanCharacter(c)) {
            return List.of(c);
        }

        int i = c;
        if (jaumBegin <= i && i <= jaumEnd) {
            return List.of(c, ' ', ' ');
        }
        if (moumBegin <= i && i <= moumEnd) {
            return List.of(' ', c, ' ');
        }

        i -= korBegin;
        int cho = i / chosungBase;
        int jung = (i - cho * chosungBase) / jungsungBase;
        int jong = i - cho * chosungBase - jung * jungsungBase;

        return List.of(chosungList.get(cho), jungsungList.get(jung), jongsungList.get(jong));
    }

    public static String decomposeToString(char c) {
        return decompose(c).stream().map(Object::toString).collect(Collectors.joining());
    }

    public static boolean isKoreanCharacter(char c) {
        return ((korBegin <= c && c <= korEnd) ||
                (jaumBegin <= c && c <= jaumEnd) ||
                (moumBegin <= c && c <= moumEnd));
    }
}
