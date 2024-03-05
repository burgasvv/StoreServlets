<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Task Six Form</title>
    </head>
    <body>
        <div style="margin: 50px">
            <form action="${pageContext.request.contextPath}/task_six-servlet" method="get">
                <label>Product name <br>
                    <input type="text" name="product_name">
                </label><br><br>
                <label>Product count <br>
                    <input type="number" name="product_count">
                </label><br><br>
                <button type="submit">Submit</button>
            </form>
            <button onclick="history.back()">Back</button>
        </div>
    </body>
</html>
