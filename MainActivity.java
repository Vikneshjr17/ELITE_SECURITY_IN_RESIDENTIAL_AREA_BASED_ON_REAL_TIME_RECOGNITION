package com.example.elitesecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://elite-security-8c9ee-default-rtdb.asia-southeast1.firebasedatabase.app/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //hooks
        EditText ic = findViewById(R.id.ic);
        EditText password = findViewById(R.id.password);
        Button button = findViewById(R.id.login_button);
        TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String icTxt = ic.getText().toString();
                String passwordTxt = password.getText().toString();

                if (icTxt.isEmpty()||passwordTxt.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter your phone or password",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(icTxt)){
                                if(icTxt.equals("000001010001")){
                                    String getPassword = snapshot.child(icTxt).child("password").getValue(String.class);
                                    if(getPassword.equals(passwordTxt)){
                                        Toast.makeText(MainActivity.this,"Successfully Logged In", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,AdminMenu.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    String getPassword = snapshot.child(icTxt).child("password").getValue(String.class);
                                    if(getPassword.equals(passwordTxt)){
                                        Toast.makeText(MainActivity.this,"Successfully Logged In", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,ResidentMenu.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                            else{
                                Toast.makeText(MainActivity.this,"Wrong IC Number",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ResidentRegister.class));
            }
        });

    }
}