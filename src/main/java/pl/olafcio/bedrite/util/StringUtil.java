package pl.olafcio.bedrite.util;

public enum StringUtil {
    ;

    public static String capitalize(String text) {
        return String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static String repeat(String text, int amount) {
        StringBuilder total = new StringBuilder();

        for (int i = 0; i < amount; i++)
            total.append(text);

        return total.toString();
    }

    public static String padStart(String haystack, int amount, String rep) {
        return repeat(rep, amount - haystack.length()) + haystack;
    }
}
