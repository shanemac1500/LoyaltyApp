<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Items for Sale</title>
  </head>
  <body>
    <!-- Display logged-in user -->
    <s:if test="#session.username != null">
      <p>Logged in as: <s:property value="#session.username" /></p>
    </s:if>

    <h1>Items for Sale</h1>

    <s:if test="items != null && items.size() > 0">
      <s:iterator value="items">
        <div>
          <p>Item Name: <s:property value="name" /></p>
          <p>Description: <s:property value="description" /></p>
          <p>Price: <s:property value="price" /></p>
        </div>
        <hr />
      </s:iterator>
    </s:if>

    <s:else>
      <p>No items available for sale.</p>
    </s:else>

    <br />
    <a href="home.jsp">Back to Home</a>
  </body>
</html>
