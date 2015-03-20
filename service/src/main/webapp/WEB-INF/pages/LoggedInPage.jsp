<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
        <h3>You are already logged in as "<%out.print(request.getSession().getAttribute("personName"));%>"</h3>
        <a href="${pageContext.request.contextPath}/LoginForm.html">Loguot</a>
</body>
</html>
