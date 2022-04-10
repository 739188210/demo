package com.cloud.logbacksty;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/2/8
 */

public class LogbackDemo {

    private static Logger logger = LoggerFactory.getLogger(LogbackDemo.class);

    public static void main(String[] args) {

        logInfo();
        //  overrideDefaultConfig(args);
    }


    public static void logInfo() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusManager statusManager = loggerContext.getStatusManager();
        OnConsoleStatusListener consoleListener = new OnConsoleStatusListener();
        statusManager.add(consoleListener);
        //      logger.setLevel(Level.INFO);

        ch.qos.logback.classic.Logger logger2 = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.foo");
        Logger barlogger = LoggerFactory.getLogger("com.foo.Bar");

// This request is enabled, because WARN >= INFO
        logger.warn("Low fuel level.");

// This request is disabled, because DEBUG < INFO.
        logger.debug("Starting search for nearest gas station.");

// The logger instance barlogger, named "com.foo.Bar"
// will inherit its level from the logger named
// "com.foo" Thus, the following request is enabled
// because INFO >= INFO.
        barlogger.info("Located nearest gas station.");

// This request is disabled, because DEBUG < INFO.
        barlogger.debug("Exiting gas station search");

        //     StatusPrinter.print(loggerContext);
    }

    /**
     * 覆盖 logback 的默认配置机制
     *
     * @param args
     */
    public static void overrideDefaultConfig(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);
            // 调用 context.reset() 清除之前的任何配置，例如默认
            // 配置。对于多步配置，省略调用 context.reset()。
            loggerContext.reset();
            // 指定新的配置文件位置
            joranConfigurator.doConfigure("D://logback.xml");
        } catch (JoranException e) {
            // StatusPrinter will handle this
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
        logger.info("Exiting application.");
    }

}
