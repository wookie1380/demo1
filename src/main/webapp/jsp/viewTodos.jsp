<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>View Todo Items</title>
</head>
<body>
<h1>View Todo Items</h1>
<c:if test="${empty items}">
    <p>No to-do items.</p>
</c:if>
<c:if test="${not empty items}">
    <ul>
        <c:forEach var="item" items="${items}">
            <li>${item.task} - <a href="todo?action=delete&id=${item.id}">Delete</a></li>
        </c:forEach>
    </ul>
</c:if>
<a href="index.jsp">Return to main page</a>
</body>
</html>
