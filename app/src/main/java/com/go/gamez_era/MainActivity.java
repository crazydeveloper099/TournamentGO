package com.go.gamez_era;
import android.app.Dialog;
import android.content.Context;
import android.app.Activity;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;
import instamojo.library.Config;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.IntentFilter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.pd.chocobar.ChocoBar;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;
import com.romainpiel.shimmer.ShimmerTextView;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import io.supercharge.shimmerlayout.ShimmerLayout;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mtoolbar;

    ShimmerLayout sl;
    Drawer result;
    AccountHeader headerResult;
    private RecyclerView rv;
    private DatabaseReference mdata_ref;
    FirebaseRecyclerAdapter<Cardjava, card_view_holder> adapter;
    int a, b;


    TextView pubg_heading;
    Button tv2;
    Typeface custom_font, custom_font2;
    Users bcg;
    private DatabaseReference wallwt_ref;
    Typeface custom_font_4;
    KonfettiView kv;

    DatabaseReference db_spots;
    String n1;
    AlertDialog ad;

    RelativeLayout cl;
    RelativeLayout rl;
    String pressed_key;
    int spots = 0;
    int executor = 0;
    AVLoadingIndicatorView pb;

    TextView tv1;
    ProgressBar pgBar;

    ValueEventListener v1,v2,v3,v4,v5,v6,v7,v8,v9;

    String usr_name= myApp.user_name;
    Button live;
    Button results;
    Typeface fo;
    Shimmer shimmer = new Shimmer();

    DatabaseReference data1;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        // Call the function callInstamojo to start payment here


        cl = findViewById(R.id.NonetScreen);
        rl = findViewById(R.id.connected_main);
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


        rv = findViewById(R.id.list_card_views);
        sl = findViewById(R.id.shimmer_text);
        kv=findViewById(R.id.viewKonfetti);
        pb = findViewById(R.id.pbHeaderProgress);
        pb.setVisibility(View.GONE);
        fo = Typeface.createFromAsset(getAssets(), "font/tondo.ttf");

        FirebaseDatabase.getInstance().getReference().child("Linker").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("user_name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        myApp.user_name =dataSnapshot.getValue().toString();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        wallwt_ref = FirebaseDatabase.getInstance().getReference().child("Users");

        mdata_ref = FirebaseDatabase.getInstance().getReference().child("Cardjava");
        db_spots = FirebaseDatabase.getInstance().getReference().child("Cardjava");



        shimmer.setDuration(2500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);


        //To tell app that we are using toolbar instead odf action bar
        mtoolbar = (Toolbar) findViewById(R.id.mylol);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        custom_font = Typeface.createFromAsset(getAssets(), "font/sans_medium.ttf");
        custom_font_4 = Typeface.createFromAsset(getAssets(), "font/crb2.ttf");

        pubg_heading = findViewById(R.id.pubg_heading);
        pubg_heading.setTypeface(custom_font_4);

        tv2 = mtoolbar.findViewById(R.id.textView23);

        wallwt_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tv2.setText("₹" + dataSnapshot.child(usr_name).child("wallet_amount").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(MainActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        IProfile profile = new ProfileDrawerItem().withName("Wallet :₹" + tv2.getText().toString()).withEmail("-").withIcon(getResources().getDrawable(R.drawable.account_header_image_2)).withIdentifier(0);
        headerResult = new AccountHeaderBuilder()
                .withActivity(MainActivity.this)
                .withPaddingBelowHeader(true)

                .addProfiles(profile)

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        //to get wallet amount

        //drawer building
        new DrawerBuilder().withActivity(MainActivity.this).withAccountHeader(headerResult).build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withSelectedTextColor(getResources().getColor(R.color.black)).withIcon(R.drawable.wallet_10).withTextColor(getResources().getColor(R.color.black)).withName("My Wallet").withSelectedColor(getResources().getColor(R.color.selector_nav));
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withSelectedTextColor(getResources().getColor(R.color.black)).withIcon(R.drawable.withdraw_10).withTextColor(getResources().getColor(R.color.black)).withName("Withdraw").withSelectedColor(getResources().getColor(R.color.selector_nav));
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withSelectedTextColor(getResources().getColor(R.color.black)).withIcon(R.drawable.transactions_10).withTextColor(getResources().getColor(R.color.black)).withName("My Transactions").withSelectedColor(getResources().getColor(R.color.selector_nav));
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withSelectedTextColor(getResources().getColor(R.color.black)).withIcon(R.drawable.rules_10).withTextColor(getResources().getColor(R.color.black)).withName("Rules").withSelectedColor(getResources().getColor(R.color.selector_nav));

        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withSelectedTextColor(getResources().getColor(R.color.black)).withIcon(R.drawable.settings_10).withTextColor(getResources().getColor(R.color.black)).withName("Account Settings").withSelectedColor(getResources().getColor(R.color.selector_nav));

        result = new DrawerBuilder()
                .withActivity(MainActivity.this)
                .withToolbar(mtoolbar)
                .withAccountHeader(headerResult)
                .withSelectedItemByPosition(100)



                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (position == 1) {

                            startActivity(new Intent(MainActivity.this, Wallet_page.class));
                            result.closeDrawer();
                            return true;
                        }
                        if (position == 2) {

                            startActivity(new Intent(MainActivity.this, redeem_activiy.class));
                            result.closeDrawer();
                            return true;
                        }
                        if (position == 3) {

                            startActivity(new Intent(MainActivity.this, transcations.class));
                            result.closeDrawer();
                            return true;
                        }
                        if (position == 4) {

                            startActivity(new Intent(MainActivity.this, rules_activity.class));
                            result.closeDrawer();
                            return true;
                        }
                        if (position == 5) {
                            startActivity(new Intent(MainActivity.this, settings.class));
                            result.closeDrawer();
                            return true;
                        } else {

                            return false;
                        }

                    }

                })
                .build();
        result.addStickyFooterItem(new PrimaryDrawerItem().withIcon(R.drawable.logout_icon).withName("Logout").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                showAlert_logout();
                return true;
            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        result.getActionBarDrawerToggle().setDrawerSlideAnimationEnabled(true);
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Users").child(myApp.user_name).child("wallet_amount");
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                IProfile profile = new ProfileDrawerItem().withName("Wallet :₹" + dataSnapshot.getValue()).withEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).withIcon(getResources().getDrawable(R.drawable.account_header_image_2)).withIdentifier(0);
                headerResult.removeProfile(0);
                headerResult.addProfile(profile, 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //drawer ends here


        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Wallet_page.class);
                startActivity(intent);
            }
        });

        Query personsQuery = mdata_ref.orderByKey();

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Cardjava>().setQuery(personsQuery, Cardjava.class).build();
        //Recycler view starts

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        adapter = new FirebaseRecyclerAdapter<Cardjava, card_view_holder>(personsOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final card_view_holder holder, int position, @NonNull final Cardjava model) {


                holder.set_img(getBaseContext(), model.getImage());
                holder.setDescription(model.getDescription());
                holder.set1stamount("₹" + model.getFst_amt());
                holder.set2ndamount("₹" + model.getScnd_amt());
                holder.set3rdamount("₹" + model.getThird_amt());
                holder.set_date_value(model.getDate_value());
                holder.set_time_value(model.getTime_value());
                holder.setkill_per("₹" + model.getPer_kill_value());
                holder.set_spots(model.getSpots());
                holder.setFst("1st prize");
                holder.setScnd("2nd prize");
                holder.setThird("Per Kill");
                holder.setDevice(model.getDevice());
                holder.setTeam(model.getTeam());
                holder.setDate("Date");
                holder.setTime("Time");
                holder.setPer_kill("Entery fee");
                holder.setversion(model.getVersion());
                holder.setMatch_number("#" + model.getMatch_number());
                holder.setPk_special(model.getPk_special());


                TextView mn = holder.mview.findViewById(R.id.match_number);
                mn.setTypeface(custom_font_4);


                holder.mview.findViewById(R.id.progressBar).setVisibility(View.GONE);


                mdata_ref.child((getRef(holder.getAdapterPosition()).getKey())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        tv1 = holder.mview.findViewById(R.id.spots_left);
                        pgBar = holder.mview.findViewById(R.id.activeProgress);
                        live = holder.mview.findViewById(R.id.button99);
                        Button full = holder.mview.findViewById(R.id.button_full_card);
                        results = holder.mview.findViewById(R.id.button_results);


                        live.setTypeface(fo);
                        full.setTypeface(fo);
                        results.setTypeface(fo);

                        tv1.setText(String.valueOf(dataSnapshot.child("users").getChildrenCount()) + "/100 Joined");
                        pgBar.setProgress((int) dataSnapshot.child("users").getChildrenCount());
                        if (dataSnapshot.child("users").getChildrenCount() >= 100) {
                            live.setVisibility(View.GONE);
                            results.setVisibility(View.GONE);
                            full.setVisibility(View.VISIBLE);

                            if (dataSnapshot.child("status").exists()) {

                                if (dataSnapshot.child("status").getValue().toString().equals("over")) {
                                    live.setVisibility(View.GONE);
                                    full.setVisibility(View.GONE);
                                    results.setVisibility(View.VISIBLE);



                                } else if (dataSnapshot.child("status").getValue().toString().equals("started")) {
                                    live.setVisibility(View.GONE);
                                    results.setVisibility(View.GONE);
                                    full.setVisibility(View.VISIBLE);
                                    full.setText("MATCH LIVE");

                                }
                            } else {
                                live.setVisibility(View.GONE);
                                results.setVisibility(View.GONE);
                                full.setVisibility(View.VISIBLE);
                            }

                        } else {
                            live.setVisibility(View.GONE);
                            full.setVisibility(View.GONE);
                            results.setVisibility(View.GONE);
                            if (dataSnapshot.child("status").exists()) {
                                if (dataSnapshot.child("status").getValue().toString().equals("over")) {
                                    live.setVisibility(View.GONE);
                                    full.setVisibility(View.GONE);
                                    results.setVisibility(View.VISIBLE);



                                } else if (dataSnapshot.child("status").getValue().toString().equals("started")) {
                                    live.setVisibility(View.GONE);
                                    results.setVisibility(View.GONE);
                                    full.setVisibility(View.VISIBLE);
                                    full.setText("MATCH LIVE");

                                } else {
                                    full.setVisibility(View.GONE);
                                    results.setVisibility(View.GONE);
                                    live.setVisibility(View.VISIBLE);


                                }

                            } else {
                                full.setVisibility(View.GONE);
                                results.setVisibility(View.GONE);
                                live.setVisibility(View.VISIBLE);


                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                holder.mview.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {


                        Toasty.info(MainActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.VISIBLE);
                        pb.smoothToShow();
                        executor = 0;

                        pressed_key = getRef(holder.getAdapterPosition()).getKey();
                        availability_checker(pressed_key);
                        purchase_checker();
                        final TextView time = holder.mview.findViewById(R.id.time_value);
                        final TextView date = holder.mview.findViewById(R.id.date_value);
                        final TextView map = holder.mview.findViewById(R.id.description);
                        final Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                pb.setVisibility(View.GONE);
                                pb.smoothToHide();
                                Button button = holder.mview.findViewById(R.id.button_full_card);

                                if (button.getText().toString().equals("MATCH FULL")) {
                                    Intent intent = new Intent(MainActivity.this, Instructions_activity.class);
                                    intent.putExtra("time", time.getText().toString());
                                    intent.putExtra("date", date.getText().toString());
                                    intent.putExtra("key", pressed_key);
                                    startActivity(intent);

                                } else if (button.getText().toString().equals("MATCH LIVE")) {
                                    Intent intent = new Intent(MainActivity.this, Instructions_activity.class);
                                    intent.putExtra("time", time.getText().toString());
                                    intent.putExtra("date", date.getText().toString());
                                    intent.putExtra("key", pressed_key);
                                    startActivity(intent);
                                } else if (button.getText().toString().equals("MATCH OVER")) {
                                    Intent intent = new Intent(MainActivity.this, Instructions_activity.class);
                                    intent.putExtra("time", time.getText().toString());
                                    intent.putExtra("date", date.getText().toString());
                                    intent.putExtra("key", pressed_key);

                                    startActivity(intent);
                                } else {

                                    if (executor == 0) {


                                        a = Integer.parseInt(tv2.getText().toString().trim().substring(1));
                                        b = Integer.parseInt(n1);
                                        if (a >= b) {
                                            new FancyAlertDialog.Builder(MainActivity.this)
                                                    .setTitle("Enter the match?")
                                                    .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                                                    .setMessage("₹" + n1 + " will be deducted from your Wallet press 'Yes' to confirm")
                                                    .setNegativeBtnText("Cancel")
                                                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                                                    .setPositiveBtnText("Yes")
                                                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                                                    .setIcon(R.drawable.round_local_atm_white_48, Icon.Visible)
                                                    .isCancellable(true)
                                                    .OnPositiveClicked(new FancyAlertDialogListener() {
                                                        @Override
                                                        public void OnClick() {
                                                            String deduct = String.valueOf(a - b);
                                                            FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount").setValue(deduct);
                                                            FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("purchased").push().setValue(pressed_key);

                                                            Toasty.success(MainActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(MainActivity.this, Instructions_activity.class);
                                                            intent.putExtra("time", time.getText().toString());
                                                            intent.putExtra("date", date.getText().toString());
                                                            intent.putExtra("key", pressed_key);
                                                            startActivity(intent);

                                                        }
                                                    })
                                                    .OnNegativeClicked(new FancyAlertDialogListener() {
                                                        @Override
                                                        public void OnClick() {
                                                            Toasty.info(MainActivity.this, "You Cancelled the transaction", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }).build();
                                        } else {
                                            Toasty.error(MainActivity.this, "Insufficient Balance!!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, Wallet_page.class));
                                        }
                                    } else if (executor == 1) {
                                        Intent intent = new Intent(MainActivity.this, Instructions_activity.class);
                                        intent.putExtra("time", time.getText().toString());
                                        intent.putExtra("date", date.getText().toString());
                                        intent.putExtra("key", pressed_key);
                                        startActivity(intent);

                                    }
                                }


                            }
                        }, 1500);

                    }

                });
                holder.mview.findViewById(R.id.button99).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Toasty.info(MainActivity.this, "Please wait").show();
                        pb.setVisibility(View.VISIBLE);
                        pb.smoothToShow();
                        executor = 0;
                        pressed_key = getRef(holder.getAdapterPosition()).getKey();
                        availability_checker(pressed_key);
                        purchase_checker();
                        final TextView time = holder.mview.findViewById(R.id.time_value);
                        final TextView date = holder.mview.findViewById(R.id.date_value);
                        final TextView map = holder.mview.findViewById(R.id.description);
                        final Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                pb.setVisibility(View.GONE);
                                pb.smoothToHide();
                                if (spots >= 100) {

                                    Toasty.error(MainActivity.this, "MATCH FULL", Toast.LENGTH_SHORT).show();
                                } else {

                                    if (executor == 0) {


                                        a = Integer.parseInt(tv2.getText().toString().trim().substring(1));
                                        b = Integer.parseInt(n1);
                                        if (a >= b) {
                                            new FancyAlertDialog.Builder(MainActivity.this)
                                                    .setTitle("Enter the match?")
                                                    .setBackgroundColor(Color.parseColor("#303F9F"))
                                                    .setMessage("₹" + n1 + " will be deducted from your Wallet press 'Yes' to confirm")
                                                    .setNegativeBtnText("Cancel")
                                                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))
                                                    .setPositiveBtnText("Yes")
                                                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))
                                                    .setIcon(R.drawable.round_local_atm_white_48, Icon.Visible)
                                                    .isCancellable(true)
                                                    .OnPositiveClicked(new FancyAlertDialogListener() {
                                                        @Override
                                                        public void OnClick() {

                                                            String deduct = String.valueOf(a - b);
                                                            FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount").setValue(deduct);
                                                            FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("purchased").child(pressed_key).child("flag").setValue("0");

                                                            Toasty.success(MainActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                                                            mdata_ref.child(pressed_key).child("users").child(usr_name).child("congo_dialog_status").setValue("false");
                                                            post_pur(n1,model.getMatch_number());
                                                            Intent intent = new Intent(MainActivity.this, Instructions_activity.class);
                                                            intent.putExtra("time", time.getText().toString());
                                                            intent.putExtra("date", date.getText().toString());
                                                            intent.putExtra("key", pressed_key);
                                                            startActivity(intent);

                                                        }
                                                    })
                                                    .OnNegativeClicked(new FancyAlertDialogListener() {
                                                        @Override
                                                        public void OnClick() {
                                                            Toasty.info(MainActivity.this, "You Cancelled the transcation", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).build();
                                        } else {
                                            ChocoBar.builder().setActivity(MainActivity.this)
                                                    .setText("Insufficient balance in wallet")
                                                    .setDuration(10000)
                                                    .red()
                                                    .setActionTextColor(Color.parseColor("#ffffff"))
                                                    .setAction("ADD", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            startActivity(new Intent(MainActivity.this, Wallet_page.class));
                                                        }
                                                    })// in built green ChocoBar
                                                    .show();
                                        }
                                    } else if (executor == 1) {
                                        Intent intent = new Intent(MainActivity.this, Instructions_activity.class);
                                        intent.putExtra("time", time.getText().toString());
                                        intent.putExtra("date", date.getText().toString());
                                        intent.putExtra("key", pressed_key);

                                        startActivity(intent);
                                    }
                                }
                            }
                        }, 1500);

                    }

                });
                holder.mview.findViewById(R.id.button_results).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressed_key = getRef(holder.getAdapterPosition()).getKey();
                        Intent intent = new Intent(MainActivity.this, results.class);
                        intent.putExtra("key", pressed_key);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public card_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())

                        .inflate(R.layout.card_viewww, viewGroup, false);

                return new MainActivity.card_view_holder(view);
            }

            @Override
            public void onDataChanged() {
                pb.smoothToHide();
                pb.setVisibility(View.GONE);

                sl.setVisibility(View.GONE);
                sl.stopShimmerAnimation();
                rv.setVisibility(View.VISIBLE);



            }
        };
        rv.setAdapter(adapter);
        executor = 0;
        //custom_font2 = Typeface.createFromAsset(getAssets(), "font/boldness.otf");
        //TextView textView2 = findViewById(R.id.tv1);

        //   TextView textView4 = findViewById(R.id.go_id);
        //  textView2.setTypeface(custom_font2);
        //  textView4.setTypeface(custom_font2);
        get_money_status();
        show_updator();
        ad= new AlertDialog.Builder(MainActivity.this)
                .setTitle("Update Available!")
                .setMessage("A new version is available.")
                .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String url = "http://2ournamentgo.com/tournamentgo.apk";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.updater_icon).create();

    }


    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onDestroy() {

        FirebaseDatabase.getInstance().getReference().removeEventListener(v1);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v2);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v3);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v4);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v5);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v6);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v7);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v8);
        FirebaseDatabase.getInstance().getReference().removeEventListener(v9);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {


        pb.setVisibility(View.VISIBLE);
        pb.smoothToShow();
        sl.setVisibility(View.VISIBLE);
        sl.startShimmerAnimation();
        rv.setVisibility(View.GONE);

        super.onResume();
    }


    @Override
    protected void onPause() {
        shimmer.cancel();
        super.onPause();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //setting values of text_vies and images in card_view
    public class card_view_holder extends RecyclerView.ViewHolder {

        public String image1;
        View mview;

        public card_view_holder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setTeam(String team) {
            TextView mteam = mview.findViewById(R.id.team);
            mteam.setText(team);
        }

        public void setDevice(String device) {
            TextView mdevice = mview.findViewById(R.id.device);
            mdevice.setText(device);
        }

        public void setDescription(String description) {
            TextView mdescription = mview.findViewById(R.id.description);

            mdescription.setTypeface(custom_font);
            mdescription.setText(description);
        }

        public void setversion(String ver) {
            TextView mdescription = mview.findViewById(R.id.version_card_view);
            mdescription.setTypeface(custom_font);
            mdescription.setText(ver);
        }

        public void set1stamount(String fst_amt) {
            TextView set1st_amt = mview.findViewById(R.id.fst_amt);
            set1st_amt.setTypeface(custom_font);
            set1st_amt.setText(fst_amt);
        }

        public void set2ndamount(String scnd_amt) {
            TextView set2nd_amt = mview.findViewById(R.id.scnd_amt);
            set2nd_amt.setTypeface(custom_font);

            set2nd_amt.setText(scnd_amt);
        }

        public void set3rdamount(String third_amt) {
            TextView set3rd_amt = mview.findViewById(R.id.third_amt);
            set3rd_amt.setTypeface(custom_font);
            set3rd_amt.setText(third_amt);
        }


        public void set_date_value(String date_value) {
            TextView st_value = mview.findViewById(R.id.date_value);
            st_value.setTypeface(custom_font);
            st_value.setText(date_value);
        }

        public void set_time_value(String time_value) {
            TextView tm_value = mview.findViewById(R.id.time_value);
            tm_value.setTypeface(custom_font);
            tm_value.setText(time_value);
        }

        public void setkill_per(String per_kill_value) {
            TextView set_p_k_v = mview.findViewById(R.id.per_kill_value);
            set_p_k_v.setTypeface(custom_font);
            set_p_k_v.setText(per_kill_value);
        }

        public void set_img(final Context ctx, String image) {


            ImageView set_img = mview.findViewById(R.id.image_card_view);
            Picasso.with(ctx).load(image).placeholder(R.drawable.image_holder_picasso).centerInside().fit()
                    .into(set_img);

        }

        public void set_spots(String spots) {
            TextView spt = mview.findViewById(R.id.spots_left);

            spt.setText(spots);
        }

        public void setFst(String fst) {
            TextView prz = mview.findViewById(R.id.fst);
            prz.setTypeface(custom_font);
            prz.setText(fst);

        }

        public void setScnd(String scnd) {
            TextView prz = mview.findViewById(R.id.scnd);
            prz.setTypeface(custom_font);
            prz.setText(scnd);
        }

        public void setThird(String third) {
            TextView prz = mview.findViewById(R.id.thirdee);
            prz.setTypeface(custom_font);
            prz.setText(third);
        }

        public void setDate(String date) {
            TextView prz = mview.findViewById(R.id.date_name);
            prz.setTypeface(custom_font);
            prz.setText(date);
        }

        public void setTime(String time) {
            TextView prz = mview.findViewById(R.id.time_name);
            prz.setTypeface(custom_font);
            prz.setText(time);
        }

        public void setPer_kill(String per_kill) {
            TextView prz = mview.findViewById(R.id.per_kill);
            prz.setTypeface(custom_font);
            prz.setText(per_kill);
        }

        public void setMatch_number(String match_number) {
            TextView match_numbe = mview.findViewById(R.id.match_number);
            match_numbe.setTypeface(custom_font);
            match_numbe.setText(match_number);
        }
        public void setPk_special(String pk_special) {

            if(pk_special.equals("1"))
            {
                TextView pk_spc=mview.findViewById(R.id.per_kill_special);

                TextView fst=mview.findViewById(R.id.fst);
                TextView fst_amt=mview.findViewById(R.id.fst_amt);
                TextView thirdee=mview.findViewById(R.id.thirdee);
                TextView scnd=mview.findViewById(R.id.scnd);
                scnd.setText("Per kill");
                TextView third_amt=mview.findViewById(R.id.third_amt);
                fst.setVisibility(View.INVISIBLE);
                fst_amt.setVisibility(View.INVISIBLE);
                third_amt.setVisibility(View.INVISIBLE);
                thirdee.setVisibility(View.INVISIBLE);
                LinearLayout li=mview.findViewById(R.id.pk_special_view);
                li.setVisibility(View.VISIBLE);
                pk_spc.setTypeface(fo);
            }

        }


    }


    void purchase_checker() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("purchased").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    val = snapshot.getKey();
                    if (val.equals(pressed_key)) {
                        executor = 1;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void availability_checker(String key) {
        final DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Cardjava").child(key);
        mdata_ref.child(key).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spots = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mdata_ref.child(key).child("per_kill_value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n1 = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    public void showAlert_logout() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("Logout ?");
        builder1.setCancelable(true);
        builder1.setMessage("Are you sure you want to logout?");
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(MainActivity.this, login_activity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void get_money_status()
    {   data1=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference wallet_ref=FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount");
        data1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                op1:
                for(DataSnapshot ds:dataSnapshot.child("Cardjava").getChildren())
                {
                    if(ds.child("result").exists()) {

                        for (DataSnapshot ds1 : ds.child("result").child("all").getChildren()) {
                            if ((ds1.child("usr_name").exists()) && ds1.child("amount").exists() && ds1.child("kills").exists()) {

                                if ((ds1.child("usr_name").getValue().toString()).equals(usr_name)) {

                                    if (dataSnapshot.child("Users").child(usr_name).child("purchased").exists()) {
                                        outerloop:
                                        for (DataSnapshot ds2 : dataSnapshot.child("Users").child(usr_name).child("purchased").getChildren()) {

                                            if (ds2.getKey().equals(ds.getKey())) {

                                                if (ds2.child("flag").getValue().toString().equals("0")) {
                                                    ds2.getRef().child("flag").setValue("1");
                                                    if (tv2 != null) {
                                                        if (Integer.parseInt(ds1.child("amount").getValue().toString()) > 0) {
                                                            wallet_ref.setValue(String.valueOf(Integer.parseInt(tv2.getText().toString().substring(1)) + Integer.parseInt(ds1.child("amount").getValue().toString())));
                                                            post_won(ds1.child("amount").getValue().toString());
                                                            dataSnapshot.child("Users").child(usr_name).child("total_winning").getRef().setValue(String.valueOf(Integer.parseInt(dataSnapshot.child("Users").child(usr_name).child("total_winning").getValue().toString()) + Integer.parseInt(ds1.child("amount").getValue().toString())));
                                                            dataSnapshot.child("Users").child(usr_name).child("total_kills").getRef().setValue(String.valueOf(Integer.parseInt(dataSnapshot.child("Users").child(usr_name).child("total_kills").getValue().toString()) + Integer.parseInt(ds1.child("kills").getValue().toString())));

                                                            new PrettyDialog(MainActivity.this)
                                                                    .setTitle("Congratulations!")
                                                                    .setMessage("You have won ₹" + ds1.child("amount").getValue().toString() + " for match #" + ds.child("match_number").getValue() + ".")

                                                                    .setIcon(R.drawable.trophy_sized)
                                                                    .show();


                                                            kv.build()
                                                                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                                                                    .setDirection(0.0, 359.0)
                                                                    .setSpeed(1f, 5f)

                                                                    .setFadeOutEnabled(true)
                                                                    .setTimeToLive(2000L)
                                                                    .addShapes(Shape.RECT, Shape.CIRCLE)

                                                                    .addSizes(new Size(12, 5))
                                                                    .setPosition(-50f, kv.getWidth() + 50f, -50f, -50f)
                                                            ;


                                                            break op1;

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                        }
                        outerloop2:
                        for (DataSnapshot ds1 : ds.child("result").child("fst").getChildren()) {
                            if ((ds1.child("usr_name").exists()) && ds1.child("amount").exists() && ds1.child("kills").exists()) {

                                if ((ds1.child("usr_name").getValue().toString()).equals(usr_name)) {

                                    if (dataSnapshot.child("Users").child(usr_name).child("purchased").exists()) {

                                        for (DataSnapshot ds2 : dataSnapshot.child("Users").child(usr_name).child("purchased").getChildren()) {

                                            if (ds2.getKey().equals(ds.getKey())) {

                                                if (ds2.child("flag").getValue().toString().equals("0")) {
                                                    ds2.getRef().child("flag").setValue("1");
                                                    if (tv2 != null) {
                                                        if (Integer.parseInt(ds1.child("amount").getValue().toString()) > 0) {
                                                            dataSnapshot.child("Users").child(usr_name).child("total_winning").getRef().setValue(String.valueOf(Integer.parseInt(dataSnapshot.child("Users").child(usr_name).child("total_winning").getValue().toString()) + Integer.parseInt(ds1.child("amount").getValue().toString())));
                                                            dataSnapshot.child("Users").child(usr_name).child("total_kills").getRef().setValue(String.valueOf(Integer.parseInt(dataSnapshot.child("Users").child(usr_name).child("total_kills").getValue().toString()) + Integer.parseInt(ds1.child("kills").getValue().toString())));
                                                            wallet_ref.setValue(String.valueOf(Integer.parseInt(tv2.getText().toString().substring(1)) + Integer.parseInt(ds1.child("amount").getValue().toString())));
                                                            new PrettyDialog(MainActivity.this)
                                                                    .setTitle("Congratulations!")
                                                                    .setMessage("You have won ₹" + ds1.child("amount").getValue().toString() + " for match #" + ds.child("match_number").getValue() + ".")

                                                                    .setIcon(R.drawable.trophy_sized)
                                                                    .show();


                                                            kv.build()
                                                                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                                                                    .setDirection(0.0, 359.0)
                                                                    .setSpeed(1f, 5f)

                                                                    .setFadeOutEnabled(true)
                                                                    .setTimeToLive(2000L)
                                                                    .addShapes(Shape.RECT, Shape.CIRCLE)

                                                                    .addSizes(new Size(12, 5))
                                                                    .setPosition(-50f, kv.getWidth() + 50f, -50f, -50f)
                                                                    .streamFor(300, 5000L);
                                                            post_won(ds1.child("amount").getValue().toString());
                                                            break op1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                        }
                        outerloop3:
                        for (DataSnapshot ds1 : ds.child("result").child("scnd").getChildren()) {
                            if ((ds1.child("usr_name").exists()) && ds1.child("amount").exists() && ds1.child("kills").exists()) {

                                if ((ds1.child("usr_name").getValue().toString()).equals(usr_name)) {

                                    if (dataSnapshot.child("Users").child(usr_name).child("purchased").exists()) {
                                        for (DataSnapshot ds2 : dataSnapshot.child("Users").child(usr_name).child("purchased").getChildren()) {

                                            if (ds2.getKey().equals(ds.getKey())) {

                                                if (ds2.child("flag").getValue().toString().equals("0")) {
                                                    ds2.getRef().child("flag").setValue("1");
                                                    if (tv2 != null) {
                                                        if (Integer.parseInt(ds1.child("amount").getValue().toString()) > 0) {
                                                            dataSnapshot.child("Users").child(usr_name).child("total_winning").getRef().setValue(String.valueOf(Integer.parseInt(dataSnapshot.child("Users").child(usr_name).child("total_winning").getValue().toString()) + Integer.parseInt(ds1.child("amount").getValue().toString())));
                                                            dataSnapshot.child("Users").child(usr_name).child("total_kills").getRef().setValue(String.valueOf(Integer.parseInt(dataSnapshot.child("Users").child(usr_name).child("total_kills").getValue().toString()) + Integer.parseInt(ds1.child("kills").getValue().toString())));

                                                            wallet_ref.setValue(String.valueOf(Integer.parseInt(tv2.getText().toString().substring(1)) + Integer.parseInt(ds1.child("amount").getValue().toString())));
                                                            new PrettyDialog(MainActivity.this)
                                                                    .setTitle("Congratulations!")
                                                                    .setMessage("You have won ₹" + ds1.child("amount").getValue().toString() + " for match #" + ds.child("match_number").getValue() + ".")
                                                                    .setIcon(R.drawable.trophy_sized)
                                                                    .show();


                                                            kv.build()
                                                                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                                                                    .setDirection(0.0, 359.0)
                                                                    .setSpeed(1f, 5f)

                                                                    .setFadeOutEnabled(true)
                                                                    .setTimeToLive(2000L)
                                                                    .addShapes(Shape.RECT, Shape.CIRCLE)

                                                                    .addSizes(new Size(12, 5))
                                                                    .setPosition(-50f, kv.getWidth() + 50f, -50f, -50f)
                                                                    .streamFor(300, 5000L);
                                                            post_won(ds1.child("amount").getValue().toString());
                                                            break op1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void post_won(String amt)
    {
        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(time);
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        String formattedTime = tf.format(time);
        String credit = "WON-" + formattedDate + "-" + formattedTime + "-" + amt;
        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("transactions").push().setValue(credit);
    }
    public void post_pur(String amt,String pressed_key)
    {
        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(time);
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        String formattedTime = tf.format(time);
        String credit = "PUR-" + formattedDate + "-" + formattedTime + "-" + amt+"-"+pressed_key;
        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("transactions").push().setValue(credit);
    }
    public void show_updator()
    {
        DatabaseReference dbr2=FirebaseDatabase.getInstance().getReference();
        dbr2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("update").exists())
                {
                    if(Double.parseDouble(dataSnapshot.child("update").child("ver").getValue().toString())>myApp.ver)
                    {
                        ad.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}