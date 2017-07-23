package cn.xiaoxige.a2017_5_27demo;

import rx.Observable;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class FileUpUseCase extends BaseUseCase {

    private FileUpRepo mFileUpRepo;
    private String mPath;

    public FileUpUseCase(FileUpRepo fileUpRepo, String path) {
        mFileUpRepo = fileUpRepo;
        mPath = path;
    }

    @Override
    public Observable buildObservable() {
        return mFileUpRepo.upfile(mPath);
    }
}
