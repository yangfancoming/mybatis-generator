
package org.mybatis.generator.logging;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import org.mybatis.generator.logging.commons.JakartaCommonsLoggingLogFactory;
import org.mybatis.generator.logging.jdk14.Jdk14LoggingLogFactory;
import org.mybatis.generator.logging.log4j.Log4jLoggingLogFactory;
import org.mybatis.generator.logging.log4j2.Log4j2LoggingLogFactory;
import org.mybatis.generator.logging.nologging.NoLoggingLogFactory;
import org.mybatis.generator.logging.slf4j.Slf4jLoggingLogFactory;

/**
 * Factory for creating loggers.
 * 
 * @author Jeff Butler
 * 
 */
public class LogFactory {
    private static AbstractLogFactory theFactory;
    public static final String MARKER = "MYBATIS-GENERATOR"; //$NON-NLS-1$

    static {
        tryImplementation(new Slf4jLoggingLogFactory());
        tryImplementation(new JakartaCommonsLoggingLogFactory());
        tryImplementation(new Log4j2LoggingLogFactory());
        tryImplementation(new Log4jLoggingLogFactory());
        tryImplementation(new Jdk14LoggingLogFactory());
        tryImplementation(new NoLoggingLogFactory());
    }

    public static Log getLog(Class<?> clazz) {
        try {
            return theFactory.getLog(clazz);
        } catch (Exception t) {
            throw new RuntimeException(getString("RuntimeError.21", //$NON-NLS-1$
                    clazz.getName(), t.getMessage()), t);
        }
    }

    /**
     * This method will switch the logging implementation to Java native
     * logging. This is useful in situations where you want to use Java native
     * logging to log activity but Log4J is on the classpath. Note that this
     * method is only effective for log classes obtained after calling this
     * method. If you intend to use this method you should call it before
     * calling any other method.
     */
    public static synchronized void forceJavaLogging() {
        setImplementation(new Jdk14LoggingLogFactory());
    }

    public static synchronized void forceSlf4jLogging() {
        setImplementation(new Slf4jLoggingLogFactory());
    }

    public static synchronized void forceCommonsLogging() {
        setImplementation(new JakartaCommonsLoggingLogFactory());
    }

    public static synchronized void forceLog4jLogging() {
        setImplementation(new Log4jLoggingLogFactory());
    }

    public static synchronized void forceLog4j2Logging() {
        setImplementation(new Log4j2LoggingLogFactory());
    }

    public static synchronized void forceNoLogging() {
        setImplementation(new NoLoggingLogFactory());
    }

    public static void setLogFactory(AbstractLogFactory logFactory) {
        setImplementation(logFactory);
    }

    private static void tryImplementation(AbstractLogFactory factory) {
        if (theFactory == null) {
            try {
                setImplementation(factory);
            } catch (LogException e) {
                // ignore
            }
        }
    }

    private static void setImplementation(AbstractLogFactory factory) {
        try {
            Log log = factory.getLog(LogFactory.class);
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + factory + "' adapter."); //$NON-NLS-1$ //$NON-NLS-2$
            }
            theFactory = factory;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t.getMessage(), t); //$NON-NLS-1$
        }
    }
}
