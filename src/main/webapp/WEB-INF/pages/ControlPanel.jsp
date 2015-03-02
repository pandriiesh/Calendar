<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Control Panel</title>

</head>
<body>
    <h1>${headerMessage}</h1>
    <h2>Control Panel of user "<%out.print(request.getSession().getAttribute("personName"));%>"</h2>
    <h3>Not <%out.print(request.getSession().getAttribute("personName"));%>?
         <a href="${pageContext.request.contextPath}/LoginForm.html">Loguot</a></h3>
</body>
</html>
