<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>My Bids</title>
  </head>
  <body>
    <!-- Display logged-in user -->
    <s:if test="#session.username != null">
      <p>Logged in as: <s:property value="#session.username" /></p>
    </s:if>

    <h1>My Bids</h1>

    <!-- Iterate through the bids -->
    <s:if test="bids != null && bids.size() > 0">
      <s:iterator value="bids">
        <div>
          <p>Item ID: <s:property value="itemId" /></p>
          <p>Bid Amount: <s:property value="bidAmount" /></p>
        </div>
        <hr />
      </s:iterator>
    </s:if>

    <!-- Display message if no bids are available -->
    <s:else>
      <p>No bids available.</p>
    </s:else>

    <br />
    <a href="home.jsp">Back to Home</a>
  </body>
</html>
