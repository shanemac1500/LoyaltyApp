<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Error</title>
  </head>
  <body>
    <h1>Error</h1>
    <p>Something went wrong. Please try again.</p>

    <!-- Display logged-in user -->
    <s:if test="#session.username != null">
      <p>Logged in as: <s:property value="#session.username" /></p>
    </s:if>

    <!-- Display action errors -->
    <s:if test="hasActionErrors()">
      <div style="color: red">
        <s:actionerror />
      </div>
    </s:if>

    <br />
    <a href="home.jsp">Back to Home</a>
  </body>
</html>
