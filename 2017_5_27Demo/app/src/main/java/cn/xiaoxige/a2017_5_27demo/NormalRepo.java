package cn.xiaoxige.a2017_5_27demo;

import rx.Observable;

/**
 * @author by xiaoxige on 2018/4/2.
 */

public interface NormalRepo {

    Observable getMsg(RequestEntity entity);
}
