package com.styshak.dao;

import com.styshak.entity.HibernateUtil;
import com.styshak.entity.User;
import java.util.Objects;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserDaoImpl implements UserDao {

    private Session session;
    
    public Session openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
    public void closeSession() {
        session.close();
    }
     
    @Override
    public User getUserFromDB(String login, String password) {
        openSession();
        Query query = session.createQuery("from User where login = :login and password = :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        User user = (User) query.uniqueResult();
        isUserInRole("admin", user);
        closeSession();
        return user;
    }

    @Override
    public boolean isUserInRole(String role, User user) {
        Long groupId = DaoFactory.getInstance().getGroupDao().getGroupIdByName(role);
        return Objects.equals(groupId, user.getGroups().iterator().next().getId());
    }
}
