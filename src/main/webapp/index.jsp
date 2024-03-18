<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>java-dev10-servlets</title>
</head>
<body>
<%--<h1><%= "Hello World!" %>--%>
</h1>
<br/>
<p><a href="time">Time Performer default</a></p>
<p><a href="time?timezone=UTC+2">Time Performer UTC+2</a></p>
<p><a href="time?timezone=UTC+2&timezone=UTC+6&testParamName=testParamValue">Time Performer UTC+2&UTC+6&testParam</a></p>
<p><a href="time?timezone=UTC-2">Time Performer UTC-2</a></p>
<p><a href="time?timezone=UTC+15">Time Performer UTC+15</a></p>
<p><a href="time?timezone=UTC-13">Time Performer UTC-13</a></p>
<p><a href="time?timezone=UTC+600">Time Performer UTC+600</a></p>
<p><a href="time?timezone=UTC+">Time Performer UTC+</a></p>
</body>
</html>