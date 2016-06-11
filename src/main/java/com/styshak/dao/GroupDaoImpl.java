package com.styshak.dao;

import com.styshak.entity.Group;
import com.styshak.entity.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

public class GroupDaoImpl implements GroupDao {

    private Session session;
    
    public Session openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    public void closeSession() {
        session.close();
    }
    
    @Override
    public Long getGroupIdByName(String groupName) {
        openSession();
        Query query = session.createQuery("from Group where name = :name");
        query.setParameter("name", groupName);
        Group group = (Group) query.uniqueResult();
        closeSession();
        return group.getId();
    }
}
