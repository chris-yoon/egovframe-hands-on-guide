package org.egovframe.lab.ex;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggingAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	public void beforeAdvice(JoinPoint thisJoinPoint) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

        StringBuffer buf = new StringBuffer();
        buf.append("LoggingAspect.beforeAdvice : [" + className
            + "." + methodName + "()]");
        Object[] arguments = thisJoinPoint.getArgs();
        int argCount = 0;
        for (Object obj : arguments) {
            buf.append("\n - arg ");
            buf.append(argCount++);
            buf.append(" : ");
            buf.append(ToStringBuilder.reflectionToString(obj));
        }
        LOGGER.debug(buf.toString());
    }

    public void afterAdvice(JoinPoint thisJoinPoint) {
    	LOGGER.debug("LoggingAspect.afterAdvice executed.");
    }

	public void afterReturningAdvice(JoinPoint thisJoinPoint,
            Object retVal) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

        StringBuffer buf = new StringBuffer();
        buf.append("LoggingAspect.afterReturningAdvice : ["
            + className + "." + methodName + "()]");

        buf.append("\n");

        if (retVal instanceof List) {
            List<?> resultList = (List<?>) retVal;
            buf.append("resultList size : " + resultList.size() + "\n");
            for (Object oneRow : resultList) {
                buf.append(ToStringBuilder.reflectionToString(oneRow));
                buf.append("\n");
            }
        } else {
            buf.append(ToStringBuilder.reflectionToString(retVal));
        }
        LOGGER.debug(buf.toString());
    }

    public void afterThrowingAdvice(JoinPoint thisJoinPoint,
            Exception exception) throws Exception {
    	LOGGER.debug("LoggingAspect.afterThrowingAdvice executed.");
    	LOGGER.error("An error occured. {}", exception);

        throw new Exception("An error occured.", exception);
    }

    public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint)
            throws Throwable {
    	LOGGER.debug("LoggingAspect.aroundAdvice start.");

        Object retVal = thisJoinPoint.proceed();

        LOGGER.debug("LoggingAspect.aroundAdvice end.");
        return retVal;
    }

}
