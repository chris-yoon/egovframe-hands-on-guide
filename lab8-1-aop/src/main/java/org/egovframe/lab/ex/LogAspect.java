package org.egovframe.lab.ex;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

	public void beforeTargetMethod(JoinPoint thisJoinPoint) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

        StringBuffer buf = new StringBuffer();
        buf.append("LogAspect.beforeTargetMethod : [" + className
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

    public void afterTargetMethod(JoinPoint thisJoinPoint) {
    	LOGGER.debug("LogAspect.afterTargetMethod executed.");
    }

	public void afterReturningTargetMethod(JoinPoint thisJoinPoint,
            Object retVal) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

        StringBuffer buf = new StringBuffer();
        buf.append("LogAspect.afterReturningTargetMethod : ["
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

    public void afterThrowingTargetMethod(JoinPoint thisJoinPoint,
            Exception exception) throws Exception {
    	LOGGER.debug("LogAspect.afterThrowingTargetMethod executed.");
    	LOGGER.error("An error occured. {}", exception);

        throw new Exception("An error occured.", exception);
    }

    public Object aroundTargetMethod(ProceedingJoinPoint thisJoinPoint)
            throws Throwable {
    	LOGGER.debug("LogAspect.aroundTargetMethod start.");

        Object retVal = thisJoinPoint.proceed();

        LOGGER.debug("LogAspect.aroundTargetMethod end.");
        return retVal;
    }

}
