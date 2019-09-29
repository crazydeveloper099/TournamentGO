package com.go.gamez_era;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class privacy_policy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        TextView textView=findViewById(R.id.privacyid);
        textView.setText(Html.fromHtml(getString(R.string.privacy_policy)));
    }
}
