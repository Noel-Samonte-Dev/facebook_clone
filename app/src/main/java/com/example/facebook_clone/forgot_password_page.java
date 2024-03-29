package com.example.facebook_clone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.facebook_clone.Database.DBhelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class forgot_password_page extends AppCompatActivity {
    private TextInputEditText new_pw_email_text_field, change_pw_otp_text_field,
            change_pw_pw_text_field, change_pw_confirm_pw_text_field;
    private AppCompatButton change_pw_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_page);

        mAuth = FirebaseAuth.getInstance();

        ImageView back_btn_new_pw = findViewById(R.id.back_btn_new_pw);
        back_btn_new_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new_pw_email_text_field = findViewById(R.id.new_pw_email_text_field);
        change_pw_otp_text_field = findViewById(R.id.change_pw_otp_text_field);
        change_pw_pw_text_field = findViewById(R.id.change_pw_pw_text_field);
        change_pw_confirm_pw_text_field = findViewById(R.id.change_pw_confirm_pw_text_field);

        getKeyboardEvent(new_pw_email_text_field);
        getKeyboardEvent(change_pw_otp_text_field);
        getKeyboardEvent(change_pw_pw_text_field);
        getKeyboardEvent(change_pw_confirm_pw_text_field);

        TextView get_code = findViewById(R.id.get_code);
        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBhelper dbh =  new DBhelper(getApplicationContext());
                String mobile_number = new_pw_email_text_field.getText().toString().trim();
                if (!dbh.checkExistingMobile(mobile_number)) {
                    Toast.makeText(forgot_password_page.this,
                            "Mobile number does not exist in our database.", Toast.LENGTH_LONG).show();
                } else {
                    otpSend(mobile_number);
                }
            }
        });

        change_pw_btn = findViewById(R.id.change_pw_btn);
        change_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = change_pw_otp_text_field.getText().toString();
                verifyCode(code);
            }
        });

    }

    private FirebaseAuth mAuth;
    private void otpSend(String phoneNumber) {
        String phonse_number = "+63" + phoneNumber.substring(1);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phonse_number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private String verificationId;
    private String otpSent;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verificationId = code;
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DBhelper dbh = new DBhelper(getApplicationContext());
                            dbh.getUserID(getApplicationContext(), new_pw_email_text_field.getText().toString().trim());
                            Toast.makeText(forgot_password_page.this,
                                    "PLEASE WAIT!!!", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences sp = getSharedPreferences("Profile", MODE_PRIVATE);
                                    String user_id = sp.getString("cp_user_id", "");
                                    dbh.changePassword(change_pw_pw_text_field.getText().toString().trim(), user_id);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(forgot_password_page.this,
                                                    "Congratulations! Password update success.", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }, 1000);
                                }
                            }, 1000);
                        } else {
                            Toast.makeText(forgot_password_page.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getKeyboardEvent(TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE) ||
                        (actionId == EditorInfo.IME_ACTION_GO) || (actionId == EditorInfo.IME_ACTION_SEARCH) ||
                        (actionId == EditorInfo.IME_ACTION_SEND)) {
                    closeKeyboard();
                    return true;
                }

                return false;
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void checkInputs() {
        String pw = change_pw_pw_text_field.toString().trim();
        String confirm_pw = change_pw_confirm_pw_text_field.getText().toString().trim();
        String email = new_pw_email_text_field.getText().toString().trim();
        boolean is_valid = email.isEmpty() || change_pw_otp_text_field.getText().toString().trim().isEmpty()
                || pw.isEmpty() || confirm_pw.isEmpty() || !passwordValidation(pw) || !emailValidation(email);
        change_pw_btn.setClickable(!is_valid);
        change_pw_btn.setBackgroundResource(!is_valid ? R.drawable.button_background_blue : R.drawable.button_background_gray);
    }

    private boolean passwordValidation(String pw) {
        String password_pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\/\";<>:\\\'(){}\\[\\],._!@#$%^&*+=?-]).{8,}$";
        return pw.matches(password_pattern) && change_pw_pw_text_field.getText().toString().trim().equals(change_pw_confirm_pw_text_field.getText().toString().trim());
    }

    private boolean emailValidation(String email) {
        String email_pattern = "^.*@\\w+([\\.-]?\\w+)(\\.\\w{2,3})+$";
        return true;
    }
}
