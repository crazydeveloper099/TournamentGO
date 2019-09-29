package com.go.gamez_era;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Instructions_activity extends AppCompatActivity {
    TextView tv,per_kill_sp, date_view, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rules_heading, match_info, room_id_name, room_pass_name, room_id_value, room_pass_value, map_name, map_value, type_name, type_value, contestant, fst_prize, scnd_prize, per_kill, version;
    Typeface zag;
    DatabaseReference mdata_ref;
    RelativeLayout cl;
    LinearLayout rl,li_normal;
    LinearLayout pk_special;
    RecyclerView RView;
    DatabaseReference databaseReference;
    ImageView image;
    Button iv;

    ArrayList<list_view_constant> arrayList = new ArrayList<>();
    contestant_adapter adapter;
    DatabaseReference getimage;
    LinearLayout li7;
    String usr_name = myApp.user_name;
    TextView invisible_line;
    TextView no_joined;
    Typeface fo;
    TextView line;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_instructions_activity);


        cl = findViewById(R.id.NonetScreen_Instructions);
        rl = findViewById(R.id.connected_Instructions);
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
        per_kill_sp=findViewById(R.id.per_kill_special);
        line=findViewById(R.id.line_vis);
        fo = Typeface.createFromAsset(getAssets(), "font/tondo.ttf");
        per_kill_sp.setTypeface(fo);
        li_normal=findViewById(R.id.li_normal);
        pk_special=findViewById(R.id.pk_special_view);
        pk_special.setVisibility(View.GONE);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "font/sans_bold.ttf");
        zag = Typeface.createFromAsset(getAssets(), "font/sans_bold.ttf");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/sans_medium.ttf");
        map_value = findViewById(R.id.map_value);
        type_value = findViewById(R.id.type_value);
        room_id_value = findViewById(R.id.room_id_value);
        room_pass_value = findViewById(R.id.room_password_value);
        contestant = findViewById(R.id.contestants_heading);
        contestant.setTypeface(zag);
        invisible_line=findViewById(R.id.invisible_line);
        map_value.setTypeface(custom_font);
        type_value.setTypeface(custom_font);
        room_id_value.setTypeface(custom_font);
        room_pass_value.setTypeface(custom_font);
        iv = findViewById(R.id.back_button_instructions_2);
        image = findViewById(R.id.image_instructions_activity);
        fst_prize = findViewById(R.id.fst_value_info);
        scnd_prize = findViewById(R.id.scnd_value_info);
        per_kill = findViewById(R.id.pk_value_info);
        version = findViewById(R.id.version_value);
        Bundle bundle = getIntent().getExtras();
        String a = bundle.getString("time");
        String b = bundle.getString("date");
        String pressed = bundle.getString("key");
        get_pk_status(pressed);

        getImage(pressed);
        mdata_ref = FirebaseDatabase.getInstance().getReference().child("Cardjava").child(pressed);
        get_map_type();
        get_fst_scnd_pk_version_values(pressed);
        tv = findViewById(R.id.time_view);

        date_view = findViewById(R.id.date_view);
        rule1 = findViewById(R.id.rule_1);
        rule2 = findViewById(R.id.rule_2);
        rule3 = findViewById(R.id.rule_3);
        rule4 = findViewById(R.id.rule_4);
        rule5 = findViewById(R.id.rule_5);
        rule6 = findViewById(R.id.rule_6);
        rule7 = findViewById(R.id.rule_7);
        rule8 = findViewById(R.id.rule_8);
        match_info = findViewById(R.id.match_info);
        room_id_name = findViewById(R.id.room_id_text);
        room_pass_name = findViewById(R.id.room_password_text);
        rules_heading = findViewById(R.id.rules_heading);
        li7 = findViewById(R.id.hotz_3);
        map_name = findViewById(R.id.MAP_text);
        type_name = findViewById(R.id.type_name);

        rule1.setTypeface(custom_font);
        rule2.setTypeface(custom_font);
        rule3.setTypeface(custom_font);
        rule4.setTypeface(custom_font);
        rule5.setTypeface(custom_font);
        rule6.setTypeface(custom_font);
        rule7.setTypeface(custom_font);
        rule8.setTypeface(custom_font);

        date_view.setTypeface(custom_font);
        match_info.setTypeface(zag);
        room_id_name.setTypeface(custom_font);
        room_pass_name.setTypeface(custom_font);
        rules_heading.setTypeface(zag);
        map_name.setTypeface(custom_font);
        type_name.setTypeface(custom_font);
        no_joined=findViewById(R.id.no_one_joined_text);
        get_status(pressed);
        update_list(pressed);
        date_view.setText(b);
        tv.setText(a);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instructions_activity.this.finish();
            }
        });

    }

    public void get_status(final String key) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Users").child(usr_name).child("user_name").getValue().toString();
                if (dataSnapshot.child("Cardjava").child(key).child("users").exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.child("Cardjava").child(key).child("users").getChildren()) {
                        if (snapshot.getKey().equals(name)) {
                            li7.setVisibility(View.VISIBLE);
                            invisible_line.setVisibility(View.VISIBLE);
                            get_i_p_values();

                            break;
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void get_pk_status(String key)
    {
        FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key).child("pk_special").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("1"))
                {
                    pk_special.setVisibility(View.VISIBLE);
                    li_normal.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                }
                else
                {
                    pk_special.setVisibility(View.GONE);
                    li_normal.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //to get map data
    public void get_map_type() {
        mdata_ref.child("description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                map_value.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mdata_ref.child("team").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                type_value.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void get_i_p_values() {
        mdata_ref.child("r_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                room_id_value.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mdata_ref.child("r_pass").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                room_pass_value.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void get_fst_scnd_pk_version_values(String key) {
        DatabaseReference fref = FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key);
        fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("pk_special").getValue().toString().equals("0")) {
                    fst_prize.setText("₹" + dataSnapshot.child("fst_amt").getValue().toString());
                    scnd_prize.setText("₹" + dataSnapshot.child("scnd_amt").getValue().toString());
                    per_kill.setText("₹" + dataSnapshot.child("third_amt").getValue().toString());
                    version.setText(dataSnapshot.child("version").getValue().toString());
                }
                if (dataSnapshot.child("pk_special").getValue().toString().equals("1")) {
                    per_kill.setText("₹" + dataSnapshot.child("third_amt").getValue().toString());
                    version.setText(dataSnapshot.child("version").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getImage(String key) {

        getimage = FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key).child("image");
        getimage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    image.setVisibility(View.GONE);
                } else {
                    final Callback loadedCallback = new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                        }
                    };
                    image.setTag(loadedCallback);


                    Picasso.with(Instructions_activity.this).load(dataSnapshot.getValue().toString()).placeholder(R.drawable.image_holder_picasso).into(image, loadedCallback);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onResume() {

        super.onResume();
    }

    //to get list of contestants
    public void update_list(final String key) {
        FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users").hasChildren()) {
                    no_joined.setVisibility(View.GONE);
                    RView = findViewById(R.id.recycler_view_constant);
                    Activity act = Instructions_activity.this;
                    adapter = new contestant_adapter(arrayList, act, key);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    RView.setLayoutManager(mLayoutManager);
                    RView.setItemAnimator(new DefaultItemAnimator());
                    RView.setAdapter(adapter);


                    FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key).child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                list_view_constant list = new list_view_constant(ds.getKey(), 0);
                                arrayList.add(list);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }

                else {
                    no_joined.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    class contestant_adapter extends RecyclerView.Adapter<contestant_adapter.MyViewHolder> {
        int flag = 0;
        AppCompatActivity ctx;
        String usr_name = myApp.user_name;
        ;
        String key;
        private ArrayList<list_view_constant> contestantList;


        public contestant_adapter(ArrayList<list_view_constant> contestantList, AppCompatActivity ctx, String key) {
            this.contestantList = contestantList;
            this.ctx = ctx;
            this.key = key;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name1;
            public TextView index;


            public MyViewHolder(View view) {

                super(view);
                name1 = view.findViewById(R.id.text_view_constant_name);
                Typeface custom_font2 = Typeface.createFromAsset(ctx.getAssets(), "font/sans_bold.ttf");
                name1.setTextColor(ctx.getResources().getColor(R.color.inst_color_values));
                index = view.findViewById(R.id.number_contestant);
                index.setTextColor(Color.parseColor("#000000"));
                name1.setTypeface(custom_font2);
                index.setTypeface(custom_font2);
            }
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contestant_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            list_view_constant cont = contestantList.get(position);

            holder.name1.setText(cont.getName());
            holder.index.setText("•");


        }

        @Override
        public int getItemCount() {
            return contestantList.size();
        }
    }
}