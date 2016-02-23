package com.uta.bookkart;

import android.content.Context;
import android.content.SharedPreferences;

import com.uta.bookkart.User;

public class UserLocal {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocal(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("f_name", user.f_name);
        spEditor.putString("l_name", user.l_name);
        spEditor.putString("emailid", user.emailid);
        spEditor.putString("password", user.password);
        spEditor.putString("c_num", user.c_num);
        spEditor.putString("address", user.address);
        spEditor.commit();
    }

    public User getLoggedInUser() {
        String f_name = userLocalDatabase.getString("f_name", "");
        String l_name = userLocalDatabase.getString("l_name", "");
        String emailid = userLocalDatabase.getString("emailid", "");
        String password = userLocalDatabase.getString("password", "");
        String c_num = userLocalDatabase.getString("c_num", "");
        String address = userLocalDatabase.getString("address", "");

        User storedUser = new User(f_name, l_name, emailid, password, c_num, address);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();

    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
    }

}
