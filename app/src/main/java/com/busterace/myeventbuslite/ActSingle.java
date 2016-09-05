package com.busterace.myeventbuslite;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.busterace.eventbuslitelibrary.EventBusLite;

public class ActSingle extends AppCompatActivity {

    private Button btnPost;
    private EditText etSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_single);
        initView();
        initMonitor();
        EventBusLite.getDefault().register(this);
    }

    private void initView() {
        btnPost = (Button) findViewById(R.id.btn_post);
        etSubscriber = (EditText) findViewById(R.id.et_subscriber);
    }

    private void initMonitor() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);//模拟延时
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String msg = "Thread:"+Thread.currentThread().getName()+"  "+Thread.currentThread().getId();
                        Log.d("ddddd",msg);
                        Bundle bundle = new Bundle();
                        bundle.putString("editText","asd");
                        EventBusLite.getDefault().post(bundle);
                    }
                }.start();
            }
        });
    }

    private Handler handler = new Handler();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusLite.getDefault().unregister(this);
    }

    public void onEventOnMainThread(Bundle bundle){
        String msg = "onEventChangeEditText:"+Thread.currentThread().getName()+"  "+Thread.currentThread().getId();
        Log.d("ddddd",msg);
        String editText = bundle.getString("editText");
        if (editText != null){
            etSubscriber.setText(editText);
        }
    }
}
