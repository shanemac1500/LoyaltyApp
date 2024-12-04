import java.sql.*; // For database operations
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest; // To access HTTP request data
import javax.servlet.http.HttpSession; // To manage sessions
import org.apache.struts2.interceptor.ServletRequestAware; // Interface for Struts2 to inject HTTP request

public class BidAction implements ServletRequestAware {
    // Fields for bid details
    private int itemId; // ID of the item being bid on
    private int userId; // ID of the logged-in user
    private double bidAmount; // Amount of the bid
    private List<String> bids; // List to store bids for display
    private HttpServletRequest request; // To access session data

    // Setters and Getters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getBidAmount() { return bidAmount; }
    public void setBidAmount(double bidAmount) { this.bidAmount = bidAmount; }

    public List<String> getBids() { return bids; }
    public void setBids(List<String> bids) { this.bids = bids; }

    // Inject the HttpServletRequest
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    // 1. Make a Bid
    public String placeBid() {
        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return "LOGIN_REQUIRED"; // Redirect to login if not logged in
        }

        // Get the logged-in user's ID from the session
        userId = (int) session.getAttribute("userId");

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to insert a new bid
            String sql = "INSERT INTO bids (item_id, user_id, bid_amount) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId); // Set item ID
            statement.setInt(2, userId); // Set user ID
            statement.setDouble(3, bidAmount); // Set bid amount
            int rowsInserted = statement.executeUpdate(); // Execute the query

            return rowsInserted > 0 ? "SUCCESS" : "ERROR"; // Return based on query success
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR"; // Return error if something goes wrong
        }
    }

    // 2. View My Bids
    public String viewMyBids() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return "LOGIN_REQUIRED"; // Redirect to login if not logged in
        }

        // Get the logged-in user's ID
        userId = (int) session.getAttribute("userId");
        bids = new ArrayList<>(); // Initialize the list for storing bids

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to fetch bids made by the logged-in user
            String sql = "SELECT i.name, b.bid_amount FROM bids b INNER JOIN items i ON b.item_id = i.id WHERE b.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Format and add each bid to the list
                String itemName = resultSet.getString("name");
                double bidAmount = resultSet.getDouble("bid_amount");
                bids.add("Item: " + itemName + ", Bid: $" + bidAmount);
            }
            return "SUCCESS"; // Return success if data is retrieved
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // 3. View All Bids on an Item
    public String viewBidsForItem() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        bids = new ArrayList<>(); // Initialize the list for storing bids

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to fetch all bids on a specific item
            String sql = "SELECT u.username, b.bid_amount FROM bids b INNER JOIN users u ON b.user_id = u.id WHERE b.item_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Format and add each bid to the list
                String bidder = resultSet.getString("username");
                double bidAmount = resultSet.getDouble("bid_amount");
                bids.add("Bidder: " + bidder + ", Bid: $" + bidAmount);
            }
            return "SUCCESS"; // Return success if data is retrieved
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}

