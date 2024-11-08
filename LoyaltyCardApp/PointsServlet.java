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

        // Retrieve and validate addPoints and spendPoints parameters
        String addPointsParam = request.getParameter("addPoints");
        String spendPointsParam = request.getParameter("spendPoints");

        int addPoints = 0;
        int spendPoints = 0;

        // Only parse if the parameter is non-null and non-empty
        try {
            if (addPointsParam != null && !addPointsParam.isEmpty()) {
                addPoints = Integer.parseInt(addPointsParam);
            }
            if (spendPointsParam != null && !spendPointsParam.isEmpty()) {
                spendPoints = Integer.parseInt(spendPointsParam);
            }
        } catch (NumberFormatException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h3>Invalid point value entered. Please enter a valid number.</h3>");
            return;
        }

        String jdbcURL = "jdbc:mysql://localhost:3306/loyalty_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "rootroot1";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql;
            PreparedStatement statement;
            int currentPoints = (int) session.getAttribute("points");

            // Add points logic
            if (addPoints > 0) {
                sql = "UPDATE users SET points = points + ? WHERE username = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, addPoints);
                statement.setString(2, username);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    currentPoints += addPoints;
                    session.setAttribute("points", currentPoints);
                    out.println("<h3>Successfully added " + addPoints + " points! You now have " + currentPoints + " points, " + username + "</h3>");
                }
            }

            // Spend points logic
            if (spendPoints > 0) {
                if (spendPoints <= currentPoints) {
                    sql = "UPDATE users SET points = points - ? WHERE username = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, spendPoints);
                    statement.setString(2, username);
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        currentPoints -= spendPoints;
                        session.setAttribute("points", currentPoints);
                        out.println("<h3>Successfully spent " + spendPoints + " points! You now have " + currentPoints + " points, " + username + "</h3>");
                    }
                } else {
                    out.println("<h3>Not enough points to spend. You currently have " + currentPoints + " points.</h3>");
                }
            }

            if (addPoints == 0 && spendPoints == 0) {
                out.println("<h3>Please enter a valid number in either Add Points or Spend Points.</h3>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error connecting to the database. Please try again later.</h3>");
        }
    }
}
