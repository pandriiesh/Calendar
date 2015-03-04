<%@ page import="com.home.common.Person" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Persons</title>
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
</body>
</html>
