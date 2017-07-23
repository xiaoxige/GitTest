package cn.xiaoxige.a2017_5_27demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class ProgressRequestBody extends RequestBody {

    private MediaType mMediaType;
    private File mFile;

    private ProgressListener listener;

    public ProgressRequestBody(MediaType mediaType, File file) {
        mMediaType = mediaType;
        mFile = file;
    }


    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mMediaType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        int len;
        byte[] b = new byte[1024];
        long progress = 0;
        FileInputStream fis = new FileInputStream(mFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        // 断点...
        bis.skip(0);

        while ((len = bis.read(b)) != -1) {
            sink.write(b, 0, len);
            progress += len;
            if (listener != null) {
                listener.progress(contentLength(), progress, ((float) progress / contentLength() * 100));
            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        sink.flush();
        fis.close();
        bis.close();
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }
}
