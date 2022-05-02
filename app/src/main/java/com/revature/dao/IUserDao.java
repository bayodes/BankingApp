package com.revature.dao;

import com.revature.models.User;
import java.util.List;

public interface IUserDao {

    // Create - Registering a new user
    public User createUser(User u);

    // Read
    //public List<User> readAllUsers();

    // Read - Logging In
    public User readUserByEmail(String email);

    // Update - Updating user information
    public User updateUser(User u);

    // Delete - Deleting a user from the database
    public boolean deleteUser(User u);

}
