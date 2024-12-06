<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Welcome to E-Commerce App</title>
  </head>
  <body>
    <h1>Welcome to the E-Commerce Platform</h1>
    <p>Please select an action below:</p>

    <s:form action="register">
      <s:submit value="Register" />
    </s:form>

    <s:form action="login">
      <s:submit value="Login" />
    </s:form>
  </body>
</html>
