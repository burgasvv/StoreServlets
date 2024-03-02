<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task Six Servlet</title>
</head>
    <body>
        <%
            Object orderNumbers = request.getAttribute("order numbers");
        %>
        <div style="margin: 50px">
            <h1>Deleted orders with cascade: <%=orderNumbers%></h1>
            <input style="margin-top: 50px" type="button" name="Back" value="Back" onclick="history.back()">
        </div>
    </body>
</html>
