package com.go.gamez_era;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class edit_profile extends AppCompatActivity {
    DatabaseReference DBref = FirebaseDatabase.getInstance().getReference().child("Users").child(myApp.user_name);
    ImageView back;
    Button bt;
    RelativeLayout cl;
    EditText email;
    EditText fst_name;
    EditText last_name;
    EditText phone;
    RelativeLayout rl;
    EditText userId;
    String usr_name = myApp.user_name;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_edit_profile);
        cl = findViewById(R.id.NonetScreen_edit_profile);
        rl = findViewById(R.id.connected_edit_profile);
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
        fst_name = (EditText) findViewById(R.id.first_name_ep);
        last_name = (EditText) findViewById(R.id.last_name_ep);
        userId = (EditText) findViewById(R.id.pubg_user_name_ep);
        email = (EditText) findViewById(R.id.email_settings_user_ep);
        phone = (EditText) findViewById(R.id.phone_settings_ep);
        bt = (Button) findViewById(R.id.confirm_edi_profile);
        back = (ImageView) findViewById(R.id.back_arrow_edit_profile);
        check_fst_name();
        check_lst_name();
        set_userId();
        check_phone();
        email.setHint(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                button_pressed();
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_profile.this.finish();
            }
        });
    }

    public void check_fst_name() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("first_name").exists()) {
                    fst_name.setText(dataSnapshot.child("first_name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void check_lst_name() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("last_name").exists()) {
                    last_name.setText(dataSnapshot.child("last_name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void set_userId() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("user_name").exists()) {
                    userId.setHint(dataSnapshot.child("user_name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void check_phone() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(usr_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("phone").exists()) {
                    phone.setText(dataSnapshot.child("phone").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    void button_pressed() {
        if (!fst_name.getText().toString().equals("") && !last_name.getText().toString().equals("") && !phone.getText().toString().equals("")) {
            DatabaseReference child = FirebaseDatabase.getInstance().getReference().child("Users").child(myApp.user_name);
            child.child("first_name").setValue(fst_name.getText().toString());
            child.child("last_name").setValue(last_name.getText().toString());
            child.child("phone").setValue(phone.getText().toString());
            Toasty.success(edit_profile.this, "Profile updated.").show();
        } else if (fst_name.getText().toString().isEmpty() && last_name.getText().toString().isEmpty() && phone.getText().toString().isEmpty()) {
            Toasty.error(edit_profile.this, "Please fill all the fields and try again.").show();
        } else if (fst_name.getText().toString().equals("")) {
            Toasty.error(edit_profile.this, "First name can't be empty!").show();
        } else if (last_name.getText().toString().equals("")) {
            Toasty.error(edit_profile.this, "Last name can't be empty!").show();
        } else if (phone.getText().toString().equals("")) {
            Toasty.error(edit_profile.this, "Phone number can't be empty!").show();
        }
    }
}



