package com.styshak.dao;

import com.styshak.entity.Book;
import com.styshak.entity.HibernateUtil;
import com.styshak.entity.User;
import com.styshak.entity.Vote;
import org.hibernate.Session;

public class VoteDaoImpl implements VoteDao {

    private Session session;
    
    public Session openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    public void closeSession() {
        session.close();
    }
    
    @Override
    public void vote(Book book, User user) {
        openSession();
        Vote vote = new Vote();
        vote.setBook(book);
        vote.setUser(user);
        vote.setValue(book.getRating());
        session.save(vote);
        closeSession();
    }
}
