///settings
package com.go.gamez_era;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class settings extends AppCompatActivity {
    ArrayList<model_for_settings_list_view> arrayList;
    ImageView back_arrow;
    RelativeLayout cl;
    DatabaseReference dbRefrence;
    DatabaseReference getDbRefrence_total_winning;
    TextView logout;
    ListView lv;
    RelativeLayout rl;
    TextView text_Email;
    TextView text_userID;
    TextView text_wallet_balance;
    Toolbar toolbar;
    TextView total_won_value;
    String usr_name = myApp.user_name;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_settings);
        cl = findViewById(R.id.NonetScreen_settings);
        rl = findViewById(R.id.connected_settings);
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
        lv = findViewById(R.id.list_view_settings);
        total_won_value = findViewById(R.id.total_won_value);
        back_arrow = findViewById(R.id.back_arrow);

        logout = findViewById(R.id.logout_button);
        text_userID = findViewById(R.id.pubgId);
        text_userID.setText(usr_name);
        text_Email =  findViewById(R.id.email_user);
        text_wallet_balance =  findViewById(R.id.balance_wallet_value);
        text_Email.setText((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())).getEmail());
        setText_wallet_balance();

        arrayList = new ArrayList<>();
        arrayList.add(new model_for_settings_list_view("Edit Profile", "Change your name,number and description."));
        arrayList.add(new model_for_settings_list_view("Wallet", "Add money and view your wallet."));
        arrayList.add(new model_for_settings_list_view("Contact us", "Contact customer support through email and get support within 24hrs."));
        arrayList.add(new model_for_settings_list_view("Terms of Use", "Read document stating Terms and Conditions for the service of this app."));
        arrayList.add(new model_for_settings_list_view("Privacy Policy", "Legal document for Privacy Policy."));
        lv.setAdapter(new settings_list_adapter(this,  arrayList));
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if (i == 0) {

                    startActivity(new Intent(settings.this, edit_profile.class));
                }
                if (i == 1) {
                    startActivity(new Intent(settings.this, Wallet_page.class));
                }
                if (i == 2) {
                    startActivity(new Intent(settings.this, Contact_us_activity.class));
                }
                if (i == 3) {
                    startActivity(new Intent(settings.this, terms_and_conditions.class));
                }
                if (i == 4) {
                    startActivity(new Intent(settings.this, privacy_policy.class));
                }
            }
        });
        back_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.this.finish();
            }
        });
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert_logout();
            }
        });
        get_total_winning();
    }
    private void showAlert_logout()
    {
        final AlertDialog.Builder bd=  new AlertDialog.Builder(settings.this);
        bd.setTitle("Logout?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(settings.this,login_activity.class));
                        settings.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

    }


    private void setText_wallet_balance()
    {
        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("wallet_amount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text_wallet_balance.setText(" ₹"+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void get_total_winning()
    {
        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).child("total_winning").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total_won_value.setText(" ₹"+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






}

