package de.iisys.sysint.hicumes.core.utils.logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Logger {

    private java.util.logging.Logger logger;
    private int lineLength = 150;
    private int lineBegin = 20;

    private static HashMap<String, Logger> INSTANCES = new HashMap<>();

    public static Logger getInstance(String className, String fileName)
    {
        if(INSTANCES.get(className) == null)
        {
            INSTANCES.put(className, new Logger(className, fileName));
        }

        return INSTANCES.get(className);
    }

    public Logger(String className) {
        logger = java.util.logging.Logger.getLogger(className);
    }

    public Logger(String className, String logName) {
        logger = java.util.logging.Logger.getLogger(className);
        //https://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            String basepath = System.getProperty("user.dir") + "/log/";
            System.out.println("Logging Directory = " + basepath);
            fh = new FileHandler(basepath + logName + "%u_%g.log", 100000000, 3, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);

            // the following statement is used to log any messages
            //logger.info("My first log");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logMessage(String header, String symbol) {
        String infoMessage = makeHeader(header, symbol);
        logger.info(infoMessage);
    }
    public void logMessage(String header, String symbol, Object... messages) {
        String infoMessage = makeHeader(header, symbol);
        infoMessage = makeBody(infoMessage, messages);
        infoMessage = makeFooter(symbol, infoMessage);

        logger.info(infoMessage);
    }

    private String makeFooter(String symbol, String infoMessage) {
        for (int i = 0; i< lineLength;) {
            i+= symbol.length();
            infoMessage += symbol;
        }
        infoMessage += "\n";
        return infoMessage;
    }

    private String makeBody(String infoMessage, Object[] messages) {
        for (Object message : messages) {
            infoMessage += message.toString() + "\n";
        }
        return infoMessage;
    }

    private String makeHeader(String header, String symbol) {
        int lineEnd = lineLength - header.length() - 2 - lineBegin;
        String infoMessage = "\n\n";
        for (int i = 0; i< lineBegin;) {
            i+= symbol.length();
            infoMessage += symbol;
        }
        infoMessage += " " + header + " ";
        for (int i = 0; i< lineEnd;) {
            i+= symbol.length();
            infoMessage += symbol;
        }
        infoMessage += "\n";
        return infoMessage;
    }

    public void logMessage(String header) {
        logMessage(header, "-");
    }
}
