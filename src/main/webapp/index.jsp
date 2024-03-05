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
                <li><h2><a href="${pageContext.request.contextPath}/task_one-form.jsp">Task 1</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_two-form.jsp">Task 2</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_three-form.jsp">Task 3</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_four-form.jsp">Task 4</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_five-form.jsp">Task 5</a></h2></li>
                <li><h2><a href="${pageContext.request.contextPath}/task_six-form.jsp">Task 6</a></h2></li>
            </ul>
        </div>
    </body>
</html>