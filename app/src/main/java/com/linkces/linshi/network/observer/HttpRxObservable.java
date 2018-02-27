package com.linkces.linshi.network.observer;

import com.google.gson.Gson;
import com.linkces.linshi.entity.ResultModel;
import com.linkces.linshi.network.function.HttpResultFunction;
import com.linkces.linshi.network.function.ServerResultFunction;
import com.linkces.linshi.utils.LogUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.Serializable;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 适用Retrofit网络请求Observable(被监听者)
 *
 */
public class HttpRxObservable {

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 无管理生命周期,容易导致内存溢出
     *
     */
    public static <T extends ResultModel<? extends Serializable>> Observable<T>
            getObservable(Observable<T> apiObservable) {
       // showLog(request);
        return apiObservable
                .map(new ServerResultFunction<>())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider自动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity.../RxFragment...
     *
     */
    public static <T extends ResultModel<? extends Serializable>> Observable<T>
            getObservable(Observable<T> apiObservable, LifecycleProvider<T> lifecycle) {
        //showLog(request);
        Observable<T> observable;

        if (lifecycle != null) {
            //随生命周期自动管理.eg:onCreate(start)->onStop(end)
            observable =apiObservable
                    .map(new ServerResultFunction<>())
                    .compose(lifecycle.bindToLifecycle())//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<ActivityEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity,RxAppCompatActivity,RxFragmentActivity
     *
     */
    public static <T extends ResultModel<? extends Serializable>> Observable<T>
            getObservable(Observable<T> apiObservable, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event) {
        Observable<T> observable;
        if (lifecycle != null) {
            //手动管理移除监听生命周期.eg:ActivityEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction<>())
                    .compose(lifecycle.bindUntilEvent(event))//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }


    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<FragmentEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxFragment,RxDialogFragment
     *
     */
    public static <T extends ResultModel<? extends Serializable>> Observable<T>
            getObservable(Observable<T> apiObservable, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event) {
        Observable<T> observable;
        if (lifecycle != null) {
            //手动管理移除监听生命周期.eg:FragmentEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction<>())
                    .compose(lifecycle.bindUntilEvent(event))//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }


    /**
     * 打印log
     *
     */
    private static void showLog(Map<String, Object> request) {
        if (request == null || request.size() == 0) {
            LogUtil.e("[http request]:");
        }
        LogUtil.e("[http request]:" + new Gson().toJson(request));
    }

}
