<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>Add Item</title>
  </head>
  <body>
    <h1>Add Item for Sale</h1>
    <s:form action="addItem">
      <s:textfield name="itemName" label="Item Name" />
      <s:textfield name="itemDescription" label="Description" />
      <s:textfield name="itemPrice" label="Price" />
      <s:submit value="Add Item" />
    </s:form>
    <p><a href="viewItems.action">View Items</a></p>
  </body>
</html>
