<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
        <h1>${headerMessage}</h1>
        <h2>Please enter your login and password to proceed:</h2>
        <form action="${pageContext.request.contextPath}/submitLoginForm.html" method="post">
            <table>
                <tr><td>Login:      </td><td><input type="text" name="login"/></td></tr>
                <tr><td>Password:   </td><td><input type="password" name="password"/></td></tr>
            </table>
            <input type="submit" value="Done">
        </form>
</body>
</html>
