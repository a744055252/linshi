package com.linkces.linshi.network.function;

import com.linkces.linshi.entity.ResultModel;
import com.linkces.linshi.network.exception.ServerException;
import com.linkces.linshi.utils.LogUtil;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * 服务器结果处理函数
 *
 */
public class ServerResultFunction<T extends ResultModel<? extends Serializable>> implements Function<T, T> {
    @Override
    public T apply(@NonNull T response) throws Exception {
        //打印服务器回传结果
        LogUtil.e(response.toString());
        if (response.isError()) {
            throw new ServerException(response.getCode(), response.getMessage());
        }
        return response;
    }
}
