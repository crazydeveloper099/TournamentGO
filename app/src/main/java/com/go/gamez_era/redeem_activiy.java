package com.go.gamez_era;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class redeem_activiy extends AppCompatActivity {


    TextView rs_symb, wllt_amt, heading, tandc, rs_symbol_2,redeem_title;
    EditText phone_paytm, amount_redeem;
    ShimmerButton button_redeem;
    Toolbar tb;

    TextView paytm, google_pay, phone_pe, freecharge;
    ImageView imageView;
    RelativeLayout cl;
    LinearLayout rl;
    String get_type;
    String usr_name = myApp.user_name;
    Shimmer shimmer = new Shimmer();
    int flag=0;
    DatabaseReference getMdata_ref = FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(redeem_activiy.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_activiy);
        tandc = findViewById(R.id.TandC);
        redeem_title=findViewById(R.id.withdraw_title);
        //No internet connection
        imageView = findViewById(R.id.back_button_redeem);


        cl = findViewById(R.id.NonetScreen_redeem);
        rl = findViewById(R.id.connected_redeem);
        Tovuti.from(this).monitor(new Monitor.ConnectivityListener() {
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast) {
                if (isConnected) {
                    cl.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                    tandc.setVisibility(View.VISIBLE);
                    redeem_title.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                } else {
                    cl.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                    tandc.setVisibility(View.GONE);
                    redeem_title.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                }
            }
        });

        tb = findViewById(R.id.toolbar_redeem);

        rs_symb = findViewById(R.id.textView_rs_symbol);
        wllt_amt = findViewById(R.id.textView3);
        heading = findViewById(R.id.heading);
        phone_paytm = findViewById(R.id.phone_paytm);
        amount_redeem = findViewById(R.id.amount_paytm);
        rs_symbol_2 = findViewById(R.id.textView_rs_symbol_2);
        button_redeem = findViewById(R.id.redeem_bt);
        shimmer.start(button_redeem);
        shimmer.setDuration(2500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "font/sans_bold.ttf");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/sans_medium.ttf");
        rs_symb.setTypeface(custom_font);
        wllt_amt.setTypeface(custom_font);
        heading.setTypeface(custom_font2);
        phone_paytm.setTypeface(custom_font);
        amount_redeem.setTypeface(custom_font);
        paytm = findViewById(R.id.paytm_button);
        google_pay = findViewById(R.id.google_pay_button);
        phone_pe = findViewById(R.id.phone_pe_button);
        freecharge = findViewById(R.id.freecharge_button);
        rs_symbol_2.setTypeface(custom_font);
        button_redeem.setTypeface(custom_font);
        tandc.setTypeface(custom_font);
        set_et_text_onclick();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redeem_activiy.this.finish();
            }
        });
        set_wallet_amount();
        button_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_redeem_request(get_type);

            }
        });

    }

    public void set_wallet_amount() {
        getMdata_ref.child(usr_name).child("wallet_amount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wllt_amt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void set_et_text_onclick() {
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_type = "paytm";
                flag=0;
                phone_paytm.setHint("Paytm number");
            }
        });
        google_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_type = "Gpay";
                flag=1;
                phone_paytm.setHint("Google Pay number");
            }
        });
        phone_pe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_type = "PhonePe";
                flag=2;
                phone_paytm.setHint("PhonePe number");
            }
        });
        freecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_type = "freecharge";
                flag=3;
                phone_paytm.setHint("Freecharge number");
            }
        });
    }

    public void post_redeem_request(String type) {
        if (is_numeric(amount_redeem.getText().toString()) && is_valid_pohone(phone_paytm.getText().toString())) {
            if ((Integer.parseInt(wllt_amt.getText().toString()) - Integer.parseInt(amount_redeem.getText().toString())) >= 0) {
                //to update wallet amount
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Redeem_Request").child(usr_name).child("wallet_amount").setValue(wllt_amt.getText().toString());
                databaseReference.child("Redeem_Request").child(usr_name).child("phone").setValue(phone_paytm.getText().toString());
                databaseReference.child("Redeem_Request").child(usr_name).child("redeem_amount").setValue(amount_redeem.getText().toString());
                databaseReference.child("Redeem_Request").child(usr_name).child("type").setValue(type);

                getMdata_ref.child(usr_name).child("wallet_amount").setValue(String.valueOf(Integer.parseInt(wllt_amt.getText().toString()) - Integer.parseInt(amount_redeem.getText().toString())));
                post_transcationUpdate();


            } else {
                Toasty.error(this, "Insufficient balance!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toasty.error(this, "Incorrect phone number or amount", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean is_numeric(String str) {

        try {
            return Integer.parseInt(str) > 0 && Integer.parseInt(str) < 10000;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public Boolean is_valid_pohone(String str) {
        if (str.length() >= 10 && str.length() <= 13) {
            return true;
        } else {
            return false;
        }
    }

    public void post_transcationUpdate() {
        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(time);
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        String formattedTime = tf.format(time);
        String debit = "DEBIT-" + formattedDate + "-" + formattedTime + "-" + amount_redeem.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("transactions").push().setValue(debit);
        if(flag==0)
        {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Done!")

                    .setContentText("Your amount will be transferred to your Paytm wallet within 24hrs.Thanks.")
                    .show();
        }
        else if(flag==1)
        {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Done!")
                    .setContentText("Amount will be transferred to your account within 24hrs.Thanks.")
                    .show();
        }
        else if(flag==2)
        {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Done!")

                    .setContentText("Your amount will be transferred to your PhonePe wallet within 24hrs.Thanks.")
                    .show();
        }
        else if(flag==3)
        {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Done!")
                    .setContentText("Your amount will be transferred to your freecharge wallet within 24hrs.Thanks.")
                    .show();
        }

        phone_paytm.setText("");
        amount_redeem.setText("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmer.start(button_redeem);
        shimmer.setDuration(2500)
                .setStartDelay(300)

                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.grad_back_wallets);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}
