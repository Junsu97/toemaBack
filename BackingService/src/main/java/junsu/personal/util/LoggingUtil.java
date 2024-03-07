package junsu.personal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {
    private static final Logger log = LoggerFactory.getLogger(LoggingUtil.class);

    public static void logStart(Class<?> clazz, String methodName){
        log.info(clazz.getName() + "." + methodName + "Start!!!");
    }
    public static void logEnd(Class<?> clazz, String methodName){
        log.info(clazz.getName() + "." + methodName + "End!!!");
    }
}
