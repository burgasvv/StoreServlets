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
                <li><h2><a href="${pageContext.request.contextPath}/task/task_one-form.jspm.jsp">Task 1</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task/task_two-form.jspm.jsp">Task 2</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task/task_three-form.jspm.jsp">Task 3</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task/task_four-form.jspm.jsp">Task 4</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task/task_five-form.jspm.jsp">Task 5</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task/task_six-form.jspm.jsp">Task 6</a></h2></li>
            </ul>
        </div>
    </body>
</html>