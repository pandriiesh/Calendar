<%@ page import="com.home.common.EventClone" %>
<%@ page import="java.util.List" %>
<%@ page import="com.home.common.Person" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
        <form method="post">
            <table>
                <tr><td>Find event with title</td>
                    <td><input type="text" name="eventToFind"></td>
                    <td><input type="submit" value="Find" formaction="/FindEvent.html"></td>
                </tr>
                <tr><td>Remove event with title </td>
                    <td><input type="text" name="eventToRemove"></td>
                    <td><input type="submit" value="Remove" formaction="/RemoveEvent.html"></td>
                </tr>
            </table>
        </form>

    <h2>Events with you:</h2>
    <%
        Person person = (Person) request.getSession().getAttribute("person");

        List<EventClone> eventList = person.getEvents();

        for (EventClone eventClone : eventList) {
    %>
            <table>
                <tr><td>Title:            </td><td><%out.print(eventClone.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(eventClone.getDescription());%></td></tr>
                <tr><td>Start Date/Time:  </td><td><%out.print(eventClone.getStartTime());%></td></tr>
                <tr><td>End Date/Time:    </td><td><%out.print(eventClone.getEndTime());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(eventClone.getAttendersLogins().toString());%></td></tr>
                <tr><td>UUID:             </td><td><%out.print(eventClone.getId());%></td></tr>

            </table>
            <br>
        <%} %>

        <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
