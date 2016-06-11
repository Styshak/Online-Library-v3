package com.styshak.dao;

import com.styshak.entity.Book;
import com.styshak.entity.User;

public interface VoteDao {

    public void vote(Book book, User user);
}
