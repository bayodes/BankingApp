package com.revature.dao;

import com.revature.models.User;
import com.revature.utils.ConnectionSingleton;
import java.sql.*;

public class UserDaoJDBC implements IUserDao {

    private ConnectionSingleton cs = ConnectionSingleton.getConnectionSingleton();

    @Override
    public User createUser(User u) {
        Connection c = cs.getConnection();

        try {
            String sql = "insert into users (first_name, last_name, email, password, user_type) values " +
                    "('" + u.getFirstName() + "','" + u.getLastName() + "','" + u.getEmail() +"','" +
                    u.getPassword() + "','" + u.getUserType() + "')";

            Statement s = c.createStatement();
            s.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // set the user to be logged in
        u = readUserByEmail(u.getEmail());
        return u;
    }

    @Override
    public User readUserByEmail(String email) {
        Connection c = cs.getConnection();

        try {
            String sql = "select * from users where email = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            User loggedIn = null;
            while(rs.next()){
                loggedIn = new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6));
            }
            return loggedIn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User updateUser(String firstName, String lastName, String password, User u) {
        Connection c = cs.getConnection();

        try {
            String sql = "update users " +
                    "set first_name = ?, " +
                    "last_name = ?, " +
                    "password = ? " +
                    "where email = ?";

            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, password);
            ps.setString(4, u.getEmail());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public void deleteUser(User u) {
        Connection c = cs.getConnection();

        try {
            String sql = "delete from users where user_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserID());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEmpty() {
        Connection c = cs.getConnection();

        try {
            String sql = "select count(*) from users";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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

        try {
            String sql = "select count(user_id) from users where user_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserID());

            ResultSet rs = ps.executeQuery();

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