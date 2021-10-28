package com.aire.android.okhttp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.okhttp.model.Reception;
import com.aire.android.test.R;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final TextView tv = findViewById(R.id.main_request_tv);
        tv.setText("虎丘");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cms.9nali.com/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        String body = "{\"cid\":109752,\"clientTime\":1635389985782,\"dataId\":0,\"isManual\":true,\"matchType\":0,\"metaId\":36539,\"mt\":0,\"props\":{\"currPage\":\"newPlay\",\"exposePercent\":\"50\",\"currAlbumId\":\"30820071\",\"exploreType\":\"6\",\"anchorId\":\"54267262\",\"currTrackId\":\"226803767\",\"categoryId\":\"12\"},\"seq\":90,\"serviceId\":\"slipPage\",\"sessionId\":1635389699644,\"ubtSource\":[{\"metaId\":778,\"props\":{\"rec_track\":\"3.35_52-298_412-302_426-100_137.1063\",\"adId\":\"0\",\"recommendedLanguage\":\"\",\"albumId\":\"30820071\",\"rec_src\":\"UCFJ5.cvrpcexpbsaog21\",\"position\":\"4\",\"isAd\":\"false\",\"reason_track\":\"\",\"ubtTraceId\":\"-11172277101635389704226\",\"reason_src\":\"\",\"categoryId\":\"12\",\"status\":\"flow\"}}],\"ubtTraceId\":\"-11172277101635389704226\",\"isMarkId\":false}";

        RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json"), body);
        Call<Reception> call = request.getMermaidConfigData(jsonBody);
        call.enqueue(new Callback<Reception>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<Reception> call, Response<Reception> response) {
                //请求处理,输出结果
                response.body().show();
            }

            // 请求失败时候的回调
            @Override
            public void onFailure(Call<Reception> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });

//        //同步请求
//        try {
//            Response<Reception> response = call.execute();
//            response.body().show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient.Builder().build();
//
//                client.newCall(new Request.Builder().url("https://www.baidu.com").get().build()).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        tv.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                tv.setText("request faile");
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//                        final String result = response.body().string();
//                        tv.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                tv.setText(result);
//                            }
//                        });
//                    }
//                });
//            }
//        });
    }
}