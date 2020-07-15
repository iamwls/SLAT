package com.example.slat_2;

import android.content.Intent;
import android.location.GpsStatus;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScreenMainActivity extends AppCompatActivity {

    ListView todolist;
    Button plusBtn, showTimeBtn,logoutBtn;
    TextView class_name;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    Calendar cal = Calendar.getInstance();
    int dayofWeek = cal.get(Calendar.DAY_OF_WEEK);
    int hour = cal.get(Calendar.HOUR);
    int min = cal.get(Calendar.MINUTE);
    String ban,grade;
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    Item item;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        //초기 데이터 추가
        reference = database.getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item = snapshot.child("item").child(uid).getValue(Item.class);
                if(item != null){
                    title = item.getTitlestr();
                    content = item.getDescriptionstr();
                    makeAdapter(title, content);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        todolist = findViewById(R.id.todolist);
        plusBtn = findViewById(R.id.plusBtn);
        showTimeBtn = findViewById(R.id.showTimeBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        Intent intent = getIntent();
        ban = intent.getStringExtra("ban");
        grade = intent.getStringExtra("grade");



//        String receiveTitle = intent.getStringExtra("title");
//        String receiveContent = intent.getStringExtra("content");
//        String receiveType = intent.getStringExtra("type");
//        final String receiveBan = intent.getStringExtra("class");
//        final String receiveGrade = intent.getStringExtra("grade");




        //로그인 버튼
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ScreenMainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });



        //할 일 추가 버튼
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ScreenMainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        //시간표 보러가기 버튼
        showTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScreenMainActivity.this, ScheduleActivity.class);
                intent.putExtra("ban", ban);
                intent.putExtra("grade", grade);
                startActivity(intent);
            }
        });




    }
    public void makeAdapter(ArrayList<String> title, ArrayList<String> content){
        Item item1 = new Item(title, content, null);
        //CustomAdapter 생성
        final CustomAdapter adapter = new CustomAdapter(this,R.layout.listview_item,item1);

        //listView에 어댑터 연결
        todolist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        todolist.setSelection(adapter.getCount()-1);

    }

}
