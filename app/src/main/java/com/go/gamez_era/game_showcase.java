package com.go.gamez_era;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import io.armcha.elasticview.ElasticView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class game_showcase extends AppCompatActivity {
TextView title;
Typeface custom_font;
    Drawer result;
    private Toolbar mtoolbar;
    Button tv2;

    AccountHeader headerResult;
    private DatabaseReference wallwt_ref;
    Users bcg;
    String usr_name=myApp.user_name;
    TextView counter,strategy_heading;
    ShimmerTextView tv1,tv_loading,coming_soon_2;
    ElasticView ev;
    Shimmer shimmer = new Shimmer();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_showcase);
        title=findViewById(R.id.game_show_case);
        ev=findViewById(R.id.imageElasticView);
        tv_loading=findViewById(R.id.coming_soon);
        coming_soon_2=findViewById(R.id.coming_soon_2);
        strategy_heading=findViewById(R.id.strategy_heading);
        custom_font = Typeface.createFromAsset(getAssets(), "font/crb2.ttf");
        title.setTypeface(custom_font);
        strategy_heading.setTypeface(custom_font);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "font/boldness.otf");
        TextView textView2=findViewById(R.id.tv1);
        wallwt_ref = FirebaseDatabase.getInstance().getReference().child("Users");
        mtoolbar = (Toolbar) findViewById(R.id.mylol);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tv2 = mtoolbar.findViewById(R.id.textView23);

        wallwt_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bcg = new Users();

                bcg.setWallet_amount(dataSnapshot.child(usr_name).getValue(Users.class).getWallet_amount());
                tv2.setText("₹"+bcg.getWallet_amount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(game_showcase.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        TextView textView4=findViewById(R.id.go_id);
        textView2.setTypeface(custom_font2);
        textView4.setTypeface(custom_font2);

        IProfile profile = new ProfileDrawerItem().withName("Wallet :₹"+tv2.getText().toString()).withEmail("-").withIcon(getResources().getDrawable(R.drawable.account_header_image_2)).withIdentifier(0);
        headerResult = new AccountHeaderBuilder()
                .withActivity(game_showcase.this)
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
        new DrawerBuilder().withActivity(game_showcase.this).withAccountHeader(headerResult).build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.drawable.wallet_10).withName("My Wallet").withSelectedColor(getResources().getColor(R.color.selector_nav));
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withIcon(R.drawable.withdraw_10).withName("Withdraw").withSelectedColor(getResources().getColor(R.color.selector_nav));
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withIcon(R.drawable.transactions_10).withName("My Transactions").withSelectedColor(getResources().getColor(R.color.selector_nav));
        PrimaryDrawerItem item4= new PrimaryDrawerItem().withIdentifier(4).withIcon(R.drawable.rules_10).withName("Rules").withSelectedColor(getResources().getColor(R.color.selector_nav));

        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withIcon(R.drawable.settings_10).withName("Account Settings").withTextColor(getResources().getColor(R.color.text_nav)).withSelectedColor(getResources().getColor(R.color.selector_nav));

        result = new DrawerBuilder()
                .withActivity(game_showcase.this)
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

                        if(position==1)
                        {

                            startActivity(new Intent(game_showcase.this, Wallet_page.class));
                            result.closeDrawer();return true;
                        }
                        if(position==2)
                        {

                            startActivity(new Intent(game_showcase.this, redeem_activiy.class));
                            result.closeDrawer();return true;
                        }
                        if(position==3)
                        {

                            startActivity(new Intent(game_showcase.this, transcations.class));
                            result.closeDrawer();return true;
                        }
                        if(position==4)
                        {

                            startActivity(new Intent(game_showcase.this, rules_activity.class));
                            result.closeDrawer();
                            return true;
                        }
                        if(position==5)
                        {
                            startActivity(new Intent(game_showcase.this, settings.class));
                            result.closeDrawer();
                            return true;
                        }

                        else
                        {

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

        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount");
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                IProfile profile = new ProfileDrawerItem().withName("Wallet :₹"+dataSnapshot.getValue()).withEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).withIcon(getResources().getDrawable(R.drawable.account_header_image_2)).withIdentifier(0);
                headerResult.removeProfile(0);
                headerResult.addProfile(profile,0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //drawer ends here

        shimmer.setDuration(2500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
      //  tv1=findViewById(R.id.game_title);
        shimmer.start(tv1);
        counter=findViewById(R.id.counter);

        set_counter();
        ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(game_showcase.this,MainActivity.class));
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(game_showcase.this,Wallet_page.class));
            }
        });

        get_money_status();
    }
    public void showAlert_logout()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(game_showcase.this);
        builder1.setMessage("Logout ?");
        builder1.setCancelable(true);
        builder1.setMessage("Are you sure you want to logout?");
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(game_showcase.this, login_activity.class);
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
        shimmer.start(tv_loading);

        shimmer.start(coming_soon_2);

    }
    public void set_counter()
    {
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counter.setText(String.valueOf(dataSnapshot.getChildrenCount()+53));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmer.cancel();
    }
    @Override
    protected void onResume() {
        super.onResume();
        shimmer.start(tv1);
        shimmer.start(tv_loading);
        shimmer.start(coming_soon_2);
    }
    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            game_showcase.this.finish();
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

    public void get_money_status()
    {
       final DatabaseReference wallet_ref=FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount");
        FirebaseDatabase.getInstance().getReference().child("Cardjava").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(ds.child("result").exists())
                    {
                        for(DataSnapshot ds1:ds.child("result").child("all").getChildren())
                        {
                            if((ds1.child("usr_name").getValue().toString()).equals(usr_name))
                            {
                                if(Integer.parseInt(ds1.child("flag").getValue().toString())==0) {
                                    if (tv2 != null) {
                                        wallet_ref.setValue(String.valueOf(Integer.parseInt(tv2.getText().toString().substring(1)) + Integer.parseInt(ds1.child("amount").getValue().toString())));

                                        break;
                                    }
                                }

                            }
                        }
                        for(DataSnapshot ds1:ds.child("result").child("fst").getChildren())
                        {
                            if((ds1.child("usr_name").getValue().toString()).equals(usr_name))
                            {
                                if(tv2!=null)
                                {
                                    wallet_ref.setValue(String.valueOf(Integer.parseInt(tv2.getText().toString().substring(1))+Integer.parseInt(ds1.child("amount").getValue().toString())));
                                    break;
                                }

                            }
                        }
                        for(DataSnapshot ds1:ds.child("result").child("scnd").getChildren())
                        {
                            if((ds1.child("usr_name").getValue().toString()).equals(usr_name))
                            {
                                if(tv2!=null)
                                {
                                    wallet_ref.setValue(String.valueOf(Integer.parseInt(tv2.getText().toString().substring(1))+Integer.parseInt(ds1.child("amount").getValue().toString())));
                                    break;
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


}
