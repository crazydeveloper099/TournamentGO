package com.go.gamez_era;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class forget_pass extends AppCompatActivity {

    EditText forget;
    Button reset_pass;
    Toolbar tb;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        reset_pass=findViewById(R.id.reset_pass);
        forget=findViewById(R.id.email_forget_pass);
        tb=findViewById(R.id.toolbar_reset_pass);
        tb.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_forget_pass));
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget_pass.this.finish();
            }
        });
        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!forget.getText().toString().isEmpty())
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(forget.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toasty.success(forget_pass.this,"Reset instructions mailed!").show();
                                    }
                                }
                            });
                }

            }
        });

    }
}