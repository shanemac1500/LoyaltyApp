
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;

    // Fields for bidding details
    private int itemId; // Item ID
    private double bidAmount; // Bid amount
    private String message; // Feedback message
    private List<Map<String, Object>> bids; // List to hold bid details
    private Map<String, Object> session; // Session map

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getMessage() {
        return message;
    }

    public List<Map<String, Object>> getBids() {
        return bids;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setSession(Map session) {
        this.session = (Map<String, Object>) session;
    }

    // Database connection helper
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db?serverTimezone=UTC", "root", "rootroot1");
    }

    public String placeBid() {
        try (Connection connection = getConnection()) {
            // Fetch items for the dropdown menu
            String fetchItemsSql = "SELECT id, name FROM items";
            PreparedStatement fetchItemsStatement = connection.prepareStatement(fetchItemsSql);
            ResultSet itemsResultSet = fetchItemsStatement.executeQuery();

            List<Map<String, Object>> itemsList = new ArrayList<>();
            while (itemsResultSet.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", itemsResultSet.getInt("id"));
                item.put("name", itemsResultSet.getString("name"));
                itemsList.add(item);
            }
            session.put("items", itemsList); // Store items in session
            fetchItemsStatement.close();

            // If bidAmount is not set, return the form with items
            if (bidAmount <= 0 || itemId == 0) {
                message = "DEBUG: Preparing bid form.";
                System.out.println(message);
                return "INPUT"; // Return INPUT to show the form
            }

            // Handle bid submission
            Integer userId = (Integer) session.get("loggedInUserId");
            if (userId == null) {
                message = "DEBUG: User not logged in.";
                System.out.println(message);
                return "ERROR";
            }

            String insertBidSql = "INSERT INTO bids (item_id, user_id, bid_amount) VALUES (?, ?, ?)";
            PreparedStatement insertBidStatement = connection.prepareStatement(insertBidSql);
            insertBidStatement.setInt(1, itemId);
            insertBidStatement.setInt(2, userId);
            insertBidStatement.setDouble(3, bidAmount);

            int rowsInserted = insertBidStatement.executeUpdate();
            insertBidStatement.close();

            if (rowsInserted > 0) {
                message = "DEBUG: Bid placed successfully!";
                System.out.println(message);
                return "SUCCESS";
            } else {
                message = "DEBUG: Failed to place bid.";
                System.out.println(message);
                return "ERROR";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "DEBUG: Database error occurred: " + e.getMessage();
            return "ERROR";
        }
    }
    
    public String viewBidsForItem() {
        try (Connection connection = getConnection()) {
            Integer userId = (Integer) session.get("loggedInUserId");
            if (userId == null) {
                message = "DEBUG: User not logged in.";
                System.out.println(message);
                return "ERROR";
            }

            // Query to get bids on items posted by the logged-in user
            String sql = "SELECT b.item_id, b.user_id, b.bid_amount, i.name AS item_name " +
                         "FROM bids b " +
                         "JOIN items i ON b.item_id = i.id " +
                         "WHERE i.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            bids = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> bid = new HashMap<>();
                bid.put("itemId", resultSet.getInt("item_id"));
                bid.put("itemName", resultSet.getString("item_name"));
                bid.put("userId", resultSet.getInt("user_id"));
                bid.put("bidAmount", resultSet.getDouble("bid_amount"));
                bids.add(bid);
            }

            statement.close();

            if (bids.isEmpty()) {
                message = "DEBUG: No bids found for your items.";
                System.out.println(message);
            } else {
                System.out.println("DEBUG: Bids found for your items: " + bids.size());
            }

            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            message = "DEBUG: Database error occurred.";
            return "ERROR";
        }
    }
    
    public String viewMyBids() {
        try (Connection connection = getConnection()) {
            Integer userId = (Integer) session.get("loggedInUserId");
            if (userId == null) {
                message = "DEBUG: User not logged in.";
                System.out.println(message);
                return "ERROR";
            }

            // Updated SQL to join items and fetch item name
            String sql = "SELECT b.item_id, b.bid_amount, i.name AS itemName " +
                         "FROM bids b " +
                         "JOIN items i ON b.item_id = i.id " +
                         "WHERE b.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            bids = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> bid = new HashMap<>();
                bid.put("itemId", resultSet.getInt("item_id"));
                bid.put("bidAmount", resultSet.getDouble("bid_amount"));
                bid.put("itemName", resultSet.getString("itemName")); // Fetch item name
                bids.add(bid);
            }

            statement.close();
            System.out.println("DEBUG: Number of bids: " + bids.size());
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            message = "DEBUG: Database error occurred.";
            return "ERROR";
        }
    }
    }
