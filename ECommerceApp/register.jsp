
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>Register</title>
  </head>
  <body>
    <h1>User Registration</h1>
    <s:form action="register">
      <s:textfield name="username" label="Username" />
      <s:password name="password" label="Password" />
      <s:textfield name="email" label="Email" />
      <s:submit value="Register" />
    </s:form>
    <p><a href="index.jsp">Back to Home</a></p>
  </body>
</html>
