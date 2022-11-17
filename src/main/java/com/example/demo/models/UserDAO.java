package com.example.demo.models;

import java.sql.*;

public class UserDAO {
    private static final UserDAO instance = new UserDAO();
    private static String url = "jdbc:sqlite:D:\\Java_Learn\\Lessons\\Lesson_131122_Receipts\\demo\\database.db";

    private UserDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static UserDAO getInstance() {
        return instance;
    }

    //Авторизация
    public boolean login(String email, String password) {

        try(Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM User WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            if(statement.execute()) {
                ResultSet set = statement.getResultSet();
                return set.next();
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean emailExists(String email) {
        try(Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM User WHERE email= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            if(statement.execute()) {
                ResultSet set = statement.getResultSet();
                return set.next();
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //Регистрация
    public boolean register(User user) {
        if(!emailExists(user.getEmail())) {
            try(Connection connection = DriverManager.getConnection(url)) {
                String query = "INSERT INTO User(email, password) VALUES(?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.executeUpdate();
                return true;
            }
            catch(SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public User getUserById(int id) {
        try(Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM User WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(id));

            if(statement.execute()) {
                ResultSet set = statement.getResultSet();
                if(set.next()) {
                    String email = set.getString("email");
                    String password = set.getString("password");
                    return new User(email, password);
                }
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        try(Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM User WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            if(statement.execute()) {
                ResultSet set = statement.getResultSet();
                if(set.next()) {
                    String password = set.getString("password");
                    return new User(email, password);
                }
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
