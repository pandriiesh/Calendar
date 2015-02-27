<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
        <h1>${headerMessage}</h1>
        <h2>Please fill the following form in order to complete registration:</h2>
        <form action="${pageContext.request.contextPath}/submitRegistrationForm.html" method="post">
            <table>
                <tr><td>Name: <input type="text" name="personName"/></td></tr>
                <tr><td>Email: <input type="text" name="personEmail"/></td></tr>
                <tr><td>Login: <input type="text" name="login"/></td></tr>
                <tr><td>Password: <input type="password" name="password"/></td></tr>
            </table>
            <input type="submit" value="Done">
        </form>
</body>
</html>
