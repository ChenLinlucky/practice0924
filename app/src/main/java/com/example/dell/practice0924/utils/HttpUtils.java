package com.example.dell.practice0924.utils;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtils {
    private static HttpUtils httpUtils;
    private final OkHttpClient okHttpClient;

    public HttpUtils(){
        okHttpClient = new OkHttpClient();
                //.newBuilder().addInterceptor(new longinterceptor()).build();//new拦截器
    }
/*

    //自定义拦截器
    class longinterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = chain.request().newBuilder().addHeader("source", "android");
            Log.d("xxxxxx", "request:" + request);
            Response proceed = chain.proceed(request);
            return proceed;
        }
    }
*/


    public static HttpUtils getHttpUtils() {
        if(httpUtils==null){
            synchronized (HttpUtils.class){
                if(httpUtils==null){
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }
    public void getdata(String path, Callback callback){
        Request request = new Request.Builder().url(path).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
