package de.eazypaulcode.todobot.logging;

import de.eazypaulcode.todobot.message.Key;
import de.eazypaulcode.todobot.message.Localization;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements AutoCloseable {

    private final boolean debug;
    private final String name;
    private final PrintStream sysOut = AnsiConsole.sysOut();

    public Logger(String name, boolean debug) {
        this.debug = debug;
        this.name = name;
    }

    public Logger(String name) {
        this(name, false);
    }

    public void debug(Key key) {
        debug(Localization.get(key));
    }

    public void debug(Key key, String... replacements) {
        debug(Localization.get(key, replacements));
    }

    public void debug(String message) {
        if (!debug) return;
        sysOut.println("[" + name + "] " + "[" + getTimestamp() + "]" + " [DEBUG] " + message);
    }

    public void info(Key key) {
        info(Localization.get(key));
    }

    public void info(Key key, String... replacements) {
        info(Localization.get(key, replacements));
    }

    public void info(String message) {
        sysOut.println("[" + name + "] " + "[" + getTimestamp() + "]" + " [INFO] " + message);
    }

    public void warn(Key key) {
        warn(Localization.get(key));
    }

    public void warn(Key key, String... replacements) {
        warn(Localization.get(key, replacements));
    }

    public void warn(String message) {
        sysOut.println("[" + name + "] " + "[" + getTimestamp() + "]" + ConsoleColor.YELLOW.getAnsiCode() + " [WARNING] " + message);
    }

    public void error(Key key) {
        error(Localization.get(key));
    }

    public void error(Key key, String... replacements) {
        error(Localization.get(key, replacements));
    }

    public void error(String message) {
        sysOut.println("[" + name + "] " + "[" + getTimestamp() + "]" + ConsoleColor.RED.getAnsiCode() + " [ERROR] " + message);
    }

    public void critical(Key key) {
        critical(Localization.get(key));
    }

    public void critical(Key key, String... replacements) {
        critical(Localization.get(key, replacements));
    }

    public void critical(String message) {
        sysOut.println("[" + name + "] " + "[" + getTimestamp() + "]" + ConsoleColor.DARK_RED.getAnsiCode() + " [CRITICAL] " + message);
    }

    public void fine(Key key) {
        fine(Localization.get(key));
    }

    public void fine(Key key, String... replacements) {
        fine(Localization.get(key, replacements));
    }

    public void fine(String message) {
        sysOut.println("[" + name + "] " + "[" + getTimestamp() + "]" + ConsoleColor.GREEN.getAnsiCode() + " [FINE] " + ConsoleColor.DEFAULT.getAnsiCode()  + message);
    }

    private String getTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM HH:mm:ss.SSS");

        return format.format(new Date());
    }


    @Override
    public void close() {
        sysOut.close();
    }
}
