<%@ page import="com.home.common.Person" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
    <h2>Registered users:</h2>
    <h3><%
        List<Person> personList = (List<Person>) session.getAttribute("personList");
        Collections.sort(personList);

        for (Person person : personList) {
            out.println(person.getLogin() +", ");
        }
    %>
    </h3>

    <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
