package com.tcs.sinanews.netwrok;

import android.content.Context;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.File;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by pingfan.yang on 2017/1/1.
 */
public class RetrofitUtils {
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private static NewAPiServer sNewAPiServer;
    //设置OKhttp拦截器
    static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    //获取实例
    public static NewAPiServer newInstance(Context context){
        if (mRetrofit == null)
        {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            mOkHttpClient = new OkHttpManager(context.getCacheDir()+ File.separator +"network",1024*1024*50,5)
                    .addInterceptor(new ResponseInfoInterceptor(context))
                    .build();
          /*  mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor()
                    .build();*/
            mRetrofit = new Retrofit.Builder()
                    //设置OKHttpClient,如果不设置会提供一个默认的
                    .client(mOkHttpClient)
                    //设置baseUrl
                    .baseUrl("http://api.tianapi.com/")
                    //添加LoganSquare转换器
                    .addConverterFactory(LoganSquareConverterFactory.create())
                    .build();
            sNewAPiServer = mRetrofit.create(NewAPiServer.class);

        }
        return sNewAPiServer;
    }



}
