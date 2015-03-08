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
                <tr><td>Find events by title </td>
                    <td><input type="text" name="eventTitleToFind"></td>
                    <td><input type="submit" value="Find" formaction="/FindEventsByTitle.html"></td>
                </tr>
                <tr><td>Find events by attender </td>
                    <td><input type="text" name="attenderLogin"></td>
                    <td><input type="submit" value="Find" formaction="/FindEventByAttender.html"></td>
                </tr>
                <tr><td>Find events by day </td>
                    <td><input type="text" name="dateToFind" value="DD.MM.YYYY"
                               onblur="if (this.value == '') {this.value = 'DD.MM.YYYY';}"
                               onfocus="if (this.value == 'DD.MM.YYYY') {this.value = '';}"
                            ></td>
                    <td><input type="submit" value="Find" formaction="/FindEventByDate.html"></td>
                </tr>
                <tr><td>Find event by ID </td>
                    <td><input type="text" name="ID"></td>
                    <td><input type="submit" value="Find" formaction="/FindEventByID.html"></td>
                </tr>
                <tr><td>Remove event by ID </td>
                    <td><input type="text" name="eventID"></td>
                    <td><input type="submit" value="Remove" formaction="/RemoveEventByID.html"></td>
                </tr>
            </table>
        </form>

        <%
            List<Event> events = (List<Event>) request.getAttribute("foundedEvents");

            if (events !=null) {

        %>
        <h3>Founded <%out.print(events.size());%> event(s).</h3>
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
                <tr><td>ID:               </td><td><%out.print(event.getId());%></td></tr>
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
                <tr><td>ID:               </td><td><%out.print(event.getId());%></td></tr>
            </table>
            <br>
        <%} %>

        <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
