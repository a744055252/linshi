package com.linkces.linshi.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.linkces.linshi.App;
import com.linkces.linshi.network.api.LoginService;
import com.linkces.linshi.network.retrofit.HttpsUtils;
import com.linkces.linshi.network.retrofit.MyHostNameVerifier;
import com.linkces.linshi.utils.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit帮助类
 */
public class RetrofitHelper {

    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

//    public static Im9Service getIm9API() {
//        return createApi(Im9Service.class, ApiConstants.IM9_BASE_URL);
//    }

    public static LoginService getLoginService() {
        return createApi(LoginService.class, ApiConstants.BASE_URL);
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {

        //InputStream[] cerInput = null;
        //读取证书流，可以为多个
        //cerInput[0] = App1.getsContext().getAssets().open("android.cer");

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(App.app.getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//配置证书
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                            .hostnameVerifier(new MyHostNameVerifier())
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }


    /**
     * 添加UA拦截器
     */
    private static class UserAgentInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
//                    .addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(App.app)) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(App.app)) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
