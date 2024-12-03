
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <title>Items for Sale</title>
    </head>
    <body>
        <h1>Items for Sale</h1>
        <s:iterator value="items" var="item">
            <p>
                <b>Item:</b> <s:property value="#item.name" /><br>
                <b>Description:</b> <s:property value="#item.description" /><br>
                <b>Price:</b> <s:property value="#item.price" /><br>
                <form action="placeBid" method="post">
                    <input type="hidden" name="itemId" value="<s:property value='#item.id' />">
                    <label for="bidAmount">Your Bid:</label>
                    <input type="text" name="bidAmount" required>
                    <button type="submit">Place Bid</button>
                </form>
            </p>
        </s:iterator>
        <p><a href="addItem.jsp">Add Item</a></p>
    </body>
</html>
