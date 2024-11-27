

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lotto Confirmation Page</title>
    </head>
    <body>
        <h1>Well done ! Here are your chosen numbers</h1>
		<s:property value="num1" /> <br/>
		<s:property value="num2" />  <br/>
		<s:property value="num3" />  <br/>
		<s:property value="num4" />  <br/>
		
		Also, here's the fixedMessage variable value that I set in the play method: <s:property value="fixedMessage" />  
    </body>
</html>
