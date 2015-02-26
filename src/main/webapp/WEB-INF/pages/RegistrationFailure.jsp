<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>${headerMessage}</h1>
    <h2>SORRY! YOU'RE REGISTRATION IS FAILED</h2>
    <h3>You typed such information: </h3>
    <table>
        <tr>
            <td>Name:</td>
            <td>${person.personName}</td>
        </tr>
        <tr>
            <td>Age:</td>
            <td>${person.personEmail}</td>
        </tr>
    </table>
    <p><a href="${pageContext.request.contextPath}/RegistrationForm.html">Back to Registration Form</a></p>

</body>
</html>
