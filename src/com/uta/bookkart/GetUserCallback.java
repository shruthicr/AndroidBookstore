package com.uta.bookkart;

import com.uta.bookkart.User;

public interface GetUserCallback {
    public abstract void done(User returnedUser);
}
