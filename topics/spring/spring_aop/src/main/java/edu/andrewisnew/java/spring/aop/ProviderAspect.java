package edu.andrewisnew.java.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;

@Aspect
@Component
public class ProviderAspect {
    private static final Logger log = LoggerFactory.getLogger(ProviderAspect.class);

    /*
    execution( [Modifiers] ReturnType FullClassName.[MethodName] (Arguments) [throws ExceptionType])

    * - any group of characters
    + - search in subclasses too
    || and && may be used : execution(...) || execution(...)

    The [ReturnType] is mandatory. If the return type is not a criterion, just use *.
    The [Modifers] is not mandatory and if not specified defaults to public.
    The [MethodName] is not mandatory, meaning no exception will be thrown at
    boot time. But if unspecified, the join point where to execute the advice won’t be
    identified. It’s safe to say that if you want to define a technically useful pointcut
    expression you need to specify it.
    • The [Arguments] is mandatory. If the arguments are not a
    criterion, just use (..) which matches a method with 0 or many arguments.
    If you want the match to be done on a method with no arguments, use (). If you
    want the match to be done on a method with a single argument, use (*)
     */
    @Before("execution(public * edu.andrewisnew.java.spring.aop.*.saveNews(..))")
    public void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info(" ---> Method " + methodName + " is about to be called");
    }

    @Before("loggablePointcut()")
    public void beforeByAnnotation(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info(" ---> Method " + methodName + " is about to be called");
    }

    @Before("execution(public * edu.andrewisnew.java.spring.aop.*.saveNews(..)) && args(news) && target(newsProvider)")
    public void before2(String news, NewsProvider newsProvider) {
        log.info(news + " 2 to be saved by " + newsProvider);
    }

    @Before("beforePointcut(news, newsProvider)")
    public void before3(String news, NewsProvider newsProvider) {
        log.info(news + " 3 to be saved by " + newsProvider);
    }

    @AfterReturning(value = "loggablePointcut()", returning = "res")
    public void afterRet(List<String> res) {
        log.info(res + " are returned");
    }

    @AfterThrowing(value = "loggablePointcut()", throwing = "e")
    public void afterTh(Exception e) {
        log.info("exception: " + e);
    }

    @After(value = "loggablePointcut()")
    public void after(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info(" ---> After " + methodName);
    }

    @Around("loggablePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws
            Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("[aroundFind]: ---> Intercepting call of {}",
                methodName);
        long t1 = System.currentTimeMillis();

        try {
            Thread.sleep(1000L);
            Object obj = joinPoint.proceed();
            return obj;//даже если void
        } finally {
            long t2 = System.currentTimeMillis();
            log.info("[aroundFind]: ---> Execution of {} took {} ",
                    methodName, (t2 - t1) / 1000 + " s.");
        }
    }


    @Pointcut("execution(@edu.andrewisnew.java.spring.aop.Loggable * *(..))")
    public void loggablePointcut() {
    }

    @Pointcut("execution(public * edu.andrewisnew.java.spring.aop.*.saveNews(..)) && args(news) && target(newsProvider)")
    public void beforePointcut(String news, NewsProvider newsProvider) {
    }
}

@interface Loggable {
}

