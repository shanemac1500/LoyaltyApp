
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>My Profile</title>
  </head>
  <body>
    <h1>My Profile</h1>
    <p>Username: <s:property value="username" /></p>
    <p>Email: <s:property value="email" /></p>
    <a href="logoff.action">Logoff</a> |
    <a href="viewItems.action">View Items</a>
  </body>
</html>
