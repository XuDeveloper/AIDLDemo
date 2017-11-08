package com.tencent.zhaoxuzhang.appclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tencent.zhaoxuzhang.aidldemo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private IMyAidlInterface mIMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.tencent.service.ACTION");
                intent.setPackage("com.tencent.zhaoxuzhang.aidldemo");
                bindService(intent, new ConnectCallBack(), Context.BIND_AUTO_CREATE);
            }
        });
    }

    class ConnectCallBack implements ServiceConnection {

        // 服务连接成功回调
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                mIMyAidlInterface.login("xu", "123");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        // 服务断开连接回调
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIMyAidlInterface = null;
        }
    }

}
