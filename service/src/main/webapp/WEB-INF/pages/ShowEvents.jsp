<%@ page import="com.home.common.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
        <%
        Date calculatedTimeForNewEvent = (Date) request.getAttribute("calculatedTimeForNewEvent");
        %>

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
                <tr><td>Find events by date </td>
                    <td><input type="text" name="dateToFind" value="DD.MM.YYYY"
                               onblur="if (this.value == '') {this.value = 'DD.MM.YYYY';}"
                               onfocus="if (this.value == 'DD.MM.YYYY') {this.value = '';}"></td>
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
            <br>
            Find best time for your event by entering events duration(in minutes) and attenders:
            <table>

                <tr><td><input type="text" name="eventDuration" value="Duration"
                    onblur="if (this.value == '') {this.value = 'Duration';}"
                    onfocus="if (this.value == 'Duration') {this.value = '';}"></td>
                <td><input type="text" name="eventAttenders" value="Attenders"
                    onblur="if (this.value == '') {this.value = 'Attenders';}"
                    onfocus="if (this.value == 'Attenders') {this.value = '';}"></td>
                <td><input type="submit" value="Find" formaction="/findBestTimePeriodToCreateEventForUsers.html"></td>
                </tr>

            </table>
            <% if (calculatedTimeForNewEvent!=null) { %>
            The best time for new event is <%out.print(calculatedTimeForNewEvent);} %>
        </form>
        <%
            List<Event> events = (List<Event>) request.getAttribute("foundedEvents");

            if (events !=null) {

        %>
        <h3>Founded <%out.print(events.size());%> event(s).</h3>
        <%

                for (Event event : events) {
        %>
            <table>
                <tr><td>Title:            </td><td><%out.print(event.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(event.getDescription());%></td></tr>
                <tr><td>Start Date/Time:  </td><td><%out.print(event.getStartTime());%></td></tr>
                <tr><td>End Date/Time:    </td><td><%out.print(event.getEndTime());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(event.getAttenders().toString());%></td></tr>
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
        List<Event> eventList = (List<Event>) request.getSession().getAttribute("personEvents");

        for (Event event : eventList) {

    %>
            <table>
                <tr><td>Title:            </td><td><%out.print(event.getTitle());%></td></tr>
                <tr><td>Description:      </td><td><%out.print(event.getDescription());%></td></tr>
                <tr><td>Start Date/Time:  </td><td><%out.print(event.getStartTime());%></td></tr>
                <tr><td>End Date/Time:    </td><td><%out.print(event.getEndTime());%></td></tr>
                <tr><td>Attenders:        </td><td><%out.print(event.getAttenders().toString());%></td></tr>
                <tr><td>ID:               </td><td><%out.print(event.getId());%></td></tr>
            </table>
            <br>
        <%} %>

        <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
