package cn.xiaoxige.a2017_5_27demo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author by xiaoxige on 2018/4/2.
 */

public interface NormalApi {

    @POST("/xiaoxige/user/register")
    Call<ResponseBody> getMsg(@Body RequestEntity entity) throws Exception;
}
