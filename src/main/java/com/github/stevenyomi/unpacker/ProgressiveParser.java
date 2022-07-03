package com.github.stevenyomi.unpacker;

public class ProgressiveParser {

    public final String text;
    public int startIndex;

    public ProgressiveParser(String text) {
        this.text = text;
        startIndex = 0;
    }

    public void consumeTo(String str) {
        startIndex = text.indexOf(str, startIndex) + str.length();
    }

    public String substringBetween(String left, String right) {
        final int leftIndex = text.indexOf(left, startIndex) + left.length();
        final int rightIndex = text.indexOf(right, leftIndex);
        startIndex = rightIndex + right.length();
        return text.substring(leftIndex, rightIndex);
    }

    public String substringBefore(String str) {
        final int index = text.indexOf(str, startIndex);
        final String result = text.substring(startIndex, index);
        startIndex = index + str.length();
        return result;
    }
}
