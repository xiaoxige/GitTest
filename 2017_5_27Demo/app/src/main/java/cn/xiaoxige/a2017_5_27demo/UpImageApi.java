package cn.xiaoxige.a2017_5_27demo;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by 小稀革 on 2017/5/28.
 * https://mp.weixin.qq.com/s/3Z-z4fTRuq8ahbDob_UIDg
 */

public interface UpImageApi {
    @Multipart
    @POST("/FileUpload/FileUploadServlet")
    Call<ResponseBody> uploadImg(@Part MultipartBody.Part part) throws Exception;
}
