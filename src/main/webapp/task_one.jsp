<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task One Servlet</title>
</head>
    <body>
        <%
            Object info = request.getAttribute("info");
        %>
        <div style="margin: 200px">
            <h1>Information about order by number</h1>
            <h3><%=info%></h3>
            <input style="margin-top: 50px" type="button" name="Back" value="Back" onclick="history.back()">
        </div>
    </body>
</html>
