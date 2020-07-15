package com.example.slat_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SelectActivity extends Activity {

    private FirebaseAuth firebaseAuth;
    private Spinner spinnergrade, spinnerclass;
    private Button selectBtn;
    String grade,ban;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);




        spinnergrade = (Spinner)findViewById(R.id.spinnergrade);
        spinnerclass = (Spinner)findViewById(R.id.spinnerclass);
        selectBtn = (Button)findViewById(R.id.selectBtn);
        reference = database.getReference("Users");


        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        final String uid = user.getUid();
        final String email = user.getEmail();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                grade = dataSnapshot.child(uid).child("hashMap").child("grade").getValue(String.class);
                ban = dataSnapshot.child(uid).child("hashMap").child("ban").getValue(String.class);
                if(grade != null && ban != null){
                    moveMain(grade,ban);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        spinnergrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //grade = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        spinnerclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spinnerclass.getSelectedItem()==null || spinnergrade.getSelectedItem()==null){
                    Toast.makeText(SelectActivity.this, "모두 선택하세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    String ban = spinnerclass.getSelectedItem().toString();
                    String grade = spinnergrade.getSelectedItem().toString();

                    Data data;
                    data = new Data(email, uid, grade, ban);


                    reference.child(uid).setValue(data);

                    moveMain(grade, ban);
                }


            }
        });

    }
    private void moveMain(String grade, String ban){
        Toast.makeText(SelectActivity.this,grade+" "+ban+" 으로 환영합니다",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SelectActivity.this,ScreenMainActivity.class);
        intent.putExtra("ban",ban);
        intent.putExtra("grade",grade);
        startActivity(intent);
        finish();
    }

    public class Data {
        HashMap<String,String> hashMap = new HashMap<>();

        public Data(String email, String uid, String grade, String ban){
            hashMap.put("email",email);
            hashMap.put("uid",uid);
            hashMap.put("grade",grade);
            hashMap.put("ban",ban);
        }
        public HashMap<String, String> getHashMap(){
            return hashMap;
        }
    }
}
