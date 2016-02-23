package com.uta.bookkart;

import com.uta.bookkart.GetBookCallback;
import com.uta.bookkart.Search;
import com.uta.bookkart.ServerReq;
import com.uta.bookkart.book;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Search extends Activity {

    int i = 0;
    UserLocal userLocal;
    TextView twelcome, tsearched_book;
    EditText etsearch_name;
    Button bsearch;
    BookLocal bookLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        twelcome = (TextView) findViewById(R.id.welcome);
        etsearch_name = (EditText) findViewById(R.id.search_name);
        bsearch = (Button) findViewById(R.id.bsearch);
        tsearched_book = (TextView) findViewById(R.id.searched_book);

        bookLocal = new BookLocal(this);
        //Intent intent = getIntent();
        //twelcome.setText("Welcome" + intent.getStringExtra("mag_f_name"));
        userLocal = new UserLocal(this);
        User user = userLocal.getLoggedInUser();
        twelcome.setText("Welcome " + user.f_name);


        bsearch.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String search_query = etsearch_name.getText().toString();

                book b_search = new book(search_query);

                authenticate(b_search);

                bookLocal.storeBookData(b_search);
                bookLocal.setBookLoggedIn(true);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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

    private void authenticate(book book) {
        ServerReq serverreq = new ServerReq(this);
        serverreq.fetchBookDataAsyncTask(book, new GetBookCallback() {
            @Override
            public void done(book returnedbook) {
                if (returnedbook == null) {
                    showErrorMessage();
                } else {
                    logBookIn(returnedbook);
                }
            }
        });

    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Search.this);
        dialogBuilder.setMessage("No Book Found");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logBookIn(book returnedbook) {
        bookLocal.storeBookData(returnedbook);
        bookLocal.setBookLoggedIn(true);
        final book book = bookLocal.getLoggedInBook();
        tsearched_book.setText("Book Name:" + book.b_name + "\nBook Author:" + book.b_author + "\n" +
                "Book ISBN:" + book.b_isbn + "\nBook Price:" + book.b_price);
        tsearched_book.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                CheckOutKart citem = new CheckOutKart(book.b_name, book.b_category, book.b_isbn, book.b_author, book.b_price);
                Intent checkoutscr = new Intent("com.uta.bookkart.CHECK_OUT");
                startActivity(checkoutscr);

            }
        });
    }
}
