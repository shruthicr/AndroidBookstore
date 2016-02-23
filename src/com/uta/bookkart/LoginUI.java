package com.uta.bookkart;

import com.uta.bookkart.GetUserCallback;
import com.uta.bookkart.LoginUI;
import com.uta.bookkart.ServerReq;
import com.uta.bookkart.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginUI extends Activity {

    Button blogin;
    EditText username, password;
    TextView reglink;
    UserLocal userLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        blogin = (Button) findViewById(R.id.blogin);
        reglink = (TextView) findViewById(R.id.reglink);
        StrictMode.enableDefaults();
        userLocal = new UserLocal(this);

        blogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                String emailid = username.getText().toString();
                String pass_word = password.getText().toString();

                User user = new User(emailid, pass_word);

                authenticate(user);

                userLocal.storeUserData(user);
                userLocal.setUserLoggedIn(true);


            }
        });

        reglink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent regscr = new Intent("com.uta.bookkart.REGISTER");
                startActivity(regscr);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void authenticate(User user) {
        ServerReq serverreq = new ServerReq(this);
        serverreq.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });

    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginUI.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocal.storeUserData(returnedUser);
        userLocal.setUserLoggedIn(true);
        /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginUI.this);
        dialogBuilder.setMessage("Login Successful");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();*/
        Intent searchscr = new Intent("com.uta.bookkart.SEARCH");
        startActivity(searchscr);
        //reglink.setText(returnedUser.f_name);
        //startActivity(new Intent(this, LoginUI.class));
    }
}
