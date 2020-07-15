package com.example.slat_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScheduleActivity extends AppCompatActivity {

    ImageView schedule;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    String grade, ban;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Intent getintent = getIntent();
        reference = database.getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                grade = dataSnapshot.child(uid).child("hashMap").child("grade").getValue(String.class);
                ban = dataSnapshot.child(uid).child("hashMap").child("ban").getValue(String.class);
                if(grade != null && ban != null){
                    if(grade == "1학년" && ban == "1반"){
                        schedule.setImageResource(R.drawable.one1);
                    }
                    else if(grade == "1학년" && ban == "2반"){
                        schedule.setImageResource(R.drawable.one2);
                    }
                    else if(grade == "1학년" && ban == "3반"){
                        schedule.setImageResource(R.drawable.one3);
                    }
                    else if(grade == "1학년" && ban == "4반"){
                        schedule.setImageResource(R.drawable.one1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}
