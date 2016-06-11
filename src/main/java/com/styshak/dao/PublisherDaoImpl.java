package com.styshak.dao;

import com.styshak.entity.HibernateUtil;
import com.styshak.entity.Publisher;
import java.util.List;
import org.hibernate.Session;

public class PublisherDaoImpl implements PublisherDao{

    private Session session;
    
    public Session openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    public void closeSession() {
        session.close();
    }
    
    @Override
    public List<Publisher> getAllPublishers() {
        List<Publisher> list = null;
        openSession();
        list = session.createCriteria(Publisher.class).list();
        closeSession();
        return list;
    }
    
}
