package me.earth.headlessmc.logging;

import lombok.Cleanup;
import me.earth.headlessmc.config.HmcProperties;
import me.earth.headlessmc.util.ResourceUtil;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * A {@link StreamHandler} logging to {@link FileDescriptor#out} using a {@link
 * ThreadFormatter}.
 */
public class LoggingHandler extends StreamHandler {
    public LoggingHandler() {
        super(new PrintStream(new FileOutputStream(FileDescriptor.out)),
              new ThreadFormatter());
    }

    public static void apply() throws IOException {
        @Cleanup
        InputStream is = ResourceUtil.getHmcResource("logging.properties");
        LogManager.getLogManager().readConfiguration(is);
        String property = System.getProperty(HmcProperties.LOGLEVEL.getName());
        if (property == null || !LogLevelUtil.trySetLevel(property)) {
            LogLevelUtil.setLevel(Level.INFO);
        }
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();
    }

    @Override
    public void close() {
        flush();
    }

}