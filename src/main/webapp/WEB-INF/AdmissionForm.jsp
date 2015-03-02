<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
        <h1>${headerMessage}</h1>
        <h3>STUDENT ADMISSION FORM FOR THE ENGINEERING COURSES</h3>
        <h2>Please fill the following form:</h2>
        <form action="${pageContext.request.contextPath}/submitAdmissionForm.html" method="post">
            <table>
                <tr><td>Name: <input type="text" name="studentName"/></td></tr>
                <tr><td>Hobby: <input type="text" name="studentHobby"/></td></tr>
                <tr><td>Age: <input type="text" name="studentAge"/></td></tr>
                <tr><td>Phone: <input type="text" name="studentPhoneNumber"/></td></tr>
                <tr><td>Birth Date: <input type="text" name="studentDOB"/></td></tr>
                <tr><td>Skills: </td>
                    <td> <select name="studentSkills" multiple>
                    <option value="Java Core">Java Core</option>
                    <option value="Spring Core">Spring Core</option>
                    <option value="Spring MVC">Spring MVC</option>
                        </select></td>
                    </tr>
            </table>
            <input type="submit" value="Submit this form by clicking here">
        </form>
</body>
</html>
