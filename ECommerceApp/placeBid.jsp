<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Place a Bid</title>
  </head>
  <body>
    <!-- Display logged-in user -->
    <s:if test="#session.username != null">
      <p>Logged in as: <s:property value="#session.username" /></p>
    </s:if>

    <h1>Place a Bid</h1>

    <!-- Form to place a bid -->
    <s:form action="placeBid" method="post">
      <p>
        <label for="itemId">Select an Item:</label>
        <s:select
          name="itemId"
          list="#session.items"
          listKey="id"
          listValue="name"
          headerKey=""
          headerValue="Select an Item"
        />
      </p>

      <p>
        <s:textfield name="bidAmount" label="Bid Amount:" />
      </p>

      <p>
        <s:submit value="Place Bid" />
      </p>
    </s:form>

    <!-- Display success or error message -->
    <s:if test="message != null">
      <p style="color: green"><s:property value="message" /></p>
    </s:if>

    <s:else>
      <p style="color: red">Something went wrong. Please try again.</p>
    </s:else>

    <br />
    <a href="viewItems.action">Back to Items</a>
  </body>
</html>
