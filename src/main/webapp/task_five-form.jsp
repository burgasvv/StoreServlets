<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Task Five Form</title>
    </head>
    <body>
    <div style="margin: 50px">
        <form action="${pageContext.request.contextPath}/task_five-servlet" method="get">
            <label> Order number <br>
                <input type="text" name="order_number">
            </label><br><br>
            <button type="submit">Submit</button>
        </form>
        <button onclick="history.back()">Back</button>
    </div>
    </body>
</html>
