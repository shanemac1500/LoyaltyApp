<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>My Profile</title>
  </head>
  <body>
    <h1>My Profile</h1>
    <p>Logged in as: <s:property value="#session.username" /></p>
    <p>Email: <s:property value="#session.email" /></p>
  </body>
</html>
