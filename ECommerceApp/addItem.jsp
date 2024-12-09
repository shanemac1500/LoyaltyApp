<%@ taglib uri="/struts-tags" prefix="s" %> <%@ page contentType="text/html"
pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Add Item</title>
  </head>
  <body>
    <h1>Add Item for Sale</h1>
    <s:form action="addItem" method="post">
      <s:textfield name="itemName" label="Item Name: " />
      <s:textarea name="itemDescription" label="Description: " />
      <s:textfield name="itemPrice" label="Price: " />
      <s:submit value="Add Item" />
    </s:form>

    <!-- Display Success or Error Message -->
    <s:if test="message != null">
      <p style="color: red"><s:property value="message" /></p>
    </s:if>

    <br />
    <a href="home.jsp">Back to Home</a>
  </body>
</html>
