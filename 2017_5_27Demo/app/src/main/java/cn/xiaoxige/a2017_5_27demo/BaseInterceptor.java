package cn.xiaoxige.a2017_5_27demo;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =
                chain.request();

        /**
         * 添加头部数据
         */
        Headers headers =
                request.headers();
        String version = headers.get("version");
        if (TextUtils.isEmpty(version)) {
            version = "1.0.0";
        }
        Headers headersBuild = headers.newBuilder()
                .add("version", version)
                .add("tokon", "")
                .build();
        request = request.newBuilder().headers(headersBuild).build();

        String method = request.method();
        if (method.equals("GET") || method.equals("DELETE")) {
            //  参数在URL中
            HttpUrl url = request.url();
            request = getDefaultRequest(request, url);
        } else {
            // 参数在body中
            request = getDefaultRequest(request, method);
        }

        Log.e("TAG", "method = " + method);
        Log.e("TAG", "url = " + request.url().toString());

        Response proceed = chain.proceed(request);
        String responseBody = proceed.body().toString();
        Log.e("TAG", "responseBody = " + responseBody);

        return proceed.newBuilder()
                .body(ResponseBody.create(proceed.body()
                        .contentType(), responseBody))
                .build();
    }

    private Request getDefaultRequest(Request request, HttpUrl url) {
        HttpUrl httpUrl = url.newBuilder()
                .addQueryParameter("key", "value")
                .build();
        return request.newBuilder().url(httpUrl).build();
    }

    private Request getDefaultRequest(Request request, String method) {
        RequestBody body = request.body();
        Buffer buffer = new Buffer();
        RequestBody requestBody = body;
        try {
            requestBody.writeTo(buffer);
            String bodyParams = buffer.readUtf8();

            // 可以对bodyParams加密这里未加密

            RequestBody newRequestBody = RequestBody.create(request.body().contentType(), bodyParams);
            return request.newBuilder().method(method, newRequestBody).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
