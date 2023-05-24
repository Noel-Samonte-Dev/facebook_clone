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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.facebook_clone.Database.DBhelper;
import com.google.android.material.textfield.TextInputEditText;

public class create_new_account_page extends AppCompatActivity {
    TextInputEditText new_account_username_text_field, first_name_text_field, last_name_text_field, mobile_text_field, new_account_email_text_field,
            new_account_pw_text_field, new_account_confirm_pw_text_field;
    AppCompatButton create_account_btn;
    DBhelper db;

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
        db = new DBhelper(this);

        is_girl = findViewById(R.id.is_girl);
        is_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_selected = 1;
                checkGender();
            }
        });

        is_boy = findViewById(R.id.is_boy);
        is_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_selected = 2;
                checkGender();
            }
        });

        is_bakla = findViewById(R.id.is_bakla);
        is_bakla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_selected = 3;
                checkGender();
            }
        });

        is_tomboy = findViewById(R.id.is_tomboy);
        is_tomboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_selected = 4;
                checkGender();
            }
        });

        create_account_btn = findViewById(R.id.create_account_btn);
        create_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = new_account_username_text_field.getText().toString();
                String password = new_account_pw_text_field.getText().toString();
                String firstname = first_name_text_field.getText().toString();
                String lastname = last_name_text_field.getText().toString();
                String mobile = mobile_text_field.getText().toString();
                String email = new_account_email_text_field.getText().toString();
                boolean is_existing_email = db.checkExistingEmail(email);
                boolean is_existing_mobile = db.checkExistingMobile(mobile);
                boolean is_existing_username = db.checkExistingUsername(username);
                boolean is_insert_data_success = false;
                if (!is_existing_email && !is_existing_mobile && !is_existing_username) {
                    is_insert_data_success =
                            db.insertData(
                                    username,
                                    password,
                                    firstname,
                                    lastname,
                                    email,
                                    mobile,
                                    String.valueOf(gender_selected),
                                    "",
                                    getApplicationContext()
                            );
                }

                boolean is_exist = false;
                if (is_existing_email) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Email address already exist, please use a different email address.",Toast.LENGTH_LONG);
                    toast.show();
                    is_exist = true;
                }

                if (!is_exist && is_existing_mobile) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Mobile number already exist, please use a different mobile number.",Toast.LENGTH_LONG);
                    toast.show();
                    is_exist = true;
                }

                if (!is_exist && is_existing_username) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Username already exist, please use a different username.",Toast.LENGTH_LONG);
                    toast.show();
                }

                if (is_insert_data_success) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Successfully Registered!",Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(create_new_account_page.this, navigation_page.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Something went wrong, please try again.",Toast.LENGTH_SHORT);
                    toast.show();
                }
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
                passwordValidation(new_account_confirm_pw_text_field.getText().toString()) && !isNullFields() && mobileValidation() && isValidGender();

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

    private CheckBox is_girl, is_boy, is_bakla, is_tomboy;
    private int gender_selected = 0;
    private void checkGender(){
        is_girl.setChecked(gender_selected == 1);
        is_boy.setChecked(gender_selected == 2);
        is_bakla.setChecked(gender_selected == 3);
        is_tomboy.setChecked(gender_selected == 4);
        checkInputs();

    }

    private boolean isValidGender(){
        return gender_selected > 0;
    }
}
