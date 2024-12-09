<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>Home</title>
  </head>
  <body>
    <!-- Display logged-in user -->
    <h1>Welcome, <s:property value="#session.username" /></h1>

    <h2>Navigation</h2>
    <ul>
      <li><a href="viewProfile.action">View My Profile</a></li>
      <li><a href="viewAllUsers.action">View All Users</a></li>
      <li><a href="viewItems.action">View Items for Sale</a></li>
      <li><a href="addItem.action">Add Item for Sale</a></li>
      <li><a href="viewMyBids.action">View My Bids</a></li>
      <li><a href="viewBidsForItem.action">View Bids for an Item</a></li>
      <li><a href="placeBid.action">Place a Bid</a></li>
      <li><a href="logoff.action">Logoff</a></li>
    </ul>
  </body>
</html>
