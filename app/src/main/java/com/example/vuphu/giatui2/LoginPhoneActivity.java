package com.example.vuphu.giatui2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.vuphu.giatui2.R.id;
import static com.example.vuphu.giatui2.R.layout;

public class LoginPhoneActivity extends AppCompatActivity {


    private static String TAG = "LOGIN NUMBER";
    private EditText edt_phone, edt_code;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private SharedPreferences pre;
    private SharedPreferences.Editor edit;
    private LinearLayout sdt, code;
    private Button go;

    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login_phone);
        edt_phone = findViewById(id.edt_phone);
        sdt = (LinearLayout) findViewById(id.layout_sdt);
        code = (LinearLayout) findViewById(id.layout_code);
        edt_code = findViewById(id.edt_code);
        go = findViewById(id.btn_goon);
        mAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đang xử lí...");
        pre = getSharedPreferences("setting", MODE_PRIVATE);
        edit = pre.edit();
    }

    public void goOn(View view) {
        if (!TextUtils.isEmpty(edt_phone.getText().toString()) && go.getText().toString().contains("Tiếp tục") && valdidateNumber()) {

            progressBar.show();
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    Log.d(TAG, "onVerificationCompleted:" + credential);

                    sdt.setVisibility(View.GONE);
                    code.setVisibility(View.VISIBLE);
                    go.setText("Đăng nhập");
                    progressBar.hide();
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w(TAG, "onVerificationFailed", e);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                    }
                }

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d(TAG, "onCodeSent:" + verificationId);

                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    mResendToken = token;

                    // ...
                }
            };
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    edt_phone.getText().toString(),        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        } else {

            if (!TextUtils.isEmpty(edt_code.getText().toString())) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, edt_code.getText().toString().trim());
                Log.i("login", credential.toString());
                progressBar.show();
                signInWithPhoneAuthCredential(credential);
            }
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            updateUI(user);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(LoginPhoneActivity.this, "Không thể đăng nhập", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginPhoneActivity.this, "Mã nhập sai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void loginByEmail(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void updateUI(FirebaseUser currentUser) {

        if (!progressBar.isShowing()){
            progressBar.show();
        }

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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (pre.getBoolean("checked",false) && user!= null)
            updateUI(user);

    }
    private boolean valdidateNumber() {


        Pattern pattern = Pattern.compile("^(01[2689]|09)[0-9]{8}$");
        Matcher matcher = pattern.matcher(edt_phone.getText().toString());

        if (!matcher.matches()) {
            edt_phone.setError("Không dúng định dạng");
            return false;
        }
        return true;
    }
}
