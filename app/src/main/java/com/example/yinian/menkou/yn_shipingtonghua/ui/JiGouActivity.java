package com.example.yinian.menkou.yn_shipingtonghua.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.example.yinian.menkou.yn_shipingtonghua.MyApplication;
import com.example.yinian.menkou.yn_shipingtonghua.R;
import com.example.yinian.menkou.yn_shipingtonghua.beans.BaoCunBean;
import com.example.yinian.menkou.yn_shipingtonghua.beans.JiGouBean;
import com.example.yinian.menkou.yn_shipingtonghua.databinding.ActivityJiGouBinding;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class JiGouActivity extends AppCompatActivity {
    private ActivityJiGouBinding binding=null;
    private PopHeadBlackAdapter2 popHeadAdapterXM = null;
    private List<JiGouBean.ResultDTO.DataDTO> xiangmuList = new ArrayList<>();
    private QMUITipDialog qmuiTipDialog = null;
    private QMUIPopup popup = null;
    private String idid="";
    private BaoCunBean saveInfo=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityJiGouBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();
        EventBus.getDefault().register(this);

        binding.queding.setRadius(QMUIDisplayHelper.dp2px(this, 20));
        binding.queding.setChangeAlphaWhenPress(true);//点击改变透明度
        popHeadAdapterXM = new PopHeadBlackAdapter2(xiangmuList, JiGouActivity.this);
        binding.loudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup=QMUIPopups.listPopup(JiGouActivity.this, QMUIDisplayHelper.dp2px(JiGouActivity.this, 220), QMUIDisplayHelper.dp2px(JiGouActivity.this, 180), popHeadAdapterXM, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        popup.dismiss();
                        binding.jigou.setText(xiangmuList.get(position).getOrgName());
                        idid=xiangmuList.get(position).getId()+"";
                    }
                }).edgeProtection(QMUIDisplayHelper.dp2px(JiGouActivity.this, 10))
                        // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(JiGouActivity.this, 6))
                        .shadow(true)
                        .arrow(true)
                        .bgColor(Color.parseColor("#ffffff"))
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .show(binding.rrr);
            }
        });
        binding.queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.jigou.getText().toString().equals("未设置机构")){
                    Toast.makeText(JiGouActivity.this,"请先选择机构",Toast.LENGTH_SHORT).show();
                }else {
                    BaoCunBean saveInfo= MMKV.defaultMMKV().decodeParcelable("saveBean",BaoCunBean.class);
                    saveInfo.setJigou(binding.jigou.getText().toString());
                    saveInfo.setJigouId(idid);
                    MMKV.defaultMMKV().encode("saveBean",saveInfo);
                    startActivity(new Intent(JiGouActivity.this, PadZhiBoListActivity.class));
                    finish();
                }
            }
        });

        BaoCunBean saveInfo= MMKV.defaultMMKV().decodeParcelable("saveBean",BaoCunBean.class);
        Log.d("JiGouActivity", saveInfo.getJigouId()+"AA");
        if (!saveInfo.getJigouId().equals("")){
            startActivity(new Intent(JiGouActivity.this, PadZhiBoListActivity.class));
            finish();
        }
    }


    public  class PopHeadBlackAdapter2 extends BaseAdapter {

        private final List<JiGouBean.ResultDTO.DataDTO> mGroupNames;
        private LayoutInflater mLayoutInflater;


        public PopHeadBlackAdapter2(List<JiGouBean.ResultDTO.DataDTO> data, Context context) {
            mGroupNames=data;
        }

        @Override
        public int getCount() {
            return mGroupNames == null ? 0 : mGroupNames.size();
        }

        @Override
        public Object getItem(int position) {
            return mGroupNames == null ? null : mGroupNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (mLayoutInflater == null) {
                mLayoutInflater = LayoutInflater.from(parent.getContext());
            }
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.pophead_black_item, parent, false);
                holder = new ViewHolder();
                holder.groupNameTv =  convertView.findViewById(R.id.title);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.groupNameTv.setText(mGroupNames.get(position).getOrgName());

            return convertView;
        }


        public  class ViewHolder {
            TextView groupNameTv;
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        saveInfo= MMKV.defaultMMKV().decodeParcelable("saveBean",BaoCunBean.class);
        if (!saveInfo.getJigou().equals(""))
            binding.jigou.setText(saveInfo.getJigou());
        loginApiunBind();
    }

    private void loginApiunBind(){
        qmuiTipDialog = new QMUITipDialog.Builder(JiGouActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载中...")
                .create();
        qmuiTipDialog.show();
        //开启网关添加子设备模式
        String url = MyApplication.URL+"/api/nurse/orgList";
        Request builder = new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .get()
                .url(url)
                .build();
        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("JiGouActivity", "请求失败url():" + call.request().url());
                if (!JiGouActivity.this.isFinishing())
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("RenTiChuanGanQiActivity", "查询机构:" + call.request().url()+stA);
                    JiGouBean healthBean = JSON.parseObject(stA, JiGouBean.class);
                    if (healthBean!=null && healthBean.isSuccess()){
                        if (healthBean.getResult()!=null && healthBean.getResult().getData()!=null){
                            xiangmuList.clear();
                            xiangmuList.addAll(healthBean.getResult().getData());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    popHeadAdapterXM.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }finally {
                    if (!JiGouActivity.this.isFinishing())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }
                        });
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.equals("finish")){
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}