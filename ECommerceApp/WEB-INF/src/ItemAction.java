
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

public class ItemAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;

    // Fields for item details
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private String message;
    private Map<String, Object> session;
    private List<Map<String, Object>> items; // List to hold items for the JSP

    // Getters and Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getMessage() {
        return message;
    }

    public List<Map<String, Object>> getItems() {
        return items;
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

    // Add an item
    public String addItem() {
        try (Connection connection = getConnection()) {
            // Retrieve user ID from the session
            Integer userId = (Integer) session.get("loggedInUserId");
            if (userId == null) {
                message = "DEBUG: User not logged in.";
                System.out.println(message);
                return "ERROR";
            }

            System.out.println("DEBUG: User ID retrieved: " + userId);

            // Insert item into the database
            String sql = "INSERT INTO items (user_id, name, description, price) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, itemName);
            preparedStatement.setString(3, itemDescription);
            preparedStatement.setDouble(4, itemPrice);

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (rowsInserted > 0) {
                message = "DEBUG: Item added successfully!";
                System.out.println(message);
                return "SUCCESS";
            } else {
                message = "DEBUG: Failed to add item to the database.";
                System.out.println(message);
                return "ERROR";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "DEBUG: Database error occurred: " + e.getMessage();
            System.out.println(message);
            return "ERROR";
        }
    } 

 // View all items 
    public String viewItems() {
        items = new ArrayList<>(); // Ensure the list is initialized

        try (Connection connection = getConnection()) {
            String sql = "SELECT name, description, price FROM items";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", resultSet.getString("name"));
                item.put("description", resultSet.getString("description"));
                item.put("price", resultSet.getDouble("price"));
                items.add(item); // Add item to the list
            }

            System.out.println("DEBUG: Number of items: " + items.size());

            for (Map<String, Object> item : items) {
                System.out.println("DEBUG: Item Details - Name: " + item.get("name") + ", Price: $" + item.get("price"));
            }

            statement.close();
            return items.size() > 0 ? "SUCCESS" : "ERROR"; // Return ERROR if no items
        } catch (Exception e) {
            e.printStackTrace();
            message = "Database error occurred.";
            return "ERROR";
        }
    }
    }
