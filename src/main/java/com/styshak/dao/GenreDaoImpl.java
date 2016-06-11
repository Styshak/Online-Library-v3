package com.styshak.dao;

import com.styshak.entity.Genre;
import com.styshak.entity.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class GenreDaoImpl implements GenreDao{
    
    private Session session;
    
    public Session openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    public void closeSession() {
        session.close();
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> list = null;
        openSession();
        list = session.createCriteria(Genre.class).addOrder(Order.asc("name")).list();
        closeSession();
        return list;
    }
}
