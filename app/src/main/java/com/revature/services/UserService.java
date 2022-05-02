package com.revature.services;

import com.revature.dao.IUserDao;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;

public class UserService {

    private IUserDao uDao;

    public UserService() {}

    public UserService(IUserDao uDao) {
        this.uDao = uDao;
    }

    public User registerUser(String firstName, String lastName, String email, String password) {
        User register = new User(0, firstName, lastName, email, password);
        User u = uDao.createUser(register);

        u.getListOfUsers().add(u);
        LoggingUtil.logger.info("A new user was registered");
        return u;
    }

    public User loginUser(String email, String password) {

        User u = uDao.readUserByEmail(email);

        if (u != null && password.equals(u.getPassword())) {
            LoggingUtil.logger.info("User " + u.getEmail() + " logged in successfully");
            return u;
        } else {
            LoggingUtil.logger.error("Login attempt failed");
            return u;
        }
    }

    public User updateUser(User u) {
        User user = uDao.updateUser(u);

        if (user == null) {
            LoggingUtil.logger.error(u.getEmail() + " does not exist in the database");
        } else {
            LoggingUtil.logger.info("User with id: " + u.getUserID() + " has updated their information");
        }
        return user;
    }

    public boolean deleteUser(User u) {

        if (uDao.deleteUser(u) == false) {
            LoggingUtil.logger.error("User does not exist in the database");
            return false;
        } else {
            LoggingUtil.logger.info("User with id: " + u.getUserID() + " has been deleted from the database");
            return true;
        }
    }
}
