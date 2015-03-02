<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 02.03.2015
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
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
