import java.sql.*; // Import for database connections and SQL execution
import java.util.ArrayList;
import java.util.List;

public class ItemAction {
    // Fields for item details
    private String itemName; // Name of the item
    private String itemDescription; // Description of the item
    private double itemPrice; // Price of the item

    // Getters and Setters
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemDescription() { return itemDescription; }
    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }

    public double getItemPrice() { return itemPrice; }
    public void setItemPrice(double itemPrice) { this.itemPrice = itemPrice; }

    // Method to add an item for sale
    public String addItem() {
        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root"; // Database username
        String dbPassword = "rootroot1"; // Database password

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to insert a new item into the items table
            String sql = "INSERT INTO items (name, description, price) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, itemName); // Set item name
            statement.setString(2, itemDescription); // Set item description
            statement.setDouble(3, itemPrice); // Set item price
            // Execute the update and return the result
            return statement.executeUpdate() > 0 ? "SUCCESS" : "ERROR";
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL errors
            return "ERROR"; // Return error if operation fails
        }
    }

    // Method to view all items for sale
    public String viewItems() {
        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // SQL query to retrieve all items from the items table
            String sql = "SELECT * FROM items";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(); // Execute query and get results

            // Iterate through results and print item details (For demonstration purposes)
            while (resultSet.next()) {
                System.out.println(
                    resultSet.getString("name") + " - $" + resultSet.getDouble("price")
                );
            }
            return "SUCCESS"; // Return success if items are fetched
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL errors
            return "ERROR"; // Return error if operation fails
        }
    }
}
