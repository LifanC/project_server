package com.project.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    private static final String genLogFormat(final String tag, final String message) {
        return String.format("[%1$s] %2$s", tag, message);
    }

    public static final void info(final String tag, final String message) {
        logger.info(genLogFormat(tag, message));
    }

    public static final void error(final String tag, final String message) {
        logger.error(genLogFormat(tag, message));
    }

    public static final void warn(final String tag, final String message) {
        logger.warn(genLogFormat(tag, message));
    }

}
