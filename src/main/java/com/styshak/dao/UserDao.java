
package com.styshak.dao;

import com.styshak.entity.User;


public interface UserDao {
    
    public User getUserFromDB(String password, String login);
    
    public boolean isUserInRole(String role, User user);
}
