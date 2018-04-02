package cn.xiaoxige.a2017_5_27demo;

import android.util.Log;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

/**
 * @author by xiaoxige on 2018/4/2.
 */

public class NormalRepoImpl implements NormalRepo {

    private NormalApi mApi;

    public NormalRepoImpl() {
        mApi = RetrofitHelper.getRetrofit("http://192.168.199.165:8080").create(NormalApi.class);
    }

    @Override
    public Observable getMsg(final RequestEntity entity) {
        Observable observable = Observable.create(new Observable.OnSubscribe<ResponseBody>() {
            @Override
            public void call(Subscriber<? super ResponseBody> subscriber) {
                try {
                    ResponseBody body = OkHttpUtils.execute(mApi.getMsg(entity));
                    Log.e("TAG", "" + body.string());
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
        return observable;
    }
}
