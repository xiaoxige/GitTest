package cn.xiaoxige.a2017_5_27demo;

import retrofit2.Call;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class OkHttpUtils {
    public static <T> T execute(Call<T> t) throws Exception {

        return t.execute().body();

    }
}
