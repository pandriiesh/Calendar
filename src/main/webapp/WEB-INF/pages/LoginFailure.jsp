<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>${headerMessage}</h1>
    <h2>SORRY! YOU'VE ENTERED INVALID LOGIN OR PASSWORD</h2>
    <h3>You typed such information: </h3>
    <table>
        <tr>
            <td>Login:</td>
            <td>${login}</td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>${password}</td>
        </tr>
    </table>

    <h2>Please try again.</h2>
    <form action="${pageContext.request.contextPath}/submitLoginForm.html" method="post">
        <table>
            <tr><td>Login: <input type="text" name="login"/></td></tr>
            <tr><td>Password: <input type="password" name="password"/></td></tr>
        </table>
        <input type="submit" value="Submit">
        <a href="${pageContext.request.contextPath}/RegistrationForm.html">Register</a>
    </form>

</body>
</html>
