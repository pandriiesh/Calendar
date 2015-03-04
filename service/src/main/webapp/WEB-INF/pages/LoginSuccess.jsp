<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>${headerMessage}</h1>
    <h2>You logged in as <%out.print(request.getSession().getAttribute("personName"));%></h2>
    <h3>Welcome!</h3>
    <a href="${pageContext.request.contextPath}/ControlPanel.html">Proceed to your account</a>

</body>
</html>
