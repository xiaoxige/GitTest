package cn.xiaoxige.a2017_5_27demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.model.PictureConfig;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.RxActivity;
import com.yalantis.ucrop.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

public class MainActivity extends RxActivity {

    private static final int PERMISSIONCODE = 100;

    private ProgressBar progressBar;
    private TextView tvProgress;
    private RecyclerView recyclerView;
    private TestAdapter adapter;
    private List<String> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mItems = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mItems.add("" + i);
        }
        adapter = new TestAdapter(MainActivity.this, mItems);

        recyclerView.addItemDecoration(new TestItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new RecyclerItemTouchHelper();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ((RecyclerItemTouchHelper) callback).setListener(new TestRecyclerViewMoveListener() {
            @Override
            public void move(int from, int to) {
                Collections.swap(mItems, from, to);
                adapter.notifyItemMoved(from, to);
            }
        });

    }

    public void onClick(View v) {
        int selfPermission
                = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (selfPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONCODE);
        } else {
//            publishImg();
//            text();
            test1();
        }

//        text();

//        PictureConfig.getInstance().openPhoto(MainActivity.this, new PictureConfig.OnSelectResultCallback() {
//            @Override
//            public void onSelectSuccess(List<LocalMedia> list) {
//                for (LocalMedia media : list) {
//                    Log.e("TAG", "多选" + media.getPath() + ", " + media.getCompressPath());
//                }
//            }
//
//            @Override
//            public void onSelectSuccess(LocalMedia localMedia) {
//                Log.e("TAG", "单选" + localMedia.getPath() + ", " + localMedia.getCompressPath());
//                try {
//                    publicImg(localMedia.getCompressPath());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    private void test1() {
        RequestEntity entity = new RequestEntity();
        entity.setUserName("xiaoxige");
        entity.setUserPwd("123456");
        entity.setNickName("zhuxiaoan");
        entity.setMsg("限价房的骄傲富家大室付款就打算");
        NormalUseCase useCase = new NormalUseCase(new NormalRepoImpl(), entity);
        useCase.execute(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        }, bindUntilEvent(ActivityEvent.DESTROY));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressBack(ProgressEvent event) {
        setUpProgress((int) event.progress);
    }

    private void text() {
        setUpProgress(0);
        Log.i("TAG", "MainActivity text()");
        String directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = directory + File.separator + "a.jpg";
        FileUpRepo repo = new FileUpRepoImpl();
        FileUpUseCase useCase = new FileUpUseCase(repo, filePath);
        useCase.execute(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "完成任务", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "上传失败了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseBody o) {
                Toast.makeText(MainActivity.this, "上传成功了", Toast.LENGTH_SHORT).show();
            }
        }, bindUntilEvent(ActivityEvent.DESTROY));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONCODE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                publishImg();
//                text();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void setUpProgress(int progress) {
        progressBar.setProgress(progress);
        tvProgress.setText(progress + "");
        if (progress == 100) {
            tvProgress.setText("完成");
        }
    }

    private void publicImg(final String path) throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://116.196.92.172:4869/")
                        .addConverterFactory(new StringConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final File file = new File(path);
                final UpImageApi upImageApi = retrofit.create(UpImageApi.class);
                RequestBody body = RequestBody.create(MediaType.parse("image/" + "png"), file);

                try {

                    Call<ResponseBody> responseBodyCall = upImageApi.uploadImage(body);
                    Response<ResponseBody> response = responseBodyCall.execute();
                    String string = response.body().string();
                    Log.e("TAG", "string = " + string);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    private void publishImg() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.199.238:8080")
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpImageApi upImageApi = retrofit.create(UpImageApi.class);
        String directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(directory + File.separator + "a.jpg");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        try {

            RequestBody body = RequestBody

                    .create(MultipartBody.FORM, file);
            MultipartBody.Part part
                    = MultipartBody
                    .Part
                    .createFormData("photo2", file.getName(), body);

            Call<ResponseBody> uploadImg =
                    upImageApi.uploadImg(part);

            uploadImg.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.e("TAG", "成功" + response.body().toString());

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("TAG", "失败" + t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "" + e.getMessage());
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public String getFileSuffix(String filePath) {
        try {
            String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
            return fileType;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
