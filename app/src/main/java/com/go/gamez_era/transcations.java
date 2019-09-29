package com.go.gamez_era;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;

import java.util.ArrayList;
import java.util.StringTokenizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class transcations extends AppCompatActivity {
    TextView heading;
    TextView mn;
    DatabaseReference dbr;
    RelativeLayout li1, li2;
    RecyclerView RView;
    ImageView imageView, image_2;
    RelativeLayout cl;
    ShimmerButton make_transaction;
    RelativeLayout rl;
    ArrayList<transcations_model_class> arrayList = new ArrayList<>();
    transcations_adapter adapter;
    String usr_name = myApp.user_name;
    Shimmer shimmer = new Shimmer();


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(transcations.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcations);


        cl = findViewById(R.id.NonetScreen_transcations123);
        rl = findViewById(R.id.connected_transcations123);
        Tovuti.from(this).monitor(new Monitor.ConnectivityListener() {
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast) {
                if (isConnected) {
                    cl.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                } else {
                    cl.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                }
            }
        });

        image_2 = findViewById(R.id.back_button_transcations_2);
        li1 = findViewById(R.id.transactions_are_there);
        li2 = findViewById(R.id.no_transactions);
        heading = findViewById(R.id.heading_transcations);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "font/sans_bold.ttf");
        heading.setTypeface(custom_font2);
        mn=findViewById(R.id.textView3);
        imageView = findViewById(R.id.back_button_transcations);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transcations.this.finish();
            }
        });
        image_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transcations.this.finish();
            }
        });
        RView = findViewById(R.id.recyclerView_transcations);
        make_transaction = findViewById(R.id.make_a_transcation);
        shimmer.start(make_transaction);
        shimmer.setDuration(2500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
        make_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(transcations.this, Wallet_page.class));
                transcations.this.finish();
            }
        });
        check_transcations();
        get_amt();
    }

    public void check_transcations() {
        dbr = FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("transactions").exists()) {
                    li2.setVisibility(View.GONE);
                    li1.setVisibility(View.VISIBLE);
                    update_list();

                } else {
                    li1.setVisibility(View.GONE);
                    li2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void get_amt()
    {
        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mn.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void update_list() {

        adapter = new transcations_adapter(arrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        RView.setLayoutManager(mLayoutManager);
        RView.setItemAnimator(new DefaultItemAnimator());


        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("transactions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String s = ds.getValue(String.class);
                    transcations_model_class list = new transcations_model_class(s);

                    arrayList.add(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        RView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmer.start(make_transaction);
        shimmer.setDuration(2500)
                .setStartDelay(300)

                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);


    }

    class transcations_adapter extends RecyclerView.Adapter<transcations_adapter.MyViewHolder> {
        private ArrayList<transcations_model_class> contestantList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView add_withdraw_text;
            TextView date_time;
            TextView amount;

            public MyViewHolder(View view) {

                super(view);
                image = view.findViewById(R.id.withdraw_add_image);
                add_withdraw_text = view.findViewById(R.id.withdraw_add_text);
                date_time = view.findViewById(R.id.date_time_transactions);
                amount = view.findViewById(R.id.amount_transcation);
            }
        }

        public transcations_adapter(ArrayList<transcations_model_class> contestantList) {
            this.contestantList = contestantList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transcation_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            transcations_model_class cont = contestantList.get(position);

            StringTokenizer a = new StringTokenizer(cont.getS(), "-");
            String a1 = a.nextToken();
            String a2 = a.nextToken();
            String a3 = a.nextToken();
            String a4 = a.nextToken();
            String a5="";
            if(a1.equals("PUR"))
            {
                a5=a.nextToken();
            }
            if (a1.equals("CREDIT")) {
                holder.image.setImageResource(R.drawable.add_money_image);
                holder.add_withdraw_text.setText("Money added");
                holder.add_withdraw_text.setTextColor(Color.parseColor("#6FE200"));
                holder.amount.setText("+ " + "₹" + a4);
            } else if (a1.equals("DEBIT")) {
                holder.image.setImageResource(R.drawable.withdraw_icon_2);
                holder.add_withdraw_text.setText("Money withdraw");
                holder.add_withdraw_text.setTextColor(Color.parseColor("#FF4444"));
                holder.amount.setText("- " + "₹" + a4);
            }
            else if (a1.equals("WON")) {
                holder.image.setImageResource(R.drawable.trophy_sized);
                holder.add_withdraw_text.setText("Money won");
                holder.amount.setText("+ " + "₹" + a4);
            }
            else if (a1.equals("PUR")) {
                holder.image.setImageResource(R.drawable.shopping_car);
                holder.add_withdraw_text.setText("Purchase for match #"+a5);
                holder.amount.setText("- " + "₹" + a4);
            }
            holder.date_time.setText(a2 + " at " + a3);

        }

        @Override
        public int getItemCount() {
            return contestantList.size();
        }

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


