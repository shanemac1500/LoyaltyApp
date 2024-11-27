

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello Lotto Player</title>
    </head>
    <body>
    Play the lotto!
       <s:form action="playLotto" >
            <s:textfield name="num1" label="Enter your first number " />
			 <s:textfield name="num2" label="Enter your second number" />
			    <s:textfield name="num3" label="Enter your third number" /> 
				<s:textfield name="num4" label="Enter your fourth number" />
            <s:submit />
        </s:form>
		
    </body>
</html>










