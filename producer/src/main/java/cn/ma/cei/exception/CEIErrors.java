package cn.ma.cei.exception;

import cn.ma.cei.generator.BuilderContext;
import org.apache.log4j.Logger;

public class CEIErrors {
    private static Logger logger = Logger.getLogger(CEIErrors.class);

    public static void showFailure(CEIErrorType errorType, String message, Object... args) throws CEIException {
        String res = String.format(message, args);
        if (errorType == CEIErrorType.CODE)
        logger.fatal(BuilderContext.getCurrentLanguage().getName() + ": " + res);
        throw new CEIException(res);
    }

    public static void showWarning(CEIErrorType errorType, String message, Object... args) {

    }

    public static void showInfo(String message, Object... args) {
        logger.info(String.format(message, args));
    }

    public static void showDebug(String message, Object... args) {
        logger.debug(String.format(message, args));
    }
}
