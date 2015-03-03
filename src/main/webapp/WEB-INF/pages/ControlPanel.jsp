<%@ page import="com.home.common.Person" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Control Panel</title>

</head>
<body>
<% Person person = (Person) request.getSession().getAttribute("person"); %>

    <h1>${headerMessage}</h1>
    <h2>Control Panel of user "<%out.print(person.getPersonName());%>"</h2>
    <table>
        <tr><td>Name:       </td><td><%out.print(person.getPersonName());%></td></tr>
        <tr><td>Email:      </td><td><%out.print(person.getPersonEmail());%></td></tr>
        <tr><td>Login:      </td><td><%out.print(person.getLogin());%></td></tr>
        <tr><td>Password:   </td><td><%out.print(person.getPassword());%></td></tr>

    </table>
    <h3>Not "<%out.print(person.getPersonName());%>"?
         <a href="${pageContext.request.contextPath}/LoginForm.html">Loguot</a></h3>

        <h3><a href="${pageContext.request.contextPath}/RegisteredPersons.html">View all registered persons</a></h3>

        <h3><a href="${pageContext.request.contextPath}/CreateEventForm.html">Create new event</a></h3>
        <h3><a href="${pageContext.request.contextPath}/ShowEvents.html">Show my Events</a></h3>


</body>
</html>
