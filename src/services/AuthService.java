package services;

import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import models.User;

public class AuthService {

    private static final String DB_URL = "jdbc:sqlite:oop.db";

    public static User loginUser(String email, String password) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DB_URL);

            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String passDb = rs.getString("password");

                if (password.equals(passDb)) {
                    LocalDate birthday;
                    Date sqlDate = rs.getDate("birthday");
                    if (sqlDate != null) {
                        birthday = sqlDate.toLocalDate();
                    } else {
                        birthday = LocalDate.of(1970, 1, 1);
                    }
                    User user = new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            birthday,
                            passDb,
                            rs.getString("email"),
                            rs.getString("home_address"),
                            rs.getString("work_address"),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            0
                    );
                    rs.close();
                    pst.close();
                    con.close();

                    return user;
                }
            }
            rs.close();
            pst.close();
            con.close();

            return null;

        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }

    public static boolean registerUser(String userName, String name, String surname, String password, String email, java.sql.Date birthday) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DB_URL);
            Statement st = con.createStatement();

            st.execute("PRAGMA foreign_keys = ON;");
            String insertQuery = "INSERT INTO users(user_name, name, surname, birthday, password, email, home_address, work_address) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, userName);
            pst.setString(2, name);
            pst.setString(3, surname);
            pst.setDate(4, birthday);
            pst.setString(5, password);
            pst.setString(6, email);
            pst.setNull(7, java.sql.Types.VARCHAR);
            pst.setNull(8, java.sql.Types.VARCHAR);
            pst.executeUpdate();

            con.close();
            return true;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteUserById(int userId) {
        try {
            Connection con = DriverManager.getConnection(DB_URL);
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userId);
            int affectedRows = pst.executeUpdate();
            pst.close();
            con.close();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
