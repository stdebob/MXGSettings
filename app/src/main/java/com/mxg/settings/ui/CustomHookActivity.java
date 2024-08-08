// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.mxg.settings.R;
import com.mxg.settings.ui.customhook.CustomHookConfigActivity;

import moralnorm.appcompat.app.AppCompatActivity;

public class CustomHookActivity extends AppCompatActivity {

    Button mAddConfig;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_custom_hook);
        initView();
    }

    private void initView() {
        mAddConfig = findViewById(R.id.add_config);

        mAddConfig.setOnClickListener(v -> startActivity(new Intent(this, CustomHookConfigActivity.class)));
    }
}
