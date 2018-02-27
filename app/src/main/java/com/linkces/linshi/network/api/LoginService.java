package com.linkces.linshi.network.api;


import com.linkces.linshi.entity.ResultModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("User/token")
    Observable<ResultModel<String>> getToken(@Field("account") String account,
                                     @Field("password") String password);

}