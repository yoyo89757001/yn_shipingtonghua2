package com.example.yinian.menkou.yn_shipingtonghua.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yinian.menkou.yn_shipingtonghua.MyApplication;
import com.example.yinian.menkou.yn_shipingtonghua.R;
import com.example.yinian.menkou.yn_shipingtonghua.beans.BaoCunBean;
import com.example.yinian.menkou.yn_shipingtonghua.beans.Kalaker;
import com.example.yinian.menkou.yn_shipingtonghua.utils.CommomDialog;
import com.example.yinian.menkou.yn_shipingtonghua.utils.Constant;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.qmuiteam.qmui.layout.IQMUILayout.HIDE_RADIUS_SIDE_BOTTOM;

public class PhoneZhiBoListActivity extends AppCompatActivity {//手机端直播列表

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private BianQianAdapter bianQianAdapter=null;
    private List<Kalaker> dangBeanList=new ArrayList<>();
    private QMUITipDialog qmuiTipDialog = null;
    private ImageView popviews;
    private QMUIPopup mPopupWindow = null;
    private BaoCunBean baoCunBean=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_zhi_bo_list);
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();
        refreshLayout=findViewById(R.id.refreshLayout);
        recyclerView=findViewById(R.id.recyclerview);
        popviews=findViewById(R.id.popview);
        popviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PhoneZhiBoListActivity", "dddd");
                showMenuPop(popviews);
            }
        });
        baoCunBean= MMKV.defaultMMKV().decodeParcelable("saveBean", BaoCunBean.class);

        bianQianAdapter=new BianQianAdapter(R.layout.kuaidi_item_rtcgq2,dangBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(bianQianAdapter);
        View view1= LayoutInflater.from(PhoneZhiBoListActivity.this).inflate(R.layout.anull_data,null);
        bianQianAdapter.setEmptyView(view1);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                loginApiunBind();
            }
        });
        bianQianAdapter.addChildClickViewIds(R.id.queding);
        bianQianAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId()==R.id.queding){
                    startActivity(new Intent(PhoneZhiBoListActivity.this,RTCActivity.class)
                            .putExtra(Constant.USER_ID,baoCunBean.getUserId())
                            .putExtra(Constant.ROOM_ID,dangBeanList.get(position).getUserCode())
                            .putExtra("isLR",false)
                            .putExtra("time",dangBeanList.get(position).getEndTime()));
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        qmuiTipDialog = new QMUITipDialog.Builder(PhoneZhiBoListActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("获取数据中...")
                .create();
        qmuiTipDialog.show();
        loginApiunBind();
    }

    private static class BianQianAdapter extends BaseQuickAdapter<Kalaker, BaseViewHolder> implements LoadMoreModule {


        public BianQianAdapter(int layoutResId, @Nullable List<Kalaker> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, Kalaker taskBean) {
            try {
                QMUIButton but=baseViewHolder.getView(R.id.queding);
                but.setRadius(QMUIDisplayHelper.dp2px(getContext(), 6));
                but.setChangeAlphaWhenPress(true);//点击改变透明度
                QMUIRadiusImageView2 radiusImageView=baseViewHolder.getView(R.id.d1122);
                radiusImageView.setHideRadiusSide(HIDE_RADIUS_SIDE_BOTTOM);
                QMUIRadiusImageView2 radiusImageView2=baseViewHolder.getView(R.id.d11);
                radiusImageView2.setHideRadiusSide(HIDE_RADIUS_SIDE_BOTTOM);
                baseViewHolder.setText(R.id.name,taskBean.getElderName()+"  "+taskBean.getRoomName());
                baseViewHolder.setText(R.id.time,"探视时间"+taskBean.getVisitTime().split(" ")[0]+"\n"+taskBean.getStartTime()+"至"+taskBean.getEndTime());


            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void loginApiunBind(){
        //开启网关添加子设备模式
        String url = MyApplication.URL+"/app/elder/getElderInfoByAppoint";
        JSONObject json =new  JSONObject();
        JSONObject json2 =new  JSONObject();

        try {
            json.put("pageNum",1);
            json.put("pageSize",100);
            json.put("params", json2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("RenTiChuanGanQiActivity", "查询设备属性参数:"+json.toString());
        Request builder = new  Request.Builder()
                .addHeader("token",baoCunBean.getToken())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(json.toString()+"", MediaType.parse("application/json"))).url(url).build();
        // String[] ssee =body.split("\n");
        // Log.d("RenTiChuanGanQiActivity", "查询设备属性"+ssee[0] +ssee[1]);

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (!PhoneZhiBoListActivity.this.isFinishing())
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
                    Log.d("RenTiChuanGanQiActivity", "查询设备属性请求地址:" + call.request().url()+stA);
                    dangBeanList.clear();
                    List<Kalaker> kalakers = com.alibaba.fastjson.JSONObject.parseArray(stA, Kalaker.class);
                    dangBeanList.addAll(kalakers);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }finally {
                    if (!PhoneZhiBoListActivity.this.isFinishing())
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


    private void showMenuPop(View view2) {
        View view = getLayoutInflater().inflate(R.layout.pop_head_name, null, false);
        LinearLayout l3 = view.findViewById(R.id.l3);
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment1", "退出");
                new CommomDialog(PhoneZhiBoListActivity.this, R.style.dialogs, "请确认是否退出?", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        Log.d("DAFragment3", "confirm:" + confirm);
                        if (confirm) {
                            //退出动作
                            baoCunBean.setPwd("");
                            MMKV.defaultMMKV().encode("saveBean",baoCunBean);
                            dialog.dismiss();
                            startActivity(new Intent(PhoneZhiBoListActivity.this,LogingActivity.class));
                            finish();
                        }
                    }
                }).setTitle("确认").setPositiveButton("确定").show();
            }
        });
        if (mPopupWindow == null) {
            mPopupWindow = QMUIPopups.popup(PhoneZhiBoListActivity.this, QMUIDisplayHelper.dp2px(PhoneZhiBoListActivity.this, 140), QMUIDisplayHelper.dp2px(PhoneZhiBoListActivity.this, 46))
                    .view(view)
                    .edgeProtection(QMUIDisplayHelper.dp2px(PhoneZhiBoListActivity.this, 8))
                    .shadow(true)
                    .arrow(true)
                    .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        }
        mPopupWindow.show(view2);
    }


}