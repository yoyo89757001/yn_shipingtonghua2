package com.example.yinian.menkou.yn_shipingtonghua.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.yinian.menkou.yn_shipingtonghua.R;
import com.example.yinian.menkou.yn_shipingtonghua.beans.BaoCunBean;
import com.example.yinian.menkou.yn_shipingtonghua.utils.CommomDialog;
import com.example.yinian.menkou.yn_shipingtonghua.utils.CommomDialog2;
import com.example.yinian.menkou.yn_shipingtonghua.utils.Constant;
import com.example.yinian.menkou.yn_shipingtonghua.utils.DateUtils;
import com.example.yinian.menkou.yn_shipingtonghua.utils.GenerateTestUserSig;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.mmkv.MMKV;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import static com.tencent.trtc.TRTCCloudDef.TRTCRoleAnchor;
import static com.tencent.trtc.TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_QOS_PREFERENCE_SMOOTH;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_BIG;


/**
 * RTC视频通话的主页面
 *
 * 包含如下简单功能：
 * - 进入/退出视频通话房间
 * - 切换前置/后置摄像头
 * - 打开/关闭摄像头
 * - 打开/关闭麦克风
 * - 显示房间内其他用户的视频画面（当前示例最多可显示6个其他用户的视频画面）
 */
public class RTCActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RTCActivity";
    private static final int REQ_PERMISSION_CODE  = 0x1000;

   // private TextView                        mTitleText;                 //【控件】页面Title
    private TXCloudVideoView                mLocalPreviewView1;          //【控件】本地画面View 默认1是对方画面
    private TXCloudVideoView                mLocalPreviewView2;          //【控件】本地画面View
    private ImageView                       mBackButton;                //【控件】返回上一级页面
    //private Button                          mMuteVideo;                 //【控件】是否停止推送本地的视频数据
    private ImageView                          mMuteAudio;                 //【控件】开启、关闭本地声音采集并上行
    private ImageView                          mSwitchCamera;              //【控件】切换摄像头

    //private TXDeviceManagerImpl txDeviceManager=null;
    private TRTCCloud                       mTRTCCloud;                 // SDK 核心类
    private boolean                         mIsFrontCamera = true;      // 默认摄像头前置
    private static List<String>                    mRemoteUidList;             // 远端用户Id列表
    //private List<TXCloudVideoView>          mRemoteViewList;            // 远端画面列表
    private int                             mGrantedCount = 0;          // 权限个数计数，获取Android系统权限
    private int                             mUserCount = 0;             // 房间通话人数个数
   // private int                             mLogLevel = 0;              // 日志等级
    private String                          mRoomId;                    // 房间Id
    private String                          mUserId;                    // 用户Id
    private TextView  time=null;
    private CountDownTimer timer=null;
    private LinearLayout linearLayout;
    private CommomDialog dialog2;
    private BaoCunBean baoCunBean=null;
    private boolean isLR=false;//不是老人
    private String timeA;
    private boolean mac=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();
        baoCunBean= MMKV.defaultMMKV().decodeParcelable("saveBean", BaoCunBean.class);
        handleIntent();
        mac=false;
        // 先检查权限再加入通话
        if (checkPermission()) {
            initView();
            enterRoom();
        }
    }

    private void handleIntent() {
      mRoomId=getIntent().getStringExtra(Constant.ROOM_ID);
      mUserId=getIntent().getStringExtra(Constant.USER_ID);
      timeA=getIntent().getStringExtra("time");
      isLR=getIntent().getBooleanExtra("isLR",false);
        Log.d("eeeeee", mUserId+"mUserId");
        Log.d("eeeeee", mRoomId+"mRoomId");
        Log.d("eeeeee", isLR+"isLR");
        Log.d("eeeeee", timeA+"timeA");

//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
//        try {
//            if (timeA.length()<=5){
//                timeA+=":00";
//            }
//            Date date= formatter.parse(timeA);
//            Date date2= formatter.parse(DateUtils.timett(System.currentTimeMillis()+""));
//            Log.d("eeeeee", "date.getTime():" + date.getTime());
//            Log.d("eeeeee", "date.getTime():" + date2.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

    private void initView() {
        //mTitleText          = findViewById(R.id.trtc_tv_room_number);
        mLocalPreviewView1   = findViewById(R.id.trtc_tc_cloud_view_main);
        mLocalPreviewView2   = findViewById(R.id.trtc_tc_cloud_view_main2);
        mSwitchCamera          = findViewById(R.id.trtc_btn_mute_video);
        mBackButton       = findViewById(R.id.trtc_btn_mute_audio);
        mMuteAudio =  findViewById(R.id.trtc_btn_switch_camera);
        time=findViewById(R.id.time);
        linearLayout=findViewById(R.id.trtc_ll_controller);

        mBackButton.setOnClickListener(this);
        mLocalPreviewView1.setOnClickListener(this);
        mMuteAudio.setOnClickListener(this);
        mSwitchCamera.setOnClickListener(this);

        mRemoteUidList = new ArrayList<>();
        //mRemoteViewList = new ArrayList<>();
        //mRemoteViewList.add((TXCloudVideoView)findViewById(R.id.trtc_tc_cloud_view_1));

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        try {
          //  Date date1 = simpleDateFormat.parse("10/10/2013 11:30:10");//结束时间
           // Date date2 = simpleDateFormat.parse(DateUtils.time3(System.currentTimeMillis()+"")); //当前时间
            daojishi();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void daojishi(){

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        try {
            if (timeA.length()<=5){
                timeA+=":00";
            }
            Date date= formatter.parse(timeA);
            Date date2= formatter.parse(DateUtils.timett(System.currentTimeMillis()+""));

            if (date!=null && date2!=null){
                Log.d("eeeeee", "date.getTime():" + date.getTime());
                Log.d("eeeeee", "date.getTime():" + date2.getTime());
                long tt=date.getTime()-date2.getTime();
                if (tt>0){
                    timer = new CountDownTimer(tt, 1000) {
                        public void onTick(long millisUntilFinished) {
                            String hms = formatter.format(millisUntilFinished);
                            Log.d("AAAAAA", hms+"剩余多久");
                            if (millisUntilFinished<300000){
                                time.setTextColor(Color.RED);
                            }
                            time.setText(hms);
                        }
                        public void onFinish() {
                            exitThis();
                            finish();
                        }
                    };
                    //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
                    timer.start();
                }

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

//          @SuppressLint("SimpleDateFormat")
//          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//          formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));


    }


    private void enterRoom() {
        mTRTCCloud = TRTCCloud.sharedInstance(getApplicationContext());
        mTRTCCloud.setListener(new TRTCCloudImplListener(RTCActivity.this));

       // txDeviceManager=new TXDeviceManagerImpl();

        // 初始化配置 SDK 参数
        TRTCCloudDef.TRTCParams trtcParams = new TRTCCloudDef.TRTCParams();
        trtcParams.sdkAppId = GenerateTestUserSig.SDKAPPID;
        trtcParams.userId = mUserId;
        trtcParams.roomId = Integer.parseInt(mRoomId);
        // userSig是进入房间的用户签名，相当于密码（这里生成的是测试签名，正确做法需要业务服务器来生成，然后下发给客户端）
        trtcParams.userSig = GenerateTestUserSig.genTestUserSig(trtcParams.userId);
        trtcParams.role = TRTCRoleAnchor;
        Log.d("房间id", mRoomId+"   "+mUserId);

        /**
         * 设置默认美颜效果（美颜效果：自然，美颜级别：5, 美白级别：1）
         * 美颜风格.三种美颜风格：0 ：光滑  1：自然  2：朦胧
         * 视频通话场景推荐使用“自然”美颜效果
         */
        TXBeautyManager beautyManager = mTRTCCloud.getBeautyManager();
        beautyManager.setBeautyStyle(Constant.BEAUTY_STYLE_NATURE);
        beautyManager.setBeautyLevel(5);
        beautyManager.setWhitenessLevel(1);

        TRTCCloudDef.TRTCVideoEncParam encParam = new TRTCCloudDef.TRTCVideoEncParam();
        encParam.videoResolution = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_640_360;
        encParam.videoFps = Constant.VIDEO_FPS;
        encParam.videoBitrate = Constant.RTC_VIDEO_BITRATE;
        encParam.videoResolutionMode = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT; //竖屏
        mTRTCCloud.setVideoEncoderParam(encParam);
        TRTCCloudDef.TRTCNetworkQosParam tt= new TRTCCloudDef.TRTCNetworkQosParam();
        tt.preference=TRTC_VIDEO_QOS_PREFERENCE_SMOOTH;
        mTRTCCloud.setNetworkQosParam(tt);
        // 进入通话
        mTRTCCloud.enterRoom(trtcParams, TRTC_APP_SCENE_VIDEOCALL);
        // 开启本地声音采集并上行
        mTRTCCloud.startLocalAudio(2);
        // 开启本地画面采集并上行
        mTRTCCloud.startLocalPreview(mIsFrontCamera, mLocalPreviewView2);
       // mTRTCCloud.setPriorRemoteVideoStreamType(TRTC_VIDEO_STREAM_TYPE_BIG);

    }

    @Override
    protected void onDestroy() {
        if (timer!=null)
        timer.cancel();
        exitRoom();
        super.onDestroy();

    }

    /**
     * 离开通话
     */
    private void exitRoom() {
        if (mTRTCCloud!=null){
            mTRTCCloud.stopLocalAudio();
            mTRTCCloud.stopLocalPreview();
            mTRTCCloud.exitRoom();
            //销毁 trtc 实例
            if (mTRTCCloud != null) {
                mTRTCCloud.setListener(null);
            }
            mTRTCCloud = null;
            TRTCCloud.destroySharedInstance();
        }
    }

    //////////////////////////////////    Android动态权限申请   ////////////////////////////////////////

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(RTCActivity.this,
                        permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION_CODE) {
            for (int ret : grantResults) {
                if (PackageManager.PERMISSION_GRANTED == ret) mGrantedCount++;
            }
            if (mGrantedCount == permissions.length) {
                initView();
                enterRoom(); //首次启动，权限都获取到，才能正常进入通话
            } else {
                Toast.makeText(this, "没有允许需要的权限，加入通话失败", Toast.LENGTH_SHORT).show();
            }
            mGrantedCount = 0;
        }
    }

    private boolean isB=true;
    @Override
    public void onClick(View v) {
        int id = v.getId();
         if (id == R.id.trtc_btn_mute_audio) {
             if (dialog2!=null && dialog2.isShowing())
                 dialog2.dismiss();
             dialog2=  new CommomDialog(RTCActivity.this, R.style.dialogs, "您确定要退出聊天吗？", new CommomDialog.OnCloseListener() {
                 @Override
                 public void onClick(Dialog dialog, boolean confirm) {
                   //   Log.d("DAFragment3", "confirm:" + confirm);
                     if (confirm) {
                         //退出动作
                         exitThis();
                         dialog.dismiss();
                         finish();
                     }
                 }
             });
             dialog2.setTitle("温馨提示");
             dialog2.setPositiveButton("确定");
             dialog2.show();
        } else if (id == R.id.trtc_btn_switch_camera) {
            muteAudio();
        } else if (id == R.id.trtc_btn_mute_video) {
            switchCamera();
        }else if (id==R.id.trtc_tc_cloud_view_main){
             if (isB){
                 isB=false;
                 linearLayout.setVisibility(View.GONE);
             }else {
                 isB=true;
                 linearLayout.setVisibility(View.VISIBLE);
             }
         }
    }


    private boolean isSelected=false;
    @SuppressLint("UseCompatLoadingForDrawables")
    private void muteAudio() {
        if (!isSelected) {
            isSelected=true;
            mTRTCCloud.stopLocalAudio();
            mMuteAudio.setBackground(getResources().getDrawable(R.mipmap.jinyingbg,null));
        } else {
            isSelected=false;
            mTRTCCloud.startLocalAudio();
            mMuteAudio.setBackground(getResources().getDrawable(R.mipmap.maikefbf,null));
        }
        //mMuteAudio.setSelected(!isSelected);
    }

    private void switchCamera() {
        mTRTCCloud.switchCamera();
        boolean isSelected = mSwitchCamera.isSelected();
        mIsFrontCamera = !isSelected;
        mSwitchCamera.setSelected(!isSelected);
       // txDeviceManager.switchCamera(mIsFrontCamera);
    }



    private class TRTCCloudImplListener extends TRTCCloudListener {

        private final WeakReference<RTCActivity>    mContext;

        public TRTCCloudImplListener(RTCActivity activity) {
            super();
            mContext = new WeakReference<>(activity);
        }

        @Override
        public void onEnterRoom(long l) {
            super.onEnterRoom(l);
            Log.d("eeeeee", "进房成功:" + l);
//            if (isLR){//是老人端，收到加入房间消息
//                 mTRTCCloud.sendCustomCmdMsg(1,baoCunBean.getRegistrationId().getBytes(),true,true);
//            }else {//是家属
//               // mTRTCCloud.sendCustomCmdMsg(1,"ssss".getBytes(),true,true);
//            }
        }

        @Override
        public void onFirstVideoFrame(String s, int i, int i1, int i2) {
            super.onFirstVideoFrame(s, i, i1, i2);
            Log.d("eeeeee", "开始渲染本地或远程用户的首帧画面。");
            if (!mac){
                mac=true;
                if (mRemoteUidList.size()==0 && !isLR){
                    RTCActivity activity = mContext.get();
                    if (activity!=null){
                        exitRoom();
                        new CommomDialog2(RTCActivity.this, R.style.dialogs, "对方未在线,请稍等或联系客服", new CommomDialog2.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                Log.d("DAFragment3", "confirm:" + confirm);
                                if (confirm) {
                                    //退出动作
                                    dialog.dismiss();
                                    finish();
                                }
                            }
                        }).setTitle("确认").setPositiveButton("确定").show();
                    }
                }
            }

        }

        @Override
        public void onRemoteUserEnterRoom(String s) {
            super.onRemoteUserEnterRoom(s);
//            if (isLR){//是老人端，收到加入房间消息
//                mTRTCCloud.sendCustomCmdMsg(1,baoCunBean.getRegistrationId().getBytes(),true,true);
//            }else {//是家属
//              //  mTRTCCloud.sendCustomCmdMsg(1,"ssss".getBytes(),true,true);
//            }
            Log.d("eeeeee", s+"加入房间");
        }


        @Override
        public void onRemoteUserLeaveRoom(String s, int i) {
            super.onRemoteUserLeaveRoom(s, i);
            Log.d("eeeeee", s+"退出房间"+i);
        }

        @Override
        public void onWarning(int i, String s, Bundle bundle) {
            super.onWarning(i, s, bundle);
            Log.d("eeeeee", s+"警告");
        }

        @Override
        public void onConnectionLost() {
            super.onConnectionLost();
            Log.d("eeeeee", "连接断开");
        }

        @Override
        public void onRecvCustomCmdMsg(String s, int i, int i1, byte[] bytes) {
            super.onRecvCustomCmdMsg(s, i, i1, bytes);
          //  String mss = new String(bytes);
           // Log.d("eeeeee", "收到自定义消息"+s+"  "+mss);
//            if (isLR){//老人
//
//            }else {//家属
//                if (mac==null){
//                    mac=mss;
//                }else {
//                    if (!mac.equals(mss)){//
//                        mTRTCCloud.sendCustomCmdMsg(1,mss.getBytes(),true,true);
//                    }
//                }
//            }
        }

        @Override
        public void onUserVideoAvailable(String userId, boolean available) {
            Log.d("eeeeee", "onUserVideoAvailable userId " + userId + ", mUserCount " + mUserCount + ",available " + available);
            int index = mRemoteUidList.indexOf(userId);
            if (available) {
                if (index != -1) { //如果mRemoteUidList有，就不重复添加
                    return;
                }
                mRemoteUidList.add(userId);
                refreshRemoteVideoViews();
            } else {
                if (index == -1) { //如果mRemoteUidList没有，说明已关闭画面
                    return;
                }
                /// 关闭用户userId的视频画面
                mTRTCCloud.stopRemoteView(userId);
                mRemoteUidList.remove(index);
                refreshRemoteVideoViews();
            }
        }


        private void refreshRemoteVideoViews() {
            Log.d("eeeeee", "mRemoteUidList.size():" + mRemoteUidList.size());
//            if (isLR){//是老人，房间不该有人，不然就是已经在开播，然后后面有人乱点又开播进房间
//                if (mRemoteUidList.size()>0){
//                    RTCActivity activity = mContext.get();
//                    if (activity!=null){
//                        exitRoom();
//                        new CommomDialog2(RTCActivity.this, R.style.dialogs, name+" 已经在探视中了!", new CommomDialog2.OnCloseListener() {
//                            @Override
//                            public void onClick(Dialog dialog, boolean confirm) {
//                                Log.d("DAFragment3", "confirm:" + confirm);
//                                if (confirm) {
//                                    //退出动作
//                                    dialog.dismiss();
//                                    finish();
//                                }
//                            }
//                        }).setTitle("确认").setPositiveButton("确定").show();
//                    }
//                    return;
//                }
//            }else {//不是老人，如果房间没人，主动退出
//
//            }

            for (int i = 0; i < mRemoteUidList.size(); i++) {
                String remoteUid = mRemoteUidList.get(i);
                Log.d("eeeeee", "mRemoteUidList.remoteUid():" + remoteUid);
                // 开始显示用户userId的视频画面
                mTRTCCloud.startRemoteView(remoteUid, TRTC_VIDEO_STREAM_TYPE_BIG, mLocalPreviewView1);
            }
        }

        // 错误通知监听，错误通知意味着 SDK 不能继续运行
        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Log.d("eeeeee", "sdk callback onError"+ errMsg + "[" + errCode+ "]");
            RTCActivity activity = mContext.get();
            if (activity != null) {
                Toast.makeText(activity, "聊天发生错误,正在重新连接" , Toast.LENGTH_SHORT).show();
                if (errCode == TXLiteAVCode.ERR_ROOM_ENTER_FAIL || errCode == TXLiteAVCode.ERR_ROOM_REQUEST_ENTER_ROOM_TIMEOUT) {
                    activity.exitRoom();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(3000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    enterRoom();
                                }
                            });
                        }
                    }).start();
                }
            }
        }
    }

    private void exitThis(){//通知后台


    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
    }
}
