package it.polimi.ingsw.util;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**Custom Formatter for Java.util.Logging.</br>
 * @since 1.0
 * @version 1.0
 * */
public class LocalFormatter extends Formatter {
    private static String format = "[%1$-7s] [%2$-16s:%3$-11s] %4$s %n";

    @Override
    public synchronized String format(LogRecord record) {
        String string = record.getSourceClassName();
        string = string.substring(string.lastIndexOf('.') + 1);
        Level level = record.getLevel();
        if (level.equals(Level.SEVERE) || level.equals(Level.WARNING)) {
            format = "\u001B[31m [%1$-7s] [%2$-16s:%3$-11s] %4$s \u001B[0m %n";
        } else {
            format = "\u001B[33m [%1$-7s] [%2$-16s:%3$-11s] %4$s  \u001B[0m %n";
        }

        return String.format(format, record.getLevel().getLocalizedName(), string, record.getSourceMethodName(), record.getMessage());

    }
}
