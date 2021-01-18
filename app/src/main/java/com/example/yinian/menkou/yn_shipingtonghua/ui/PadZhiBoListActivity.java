
package com.example.yinian.menkou.yn_shipingtonghua.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yinian.menkou.yn_shipingtonghua.MyApplication;
import com.example.yinian.menkou.yn_shipingtonghua.R;
import com.example.yinian.menkou.yn_shipingtonghua.beans.BaoCunBean;
import com.example.yinian.menkou.yn_shipingtonghua.beans.Ckkeler;
import com.example.yinian.menkou.yn_shipingtonghua.utils.Constant;
import com.example.yinian.menkou.yn_shipingtonghua.utils.DateUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.mmkv.MMKV;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PadZhiBoListActivity extends AppCompatActivity {//平板端直播列表

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private BianQianAdapter bianQianAdapter=null;
    private final List<Ckkeler> dangBeanList=new ArrayList<>();
    private QMUITipDialog qmuiTipDialog = null;
    private TextView myTitle;
    private BaoCunBean saveInfo=null;
    private TextView riqi,shijian;
    private Button button;
    private TextView t1,t2,t3;
    private EditText editText;
    private int type=1;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_bo_list);
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();
        saveInfo= MMKV.defaultMMKV().decodeParcelable("saveBean",BaoCunBean.class);
        refreshLayout=findViewById(R.id.refreshLayout);
        recyclerView=findViewById(R.id.recyclerview);
        editText=findViewById(R.id.eedit);
        t1=findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type=1;
                t1.setBackgroundResource(R.drawable.zk_lanleft);
                t2.setBackgroundResource(R.drawable.zk_lancent50);
                t3.setBackgroundResource(R.drawable.zk_lanright50);
                t1.setTextColor(Color.parseColor("#FFFFFF"));
                t2.setTextColor(Color.parseColor("#E6481D"));
                t3.setTextColor(Color.parseColor("#E6481D"));
                qmuiTipDialog = new QMUITipDialog.Builder(PadZhiBoListActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("获取数据中...")
                        .create();
                qmuiTipDialog.show();
                loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+"")+" 00:00",DateUtils.timeb(System.currentTimeMillis()+"")+" 23:50","");
            }
        });
        t2=findViewById(R.id.t2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type=2;
                t1.setBackgroundResource(R.drawable.zk_lanleft50);
                t2.setBackgroundResource(R.drawable.zk_lancent);
                t3.setBackgroundResource(R.drawable.zk_lanright50);
                t1.setTextColor(Color.parseColor("#E6481D"));
                t2.setTextColor(Color.parseColor("#FFFFFF"));
                t3.setTextColor(Color.parseColor("#E6481D"));
                qmuiTipDialog = new QMUITipDialog.Builder(PadZhiBoListActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("获取数据中...")
                        .create();
                qmuiTipDialog.show();
                loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+86400000+"")+" 00:00",DateUtils.timeb(System.currentTimeMillis()+86400000+"")+" 23:50","");
            }
        });
        t3=findViewById(R.id.t3);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type=3;
                t1.setBackgroundResource(R.drawable.zk_lanleft50);
                t2.setBackgroundResource(R.drawable.zk_lancent50);
                t3.setBackgroundResource(R.drawable.zk_lanright);
                t1.setTextColor(Color.parseColor("#E6481D"));
                t2.setTextColor(Color.parseColor("#E6481D"));
                t3.setTextColor(Color.parseColor("#FFFFFF"));

                qmuiTipDialog = new QMUITipDialog.Builder(PadZhiBoListActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("获取数据中...")
                        .create();
                qmuiTipDialog.show();
                loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+172800000+"")+" 00:00",DateUtils.timeb(System.currentTimeMillis()+172800000+"")+" 23:50","");
            }
        });
        button=findViewById(R.id.htte);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInput();
                qmuiTipDialog = new QMUITipDialog.Builder(PadZhiBoListActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("获取数据中...")
                        .create();
                qmuiTipDialog.show();
                loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+"")+" 00:00",
                        DateUtils.timeb(System.currentTimeMillis()+"")+" 23:50",editText.getText().toString().trim());
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    hideInput();
                }
                return false;
            }
        });
        findViewById(R.id.hhjj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInput();
            }
        });

        riqi=findViewById(R.id.riqi);
        shijian=findViewById(R.id.shijian);
        myTitle=findViewById(R.id.myTitle);
        bianQianAdapter=new BianQianAdapter(R.layout.kuaidi_item_rtcgq,dangBeanList);
        //View headview= LayoutInflater.from(PadZhiBoListActivity.this).inflate(R.layout.pad_head,null);
        //bianQianAdapter.setHeaderView(headview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(bianQianAdapter);
        View view1= LayoutInflater.from(PadZhiBoListActivity.this).inflate(R.layout.anull_data,null);
        bianQianAdapter.setEmptyView(view1);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                switch (type){
                    case 1:
                        loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+"")+" 00:00",DateUtils.timeb(System.currentTimeMillis()+"")+" 23:50","");
                        break;
                    case 2:
                        loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+86400000+"")+" 00:00",DateUtils.timeb(System.currentTimeMillis()+86400000+"")+" 23:50","");
                        break;
                    case 3:
                        loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+172800000+"")+" 00:00",DateUtils.timeb(System.currentTimeMillis()+172800000+"")+" 23:50","");
                        break;
                }

            }
        });

        bianQianAdapter.addChildClickViewIds(R.id.caozuo);
        bianQianAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId()==R.id.caozuo){
                   startActivity(new Intent(PadZhiBoListActivity.this,RTCActivity.class)
                           .putExtra(Constant.ROOM_ID,dangBeanList.get(position).getUserCode())
                           .putExtra(Constant.USER_ID,dangBeanList.get(position).getElderId())
                           .putExtra("isLR",true)
                           .putExtra("time",dangBeanList.get(position).getEndTime()));
                }
            }
        });
        try {
            riqi.setText(DateUtils.time1(System.currentTimeMillis()+""));
            shijian.setText(DateUtils.ti(System.currentTimeMillis()+""));
            myTitle.setText(saveInfo.getJigou());
        }catch (Exception e){
            e.printStackTrace();
        }


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        TimeChangeReceiver timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(timeChangeReceiver, intentFilter);


        String aa=JPushInterface.getRegistrationID(PadZhiBoListActivity.this);
        if (aa!=null)
        saveInfo.setRegistrationId(aa);
        MMKV.defaultMMKV().encode("saveBean",saveInfo);
        Log.d("PadZhiBoListActivity", aa+"极光id");

    }


    @Override
    protected void onResume() {
        super.onResume();
        qmuiTipDialog = new QMUITipDialog.Builder(PadZhiBoListActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("获取数据中...")
                .create();
        qmuiTipDialog.show();
        loginApiunBind(DateUtils.timeb(System.currentTimeMillis()+"")+" 00:00",DateUtils.timeb(System.currentTimeMillis()+"")+" 23:50","");

    }

    private static class BianQianAdapter extends BaseQuickAdapter<Ckkeler, BaseViewHolder> implements LoadMoreModule {


        public BianQianAdapter(int layoutResId, @Nullable List<Ckkeler> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, Ckkeler taskBean) {
            try {
                QMUIButton but=baseViewHolder.getView(R.id.caozuo);
                but.setRadius(QMUIDisplayHelper.dp2px(getContext(), 4));
                but.setChangeAlphaWhenPress(true);//点击改变透明度
                baseViewHolder.setText(R.id.fanghao,taskBean.getRoomName());
                baseViewHolder.setText(R.id.xingming,taskBean.getElderName());
                baseViewHolder.setText(R.id.chuanghao,taskBean.getBedName());
                baseViewHolder.setText(R.id.kaishi,taskBean.getStartTime());
                baseViewHolder.setText(R.id.jieshu,taskBean.getEndTime());
                baseViewHolder.setText(R.id.xingbie,taskBean.getElderCode().equals("1") ? "男" : "女");

                if (taskBean.isClick()){
                    baseViewHolder.setBackgroundResource(R.id.caozuo,R.color.colorPrimary);
                    baseViewHolder.setEnabled(R.id.caozuo,true);
                }else {
                    baseViewHolder.setBackgroundResource(R.id.caozuo,R.color.rtc_title_bg);
                    baseViewHolder.setEnabled(R.id.caozuo,false);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void loginApiunBind(String visitTimeStart,String visitTimeEnd,String elderName){
        //开启网关添加子设备模式
        String url = MyApplication.URL+"/app/elder/getElderInfoByChannelId";
        JSONObject json =new  JSONObject();
        JSONObject json2 =new  JSONObject();
        try {
            json2.put("visitTimeStart",visitTimeStart);
            json2.put("visitTimeEnd",visitTimeEnd);
            json2.put("elderName",elderName);
            json2.put("orgId",saveInfo.getJigouId());
            json.put("pageNum",1);
            json.put("pageSize",300);
            json.put("params", json2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("RenTiChuanGanQiActivity", "查询设备属性参数:"+json.toString());
        Request builder = new  Request.Builder()
                //.addHeader("token",saveInfo.getToken())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(json.toString()+"",MediaType.parse("application/json"))).url(url).build();
        // String[] ssee =body.split("\n");
        // Log.d("RenTiChuanGanQiActivity", "查询设备属性"+ssee[0] +ssee[1]);

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (!PadZhiBoListActivity.this.isFinishing())
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                            refreshLayout.finishRefresh();
                        }
                    });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("RenTiChuanGanQiActivity", "查询pad:" + call.request().url()+stA);
                    dangBeanList.clear();
                    List<Ckkeler> kalakers = com.alibaba.fastjson.JSONObject.parseArray(stA, Ckkeler.class);
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

                    for (Ckkeler kalaker : kalakers) {
                        if (type==1){//今天的 提前半个小时的
                            try {
                                String timeA=kalaker.getEndTime();
                                if (timeA.length()<=5){
                                    timeA+=":00";
                                }
                                Date date= formatter.parse(timeA); //过期时间
                                Date date2= formatter.parse(DateUtils.timett(System.currentTimeMillis()+""));//当前时间
                                if (date!=null && date2!=null){
                                    Log.d("eeeeee", "date.getTime():" + date.getTime());
                                    Log.d("eeeeee", "date.getTime():" + date2.getTime());
                                    //过期了
                                    kalaker.setClick((date.getTime() - date2.getTime()) >= 0);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dangBeanList.add(kalaker);
                        }else {
                            dangBeanList.add(kalaker);
                        }
                    }
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }finally {
                    if (!PadZhiBoListActivity.this.isFinishing())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bianQianAdapter.notifyDataSetChanged();
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                                refreshLayout.finishRefresh();
                            }
                        });
                }
            }
        });
    }


    class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(Objects.requireNonNull(intent.getAction()))) {
                riqi.setText(DateUtils.time1(System.currentTimeMillis() + ""));
                shijian.setText(DateUtils.ti(System.currentTimeMillis() + ""));
            }
        }
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