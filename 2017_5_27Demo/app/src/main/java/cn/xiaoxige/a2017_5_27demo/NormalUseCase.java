package cn.xiaoxige.a2017_5_27demo;

import rx.Observable;

/**
 * @author by xiaoxige on 2018/4/2.
 */

public class NormalUseCase extends BaseUseCase {

    private NormalRepo mRepo;
    private RequestEntity mEntity;

    public NormalUseCase(NormalRepo mRepo, RequestEntity mEntity) {
        this.mRepo = mRepo;
        this.mEntity = mEntity;
    }

    @Override
    public Observable buildObservable() {
        return mRepo.getMsg(mEntity);
    }
}
