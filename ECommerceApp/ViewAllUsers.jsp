<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>All Users</title>
  </head>
  <body>
    <!-- Display logged-in user -->
    <s:if test="#session.username != null">
      <p>Logged in as: <s:property value="#session.username" /></p>
    </s:if>

    <h1>All Users</h1>

    <s:iterator value="#session.allUsersMap">
      <div>
        <p>Username: <s:property value="key" /></p>
        <p>Email: <s:property value="value" /></p>
        <!-- Form to view the profile -->
        <s:form action="viewOtherProfile">
          <s:hidden name="targetUsername" value="%{key}" />
          <s:submit value="View Profile" />
        </s:form>
        <hr />
      </div>
    </s:iterator>

    <br />
    <a href="home.jsp">Back to Home</a>
  </body>
</html>
