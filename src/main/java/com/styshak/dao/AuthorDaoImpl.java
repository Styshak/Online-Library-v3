package com.styshak.dao;

import com.styshak.entity.Author;
import com.styshak.entity.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

public class AuthorDaoImpl implements AuthorDao {

    private Session session;
    
    public Session openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    public void closeSession() {
        session.close();
    }
    
    @Override
    public List<Author> getAllAuthors() {
        List<Author> list = null;
        openSession();
        list = session.createCriteria(Author.class).list();
        closeSession();
        return list;
    }
}
