package com.example.dell.practice0924.di;

import com.example.dell.practice0924.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Moudleimp implements Icontract.imoudle{
    private String path="https://www.zhaoapi.cn/product/getCarts?uid=71";

    @Override
    public void requestdata(final callisten callisten) {
        HttpUtils.getHttpUtils().getdata(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                callisten.requestmsg(s);
            }
        });
    }
}
