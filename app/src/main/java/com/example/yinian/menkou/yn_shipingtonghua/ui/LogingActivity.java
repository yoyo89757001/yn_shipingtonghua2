package com.example.yinian.menkou.yn_shipingtonghua.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.yinian.menkou.yn_shipingtonghua.MyApplication;
import com.example.yinian.menkou.yn_shipingtonghua.R;
import com.example.yinian.menkou.yn_shipingtonghua.beans.BaoCunBean;
import com.example.yinian.menkou.yn_shipingtonghua.beans.LoginBean;
import com.example.yinian.menkou.yn_shipingtonghua.utils.Utils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * RTC视频通话的入口页面（可以设置房间id和用户id）
 *
 * RTC视频通话是基于房间来实现的，通话的双方要进入一个相同的房间id才能进行视频通话
 */
public class LogingActivity extends AppCompatActivity {

    private EditText mInputUserId;
    private EditText mInputRoomId;
    private QMUIButton qmuiButton;
    final static int COUNTS = 5;// 点击次数
    final static long DURATION = 1500;// 规定有效时间
    long[] mHits = new long[COUNTS];
    private String resage = null;
    private QMUITipDialog qmuiTipDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_entrance);
        if (getSupportActionBar()!=null)
        getSupportActionBar().hide();
        EventBus.getDefault().register(this);
        mInputUserId = findViewById(R.id.et_input_username);
        mInputRoomId = findViewById(R.id.et_input_room_id);
        qmuiButton=findViewById(R.id.bt_enter_room);
        qmuiButton.setRadius(QMUIDisplayHelper.dp2px(this, 6));
        qmuiButton.setChangeAlphaWhenPress(true);//点击改变透明度
        qmuiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEnterRoom(); // 开始进房
            }
        });
        findViewById(R.id.rtc_entrance_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInput(); // 点击非EditText输入区域，隐藏键盘
            }
        });
        findViewById(R.id.entrance_ic_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每次点击时，数组向前移动一位
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //为数组最后一位赋值
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
                    mHits = new long[COUNTS];//重新初始化数组
                    BaoCunBean saveInfo= MMKV.defaultMMKV().decodeParcelable("saveBean",BaoCunBean.class);
                    saveInfo.setA(false);//平板端模式
                    MMKV.defaultMMKV().encode("saveBean",saveInfo);
                    startActivity(new Intent(LogingActivity.this, BaseActivity.class));
                    finish();
                }
            }
        });
//        mInputRoomId.setText("1256732");
//        String time = String.valueOf(System.currentTimeMillis());
//        String userId = time.substring(time.length() - 8);
//        mInputUserId.setText(userId);

      //  Log.d("LogingActivity", ""+(100/2345));
        BaoCunBean saveInfo= MMKV.defaultMMKV().decodeParcelable("saveBean",BaoCunBean.class);
        if (!saveInfo.getPwd().equals("")){
            Intent intent = new Intent(LogingActivity.this, PhoneZhiBoListActivity.class);
            startActivity(intent);
            finish();
        }else {
            mInputRoomId.setText(saveInfo.getPhone());
        }

//        startActivity(new Intent(LogingActivity.this, PhoneZhiBoListActivity.class));
//        finish();
    }

    private void startEnterRoom() {
        if (TextUtils.isEmpty(mInputUserId.getText().toString().trim())
                || TextUtils.isEmpty(mInputRoomId.getText().toString().trim())) {
            Toast.makeText(LogingActivity.this, "手机号和密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        qmuiTipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("登录中...")
                .create();
        qmuiTipDialog.show();
       link_loging(mInputRoomId.getText().toString().trim(),mInputUserId.getText().toString().trim());
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.split(",").length == 2) {
            if (msgWarp.split(",")[0].equals("registrationId")) {
                resage = msgWarp.split(",")[1];
            }
        }

    }



    Call call=null;
    private void link_loging(String uname, String pwd) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("phone", uname);
            object.put("password", pwd);
            object.put("deviceType", "1");
            if (resage != null) {
                object.put("deviceId", resage);
            } else if (JPushInterface.getRegistrationID(this) != null) {
                object.put("deviceId", JPushInterface.getRegistrationID(this));
            } else {
                object.put("deviceId", "");
            }
            object.put("deviceBrand", Build.BRAND);
            object.put("deviceModel", Build.MODEL);
            object.put("sysVersion", Build.VERSION.RELEASE);
            object.put("appBuild", Utils.getVersionName(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
            Log.d("LoginActivity", object.toString());
            RequestBody body = RequestBody.create(object.toString(), JSON);
            Request.Builder requestBuilder = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .post(body)
                    .url(MyApplication.URL + "/api/nurse/familyLogin");

            // step 3：创建 Call 对象
            call = MyApplication.okHttpClient.newCall(requestBuilder.build());
            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求失败" + e.getMessage());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(LogingActivity.this)
                                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                    .setTipWord("网络请求失败")
                                    .create();
                            if (!LogingActivity.this.isFinishing())
                            qmuiTipDialog2.show();
                            if (!LogingActivity.this.isFinishing())
                            qmuiButton.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!LogingActivity.this.isFinishing())
                                    qmuiTipDialog2.dismiss();
                                }
                            }, 2500);
                            if (!LogingActivity.this.isFinishing())
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("AllConnects", "请求成功" + call.request().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!LogingActivity.this.isFinishing())
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });

                    //获得返回体
                    try {
                        ResponseBody body = response.body();
                        String ss = body.string().trim();
                        Log.d("LoginActivity", ss);
                        JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(ss);
                        LoginBean logingBe = com.alibaba.fastjson.JSON.parseObject(ss, LoginBean.class);
                        if (logingBe.isSuccess()) {
                            if (logingBe.getCode() == 1 && logingBe.getResult() != null) {
                                BaoCunBean baoCunBean = MMKV.defaultMMKV().decodeParcelable("saveBean", BaoCunBean.class);
                                baoCunBean.setNurseName(logingBe.getResult().getNurseName());
                                baoCunBean.setNurseCode(logingBe.getResult().getNurseCode());
                                baoCunBean.setHeadImg(logingBe.getResult().getHeadImg());
                                baoCunBean.setToken(logingBe.getResult().getToken());
                                baoCunBean.setPhone(uname);
                                baoCunBean.setUserId(uname.substring(1));
                                baoCunBean.setPwd(pwd);
                                if (baoCunBean.getRegistrationId() == null || baoCunBean.getRegistrationId().equals("")) {
                                    if (JPushInterface.getRegistrationID(LogingActivity.this) != null) {
                                        baoCunBean.setRegistrationId(JPushInterface.getRegistrationID(LogingActivity.this));
                                    } else if (resage != null) {
                                        baoCunBean.setRegistrationId(resage);
                                    }
                                }
                                MMKV.defaultMMKV().encode("saveBean", baoCunBean);
                                Log.d("LogingActivity", baoCunBean.toString()+"");
                                Intent intent = new Intent(LogingActivity.this, PhoneZhiBoListActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(LogingActivity.this)
                                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                                .setTipWord(jsonObject.getString("errorMsg"))
                                                .create();
                                        if (!LogingActivity.this.isFinishing())
                                        qmuiTipDialog2.show();
                                        if (!LogingActivity.this.isFinishing())
                                        qmuiButton.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (!LogingActivity.this.isFinishing())
                                                qmuiTipDialog2.dismiss();
                                            }
                                        }, 2500);
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(LogingActivity.this)
                                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                            .setTipWord(jsonObject.getString("errorMsg"))
                                            .create();
                                    if (!LogingActivity.this.isFinishing())
                                    qmuiTipDialog2.show();
                                    if (!LogingActivity.this.isFinishing())
                                    qmuiButton.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!LogingActivity.this.isFinishing())
                                            qmuiTipDialog2.dismiss();
                                        }
                                    }, 2500);
                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.d("AllConnects", e.getMessage() + "异常");
                    }
                }
            });
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
