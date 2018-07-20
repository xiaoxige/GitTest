package com.saintfine.customer.test.aop;

import android.util.Log;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author chaihongwei 2018/7/18 17:57
 * 拦截重复点击事件
 */
@Aspect
public class FastClickBlockAspect {
    private static final String TAG = FastClickBlockAspect.class.getSimpleName();
    /**
     * 两次点击延迟时间
     */
    private static final long CLICK_DELAY = 1000L;
    /**
     * 最后一次点击的时间
     */
    private long sLastClickTime = 0L;
    /**
     * 最后一次点击的view的hashCode,用于区分时间间隔
     */
    private int mLastViewHashCode = 0;

    /**
     * 是否允许快速点击
     */
    private boolean isAllowFastClick = false;

//    @Before("@annotation(com.saintfine.customer.test.aop.DontBlockFastClick)")
//    public void onAllowFastClick(JoinPoint joinPoint) {
//        isAllowFastClick = true;
//    }

    @Around("execution(* android.view.View.OnClickListener.onClick(..))"
    + " && "
    + "!(@annotation(com.saintfine.customer.test.aop.DontBlockFastClick))")
    public void onClickHook(ProceedingJoinPoint joinPoint) {
        int thisObjHashCode = joinPoint.getThis().hashCode();

//        //如果两次点击的对象的hashCode不一致,说明点击的是不同的对象
//        if (isAllowFastClick || mLastViewHashCode != thisObjHashCode
//                || System.currentTimeMillis() - sLastClickTime >= CLICK_DELAY) {
//
//            isAllowFastClick = false;
//
//            mLastViewHashCode = thisObjHashCode;
//            sLastClickTime = System.currentTimeMillis();
//

        Log.e("TAG", "13245654654654614132132132");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
//        } else {
//            Log.e("TAG", "过滤了");
//        }
    }
}