<%@ page import="com.home.common.Person" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
    <h2>Registered users:</h2>
    <h3><%
        Map<String, Person> personMap = (HashMap<String, Person>) session.getAttribute("personMap");
        for (Map.Entry<String, Person> entry : personMap.entrySet()) {
            out.println(entry.getKey() +", ");
        }
    %>
    </h3>

    <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
