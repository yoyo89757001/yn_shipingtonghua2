package com.example.yinian.menkou.yn_shipingtonghua;



import android.app.Application;
import com.example.yinian.menkou.yn_shipingtonghua.beans.BaoCunBean;
import com.tencent.bugly.Bugly;
import com.tencent.mmkv.MMKV;
import java.io.File;
import java.util.concurrent.TimeUnit;
import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;
import okhttp3.OkHttpClient;


/**
 * Created by Administrator on 2018/8/3.
 */




public class MyApplication extends Application {

    public  static String SDPATH ;
    public static MyApplication myApplication;
    public static final String URL ="http://39.108.253.88:8082";
    //public static final String URL ="http://ni6c46.natappfree.cc";



    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
            //.cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();



    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;

        Bugly.init(this, "777dbe4d98", false);

        SDPATH = getExternalFilesDir(null)+ File.separator+"yinian1";
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
        // Application中配置(设置宽适配)
        AutoSizeConfig.getInstance().setExcludeFontScale(true).setBaseOnWidth(true);
        BaoCunBean baoCunBean = MMKV.defaultMMKV().decodeParcelable("saveBean",BaoCunBean.class);
        if (baoCunBean == null) {
            baoCunBean = new BaoCunBean();
            baoCunBean.setJigou("");
            baoCunBean.setJigouId("");
            baoCunBean.setPhone("");
            baoCunBean.setPwd("");
            baoCunBean.setHeadImg("");
            baoCunBean.setNurseName("");
            baoCunBean.setRegistrationId("");
            baoCunBean.setNurseCode("");
            baoCunBean.setRoomId("");
            baoCunBean.setUserId("");
            baoCunBean.setA(true);
            MMKV.defaultMMKV().encode("saveBean",baoCunBean);
        }
        JPushInterface.init(this);

    }


}
