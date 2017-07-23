package cn.xiaoxige.a2017_5_27demo;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class FileUpRepoImpl implements FileUpRepo {
    private UpImageApi upImageApi;

    public FileUpRepoImpl() {
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .baseUrl("http://192.168.199.238:8080")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        Retrofit retrofit = RetrofitHelper.getRetrofit("http://192.168.199.238:8080");
        upImageApi = retrofit.create(UpImageApi.class);
    }

    @Override
    public Observable<ResponseBody> upfile(final String path) {

        return Observable.create(new Observable.OnSubscribe<ResponseBody>() {
            @Override
            public void call(Subscriber<? super ResponseBody> subscriber) {
                File file = new File(path);
                if (!file.exists()) {
                    subscriber.onError(new Exception("文件不存在..."));
                    return;
                }

//                RequestBody body = RequestBody.create(MultipartBody.FORM, file);
                ProgressRequestBody body = new ProgressRequestBody(MultipartBody.FORM, file);

                body.setListener(new ProgressListener() {
                    @Override
                    public void progress(long all, long position, float progress) {
                        Log.e("TAG", "all = " + all + "" +
                                "position = " + position + "进度 = " + progress + "%");

                        EventBus.getDefault().postSticky(new ProgressEvent(progress));
                    }
                });


                MultipartBody.Part part = MultipartBody.Part
                        .createFormData("photo1", file.getName(), body);
                try {
                    ResponseBody execute = OkHttpUtils.execute(upImageApi.uploadImg(part));
                    subscriber.onNext(execute);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
