package com.example.elitesecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResidentRegister extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://elite-security-8c9ee-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_register);

        EditText name = findViewById(R.id.name);
        EditText phone = findViewById(R.id.phone);
        EditText address = findViewById(R.id.address);
        EditText ic = findViewById(R.id.ic);
        EditText password = findViewById(R.id.password);
        EditText conPassword = findViewById(R.id.conPassword);

        Button registerBtn = findViewById(R.id.register_button);
        TextView loginNowBtn = findViewById(R.id.loginNowBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                String phoneTxt = phone.getText().toString();
                String addressTxt = address.getText().toString();
                String icTxt = ic.getText().toString();
                String passwordTxt = password.getText().toString();
                String conPasswordTxt = conPassword.getText().toString();

                if(icTxt.isEmpty()||passwordTxt.isEmpty()||conPasswordTxt.isEmpty()){
                    Toast.makeText(ResidentRegister.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }

                else if (!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(ResidentRegister.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(icTxt)){
                                Toast.makeText(ResidentRegister.this,"Phone is already registered",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                databaseReference.child("users").child(icTxt).child("name").setValue(nameTxt);
                                databaseReference.child("users").child(icTxt).child("phone").setValue(phoneTxt);
                                databaseReference.child("users").child(icTxt).child("address").setValue(addressTxt);
                                databaseReference.child("users").child(icTxt).child("password").setValue(passwordTxt);
                                databaseReference.child("users").child(icTxt).child("role").setValue("resident");

                                Toast.makeText(ResidentRegister.this,"Resident registered successfully",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}