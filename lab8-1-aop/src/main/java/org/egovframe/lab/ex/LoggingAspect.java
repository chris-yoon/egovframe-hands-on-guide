package org.egovframe.lab.ex;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    public void beforeAdvice(JoinPoint thisJoinPoint) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

        // 클래스 및 메서드 이름 출력
        StringBuilder buf = new StringBuilder();
        buf.append("LoggingAspect.beforeAdvice : [")
           .append(className).append(".").append(methodName).append("()]");

        // 메서드 인자 출력
        Object[] arguments = thisJoinPoint.getArgs();
        int argCount = 0;
        for (Object obj : arguments) {
            buf.append("\n - arg ")
               .append(argCount++)
               .append(" : ")
               .append(convertToString(obj));
        }
        LOGGER.debug(buf.toString());
    }

    public void afterAdvice(JoinPoint thisJoinPoint) {
        LOGGER.debug("LoggingAspect.afterAdvice executed.");
    }

    public void afterReturningAdvice(JoinPoint thisJoinPoint, Object retVal) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

        StringBuilder buf = new StringBuilder();
        buf.append("LoggingAspect.afterReturningAdvice : [")
           .append(className).append(".").append(methodName).append("()]")
           .append("\n");

        // 반환 값 처리
        if (retVal instanceof List) {
            List<?> resultList = (List<?>) retVal;
            buf.append("resultList size : ").append(resultList.size()).append("\n");
            for (Object oneRow : resultList) {
                buf.append(convertToString(oneRow)).append("\n");
            }
        } else {
            buf.append(convertToString(retVal));
        }
        LOGGER.debug(buf.toString());
    }

    public void afterThrowingAdvice(JoinPoint thisJoinPoint, Exception exception) throws Exception {
        LOGGER.debug("LoggingAspect.afterThrowingAdvice executed.");
        LOGGER.error("An error occurred: {}", exception);

        throw new Exception("An error occurred.", exception);
    }

    public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        LOGGER.debug("LoggingAspect.aroundAdvice start.");
        Object retVal = thisJoinPoint.proceed();
        LOGGER.debug("LoggingAspect.aroundAdvice end.");

        return retVal;
    }

    /**
     * 안전하게 객체를 문자열로 변환하는 메서드
     */
    private String convertToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Number || obj instanceof Boolean || obj instanceof Character) {
            return obj.toString();
        }
        if (obj instanceof List) {
            StringBuilder listBuf = new StringBuilder();
            listBuf.append("List(size=").append(((List<?>) obj).size()).append("): ");
            for (Object item : (List<?>) obj) {
                listBuf.append(item).append(", ");
            }
            return listBuf.toString();
        }
        // 객체의 기본 정보를 출력
        return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
    }
}
