package cn.xiaoxige.a2017_5_27demo;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public interface FileUpRepo {

    Observable<ResponseBody> upfile(String path);

}
