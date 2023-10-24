package com.example.inf.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author czq
 * @date 2023/10/12 14:03
 * @Description:
 */
@Slf4j
@Aspect
//@Component
public class UserServiceAop {


    /**
     * * 任何字符  .. 重复内容  + 接口以及子类   &&  || ! 与或非
     * <p>
     * 必要 参数    返回值  方法名称   方法参数
     */
    @Pointcut("execution( * com.example.inf..*(..))")
    public void execution1() {

    }

    /**
     * 被注解 {@link com.example.ant.Secure} 修饰的方法
     */
    @Pointcut("execution( @com.example.ant.Secure * *(..))")
    public void execution2() {

    }


    /**
     * 被注解 {@link com.example.ant.Secure} 修饰的的方法返回值
     */
    @Pointcut("execution( (@com.example.ant.Secure *) *(..))")
    public void execution3() {

    }


    /**
     * 该接口以及子类所有方法
     */
    @Pointcut("execution( * com.example.inf.UserService+.*(..))")
    public void execution4() {

    }


    /**
     * within  匹配的是类或接口
     * 只匹配该类型
     */
    @Pointcut("within( com.example.inf.UserService)")
    public void within1() {

    }

    /**
     * 该类型以及子类型
     */
    @Pointcut("within( com.example.inf.UserService+)")
    public void within2() {

    }

    /**
     * 被该注解修饰的类型
     */
    @Pointcut("within(@com.example.ant.Secure *)")
    public void within3() {

    }


    /**
     * 按照代理类型匹配
     */
    @Pointcut("execution( * com.example.inf.*.*(..)) && this( com.example.inf.UserServiceImpl)")
    public void this1() {

    }


    /**
     * 按照被代理类型匹配
     */
    @Pointcut("target( com.example.inf.UserServiceImpl)")
    public void target1() {

    }


    /**
     * 按照实参类型匹配  execution 是按照形参类型匹配
     */
    @Pointcut("execution( * com.example.inf.*.*(..)) && args(com.example.entity.AA)")
    public void args1() {

    }


    /**
     * 被注解标记类的所有方法(不包括父类未重写)包括子类未被重写的方法
     */
    @Pointcut("@within(com.example.ant.Secure)")
    public void withinAnt() {

    }

    /**
     * 被注解标记类的所有声明方法和父类方法不包括子类
     */
    @Pointcut(" execution( * com.example.inf.*.*(..)) &&  @target(com.example.ant.Secure)")
    public void targetAnt() {

    }

    /**
     * 入参类型上有注解方法
     */
    @Pointcut("execution( * com.example.inf.*.*(..)) &&  @args(com.example.ant.Secure)")
    public void argsAnt() {

    }


    /**
     * 入参类型上有注解方法 不包括接口
     */
    @Pointcut("@annotation(com.example.ant.Secure)")
    public void ant() {

    }


    /**
     * bean名称匹配
     */
    @Pointcut("bean(*Impl)")
    public void bean1() {

    }


    @After("bean1()")
    public void after(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getDeclaringTypeName() + "" + joinPoint.getSignature().getName();
        log.info("后置方法,代理方法是:{}", method);
    }


}
