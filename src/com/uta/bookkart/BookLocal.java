package com.uta.bookkart;

import android.content.Context;
import android.content.SharedPreferences;

import com.uta.bookkart.book;

public class BookLocal {
    public static final String SP_NAME_BOOK = "bookDetails";
    SharedPreferences bookLocalDatabase;

    public BookLocal(Context context) {
        bookLocalDatabase = context.getSharedPreferences(SP_NAME_BOOK, 0);
    }

    public void storeBookData(book book) {
        SharedPreferences.Editor spEditor = bookLocalDatabase.edit();
        spEditor.putString("b_name", book.b_name);
        spEditor.putString("b_author", book.b_author);
        spEditor.putString("b_isbn", book.b_isbn);
        spEditor.putString("b_category", book.b_category);
        spEditor.putFloat("b_price", (float) book.b_price);
        spEditor.putInt("b_quantity", book.b_quantity);
        spEditor.commit();
    }

    public book getLoggedInBook() {
        String b_name = bookLocalDatabase.getString("b_name", "");
        String b_author = bookLocalDatabase.getString("b_author", "");
        String b_isbn = bookLocalDatabase.getString("b_isbn", "");
        String b_category = bookLocalDatabase.getString("b_category", "");
        float b_price = bookLocalDatabase.getFloat("b_price", (float) 0.0);
        int b_quantity = bookLocalDatabase.getInt("b_quantity", 0);

        book storedBook = new book(b_name, b_author, b_isbn, b_category, b_price, b_quantity);

        return storedBook;
    }

    public void setBookLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = bookLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();

    }

    public void clearBookData() {
        SharedPreferences.Editor spEditor = bookLocalDatabase.edit();
        spEditor.clear();
    }
}
