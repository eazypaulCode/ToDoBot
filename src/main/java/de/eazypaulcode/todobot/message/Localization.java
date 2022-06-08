package de.eazypaulcode.todobot.message;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    private static Locale locale = Locale.US;

    public static void setLanguage(Locale locale) {
        Localization.locale = locale;
    }

    private static ResourceBundle getDefaultResourceBundle() {
        return ResourceBundle.getBundle("messages", locale);
    }

    public static String get(String key) {
        ResourceBundle bundle = getDefaultResourceBundle();

        if (!bundle.containsKey(key)) {
            return key;
        }

        return bundle.getString(key);
    }

    public static String get(Key key) {
        if (key == null) return "undefined";
        return get(key.name());
    }
    
    public static String get(String key, String... replacements) {
        String message = get(key);
        for(int i = 0; i < replacements.length; i++) {
            message = message.replace("${" + i + "}", replacements[i]);
        }
        return message;
    }
    
    public static String get(Key key, String... replacements) {
        return get(key.name(), replacements);
    }

}
