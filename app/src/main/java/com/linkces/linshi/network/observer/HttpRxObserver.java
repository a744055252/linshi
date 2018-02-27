package com.linkces.linshi.network.observer;


import android.text.TextUtils;

import com.linkces.linshi.entity.ResultModel;
import com.linkces.linshi.network.exception.ApiException;
import com.linkces.linshi.network.exception.ExceptionEngine;
import com.linkces.linshi.network.retrofit.HttpRequestListener;
import com.linkces.linshi.network.retrofit.RxActionManagerImpl;

import java.io.Serializable;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 适用Retrofit网络请求Observer(监听者)
 * 备注:
 * 1.重写onSubscribe，添加请求标识
 * 2.重写onError，封装错误/异常处理，移除请求
 * 3.重写onNext，移除请求
 * 4.重写cancel，取消请求
 *
 */
public abstract class HttpRxObserver<T extends ResultModel<? extends Serializable>> implements Observer<T>, HttpRequestListener {

    private String mTag;//请求标识

    public HttpRxObserver(String tag) {
        this.mTag = tag;
    }

    @Override
    public void onError(Throwable e) {
        RxActionManagerImpl.getInstance().remove(mTag);
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(@NonNull T t) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().remove(mTag);
        }
        onSuccess(t);
    }

    public void onSubscribe(@NonNull Disposable d) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().add(mTag, d);
        }
        onStart(d);
    }

    @Override
    public void cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().cancel(mTag);
        }
    }

    /**
     * 是否已经处理
     *
     */
    public boolean isDisposed() {
        return TextUtils.isEmpty(mTag) || RxActionManagerImpl.getInstance().isDisposed(mTag);
    }

    protected abstract void onStart(Disposable d);

    /**
     * 错误/异常回调
     *
     */
    protected abstract void onError(ApiException e);

    /**
     * 成功回调
     *
     */
    protected abstract void onSuccess(T response);

}
