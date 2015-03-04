<%@ page import="com.home.common.EventClone" %>
<%@ page import="java.util.List" %>
<%@ page import="com.home.common.Person" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h3>Find event by title: <form>
        <input type="text" name="">
    </form></h3>
    <h3><a href="${pageContext.request.contextPath}/FindEvent.html">Find event</a></h3>
    <h3><a href="${pageContext.request.contextPath}/RemoveEvent.html">Remove event</a></h3>

    <h2>Events with you:</h2>
    <%
        Person person = (Person) request.getSession().getAttribute("person");

        List<EventClone> eventList = person.getEvents();

        for (EventClone eventClone : eventList) {
    %>
            <table>
                <tr><td>Title:            </td><td><%out.print(eventClone.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(eventClone.getDescription());%></td></tr>
                <tr><td>Start Date:       </td><td><%out.print(eventClone.getStartDate());%></td></tr>
                <tr><td>End Date:         </td><td><%out.print(eventClone.getEndDate());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(eventClone.getAttendersLogins().toString());%></td></tr>
                <tr><td>UUID:             </td><td><%out.print(eventClone.getId());%></td></tr>

            </table>
            <br>
        <%} %>

        <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
