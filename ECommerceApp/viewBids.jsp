
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>Bids for Item</title>
  </head>
  <body>
    <h1>Bids for Item</h1>
    <ul>
      <s:iterator value="bids" var="bid">
        <li><s:property value="#bid" /></li>
      </s:iterator>
    </ul>
    <p><a href="viewItems.action">Back to Items</a></p>
  </body>
</html>
