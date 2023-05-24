package com.example.facebook_clone.Database;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.facebook_clone.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DBName = "Profile.db";

    public DBhelper(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(" +
                "user_id Integer primary key autoincrement, " +
                "username Text, " +
                "password Text, " +
                "firstname Text, " +
                "lastname Text, " +
                "email Text, " +
                "mobile Text, " +
                "gender Text, " +
                "post Text, " +
                "profile_image Text, " +
                "shared_post Text)");

        db.execSQL("create table posts(" +
                "post_id Integer primary key autoincrement, " +
                "likes Integer, " +
                "comments Integer, " +
                "shares Integer, " +
                "comment_str Text, " +
                "user_id_shared Text, " +
                "user_id_comment Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists posts");
        onCreate(db);
    }

    public boolean insertData(
            String username,
            String password,
            String firstname,
            String lastname,
            String email,
            String mobile,
            String gender,
            String shared_post,
            Context context
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Random random = new Random();
        int rand1 = 0;
        int rand2 = 0;
        int rand3 = 0;
        int rand4 = 0;
        int rand5 = 0;
        int rand6 = 0;
        int rand7 = 0;
        int rand8 = 0;
        int rand9 = 0;
        int rand10 = 0;

        do {
            rand1 = getBannerImage(random.nextInt(5));
            rand2 = getBannerImage(random.nextInt(10));
            rand3 = getBannerImage(random.nextInt(15));
            rand4 = getBannerImage(random.nextInt(20));
            rand5 = getBannerImage(random.nextInt(25));
            rand6 = getBannerImage(random.nextInt(30));
            rand7 = getBannerImage(random.nextInt(35));
            rand8 = getBannerImage(random.nextInt(40));
            rand9 = getBannerImage(random.nextInt(45));
            rand10 = getBannerImage(random.nextInt(50));
        } while
        (rand1 == 0 || rand2 == 0 || rand3 == 0 || rand4 == 0 || rand5 == 0 || rand6 == 0 || rand7 == 0 || rand8 == 0 || rand9 == 0 || rand10 == 0);
        String post_array = rand1 + "," + rand2 + "," + rand3 + "," + rand4 + "," + rand5 + "," + rand6 + "," + rand7 + "," + rand8 + "," + rand9 + "," + rand10;

        String image = String.valueOf(profileImage(gender));

        cv.put("username", username.toLowerCase());
        cv.put("password", password);
        cv.put("firstname", firstname);
        cv.put("lastname", lastname);
        cv.put("email", email);
        cv.put("mobile", mobile);
        cv.put("gender", gender);
        cv.put("post", post_array);
        cv.put("profile_image", image);
        cv.put("shared_post", shared_post);
        long result = db.insert("users", null, cv);

        checkLogin(context, username.toLowerCase(), password);

        return !(result == -1);
    }

    public boolean insertPostData(
            int likes,
            int comments,
            int shares,
            String comment_str,
            String user_id_shared,
            String user_id_comment
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("likes", likes);
        cv.put("comments", comments);
        cv.put("shares", shares);
        cv.put("comment_str", comment_str);
        cv.put("user_id_shared", user_id_shared);
        cv.put("user_id_comment", user_id_comment);
        long result = db.insert("posts", null, cv);
        return !(result == -1);
    }

    public ProfileModel getProfileDetails(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user_id = ?", new String[] {user_id});
        ProfileModel profile_model = new ProfileModel();
        while (cursor.moveToNext()) {
            profile_model.user_id = cursor.getInt(0);
            profile_model.username = cursor.getString(1);
            profile_model.password = cursor.getString(2);
            profile_model.firstname = cursor.getString(3);
            profile_model.lastname = cursor.getString(4);
            profile_model.email = cursor.getString(5);
            profile_model.mobile = cursor.getString(6);
            profile_model.gender = cursor.getString(7);
            profile_model.post = cursor.getString(8);
            profile_model.profile_image = cursor.getString(9);
            profile_model.shared_post = cursor.getString(10);
        }

        return profile_model;
    }

    public ArrayList<ProfileModel> getAllProfileDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        ArrayList<ProfileModel> pm_array_list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ProfileModel profile_model = new ProfileModel();
            profile_model.user_id = cursor.getInt(0);
            profile_model.username = cursor.getString(1);
            profile_model.password = cursor.getString(2);
            profile_model.firstname = cursor.getString(3);
            profile_model.lastname = cursor.getString(4);
            profile_model.email = cursor.getString(5);
            profile_model.mobile = cursor.getString(6);
            profile_model.gender = cursor.getString(7);
            profile_model.post = cursor.getString(8);
            profile_model.profile_image = cursor.getString(9);
            profile_model.shared_post = cursor.getString(10);
            pm_array_list.add(profile_model);
        }

        return pm_array_list;
    }

    public Boolean checkLogin(Context context, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username.toLowerCase(), password});
        while (cursor.moveToNext()) {
            SharedPreferences sp = context.getSharedPreferences("Profile", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("login_id", String.valueOf(cursor.getInt(0)));
            editor.commit();
        }

        return cursor.getCount() > 0;
    }

    public Boolean checkExistingUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});
        return cursor.getCount() > 0;
    }

    public Boolean checkExistingEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
        return cursor.getCount() > 0;
    }

    public Boolean checkExistingMobile(String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE mobile = ?", new String[] {mobile});
        return cursor.getCount() > 0;
    }

    public void getUserID(Context context, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE mobile = ?", new String[] {mobile});
        while (cursor.moveToNext()) {
            SharedPreferences sp = context.getSharedPreferences("Profile", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("cp_user_id", String.valueOf(cursor.getInt(0)));
            editor.commit();
        }
    }

    public void changePassword(String password, String user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", password);
        db.update("users", cv, "user_id = ?", new String[]{user_id});
    }

    public Boolean updateProfilePosts(String user_id, String post_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user_id = ?", new String[] {user_id});
        return cursor.getCount() > 0;
    }

    public Integer profileImage(String gender){
        return Integer.parseInt(gender);
    }

    public Integer profileImageV2(String gender){
        int image = 0;
        if (gender.equals("1")) {
            image = R.drawable.girl;
        }

        if (gender.equals("2")) {
            image = R.drawable.boy;
        }

        if (gender.equals("3")) {
            image = R.drawable.gay;
        }

        if (gender.equals("4")) {
            image = R.drawable.lesbian;
        }

        return image;
    }

    public Integer getBannerImage(Integer input) {
        return input;
    }

    public Integer getBannerImageV2(Integer input) {
        int image_int = 0;
        switch (input) {
            case 1:
                return R.drawable.b1;
            case 2:
                return R.drawable.b2;
            case 3:
                return R.drawable.b3;
            case 4:
                return R.drawable.b4;
            case 5:
                return R.drawable.b5;
            case 6:
                return R.drawable.b6;
            case 7:
                return R.drawable.b7;
            case 8:
                return R.drawable.b8;
            case 9:
                return R.drawable.b9;
            case 10:
                return R.drawable.b10;

            case 11:
                return R.drawable.b11;
            case 12:
                return R.drawable.b12;
            case 13:
                return R.drawable.b13;
            case 14:
                return R.drawable.b14;
            case 15:
                return R.drawable.b15;
            case 16:
                return R.drawable.b16;
            case 17:
                return R.drawable.b17;
            case 18:
                return R.drawable.b18;
            case 19:
                return R.drawable.b19;
            case 20:
                return R.drawable.b20;

            case 21:
                return R.drawable.b21;
            case 22:
                return R.drawable.b22;
            case 23:
                return R.drawable.b23;
            case 24:
                return R.drawable.b24;
            case 25:
                return R.drawable.b25;
            case 26:
                return R.drawable.b26;
            case 27:
                return R.drawable.b27;
            case 28:
                return R.drawable.b28;
            case 29:
                return R.drawable.b29;
            case 30:
                return R.drawable.b30;

            case 31:
                return R.drawable.b31;
            case 32:
                return R.drawable.b32;
            case 33:
                return R.drawable.b33;
            case 34:
                return R.drawable.b34;
            case 35:
                return R.drawable.b35;
            case 36:
                return R.drawable.b36;
            case 37:
                return R.drawable.b37;
            case 38:
                return R.drawable.b38;
            case 39:
                return R.drawable.b39;
            case 40:
                return R.drawable.b40;

            case 41:
                return R.drawable.b41;
            case 42:
                return R.drawable.b42;
            case 43:
                return R.drawable.b43;
            case 44:
                return R.drawable.b44;
            case 45:
                return R.drawable.b45;
            case 46:
                return R.drawable.b46;
            case 47:
                return R.drawable.b47;
            case 48:
                return R.drawable.b48;
            case 49:
                return R.drawable.b49;
            case 50:
                return R.drawable.b50;
            default:
                return R.drawable.b13;
        }
    }
}
