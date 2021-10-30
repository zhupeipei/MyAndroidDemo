package com.aire.android.okhttp;

import com.aire.android.okhttp.model.Reception;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Author: Zhupeipei
 * @CreateDate: 2021/10/28 10:30 上午
 */
public interface GetRequest_Interface {

    // http://cms.9nali.com/mermaid/config/debug/trackName
    @POST("mermaid/config/debug/trackName")
    Call<Reception> getMermaidConfigData(@Body RequestBody jsonData);
    // @GET注解的作用:采用Get方法发送网络请求

    // getCall() = 接收网络请求数据的方法
    // 其中返回类型为Call<*>，*是接收数据的类（即上面定义的Translation类）
    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
}
