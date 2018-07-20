package com.saintfine.customer.test.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @author chaihongwei 2018/7/18 18:34
 * 不要拦截快速点击
 */
@Target({TYPE, METHOD, CONSTRUCTOR})
@Retention(CLASS)
public @interface DontBlockFastClick {
}
