<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 27.02.2015
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Control Panel</title>

</head>
<body>
    <h1>${headerMessage}</h1>
    <h2>Control Panel of user "<%out.print(request.getSession().getAttribute("personName"));%>" </h2>

</body>
</html>
