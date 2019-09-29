package com.go.gamez_era;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.go.gamez_era.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.Objects;

public class login_activity extends AppCompatActivity {
    int flag=0;
    private EditText Email,password,phone;
    private Button Login ,Signup;
    private AVLoadingIndicatorView pgbar;
    private FirebaseAuth auth;
    AVLoadingIndicatorView avLoadingIndicatorView;
    private TextView reset,title,tandcvar;
    LinearLayout ll1;
    Users bcg;
    String email,pass;
    RelativeLayout cl;
    LinearLayout rl;
    String usr_name;
    TextView login_reg_text,Login_reg_text_2;
    TextView heading;
    DatabaseReference updateData;
    TextView terms_of_use,privacy_policy;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ll1=findViewById(R.id.tandc_ll);
        //No internet connection
        cl=findViewById(R.id.NonetScreen_login);
        rl=findViewById(R.id.connected_login);
        heading=findViewById(R.id.tv11);
        avLoadingIndicatorView=findViewById(R.id.progressBar7);
        Tovuti.from(this).monitor(new Monitor.ConnectivityListener(){
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast){
                if(isConnected)
                {
                    cl.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                    heading.setVisibility(View.VISIBLE);
                    ll1.setVisibility(View.VISIBLE);
                }
                else
                {
                    cl.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                    heading.setVisibility(View.GONE);
                    ll1.setVisibility(View.GONE);
                }
            }
        });
        tandcvar=findViewById(R.id.tandc_var);


        terms_of_use=findViewById(R.id.terms_of_use);
        privacy_policy=findViewById(R.id.privacy_policy);
        terms_of_use.setPaintFlags(terms_of_use.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        terms_of_use.setText("Terms of Use");
       privacy_policy.setPaintFlags(privacy_policy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
      privacy_policy.setText("Privacy Policy");
        terms_of_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_activity.this,terms_and_conditions.class));
            }
        });
        phone=findViewById(R.id.login_phone);
        phone.setVisibility(View.GONE);
        login_reg_text=findViewById(R.id.reg_login_text);

        Login_reg_text_2=findViewById(R.id.reg_login_text_2);
       Login_reg_text_2.setVisibility(View.GONE);
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_activity.this,privacy_policy.class));
            }
        });
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "font/Pacifico.ttf");
        heading.setTypeface(custom_font2);

        updateData=FirebaseDatabase.getInstance().getReference().child("Users");
        auth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.login_button_login);
        Signup = findViewById(R.id.login_button_register);
        Email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        pgbar = findViewById(R.id.progressBar);
        reset = findViewById(R.id.forget_password);

        heading.setTypeface(custom_font2);
        bcg = new Users();
        login_reg_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_reg_text.setVisibility(View.GONE);
                Login.setVisibility(View.GONE);
                Signup.setVisibility(View.VISIBLE);
                Login_reg_text_2.setVisibility(View.VISIBLE);
                reset.setVisibility(View.GONE);
                phone.setVisibility(View.VISIBLE);
                heading.setText("signup!");
                tandcvar.setText("By signing up you agree to our");

            }
        });
        Login_reg_text_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_reg_text_2.setVisibility(View.GONE);
                login_reg_text.setVisibility(View.VISIBLE);
                Login.setVisibility(View.VISIBLE);
                Signup.setVisibility(View.GONE);
                reset.setVisibility(View.VISIBLE);
                phone.setVisibility(View.GONE);
                heading.setText("welcome back!");

                tandcvar.setText("By logging in you agree to our");

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_activity.this, forget_pass.class));
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avLoadingIndicatorView.setVisibility(View.VISIBLE);
                avLoadingIndicatorView.smoothToShow();
                String email = Email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty()) {
                    Toasty.error(login_activity.this, "Please enter all the information correctly", Toast.LENGTH_SHORT).show();
                    avLoadingIndicatorView.smoothToShow();
                    avLoadingIndicatorView.setVisibility(View.GONE);
                }

                else
                {
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference().child("Linker").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("user_name").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        myApp.user_name=dataSnapshot.getValue().toString();
                                        startActivity(new Intent(login_activity.this, MainActivity.class));
                                        pgbar.setVisibility(View.GONE);
                                        avLoadingIndicatorView.smoothToHide();
                                        avLoadingIndicatorView.setVisibility(View.GONE);
                                        Toasty.success(login_activity.this,"Sign in successful",Toast.LENGTH_SHORT).show();
                                        login_activity.this.finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            } else {
                                avLoadingIndicatorView.smoothToHide();
                                avLoadingIndicatorView.setVisibility(View.GONE);
                                Animation shake = AnimationUtils.loadAnimation(login_activity.this, R.anim.vibrate);
                                password.startAnimation(shake);
                                Email.startAnimation(shake);
                                Toasty.error(login_activity.this, "Email id or password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        }
    });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=Email.getText().toString().trim();
                pass=password.getText().toString().trim();

                if(email.isEmpty())
                {
                    Toasty.warning(login_activity.this,"Please enter email and try again",Toast.LENGTH_SHORT).show();

                }
                if(phone.getText().toString().isEmpty())
                {
                    Toasty.warning(login_activity.this,"Please enter a phone number!").show();
                }
                if(phone.getText().toString().length()<10)
                {
                    Toasty.warning(login_activity.this,"Please enter a valid phone number!").show();

                }
                else if(pass.isEmpty())
                {
                    Toasty.warning(login_activity.this,"Please enter password and try again",Toast.LENGTH_SHORT).show();

                }
                else if(pass.length()<6 )
                {
                    Toasty.warning(login_activity.this,"Password Length too short",Toast.LENGTH_SHORT).show();

                }
                else
                {

                new LovelyTextInputDialog(login_activity.this, R.style.TintTheme)

                        .setTopTitle("AWESOME!")
                        .setTopTitleColor(Color.parseColor("#000000"))

                        .setTitle("Enter PUBG username.")
                        .setMessage("Good now please enter your PUBG username so we can finalize your account.")
                        .setConfirmButtonColor(getResources().getColor(R.color.dialog_2))

                        .setConfirmButton("Submit", new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {

                                if (text.isEmpty() || text.equals("")) {
                                    Toasty.error(login_activity.this, "Invalid PUBG username!").show();
                                } else {
                                    user_name_checker(text);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {


                                            if (flag == 1) {
                                                Toasty.error(login_activity.this, "Username already in use!").show();

                                            } else {
                                                pgbar.setVisibility(View.VISIBLE);
                                                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        if (task.isSuccessful()) {
                                                            myApp.user_name = usr_name;
                                                            startActivity(new Intent(login_activity.this, MainActivity.class));

                                                            Users bcgk = new Users(phone.getText().toString(), "0", usr_name, "0", "0", "0");
                                                            FirebaseDatabase.getInstance().getReference().child("Linker").child(auth.getUid()).child("user_name").setValue(usr_name);
                                                            FirebaseDatabase.getInstance().getReference("Users").child(usr_name).setValue(bcgk);
                                                            pgbar.setVisibility(View.INVISIBLE);
                                                            login_activity.this.finish();
                                                            Toasty.success(login_activity.this, "Sign up successful", Toast.LENGTH_SHORT).show();


                                                        } else {
                                                            pgbar.setVisibility(View.GONE);
                                                            Toasty.error(login_activity.this, "Some error occured.Please try again", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                            }
                                        }
                                    }, 1500);

                                }
                            }
                        })
                        .show();
                }

        }}
        );

    }
    public void user_name_checker(String text)
    {
        flag=0;
        usr_name=text;

        updateData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (usr_name.equals(snapshot.getKey().toString())) {
                        flag = 1;
                        break;
                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        login_activity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}