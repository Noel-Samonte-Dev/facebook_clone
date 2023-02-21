package com.example.facebook_clone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;

public class login_page extends AppCompatActivity {
    private AppCompatButton login_btn;
    private TextInputEditText login_username_text_field, login_pw_text_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, navigation_page.class);
                startActivity(intent);
                finish();
            }
        });

        AppCompatButton create_new_account_btn = findViewById(R.id.create_new_account_btn);
        create_new_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, create_new_account_page.class);
                startActivity(intent);
            }
        });

        TextView forgot_pw = findViewById(R.id.forgot_pw);
        forgot_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, forgot_password_page.class);
                startActivity(intent);
            }
        });

        login_username_text_field = findViewById(R.id.login_username_text_field);
        login_pw_text_field = findViewById(R.id.login_pw_text_field);

        getKeyboardEvent(login_username_text_field);
        getKeyboardEvent(login_pw_text_field);
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
        boolean is_valid = login_pw_text_field.getText().toString().isEmpty() || login_username_text_field.getText().toString().trim().isEmpty();
        login_btn.setClickable(!is_valid);
        login_btn.setBackgroundResource(!is_valid ? R.drawable.button_background_blue : R.drawable.button_background_gray);
    }
}
