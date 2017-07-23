package cn.xiaoxige.a2017_5_27demo;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public abstract class BaseUseCase {

    public abstract Observable buildObservable();

    public void execute(Subscriber subscriber, Observable.Transformer transformer) {

        Observable observable = buildObservable();
        if (observable == null) {
            subscriber.onError(new Throwable("被观察者不存在..."));
            return;
        }

        observable.compose(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
