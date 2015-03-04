<%@ page import="com.home.common.EventClone" %>
<%@ page import="java.util.List" %>
<%@ page import="com.home.common.Person" %>
<%@ page import="com.home.common.EventInterface" %>
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

        <%
            EventInterface event = (EventInterface) request.getAttribute("foundedEvent");

            if (event!=null) {
        %>
        <h3>Founded:</h3>
            <table>
                <tr><td>Title:            </td><td><%out.print(event.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(event.getDescription());%></td></tr>
                <tr><td>Start Date/Time:  </td><td><%out.print(event.getStartTime());%></td></tr>
                <tr><td>End Date/Time:    </td><td><%out.print(event.getEndTime());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(event.getAttendersLogins().toString());%></td></tr>
                <tr><td>UUID:             </td><td><%out.print(event.getId());%></td></tr>
            </table>
            <br>
            <%} %>




        <%
            Boolean isRemoved = (Boolean) request.getAttribute("isRemoved");

            if (isRemoved!=null && isRemoved) {
        %>
        <h3>Event removed!</h3>
        <% } %>
    <h2>Events with you:</h2>
    <%
        Person person = (Person) request.getSession().getAttribute("person");

        List<EventInterface> eventList = person.getEvents();

        for (EventInterface event1 : eventList) {
    %>
            <table>
                <tr><td>Title:            </td><td><%out.print(event1.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(event1.getDescription());%></td></tr>
                <tr><td>Start Date/Time:  </td><td><%out.print(event1.getStartTime());%></td></tr>
                <tr><td>End Date/Time:    </td><td><%out.print(event1.getEndTime());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(event1.getAttendersLogins().toString());%></td></tr>
                <tr><td>UUID:             </td><td><%out.print(event1.getId());%></td></tr>
            </table>
            <br>
        <%} %>

        <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
