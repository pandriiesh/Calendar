<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 03.03.2015
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new event</title>
</head>
<body>
    <h2>Create new Event form</h2>
    <form action="${pageContext.request.contextPath}/submitCreateEventForm.html" method="post">
        <table>
            <tr><td>Title:                     </td><td><input type="text" name="title"/></td></tr>
            <tr><td>Description:               </td><td><input type="text" name="description"/></td></tr>
            <tr><td>Start Date/Time*:          </td><td><input type="text" name="startTime"/></td></tr>
            <tr><td>End Date/Time*:            </td><td><input type="text" name="endTime"/></td></tr>
            <tr><td>Attenders**:               </td><td><input type="text" name="allAttenders"/></td></tr>

        </table>
        <p>* enter Date in such format: 20.02.2015 18:45</p>
        <p>** enter attenders logins with whitespaces between them</p>
        <input type="submit" value="Done">
    </form>
</body>
</html>