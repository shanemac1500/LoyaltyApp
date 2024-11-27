
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>Login</title>
  </head>
  <body>
    <h1>User Login</h1>
    <s:form action="login">
      <s:textfield name="username" label="Username" />
      <s:password name="password" label="Password" />
      <s:submit value="Login" />
    </s:form>
    <p><a href="index.jsp">Back to Home</a></p>
  </body>
</html>
