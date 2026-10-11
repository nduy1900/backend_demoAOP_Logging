package com.example.demoAOP_Logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    // Bài 1: Ghi log trước khi thực thi phương thức service
    @Before("execution(* com.example.demoAOP_Logging.service..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("=============BEFORE============");
        // Tên class chứa phương thức
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Tên class: " + className);

        // Tên phương thức
        log.info("Tên phương thức được gọi: " + joinPoint.getSignature().getName());

        // Các tham số đầu vào
        Object[] args = joinPoint.getArgs();
        log.info("Tham số đầu vào: " + Arrays.deepToString(args));

    }

    // Bài 2: Ghi log sau khi phương thức thực thi thành công
    @AfterReturning(
            pointcut = "execution(* com.example.demoAOP_Logging.service..*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // Tên class chứa phương thức
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Tên phương thức
        String method = joinPoint.getSignature().getName();

        System.out.println("=============AFTER RETURNING============");
        log.info("Tên class: " + className);
        log.info("Tên phương thức: " + method);
        log.info("Kết quả trả về: " + result);
        log.info("Thực thi thành công");
    }

    // Bài 3: Ghi log khi phương thức xảy ra lỗi
    @AfterThrowing(
            pointcut = "execution(* com.example.demoAOP_Logging.service..*(..))",
            throwing = "ex"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        // Tên class chứa phương thức
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Tên phương thức
        String method = joinPoint.getSignature().getName();

        System.out.println("=============AFTER THROWING============");
        ex.printStackTrace();
        log.error("Phương thức xảy ra lỗi: " + method);
        log.error("Thông báo lỗi: " + ex.getMessage());

        log.error("Loại exception: " + ex.getClass().getSimpleName());
    }

    // Bài 4: Đo thời gian thực thi phương thức service
    @Around("execution(* com.example.demoAOP_Logging.service..*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("=============AROUND============");
        log.info("ProductService.getAllProducts() executed in " + (end - start) + " ms");
        return result;
    }
}
