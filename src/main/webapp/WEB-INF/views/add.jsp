<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/1/27
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Add
    <form action="/job/add" method="post">
        jobName:<input name="name" value="job1"><br/>
        jobGroup:<input name="group" value="group1"><br/>
        jobTrigger:<input name="trigger" value="0/10 * * * * ?"><br/>
        <input type="submit">
    </form>
</body>
</html>
