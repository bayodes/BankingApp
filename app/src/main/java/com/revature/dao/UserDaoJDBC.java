package com.revature.dao;

import com.revature.models.User;
import com.revature.utils.ConnectionSingleton;
import java.sql.*;
import java.util.List;

public class UserDaoJDBC implements IUserDao {

    private ConnectionSingleton cs = ConnectionSingleton.getConnectionSingleton();

    /**
     * Registering a new user
     * @param u the user object used to place into the database
     */
    @Override
    public User createUser(User u) {
        //To create a user, we must get our connection, create a statement, and execute said statement
        Connection c = cs.getConnection();

        String sql;

        try {
            sql = "insert into users (first_name, last_name, email, password) values " +
                    "('" + u.getFirstName() +"','" + u.getLastName() + "','" + u.getEmail() +"','" + u.getPassword() + "')";

            Statement s = c.createStatement();
            s.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // set the user to be logged in
        User user = readUserByEmail(u.getEmail());
        return user;
    }

    /**
     * Logging In
     * @param email the user's email, which acts as the unique identifier
     * @return the database instance of the user's email
     */
    @Override
    public User readUserByEmail(String email) {

        Connection c = cs.getConnection();
        String sql;
        PreparedStatement ps;
        ResultSet rs;

        try {
            sql = "select * from users where email = ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();

            User loggedIn = null;
            while(rs.next()){
                loggedIn = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
            return loggedIn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param u the user object that will be updated
     * @return the updated user object
     */
    @Override
    public User updateUser(User u) {
        Connection c = cs.getConnection();
        PreparedStatement ps;
        String sql;

        try {
            sql = "update users " +
                    "set first_name = ?, " +
                    "last_name = ?, " +
                    "email = ?, " +
                    "password = ? " +
                    "where user_id = ?";

            ps = c.prepareStatement(sql);

            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setInt(5, u.getUserID());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * @param u the user object that will be deleted
     */
    @Override
    public boolean deleteUser(User u) {
        Connection c = cs.getConnection();
        String sql;
        PreparedStatement ps;
        ResultSet rs;

        try {
            sql = "delete from users where user_id = ?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserID());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public boolean isEmpty(String email) {
        Connection c = cs.getConnection();
        String sql;
        PreparedStatement ps;
        ResultSet rs;

        try {
            sql = "select count(*) from users";
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("count") == 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isFound(User u) {
        Connection c = cs.getConnection();
        String sql;
        PreparedStatement ps;
        ResultSet rs;

        try {
            sql = "select count(user_id) from users where user_id = ?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserID());

            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("count") == 0) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}