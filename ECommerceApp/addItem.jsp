<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Add Item</title>
  </head>
  <body>
    <s:if test="#session.username != null">
      <p>Logged in as: <s:property value="#session.username" /></p>
    </s:if>

    <h1>Add Item for Sale</h1>
    <s:form action="addItem" method="post">
      <s:textfield name="itemName" label="Item Name: " />
      <s:textarea name="itemDescription" label="Description: " />
      <s:textfield name="itemPrice" label="Price: " />
      <s:submit value="Add Item" />
    </s:form>

    <s:if test="message != null">
      <p style="color: red"><s:property value="message" /></p>
    </s:if>

    <br />
    <a href="home.jsp">Back to Home</a>
    <a href="viewItems.action">View Items</a>
  </body>
</html>
