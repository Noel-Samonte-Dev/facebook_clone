package com.example.facebook_clone;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;

import java.util.function.Function;

public class create_new_account_page extends AppCompatActivity {
    TextInputEditText new_account_username_text_field, first_name_text_field, last_name_text_field, mobile_text_field, new_account_email_text_field,
            new_account_pw_text_field, new_account_confirm_pw_text_field;
    AppCompatButton create_account_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crete_new_account_page);
        new_account_username_text_field = findViewById(R.id.new_account_username_text_field);
        new_account_pw_text_field = findViewById(R.id.new_account_pw_text_field);
        new_account_confirm_pw_text_field = findViewById(R.id.new_account_confirm_pw_text_field);
        new_account_email_text_field = findViewById(R.id.new_account_email_text_field);
        first_name_text_field = findViewById(R.id.first_name_text_field);
        last_name_text_field = findViewById(R.id.last_name_text_field);
        mobile_text_field = findViewById(R.id.mobile_text_field);

        create_account_btn = findViewById(R.id.create_account_btn);
        create_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageView back_btn_new_acct = findViewById(R.id.back_btn_new_acct);
        back_btn_new_acct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getKeyboardEvent(new_account_username_text_field);
        getKeyboardEvent(new_account_pw_text_field);
        getKeyboardEvent(new_account_confirm_pw_text_field);
        getKeyboardEvent(new_account_email_text_field);
        getKeyboardEvent(first_name_text_field);
        getKeyboardEvent(last_name_text_field);
        getKeyboardEvent(mobile_text_field);
    }

    private void checkInputs() {
        boolean is_valid = emailValidation(new_account_email_text_field.getText().toString()) &&
        passwordValidation(new_account_pw_text_field.getText().toString()) &&
        passwordValidation(new_account_confirm_pw_text_field.getText().toString()) && !isNullFields() && mobileValidation();

        create_account_btn.setClickable(is_valid);
        create_account_btn.setBackgroundResource(is_valid ? R.drawable.button_background_blue : R.drawable.button_background_gray);
    }

    private boolean isNullFields() {
        return new_account_username_text_field.getText().toString().trim().isEmpty() || first_name_text_field.getText().toString().trim().isEmpty()
                || last_name_text_field.getText().toString().trim().isEmpty() || mobile_text_field.getText().toString().trim().isEmpty()
                || new_account_email_text_field.getText().toString().trim().isEmpty() || new_account_pw_text_field.getText().toString().trim().isEmpty()
                || new_account_confirm_pw_text_field.getText().toString().trim().isEmpty();
    }

    private boolean emailValidation(String email) {
        String email_pattern = "^.*@\\w+([\\.-]?\\w+)(\\.\\w{2,3})+$";
        return email.matches(email_pattern);
    }

    private boolean passwordValidation(String pw) {
        String password_pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\/\";<>:\\\'(){}\\[\\],._!@#$%^&*+=?-]).{8,}$";
        return pw.matches(password_pattern) && new_account_pw_text_field.getText().toString().trim().equals(new_account_confirm_pw_text_field.getText().toString().trim());
    }

    private boolean mobileValidation() {
        return mobile_text_field.getText().toString().length() == 11 && mobile_text_field.getText().toString().trim().substring(0, 2).equals("09");
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
}
