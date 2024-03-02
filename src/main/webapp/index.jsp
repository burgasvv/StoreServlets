<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Servlets postgres</title>
</head>
    <body>
        <div style="margin: 50px">
            <h1>Tasks:</h1>
            <ul>
                <li><h2><a href="${pageContext.request.contextPath}/task_one-servlet">Task 1</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_two-servlet">Task 2</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_three-servlet">Task 3</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_four-servlet">Task 4</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_five-servlet">Task 5</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_six-servlet">Task 6</a></h2></li>
            </ul>
        </div>
    </body>
</html>