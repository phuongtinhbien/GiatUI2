package com.example.vuphu.giatui2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {


    private EditText edt_email, edt_pass, edt_sdt, edt_name, edt_dc;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    public void init() {
        edt_email = findViewById(R.id.edt_signUp_email);
        edt_pass = findViewById(R.id.edt_signUp_pass);
        edt_sdt = findViewById(R.id.edt_signUp_sdt);
        edt_name = findViewById(R.id.edt_signUp_name);
        edt_dc = findViewById(R.id.edt_signUp_add);


    }

    public void signUp(View view) {

        if (checkField())
            authen(edt_email.getText().toString(), edt_pass.getText().toString());

    }

    private boolean checkField() {

        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            edt_email.setError("Chưa nhập email");
            return false;
        } else {

            if (!valdidateEmail())
                return false;
        }
        if (TextUtils.isEmpty(edt_pass.getText().toString())) {
            edt_pass.setError("Chưa nhập mật khẩu");
            return false;
        } else {
            if (edt_pass.getText().toString().length() < 6) {
                edt_pass.setError("Mật khẩu ít nhất 6 kí tự");
                return false;
            }
        }

        if (TextUtils.isEmpty(edt_sdt.getText().toString())) {
            edt_sdt.setError("Chưa nhập số điện thoại");
            return false;
        } else {
            if (!valdidateNumber())
                return false;
        }
        if (TextUtils.isEmpty(edt_name.getText().toString())) {
            edt_name.setError("Chưa nhập họ tên của bạn");
            return false;
        }
        if (TextUtils.isEmpty(edt_dc.getText().toString())) {
            edt_dc.setError("Chưa nhập địa chỉ của bạn");
            return false;
        }
        return true;
    }

    private boolean valdidateNumber() {


        Pattern pattern = Pattern.compile("^(01[2689]|09)[0-9]{8}$");
        Matcher matcher = pattern.matcher(edt_sdt.getText().toString());

        if (!matcher.matches()) {
            edt_sdt.setError("Không dúng định dạng");
            return false;
        }
        return true;
    }

    private boolean valdidateEmail() {


        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(edt_email.getText().toString());
        if (!matcher.matches()) {
            edt_email.setError("Không dúng định dạng");
            return false;
        }


        return true;


    }

    private void authen(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Đã tạo tài khoản cho bạn", Toast.LENGTH_SHORT).show();

                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(SignUpActivity.this, "Mật khẩu chưa đủ mạnh", Toast.LENGTH_SHORT).show();

                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(SignUpActivity.this, "Lỗi khi tạo tạo tài khoản", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });

    }
}
