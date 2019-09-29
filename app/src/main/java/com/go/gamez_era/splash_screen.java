package com.go.gamez_era;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.os.Bundle;
import android.transition.CircularPropagation;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.easing.linear.Linear;
import com.dcastalia.localappupdate.DownloadApk;
import com.github.atzcx.appverupdater.AppVerUpdater;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hmomeni.progresscircula.ProgressCircula;

import com.pd.chocobar.ChocoBar;

import java.util.List;
import java.util.Objects;

public class splash_screen extends AppCompatActivity {
    private int time = 2000;
    LinearLayout li1,li2;
    int flag=0;
    String url = "http://2ournamentgo.com/tournamentgo.apk";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        li1=findViewById(R.id.spl_screen);
        li2=findViewById(R.id.update_screen);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "font/boldness.otf");

        FirebaseDatabase.getInstance().getReference().child("update").child("ver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(Double.parseDouble(dataSnapshot.getValue().toString())>myApp.ver)
                {
                    flag=1;
                    li1.setVisibility(View.GONE);
                    li2.setVisibility(View.VISIBLE);
                    DownloadApk apk=new DownloadApk(splash_screen.this);
                    apk.startDownloadingApk(url);
                }
                else
                {
                    flag=2;
                    li1.setVisibility(View.VISIBLE);
                    li2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        AppVerUpdater appVerUpdater= new AppVerUpdater()
                .setUpdateJSONUrl("http://2ournamentgo.com/update.json")
                .setShowNotUpdated(true)
                .setViewNotes(true)
                .build(splash_screen.this);
        appVerUpdater.onResume();
        final ProgressBar pc = findViewById(R.id.progressBar);
        final Snackbar cb= ChocoBar.builder().setActivity(splash_screen.this)
                .setText("No internet connectivity")
                .setDuration(100000)
                .red();
        pc.setVisibility(View.GONE);



        Tovuti.from(this).monitor(new Monitor.ConnectivityListener() {
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast) {
                if (isConnected) {
                    cb.dismiss();
                    if(pc.getVisibility()==View.GONE) {
                        pc.setVisibility(View.VISIBLE);
                    }
                } else {
                    if(pc.getVisibility()==View.VISIBLE) {
                        pc.setVisibility(View.GONE);
                    }
                    cb.show();
                }
            }});


        if(flag==2) {
            final TextView tv1 = findViewById(R.id.tv_go);
            final TextView tv3 = findViewById(R.id.tv1);
            final LinearLayout lv = findViewById(R.id.text_title);

            tv1.setTypeface(custom_font2);
            tv3.setTypeface(custom_font2);
            if (user == null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(splash_screen.this, login_activity.class);
                        startActivity(intent);
                        splash_screen.this.finish();
                    }
                }, 700);
            } else {

                FirebaseDatabase.getInstance().getReference().child("Linker").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("user_name")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                myApp.user_name = dataSnapshot.getValue().toString();

                                final Intent intent = new Intent(splash_screen.this, MainActivity.class);
                                startActivity(intent);
                                splash_screen.this.finish();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    pc.setVisibility(View.VISIBLE);

                }
            }, 1500);


        }
    }
}