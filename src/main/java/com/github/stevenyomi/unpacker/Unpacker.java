package com.github.stevenyomi.unpacker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unpacker {

    public static String unpack(String script) {
        return unpack(new ProgressiveParser(script), null, null);
    }

    public static String unpack(String script, String left, String right) {
        return unpack(new ProgressiveParser(script), left, right);
    }

    public static String unpack(ProgressiveParser script) {
        return unpack(script, null, null);
    }

    public static String unpack(ProgressiveParser script, String left, String right) {
        final String packed = script
                .substringBetween("p}('", ".split('|'),0,{}))")
                .replace("\\'", "\"");
        final ProgressiveParser parser = new ProgressiveParser(packed);
        final String data;
        if (left != null && right != null) {
            data = parser.substringBetween(left, right);
            parser.consumeTo("',");
        } else {
            data = parser.substringBefore("',");
        }
        if (data.isEmpty()) return "";

        final String[] dictionary = parser.substringBetween("'", "'").split("\\|");
        final int size = dictionary.length;

        // https://stackoverflow.com/questions/38376584/
        final StringBuilder builder = new StringBuilder(data.length());
        final Matcher matcher = wordRegex.matcher(data);
        int lastAppendPosition = 0;
        while (matcher.find()) {
            builder.append(data, lastAppendPosition, matcher.start());
            lastAppendPosition = matcher.end();
            final String key = matcher.group(0);
            final int index = parseRadix62(key);
            if (index >= size) {
                builder.append(key);
                continue;
            }
            final String value = dictionary[index];
            builder.append(value.isEmpty() ? key : value);
        }
        builder.append(data, lastAppendPosition, data.length());
        return builder.toString();
    }

    private static final Pattern wordRegex = Pattern.compile("\\w+");

    public static int parseRadix62(String str) {
        int result = 0;
        for (final char ch : str.toCharArray()) {
            final int parsed;
            if (ch <= '9') {
                parsed = ch - '0';
            } else if (ch >= 'a') {
                parsed = ch - 'a' + 10;
            } else {
                parsed = ch - 'A' + 36;
            }
            result = result * 62 + parsed;
        }
        return result;
    }
}
