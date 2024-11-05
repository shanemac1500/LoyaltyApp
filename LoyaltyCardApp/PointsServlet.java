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
import javax.servlet.http.HttpSession;

@WebServlet("/PointsServlet")
public class PointsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.html");
            return;
        }

        String action = request.getParameter("action");
        int points = Integer.parseInt(request.getParameter("points"));

        String jdbcURL = "jdbc:mysql://localhost:3306/loyaltyApp?serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql;
            PreparedStatement statement;
            int currentPoints = (int) session.getAttribute("points");

            if ("add".equals(action)) {
                sql = "UPDATE users SET points = points + ? WHERE username = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, points);
                statement.setString(2, username);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    currentPoints += points;
                    session.setAttribute("points", currentPoints);
                    out.println("<h3>Successfully added " + points + " points! You now have " + currentPoints + " points, " + username + "</h3>");
                }
            } else if ("spend".equals(action)) {
                if (points <= currentPoints) {
                    sql = "UPDATE users SET points = points - ? WHERE username = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, points);
                    statement.setString(2, username);
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        currentPoints -= points;
                        session.setAttribute("points", currentPoints);
                        out.println("<h3>Successfully spent " + points + " points! You now have " + currentPoints + " points, " + username + "</h3>");
                    }
                } else {
                    out.println("<h3>Not enough points to spend. You currently have " + currentPoints + " points.</h3>");
                }
            } else {
                out.println("<h3>Invalid action.</h3>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error connecting to the database. Please try again later.</h3>");
        }
    }
}
