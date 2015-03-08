<%@ page import="com.home.common.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="com.home.common.Person" %>
<%@ page import="java.util.ArrayList" %>
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
            List<Event> events = (List<Event>) request.getAttribute("foundedEvent");

            if (events !=null) {

        %>
        <h3>Founded <%out.print(events.size());%> event(s) with title <%out.print(events.get(0).getTitle());%>:</h3>
        <%

                for (Event event : events) {
                    List<String> attenders = new ArrayList<String>();

                    for(Person person: event.getAttenders()) {
                        attenders.add(person.getLogin());
                    }

        %>
            <table>
                <tr><td>Title:            </td><td><%out.print(event.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(event.getDescription());%></td></tr>
                <tr><td>Start Date/Time:  </td><td><%out.print(event.getStartTime());%></td></tr>
                <tr><td>End Date/Time:    </td><td><%out.print(event.getEndTime());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(attenders.toString());%></td></tr>
            </table>
            <br>
            <%} }%>




        <%
            Boolean isRemoved = (Boolean) request.getAttribute("isRemoved");

            if (isRemoved!=null && isRemoved) {
        %>
        <h3>Event(s) removed!</h3>
        <% } %>
    <h2>Events with you:</h2>
    <%
        Person person = (Person) request.getSession().getAttribute("person");

        List<Event> eventList = person.getEvents();

        for (Event event : eventList) {
            List<String> attenders = new ArrayList<String>();

            for(Person person1: event.getAttenders()) {
                attenders.add(person1.getLogin());
            }
    %>
            <table>
                <tr><td>Title:            </td><td><%out.print(event.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(event.getDescription());%></td></tr>
                <tr><td>Start Date/Time:  </td><td><%out.print(event.getStartTime());%></td></tr>
                <tr><td>End Date/Time:    </td><td><%out.print(event.getEndTime());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(attenders.toString());%></td></tr>
            </table>
            <br>
        <%} %>

        <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
