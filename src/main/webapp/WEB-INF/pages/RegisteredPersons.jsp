<%@ page import="com.home.common.Person" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 03.03.2015
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
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
