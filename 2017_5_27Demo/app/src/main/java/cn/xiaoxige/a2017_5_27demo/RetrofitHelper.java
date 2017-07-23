package cn.xiaoxige.a2017_5_27demo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class RetrofitHelper {

    public static Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
        return retrofit;
    }

    public static OkHttpClient getClient() {

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new BaseInterceptor())
                .build();

        return client;
    }
}
