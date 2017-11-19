package cn.xiaoxige.customview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

@RuntimePermissions
public class MainActivity extends RxActivity {


    private TestView testView;
    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testView = (TestView) findViewById(R.id.testView);
        testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityPermissionsDispatcher.showCameraWithPermissionCheck(MainActivity.this);
            }
        });

        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String appMV = appInfo.metaData.getString("MTA_CHANNEL");
            Toast.makeText(MainActivity.this, "appMV = " + appMV, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);

        testView.setProgress(100, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                testView.setProgress(3, true);
            }
        }, 2000);


        GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setBorder(
                Color.RED,
                10
        );
        roundingParams.setRoundAsCircle(true);
        hierarchy.setRoundingParams(roundingParams);

        simpleDraweeView.setHierarchy(hierarchy);
        ControllerListener listener = new BaseControllerListener() {
            @Override
            public void onFinalImageSet(String id, @Nullable Object imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.mipmap.ic_launcher))
                        .build())
                .setOldController(simpleDraweeView.getController())
                .setControllerListener(listener)
                .build();

        simpleDraweeView.setController(controller);

        /**
         * 网络相关（retrofit+rxjava+rxlife+stetho）
         */
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request =
                                chain.request();

                        Headers headers = request.headers();

                        Headers build = headers.newBuilder()
                                .add("version", "1.0")
                                .add("token", "xiaoxige").build();
                        request = request.newBuilder()
                                .headers(build)
                                .build();

                        Log.e("TAG", "url = " + request.url().uri().toString());

                        String method = request.method();
                        if (method.equals("GET") || method.equals("DELETE")) {
                            HttpUrl url = request.url();
                            HttpUrl httpUrl = url.newBuilder()
                                    .addQueryParameter("xiaoxige", "one")
                                    .addQueryParameter("zhuxiaoan", "two")
                                    .build();
                            request = request.newBuilder()
                                    .url(httpUrl)
                                    .build();
                        } else {

                            RequestBody body = request.body();


                            if (body != null) {
                                Buffer buffer = new Buffer();
                                body.writeTo(buffer);
                                String readUtf8 = buffer.readUtf8();
                                // 可能需要对body进行加密
                                // TODO: 2017/11/3

                                RequestBody requestBody = RequestBody.create(body.contentType(), readUtf8);
                                request = request.newBuilder()
                                        .method(method, requestBody)
                                        .build();
                            }

                        }
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        final NetApi api = retrofit.create(NetApi.class);

        Flowable flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> flowableEmitter) throws Exception {
                String response = MainActivity.execute(api.getBaiduWeb());
                if (TextUtils.isEmpty(response)) {
                    flowableEmitter.onError(new Exception());
                    return;
                }
                flowableEmitter.onNext(response);
                flowableEmitter.onComplete();
            }
        }, BackpressureStrategy.LATEST)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());


        flowable.subscribe(new XXGSubscriber<String>() {

            @Override
            public void xxgNext(String o) {
                super.xxgNext(o);
                Log.e("TAG", "o = " + o);
            }

            @Override
            public void xxgError(Throwable t) {
                super.xxgError(t);
                Log.e("TAG", "t = " + t.getMessage());
            }

            @Override
            public void xxgComplete() {
                super.xxgComplete();
                Log.e("TAG", "Complete");
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void showCamera() {
        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Toast.makeText(this, "拒接", Toast.LENGTH_SHORT).show();
    }


    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void xiaoxige() {
        Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_SHORT).show();
        getAppDetailSettingIntent(MainActivity.this);
    }

    abstract class XXGSubscriber<T> implements Subscriber<T> {

        @Override
        public void onSubscribe(Subscription s) {
            s.request(Integer.MAX_VALUE);
        }

        @Deprecated
        @Override
        public void onNext(T o) {
            xxgNext(o);
        }

        @Deprecated
        @Override
        public void onError(Throwable t) {
            xxgError(t);
        }

        @Deprecated
        @Override
        public void onComplete() {
            xxgComplete();
        }

        public void xxgNext(T o) {
        }

        @CallSuper
        public void xxgError(Throwable t) {
        }

        public void xxgComplete() {
        }
    }

    private static <T> T execute(Call<T> call) throws IOException {


        Response<T> response = call.execute();
        return response.body();
    }


    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

    interface NetApi {
        @GET("/")
        Call<String> getBaiduWeb() throws Exception;
    }

}
