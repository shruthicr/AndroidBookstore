package com.uta.bookkart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {

    EditText etf_name, etl_name, etemailid, etpassword, etc_num, etaddress;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etf_name = (EditText) findViewById(R.id.f_name);
        etl_name = (EditText) findViewById(R.id.l_name);
        etemailid = (EditText) findViewById(R.id.emailid);
        etpassword = (EditText) findViewById(R.id.regpassword);
        etc_num = (EditText) findViewById(R.id.credit_number);
        etaddress = (EditText) findViewById(R.id.address);
        bRegister = (Button) findViewById(R.id.breg);
        StrictMode.enableDefaults();

        bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String f_name = etf_name.getText().toString();
                String l_name = etl_name.getText().toString();
                String emailid = etemailid.getText().toString();
                String password = etpassword.getText().toString();
                String c_num = etc_num.getText().toString();
                String address = etaddress.getText().toString();

                User user = new User(f_name, l_name, emailid, password, c_num, address);
                registerUser(user);
            }


        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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

    private void registerUser(User user) {
        // TODO Auto-generated method stub
        ServerReq serverreq = new ServerReq(this);
        serverreq.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                Intent loginIntent = new Intent(Register.this, LoginUI.class);
                startActivity(loginIntent);
            }
        });
    }
}
