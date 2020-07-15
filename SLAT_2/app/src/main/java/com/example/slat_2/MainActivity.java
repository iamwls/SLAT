package com.example.slat_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends SuperClass {

    EditText title, description;
    Button insert;
    DatabaseReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> arrTitle = new ArrayList<>();
    ArrayList<String> arrContent = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        reference = database.getReference();

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        insert = findViewById(R.id.insert);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Item item = snapshot.child("item").child(uid).getValue(Item.class);
                if(item != null){
                    arrTitle = item.getTitlestr();
                    arrContent = item.getDescriptionstr();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titletext,contenttext;

                //입력된 데이터를 가져와 저장
//                int checkedId = ((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
//                switch (checkedId){
//
//                    case R.id.first:
//                        type="국어";
//                        break;
//
//                    case R.id.second:
//                        type="수학";
//                        break;
//
//                    case R.id.third:
//                        type="한국사";
//                        break;
//
//                    case R.id.fourth:
//                        type="앱프";
//                        break;
//
//                    case R.id.fifth:
//                        type="응프";
//                        break;
//
//                    case R.id.sixth:
//                        type="기타";
//                        break;
//
//                }

                titletext = ((EditText)findViewById(R.id.title)).getText().toString();
                contenttext = ((EditText)findViewById(R.id.description)).getText().toString();


                Intent intent = new Intent (MainActivity.this,ScreenMainActivity.class);
                arrTitle.add(titletext);
                arrContent.add(contenttext);
                Item item = new Item(arrTitle, arrContent, null);
                reference.child("item").child(uid).setValue(item);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
