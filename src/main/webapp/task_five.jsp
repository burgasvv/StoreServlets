<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task Five Servlet</title>
</head>
    <body>
        <%
            Object order = request.getAttribute("order");
        %>
        <div style="margin: 200px">
            <h1>Information about created order with today's sales</h1>
            <h3><%=order%></h3>
            <input style="margin-top: 50px" type="button" name="Back" value="Back" onclick="history.back()">
        </div>
    </body>
</html>
