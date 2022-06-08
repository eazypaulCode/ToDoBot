package de.eazypaulcode.todobot.message;

public class Key {

    private final String name;

    public Key(String name) {
        this.name = name;
    }

    public static Key from(String name) {
        return new Key(name);
    }

    public String name() {
        return name;
    }

    public String[] keys(String separator) {
        return name.split(separator);
    }

    public String[] keys() {
        return keys(".");
    }

    public String lastKey(String separator) {
        if (keys(separator).length == 0) throw new IllegalStateException("Key is empty");
        return keys()[keys(separator).length - 1];
    }

    public String lastKey() {
        return lastKey(".");
    }

    public String firstKey(String separator) {
        if (keys(separator).length == 0) throw new IllegalStateException("Key is empty");
        return keys()[0];
    }

    public String firstKey() {
        return firstKey(".");
    }

    @Deprecated
    public String message() {
        return Localization.get(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Key key)) return false;
        return key.name().equals(name);
    }

    @Override
    public String toString() {
        return name();
    }
}
