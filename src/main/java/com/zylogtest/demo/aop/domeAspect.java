package com.zylogtest.demo.aop;


import java.lang.annotation.*;

//该注解是由 javadoc记录的
@Documented
//在运行时保留VM
@Retention(RetentionPolicy.RUNTIME)
//作用在类上
@Target(ElementType.TYPE)
public @interface domeAspect {
    String name()default "";
}
