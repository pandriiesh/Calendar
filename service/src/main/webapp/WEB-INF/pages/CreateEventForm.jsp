<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new event</title>
</head>
<body>
        <%
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        %>

    <h2>Create new Event form</h2>
    <form action="${pageContext.request.contextPath}/submitCreateEventForm.html" method="post">
        <table>
            <tr><td>Title:                     </td><td><input type="text" name="title"/></td></tr>
            <tr><td>Description:               </td><td><input type="text" name="description"/></td></tr>
            <tr><td>Start Date/Time*:          </td><td><input type="text" name="startTime"/></td></tr>
            <tr><td>End Date/Time*:            </td><td><input type="text" name="endTime"/></td></tr>
            <tr><td>Attenders**:               </td><td><input type="text" name="attendersLogins"/></td></tr>
            <tr><td>Make event periodic:       </td></tr>
            <tr><td><input type="radio" name="periodic" value="year"/>Every year</td></tr>
            <tr><td><input type="radio" name="periodic" value="month"/>Every month</td></tr>
            <tr><td><input type="radio" name="periodic" value="week"/>Every week</td></tr>
            <tr><td><input type="radio" name="periodic" value="day"/>Every day</td></tr>
            <tr><td><input type="radio" name="periodic" value="concreteDay"/>Every day of week:</td></tr>
            <tr><td><input type="checkbox" name="monday" value="Monday"/>Monday</td></tr>
            <tr><td><input type="checkbox" name="tuesday" value="Tuesday"/>Tuesday</td></tr>
            <tr><td><input type="checkbox" name="wednesday" value="Wednesday"/>Wednesday</td></tr>
            <tr><td><input type="checkbox" name="thursday" value="Thursday"/>Thursday</td></tr>
            <tr><td><input type="checkbox" name="friday" value="Friday"/>Friday</td></tr>
            <tr><td><input type="checkbox" name="saturday" value="Saturday"/>Saturday</td></tr>
            <tr><td><input type="checkbox" name="sunday" value="Sunday"/>Sunday</td></tr>
            <tr><td>Events quantity:</td>
                <td><input type="text" name="eventPeriodicQuantity"/></td></tr>

        </table>

        <p>* enter Date in such format: <% out.print(dateFormat.format(new Date())); %></p>
        <p>** enter attenders logins with whitespaces between them</p>
        <input type="submit" value="Done">
    </form>
    <h4><a href="${pageContext.request.contextPath}/ControlPanel.html">Go back to Control Panel</a></h4>

</body>
</html>
