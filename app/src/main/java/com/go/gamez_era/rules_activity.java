package com.go.gamez_era;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;


public class rules_activity extends AppCompatActivity {
    TextView heading,rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8;
    RelativeLayout cl;
    LinearLayout rl;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_activity);
        //No internet connection
        heading=findViewById(R.id.rules_heading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rules);


        cl=findViewById(R.id.NonetScreen_rules);
        rl=findViewById(R.id.connected_rules);
        Tovuti.from(this).monitor(new Monitor.ConnectivityListener(){
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast){
                if(isConnected)
                {
                    cl.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                    heading.setVisibility(View.VISIBLE);

                }
                else
                {
                    cl.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                    heading.setVisibility(View.GONE);

                }
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_rules));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rules_activity.this.finish();
                }
            });
        }

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/sans_medium.ttf");


        rule1=findViewById(R.id.rule_1);
        rule2=findViewById(R.id.rule_2);
        rule3=findViewById(R.id.rule_3);
        rule4=findViewById(R.id.rule_4);
        rule5=findViewById(R.id.rule_5);
        rule6=findViewById(R.id.rule_6);
        rule7=findViewById(R.id.rule_7);
        rule8=findViewById(R.id.rule_8);
        heading.setTypeface(custom_font);
        rule1.setTypeface(custom_font);
        rule2.setTypeface(custom_font);
        rule3.setTypeface(custom_font);
        rule4.setTypeface(custom_font);
        rule5.setTypeface(custom_font);
        rule6.setTypeface(custom_font);
        rule7.setTypeface(custom_font);
        rule8.setTypeface(custom_font);

    }
}
