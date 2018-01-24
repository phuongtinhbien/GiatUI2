package com.example.vuphu.giatui2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edt_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        mAuth = FirebaseAuth.getInstance();
        edt_pass = findViewById(R.id.edt_getemail);
    }

    public void forgotPass(View view) {

        if (!TextUtils.isEmpty(edt_pass.getText().toString()) && valdidateEmail())
            mAuth.sendPasswordResetEmail(edt_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPassActivity.this, "Đã gửi email cho bạn", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ForgotPassActivity.this, "Gửi email không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        else{
            edt_pass.setError("Chưa nhập email!");
        }
    }

    private boolean valdidateEmail() {


        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(edt_pass.getText().toString());
        if (!matcher.matches()) {
            edt_pass.setError("Không dúng định dạng");
            return false;
        }


        return true;


    }
}
