<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Bids on Your Items</title>
  </head>
  <body>
    <!-- Display logged-in user -->
    <s:if test="#session.username != null">
      <p>Logged in as: <s:property value="#session.username" /></p>
    </s:if>

    <h1>Bids on Your Items</h1>

    <s:if test="bids != null && bids.size() > 0">
      <s:iterator value="bids">
        <div>
          <p>Item Name: <s:property value="itemName" /></p>
          <p>Bidder User ID: <s:property value="userId" /></p>
          <p>Bid Amount: <s:property value="bidAmount" /></p>
        </div>
        <hr />
      </s:iterator>
    </s:if>

    <s:else>
      <p>No bids have been placed on your items yet.</p>
    </s:else>

    <br />
    <a href="home.jsp">Back to Home</a>
  </body>
</html>
