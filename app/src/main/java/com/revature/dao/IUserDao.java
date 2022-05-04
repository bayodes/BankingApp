package com.revature.dao;

import com.revature.models.User;

public interface IUserDao {

    public User createUser(User u);

    public User readUserByEmail(String email);

    public User updateUser(String firstName, String lastName, String password, User u);

    public void deleteUser(User u);

    public boolean isEmpty();

    public boolean isFound(User u);

}
