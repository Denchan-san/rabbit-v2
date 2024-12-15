package org.example.util;

public class Strings {

    public static String tableize(String word) {
        return pluralize(underscore(depackage(word)));
    }

    public static String pluralize(String word) {
        return Inflection.pluralize(word);
    }

    public static String underscore(String word) {
        String firstPattern = "([A-Z]+)([A-Z][a-z])";
        String secondPattern = "([a-z\\d])([A-Z])";
        String replacementPattern = "$1_$2";
        // Replace package separator with slash.
        word = word.replaceAll("\\.", "/");
        // Replace $ with two underscores for inner classes.
        word = word.replaceAll("\\$", "__");
        // Replace capital letter with _ plus lowercase letter.
        word = word.replaceAll(firstPattern, replacementPattern);
        word = word.replaceAll(secondPattern, replacementPattern);
        word = word.replace('-', '_');
        word = word.toLowerCase();
        return word;
    }

    public static String depackage(String word) {
        return word.replaceAll("^.*\\.", "");
    }
}
