package com.uta.bookkart;

public class CheckOutKart {
    int item;
    String k_name, k_category, k_author, k_isbn;
    float k_price;

    public CheckOutKart(String k_name, String k_category, String k_isbn, String k_author, float k_price) {
        this.k_name = k_name;
        this.k_author = k_author;
        this.k_isbn = k_isbn;
        this.k_category = k_category;
        this.k_price = k_price;
        //this.b_quantity = b_quantity;
    }

}
