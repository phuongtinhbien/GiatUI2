package com.example.vuphu.giatui2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {


    private EditText edt_email, edt_pass;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private CheckBox remember;

    private SharedPreferences pre;
    private SharedPreferences.Editor edit;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        remember = findViewById(R.id.remember);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đang xử lí...");
        pre = getSharedPreferences("setting", MODE_PRIVATE);
        edit = pre.edit();

        if (!pre.getBoolean("checked",false)) {
            edit.putBoolean("checked", false);
            edit.commit();
        }


    }



    public void signIn(View view) {
        if (checkField()) {
            progressBar.show();
            authen(edt_email.getText().toString(), edt_pass.getText().toString());
            if(remember.isChecked()){
                edit.putBoolean("checked", true);
                edit.commit();
            }
        }

    }

    public void signUp(View view) {
        startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
    }

   
    private void authen(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
        
    }
    
    private boolean checkField(){
        if (TextUtils.isEmpty(edt_email.getText().toString())){
            edt_email.setError("Chưa nhập email");
            return false;
        }
        if (TextUtils.isEmpty(edt_pass.getText().toString())){
            edt_pass.setError("Chưa nhập mật khẩu");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (pre.getBoolean("checked",false))
            updateUI(currentUser);
        else{
            if (currentUser!= null)
                mAuth.signOut();
        }
    }

    private void updateUI(FirebaseUser currentUser) {


        if(currentUser!= null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            progressBar.hide();
            finish();

        }
        else {
            Toast.makeText(this, "Không thể đăng nhập kiểm tra lại", Toast.LENGTH_SHORT).show();
            progressBar.hide();
        }
    }

    public void forgotPass(View view) {

        startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class));

    }
}
