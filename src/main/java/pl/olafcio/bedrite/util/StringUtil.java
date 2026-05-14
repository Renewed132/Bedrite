package pl.olafcio.bedrite.util;

public enum StringUtil {
    ;

    public static String capitalize(String text) {
        return String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1).toLowerCase();
    }
}
