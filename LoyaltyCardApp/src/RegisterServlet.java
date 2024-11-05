package com.loyaltyapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve user inputs from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            out.println("<h3>Passwords do not match. Please try again.</h3>");
            return;
        }

        // Database connection setup
        String jdbcURL = "jdbc:mysql://localhost:3306/loyaltyApp?serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "rootroot1";  

        // Insert user data into the database
        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "INSERT INTO users (username, password, points) VALUES (?, ?, 100)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h3>Registration successful! You have 100 loyalty points.</h3>");
            } else {
                out.println("<h3>Registration failed. Please try again later.</h3>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error connecting to the database.</h3>");
        }
    }
}
