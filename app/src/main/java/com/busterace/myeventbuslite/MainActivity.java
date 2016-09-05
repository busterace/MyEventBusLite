package com.busterace.myeventbuslite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnActivitySingle;
    private Button btnExistActivity;
    private Button btnFragmentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMonitor();
    }

    private void initMonitor() {
        btnActivitySingle.setOnClickListener(this);
        btnExistActivity.setOnClickListener(this);
        btnFragmentActivity.setOnClickListener(this);
    }

    private void initView() {
        btnActivitySingle = (Button) findViewById(R.id.btn_activity_single);
        btnExistActivity = (Button) findViewById(R.id.btn_exist_activity);
        btnFragmentActivity = (Button) findViewById(R.id.btn_fragment_activity);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_activity_single:
                startActivity(new Intent(this,ActSingle.class));
                break;
            case R.id.btn_exist_activity:
                Toast.makeText(MainActivity.this, "exist", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_fragment_activity:
                Toast.makeText(MainActivity.this, "fragment", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
