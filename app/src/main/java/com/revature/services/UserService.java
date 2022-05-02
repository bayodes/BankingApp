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

        LoggingUtil.logger.info("A new user was registered");
        return u;
    }

    public User loginUser(String email, String password) {

        boolean isEmpty = uDao.isEmpty(email);
        User u = uDao.readUserByEmail(email);

        if (u != null && password.equals(u.getPassword())) {
            LoggingUtil.logger.info("User " + u.getEmail() + " logged in successfully");
        } else if (isEmpty == true) {
            LoggingUtil.logger.error("There are no users in the database. Register a user");
        } else if (!password.equals(u.getPassword()) || u == null) {
            LoggingUtil.logger.error("Login attempt failed");
        } else if (u == null) {
            LoggingUtil.logger.error(email + " does not exist in the database");
        }

        return u;
    }

    public User updateUser(User u) {
        boolean isEmpty = uDao.isEmpty(u.getEmail());
        boolean isFound = uDao.isFound(u);
        User user = uDao.updateUser(u);

        if (isEmpty == true) {
            LoggingUtil.logger.error("There are no users in the database. Register a user before updating their information");
        } else if (isFound == false) {
            LoggingUtil.logger.error(u.getEmail() + " does not exist in the database");
        } else {
            LoggingUtil.logger.info(u.getUserID() + " has updated their information");
        }
        return user;
    }

    public boolean deleteUser(User u) {
        boolean isEmpty = uDao.isEmpty(u.getEmail());
        boolean isFound = uDao.isFound(u);

        if (isEmpty == true) {
            LoggingUtil.logger.error("There are no users in the database. Can't delete a user that doesn't exist");
            return false;
        } else if (isFound == false) {
            LoggingUtil.logger.error(u.getEmail() + " does not exist in the database");
            return false;
        } else {
            uDao.deleteUser(u);
            LoggingUtil.logger.info(u.getEmail() + " has been deleted from the database");
            return true;
        }
    }
}
