package com.go.gamez_era;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;

public class results extends AppCompatActivity {
    RecyclerView RView1,RView2,RView_all;
    ArrayList<result_model_class> arrayList1=new ArrayList<>();
    ArrayList<result_model_class> arrayList2=new ArrayList<>();
    ArrayList<result_model_class> arrayList3=new ArrayList<>();
    result_adapter adapter1;
    result_adapter adapter2;
    result_adapter adapter3;
    RelativeLayout cl;
    RelativeLayout rl;
    ShimmerTextView stv;

    Shimmer shimmer = new Shimmer();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_results);

        //No internet connection
        cl=findViewById(R.id.NonetScreen_results);
        rl=findViewById(R.id.connected_results);
        Tovuti.from(this).monitor(new Monitor.ConnectivityListener(){
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast){
                if(isConnected)
                {
                    cl.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                }
                else
                {
                    cl.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                }
            }
        });

        Bundle bundle=getIntent().getExtras();
        String pressed=bundle.getString("key");
        fst_list(pressed);
        scnd_list(pressed);
       all_players(pressed);
       stv=findViewById(R.id.congo_shimmer);

        shimmer.start(stv);
        shimmer.setDuration(2500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
        ImageView iv=findViewById(R.id.back_button_result);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results.this.finish();
            }
        });
    }

        public void fst_list(String key)
        {

            RView1=findViewById(R.id.result_recycler_view_fst);
            adapter1=new result_adapter(arrayList1,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RView1.setLayoutManager(mLayoutManager);
            RView1.setItemAnimator(new DefaultItemAnimator());
            RView1.setAdapter(adapter1);


            FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("result").exists()) {
                        for (DataSnapshot ds : dataSnapshot.child("result").child("fst").getChildren())
                        {
                            int rank=Integer.parseInt(ds.getKey().toString());
                            int kill=Integer.parseInt(ds.child("kills").getValue().toString());
                            String name=ds.child("usr_name").getValue().toString();
                            int amount=Integer.parseInt(ds.child("amount").getValue().toString());
                            result_model_class list = new result_model_class(rank,kill, amount,name);
                            arrayList1.add(list);
                            adapter1.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    public void scnd_list(String key)
    {
        RView2=findViewById(R.id.recycler_view_second_winners_result);
        adapter2=new result_adapter(arrayList2,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RView2.setLayoutManager(mLayoutManager);
        RView2.setItemAnimator(new DefaultItemAnimator());
        RView2.setAdapter(adapter2);

        FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("result").exists()) {
                    for (DataSnapshot ds : dataSnapshot.child("result").child("scnd").getChildren()) {
                        int rank=Integer.parseInt(ds.getKey().toString());
                        int kill=Integer.parseInt(ds.child("kills").getValue().toString());
                        String name=ds.child("usr_name").getValue().toString();
                        int amount=Integer.parseInt(ds.child("amount").getValue().toString());
                        result_model_class list = new result_model_class(rank,kill, amount,name);
                        arrayList2.add(list);
                        adapter2.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void all_players(String key)
    {
        RView_all=findViewById(R.id.result_recycler_view_all_players);
        adapter3=new result_adapter(arrayList3,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RView_all.setLayoutManager(mLayoutManager);
        RView_all.setItemAnimator(new DefaultItemAnimator());
        RView_all.setAdapter(adapter3);


        FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("result").exists()) {
                    for (DataSnapshot ds : dataSnapshot.child("result").child("fst").getChildren()) {
                        if(ds.child("kills").exists() && ds.child("usr_name").exists() && ds.child("amount").exists()) {

                                int rank = Integer.parseInt(ds.getKey().toString());
                                int kill = Integer.parseInt(ds.child("kills").getValue().toString());
                                String name = ds.child("usr_name").getValue().toString();
                                int amount = Integer.parseInt(ds.child("amount").getValue().toString());
                                result_model_class list = new result_model_class(rank, kill, amount, name);
                                arrayList3.add(list);
                                adapter3.notifyDataSetChanged();
                            }
                    }
                    for (DataSnapshot ds : dataSnapshot.child("result").child("scnd").getChildren())
                    {
                        if(ds.child("kills").exists() && ds.child("usr_name").exists() && ds.child("amount").exists()) {

                            int rank = Integer.parseInt(ds.getKey().toString());
                            int kill = Integer.parseInt(ds.child("kills").getValue().toString());
                            String name = ds.child("usr_name").getValue().toString();
                            int amount = Integer.parseInt(ds.child("amount").getValue().toString());
                            result_model_class list = new result_model_class(rank, kill, amount, name);
                            arrayList3.add(list);
                            adapter3.notifyDataSetChanged();
                        }

                    }
                    for (DataSnapshot ds : dataSnapshot.child("result").child("all").getChildren()) {
                        if(ds.child("kills").exists() && ds.child("usr_name").exists() && ds.child("amount").exists()) {
                            int rank = Integer.parseInt(ds.getKey().toString());
                            int kill = Integer.parseInt(ds.child("kills").getValue().toString());
                            String name = ds.child("usr_name").getValue().toString();
                            int amount = Integer.parseInt(ds.child("amount").getValue().toString());
                            result_model_class list = new result_model_class(rank, kill, amount, name);
                            arrayList3.add(list);
                            adapter3.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onResume() {

        shimmer.start(stv);
        shimmer.setDuration(2500)
                .setStartDelay(300)

                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);


        super.onResume();
    }
}






class result_adapter extends RecyclerView.Adapter<result_adapter.MyViewHolder>
{
    Context ctx;
    private ArrayList<result_model_class> contestantList;

    public result_adapter(ArrayList<result_model_class> contestantList, Context ctx) {
        this.contestantList = contestantList;
        this.ctx=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name1;
        public TextView kills;
        public TextView amount;
        public TextView index;

        public MyViewHolder(View view) {

            super(view);
            name1 = view.findViewById(R.id.result_contestant_name);
            index=view.findViewById(R.id.result_number_contestant);
            kills=view.findViewById(R.id.result_contestant_kills);
            amount=view.findViewById(R.id.result_contestant_amount);

            Typeface custom_font2 = Typeface.createFromAsset(ctx.getAssets(),"font/sans_bold.ttf");
            name1.setTypeface(custom_font2);
            index.setTypeface(custom_font2);
            kills.setTypeface(custom_font2);
            amount.setTypeface(custom_font2);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        result_model_class cont = contestantList.get(position);
        holder.name1.setText(cont.getName());
        holder.kills.setText(String.valueOf(cont.getKills()));
        holder.amount.setText("₹" + String.valueOf(cont.getAmount()));
        holder.index.setText(String.valueOf(cont.getNumber()));

    }
    @Override
    public int getItemCount() {
        return contestantList.size();
    }
}