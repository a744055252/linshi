package com.linkces.linshi.network.function;

import com.linkces.linshi.entity.ResultModel;
import com.linkces.linshi.network.exception.ExceptionEngine;
import com.linkces.linshi.utils.LogUtil;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * http结果处理函数
 *
 */
public class HttpResultFunction<T extends ResultModel<? extends Serializable>> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
        //打印具体错误
        LogUtil.e("HttpResultFunction:" + throwable);
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
