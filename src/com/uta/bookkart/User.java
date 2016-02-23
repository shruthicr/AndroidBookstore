package com.uta.bookkart;

public class User {
    String f_name, l_name, emailid, password, c_num, address;

    public User(String f_name, String l_name, String emailid, String password, String c_num, String address) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.emailid = emailid;
        this.password = password;
        this.c_num = c_num;
        this.address = address;
    }

    public User(String emailid, String password) {
        this.emailid = emailid;
        this.password = password;
        this.f_name = "";
        this.l_name = "";
        this.c_num = "";
        this.address = "";
    }
}
