import java.sql.*;
import java.util.*;

import org.apache.struts2.interceptor.SessionAware;

public class UserAction implements SessionAware {
    private String username;
    private String password;
    private String email;
    private String targetUsername;
    private String targetEmail;
    private String errorMessage;
    private Map<String, Object> session;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTargetUsername() { return targetUsername; }
    public void setTargetUsername(String targetUsername) { this.targetUsername = targetUsername; }

    public String getTargetEmail() { return targetEmail; }
    public void setTargetEmail(String targetEmail) { this.targetEmail = targetEmail; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    @SuppressWarnings("unchecked")
    @Override
    public void setSession(Map session) {
        this.session = (Map<String, Object>)session;
    }

    // Database connection helper
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db?serverTimezone=UTC", "root", "rootroot1");
    }

    // **Register a New User**
    public String register() {
        try (Connection connection = getConnection()) {
            PreparedStatement createUser = connection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
            createUser.setString(1, username);
            createUser.setString(2, password);
            createUser.setString(3, email);
            int rowsInserted = createUser.executeUpdate();
            createUser.close();
            return rowsInserted > 0 ? "SUCCESS" : "ERROR";
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "Database error during registration.";
            return "ERROR";
        }
    }

    // **Login User**
    public String login() {
        try (Connection connection = getConnection()) {
            PreparedStatement findUser = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            findUser.setString(1, username);
            findUser.setString(2, password);
            ResultSet resultSet = findUser.executeQuery();

            if (resultSet.next()) {
                // Store username and email in session
                session.put("username", username);
                session.put("email", resultSet.getString("email"));
                findUser.close();
                return "SUCCESS";
            } else {
                errorMessage = "Invalid credentials.";
                return "ERROR";
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "Database error during login.";
            return "ERROR";
        }
    }

    // **Logoff User**
    public String logoff() {
        session.clear(); // Clear session data
        return "SUCCESS";
    }

    // **View Logged-In User's Profile**
    public String viewProfile() {
        if (session.get("username") == null) {
            errorMessage = "No user logged in.";
            return "ERROR";
        }
        username = (String) session.get("username");
        email = (String) session.get("email");
        return "SUCCESS";
    }

    // **View All Users**
    public String viewAllUsers() {
        Map<String, String> allUsersMap = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement getUsers = connection.createStatement();
            ResultSet resultSet = getUsers.executeQuery("SELECT username, email FROM users");

            while (resultSet.next()) {
                allUsersMap.put(resultSet.getString("username"), resultSet.getString("email"));
            }
            session.put("allUsersMap", allUsersMap); // Store in session
            getUsers.close();
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "Database error during fetching all users.";
            return "ERROR";
        }
    }
}
