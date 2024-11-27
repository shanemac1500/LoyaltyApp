import java.sql.*; // For database connections and SQL queries
import javax.servlet.http.HttpServletRequest; // To access HTTP request information
import javax.servlet.http.HttpSession; // To manage user sessions

public class UserAction {
    // Fields for user information
    private String username; // Username for the user
    private String password; // Password for the user
    private String email; // Email for the user
    private String targetUsername; // Target username for viewing other profiles
    private String targetEmail; // Target email for viewing other profiles
    private HttpServletRequest request; // To access HTTP request data

    // Setters and Getters for the fields
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

    // Setter for HTTP request, allowing access to session information
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    // Method for user registration
    public String register() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db"; // Database connection URL
        String dbUser = "root"; // Database username
        String dbPassword = "rootroot1"; // Database password

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to insert a new user into the database
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username); // Set username
            statement.setString(2, password); // Set password
            statement.setString(3, email); // Set email
            int rowsInserted = statement.executeUpdate(); // Execute the query
            return rowsInserted > 0 ? "SUCCESS" : "ERROR"; // Return result based on insertion status
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL errors
            return "ERROR"; // Return error if the operation fails
        }
    }

    // Method for user login
    public String login() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to validate user credentials
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery(); // Execute query and get results

            if (resultSet.next()) {
                // If the user exists, create a session and store user details
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("email", resultSet.getString("email"));
                return "SUCCESS"; // Login successful
            } else {
                return "ERROR"; // Login failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // Method for user logoff
    public String logoff() {
        // Invalidate the session to log off the user
        HttpSession session = request.getSession(false); // Get existing session if available
        if (session != null) {
            session.invalidate(); // Destroy the session
        }
        return "SUCCESS"; // Logoff successful
    }

    // Method to view the logged-in user's profile
    public String viewProfile() {
        // Retrieve user details from the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            username = (String) session.getAttribute("username");
            email = (String) session.getAttribute("email");
            return "SUCCESS"; // Profile view successful
        }
        return "ERROR"; // No session found
    }

    // Method to view another user's profile
    public String viewOtherProfile() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to retrieve another user's details
            String sql = "SELECT username, email FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, targetUsername); // Set the target username
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Set the target user details
                targetUsername = resultSet.getString("username");
                targetEmail = resultSet.getString("email");
                return "SUCCESS"; // Profile retrieved successfully
            } else {
                return "ERROR"; // User not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // Method to view all users
    public String viewAllUsers() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to fetch all users
            String sql = "SELECT username, email FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Print all users to the console
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username") + " - " + resultSet.getString("email"));
            }
            return "SUCCESS"; // Successfully fetched all users
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR"; // Error fetching users
        }
    }
}

