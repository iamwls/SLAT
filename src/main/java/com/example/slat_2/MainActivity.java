package com.example.slat_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText idEditText,pwdEditText;
    private Button loginBtn;
    private Button newBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

        idEditText = (EditText)findViewById(R.id.idEditText);
        pwdEditText = (EditText)findViewById(R.id.pwdEditText);


        newBtn = (Button)findViewById(R.id.newBtn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SignUpActivity 연결
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idEditText.getText().toString().equals(" ") && !pwdEditText.getText().toString().equals("")) {
                    loginUser(idEditText.getText().toString(),pwdEditText.getText().toString());
                } else {

                    Toast.makeText(MainActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                }
            }
        };

    }


    public void loginUser(String id, String password) {

        firebaseAuth.signInWithEmailAndPassword(id,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    //로그인 성공
                    Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    firebaseAuth.addAuthStateListener(firebaseAuthListener);
                } else {

                    //로그인 실패
                    Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}
