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

    public User registerUser(String firstName, String lastName, String email, String password, String position) {
        User register = new User(0, firstName, lastName, email, password, position);

        User u = uDao.createUser(register);

        LoggingUtil.logger.info("Registered user " + u.getEmail());
        return u;
    }

    public User loginUser(String email, String password) {

        boolean isEmpty = uDao.isEmpty();
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

    public User updateUser(String firstName, String lastName, String password, User u) {
        boolean isEmpty = uDao.isEmpty();
        boolean isFound = uDao.isFound(u);
        User user = null;

        if (isEmpty == true) {
            LoggingUtil.logger.error("There are no users in the database. Register a user before updating their information");
        } else if (isFound == false) {
            LoggingUtil.logger.error(u.getEmail() + " does not exist in the database");
        } else {
            user = uDao.updateUser(firstName, lastName, password, u);
            user.setFirstName(firstName);
            user.setFirstName(lastName);
            user.setFirstName(password);
            LoggingUtil.logger.info(u.getEmail() + " has updated their information");
        }
        return user;
    }

    public void deleteUser(User user) {
        String email = user.getEmail();
        boolean isEmpty = uDao.isEmpty();
        boolean isFound = uDao.isFound(user);

        if (isEmpty == true) {
            LoggingUtil.logger.error("There are no users in the database. Can't delete a user that doesn't exist");
        } else if (isFound == false) {
            LoggingUtil.logger.error("User with id " + user.getUserID() + " does not exist in the database");
        } else {
            uDao.deleteUser(user);
            LoggingUtil.logger.info(email + " has been deleted from the database");
        }
    }

    public void logOut(String email) {
        LoggingUtil.logger.info(email + " has logged out");
    }
}
