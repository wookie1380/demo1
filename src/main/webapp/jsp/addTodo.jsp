<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add Todo Item</title>
</head>
<body>
<h1>Add a New Todo Item</h1>
<form action="todo" method="post">
    <input type="hidden" name="action" value="add"/>
    <label for="task">Task:</label>
    <input type="text" id="task" name="task"/>
    <input type="submit" value="Add"/>
</form>
<a href="viewTodos.jsp">View Todo List</a>
</body>
</html>
