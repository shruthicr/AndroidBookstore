package com.uta.bookkart;

public class book {

    String b_name, b_author, b_isbn, b_category;
    float b_price;
    int b_quantity;

    public book(String b_name) {
        this.b_name = b_name;
        this.b_author = "";
        this.b_isbn = "";
        this.b_category = "";
        this.b_quantity = -1;
        this.b_price = (float) -1.0;
    }

    public book(String b_name, String b_category, String b_isbn, String b_author, float b_price, int b_quantity) {
        this.b_name = b_name;
        this.b_author = b_author;
        this.b_isbn = b_isbn;
        this.b_category = b_category;
        this.b_price = b_price;
        this.b_quantity = b_quantity;
    }

}
