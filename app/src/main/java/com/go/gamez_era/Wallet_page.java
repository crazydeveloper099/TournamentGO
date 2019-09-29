package com.go.gamez_era;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.go.gamez_era.models.Checksum;
import com.go.gamez_era.models.Paytm;
import com.go.gamez_era.utils.WebServiceCaller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hmomeni.progresscircula.ProgressCircula;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.romainpiel.shimmer.Shimmer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public  class  Wallet_page extends AppCompatActivity{

    RelativeLayout cl;
    LinearLayout rl;

    TextView tv1, tv2;
    TextView title;
    EditText et1;
    Button add;
    Button add_50, add_100, add_200;
    Toolbar tb;
    private DatabaseReference getMdata_ref;
    Users bcg;
    TextView title_info;
    ImageView arrow;
    String usr_name = myApp.user_name;
    public String order_id;
    Shimmer shimmer = new Shimmer();
    ProgressCircula pc;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(Wallet_page.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_page);
        add = findViewById(R.id.add_money_button_wallet);
        title_info=findViewById(R.id.title_info);
        arrow = findViewById(R.id.back_arrow_wallet);

        //No internet connection
        cl = findViewById(R.id.NonetScreen_wallet);
        rl = findViewById(R.id.connected_wallet);
        Tovuti.from(this).monitor(
                new Monitor.ConnectivityListener() {
                    @Override
                    public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast) {
                        if (isConnected) {
                            cl.setVisibility(View.GONE);
                            add.setVisibility(View.VISIBLE);
                            title_info.setVisibility(View.VISIBLE);
                            arrow.setVisibility(View.VISIBLE);
                            rl.setVisibility(View.VISIBLE);
                        } else {
                            cl.setVisibility(View.VISIBLE);
                            rl.setVisibility(View.GONE);
                            add.setVisibility(View.GONE);
                            title_info.setVisibility(View.GONE);
                            arrow.setVisibility(View.GONE);
                        }
                    }
                });

        title = findViewById(R.id.title_add_money);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "font/sans_bold.ttf");
        title.setTypeface(custom_font2);
        pc=findViewById(R.id.progressBar);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wallet_page.this.finish();
            }
        });
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/sans_medium.ttf");
        tb = findViewById(R.id.toolbar_wallet);


        getMdata_ref = FirebaseDatabase.getInstance().getReference().child("Users");
        tv2 = findViewById(R.id.textView_rs_symbol);
        tv2.setTypeface(custom_font);

        tv1 = findViewById(R.id.textView3);
        tv1.setTypeface(custom_font);
        getMdata_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bcg = new Users();
                bcg.setWallet_amount(dataSnapshot.child(usr_name).getValue(Users.class).getWallet_amount());
                tv1.setText(bcg.getWallet_amount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        et1 = findViewById(R.id.et_wallet);
        et1.setTypeface(custom_font);
        add.setTypeface(custom_font);
        shimmer.setDuration(2500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
        add_50 = findViewById(R.id.add_50_button_wallet);
        add_50.setTypeface(custom_font);

        add_100 = findViewById(R.id.add_100_button_wallet);
        add_100.setTypeface(custom_font);

        add_200 = findViewById(R.id.add_200_button_wallet);
        add_200.setTypeface(custom_font);
        final TextView info = findViewById(R.id.textView_info);
        info.setTypeface(custom_font);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().trim().isEmpty())
                {
                    Toasty.error(Wallet_page.this,"Please Enter a valid amount!").show();
                }
                else if(et1.getText().toString().trim().equals("0"))
                {
                    Toasty.error(Wallet_page.this,"Please Enter a valid amount!").show();
                }
                else if(Integer.parseInt(et1.getText().toString().trim())>=800)
                {
                    Toasty.error(Wallet_page.this,"Please enter a amount below ₹800!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    process_paytm();
                    pc.setVisibility(View.VISIBLE);
                }

            }});
        add_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("50");
            }
        });
        add_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("100");
            }
        });
        add_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("200");
            }
        });
        getSoftButtonsBarSizePort(Wallet_page.this);
    }


    public void process_paytm()
    {
        String order_id=generateString();
        String callBackURL="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + order_id;
        final Paytm paytm=new Paytm(
                "*************",
                "WAP",
                et1.getText().toString().trim()
                ,"DEFAULT",
                callBackURL,
                "Retail",
                order_id,
                usr_name
        );


        WebServiceCaller.getClient().getChecksum(paytm.getmId(), paytm.getOrderId(), paytm.getCustId()
                , paytm.getChannelId(), paytm.getTxnAmount(), paytm.getWebsite(), paytm.getCallBackUrl(), paytm.getIndustryTypeId()
        ).enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                if (response.isSuccessful()) {
                    processToPay(response.body().getChecksumHash(),paytm);
                }
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }

        });
    }

    @Override
    protected void onResume() {

        shimmer.setDuration(2500)
                .setStartDelay(300)

                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);


        super.onResume();
    }


    @Override

    protected void onStart() {

        super.onStart();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }


    private void processToPay(String checksumHash, Paytm paytm) {
        PaytmPGService Service = PaytmPGService.getProductionService();

        HashMap<String, String> paramMap = new HashMap<String,String>();
        paramMap.put( "MID" , paytm.getmId());
        paramMap.put( "ORDER_ID" , paytm.getOrderId());
        paramMap.put( "CUST_ID" , paytm.getCustId());
        paramMap.put( "CHANNEL_ID" , paytm.getChannelId());
        paramMap.put( "TXN_AMOUNT" , paytm.getTxnAmount());
        paramMap.put( "WEBSITE" , paytm.getWebsite());
        paramMap.put( "INDUSTRY_TYPE_ID" , paytm.getIndustryTypeId());
        paramMap.put( "CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put( "CHECKSUMHASH" , checksumHash);
        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            public void someUIErrorOccurred(String inErrorMessage) {

            }
            public void onTransactionResponse(Bundle inResponse) {
                pc.setVisibility(View.GONE);
                String status = inResponse.getString("STATUS");
                if (status.equals("TXN_SUCCESS")) {

                    Toasty.success(getApplicationContext(), "Payment Successful!", Toast.LENGTH_LONG)
                            .show();
                    Date time = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = df.format(time);
                    SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
                    String formattedTime = tf.format(time);
                    String credit = "CREDIT-" + formattedDate + "-" + formattedTime + "-" + et1.getText().toString();
                    FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("transactions").push().setValue(credit);
                    FirebaseDatabase.getInstance().getReference().child("Transactions").child(usr_name).child("Status").setValue(status);
                    FirebaseDatabase.getInstance().getReference().child("Transactions").child(usr_name).child("order_ID").setValue(inResponse.getString("ORDERID"));
                    FirebaseDatabase.getInstance().getReference().child("Transactions").child(usr_name).child("amount").setValue(et1.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int a = Integer.parseInt(dataSnapshot.child("wallet_amount").getValue().toString()) + Integer.parseInt(et1.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount").setValue(String.valueOf(a));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } else if (status.equals("TXN_FAILURE")) {
                    Date time = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = df.format(time);
                    SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
                    String formattedTime = tf.format(time);
                    String credit = "CREDIT-" + formattedDate + "-" + formattedTime + "-" + et1.getText().toString();
                    FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("transactions").push().setValue(credit);
                    FirebaseDatabase.getInstance().getReference().child("Transactions").child(usr_name).child("Status").setValue(status);
                    FirebaseDatabase.getInstance().getReference().child("Transactions").child(usr_name).child("order_ID").setValue(inResponse.getString("ORDERID"));
                    FirebaseDatabase.getInstance().getReference().child("Transactions").child(usr_name).child("amount").setValue(et1.getText().toString());

                    Toasty.error(getApplicationContext(), "Payment unsuccessful", Toast.LENGTH_LONG).show();

                }
            }
            public void networkNotAvailable() {
                pc.setVisibility(View.GONE);
            }
            public void clientAuthenticationFailed(String inErrorMessage) {
                Toasty.error(Wallet_page.this, "Some technical occurred please try again!" , Toast.LENGTH_SHORT).show();
            }
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                pc.setVisibility(View.GONE);
            }
            public void onBackPressedCancelTransaction() {
                pc.setVisibility(View.GONE);
            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                pc.setVisibility(View.GONE);
            }
        });

    }
    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    public static int getSoftButtonsBarSizePort(AppCompatActivity activity) {
// getRealMetrics is only available with API 17 and +
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight)
            return realHeight - usableHeight;
        else
            return 0;
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
