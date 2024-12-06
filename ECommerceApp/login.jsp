<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <h1>Login to Your Account</h1>
        <s:form action="login">
            <s:textfield name="username" label="Username:" />
            <s:password name="password" label="Password:" />
            <s:submit value="Login" />
        </s:form>
    </body>
</html>
