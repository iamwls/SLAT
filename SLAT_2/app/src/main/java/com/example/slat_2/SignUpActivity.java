package com.example.slat_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText idEditText, pwdEditText;
    private Button signUpBtn;
    public String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        idEditText = (EditText)findViewById(R.id.idEditText);
        pwdEditText = (EditText)findViewById(R.id.pwdEditText);

        signUpBtn = (Button)findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idEditText.getText().toString().equals(" ") && !pwdEditText.getText().toString().equals("")){
                    //이메일과 비밀번호가 공백이 아닌 경우
                    createUser(idEditText.getText().toString(),pwdEditText.getText().toString());
                    Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                    startActivity(intent);
                } else {
                    //이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignUpActivity.this,"계정과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createUser(final String id, String password) {
        firebaseAuth.createUserWithEmailAndPassword(id,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //회원가입 성공
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String email = user.getEmail();
                    String uid = user.getUid();

                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("uid",uid);
                    hashMap.put("id",id);
                    hashMap.put("email",email);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Users");
                    reference.child(uid).setValue(hashMap);

                    Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //계정이 중복된 경우
                    Toast.makeText(SignUpActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                }
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

class User{

    public String email;
    public Object uid;

    public User(Object uid, String email){
        this.email = email;
        this.uid = uid;
    }

}


