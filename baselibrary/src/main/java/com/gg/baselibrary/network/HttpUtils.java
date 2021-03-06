package com.gg.baselibrary.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.Map;

/**
 * Created by GG on 2017/8/30.
 */

public class HttpUtils {

    private final int TYPE_POST = 0x0001;
    private final int TYPE_GET = 0x0002;


    private Context mContext;

    private int mType = TYPE_GET;

    private Map<String, Object> mParams;

    private IHttpEngine mHttpEngine;

    private static IHttpEngine initHttpEngine;

    private String mUrl;
    //是否缓存
    private boolean mCache = false;


    private HttpUtils(Context context) {
        mContext = context;
        mParams = new ArrayMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public static void init(IHttpEngine httpEngine) {
        initHttpEngine = httpEngine;
    }

    public HttpUtils get() {
        mType = TYPE_GET;
        return this;
    }

    public HttpUtils post() {
        mType = TYPE_POST;
        return this;
    }

    public HttpUtils param(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils params(Map<String, Object> map) {
        mParams.putAll(map);
        return this;
    }

    public HttpUtils setEngine(IHttpEngine httpEngine) {
        initHttpEngine = httpEngine;
        return this;
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils cache(boolean cache) {
        mCache = cache;
        return this;
    }


    public void execute(EngineCallBack callBack) {

        if (TextUtils.isEmpty(mUrl)) {
            throw new UrlMissingException("请传入获取的网址");
        }

        //当初始化 网络引擎  或者是在使用时替换了 网络引擎 就在这 导入
        if (initHttpEngine != null)
            mHttpEngine = initHttpEngine;

        if (mHttpEngine == null) {
            throw new EngineLackException("未设置网络引擎");
        }

        switch (mType) {
            case TYPE_GET:
                mHttpEngine.get(mContext, mUrl, mParams, callBack, mCache);
                break;
            case TYPE_POST:
                mHttpEngine.post(mContext, mUrl, mParams, callBack, mCache);
                break;
        }

    }

//    public void execute() {
//        execute(null);
//    }

    private class UrlMissingException extends RuntimeException {
        public UrlMissingException() {
        }

        UrlMissingException(String message) {
            super(message);
        }

        public UrlMissingException(String message, Throwable cause) {
            super(message, cause);
        }

        public UrlMissingException(Throwable cause) {
            super(cause);
        }

    }

    private class EngineLackException extends RuntimeException {
        public EngineLackException() {
        }

        EngineLackException(String message) {
            super(message);
        }

        public EngineLackException(String message, Throwable cause) {
            super(message, cause);
        }

        public EngineLackException(Throwable cause) {
            super(cause);
        }

    }

}
