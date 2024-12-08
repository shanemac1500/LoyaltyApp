
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>All Users</title>
  </head>
  <body>
    <h1>All Users</h1>
    <s:iterator value="#session.allUsersMap">
      <p>Username: <s:property value="key" /></p>
      <p>Email: <s:property value="value" /></p>
    </s:iterator>
  </body>
</html>
