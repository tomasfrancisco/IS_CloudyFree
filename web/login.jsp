<%--
  Created by IntelliJ IDEA.
  User: tomasfrancisco
  Date: 12/11/15
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login | Cloudyfree</title>
</head>
<body>
    <c:if test="${not empty result}">
        <h3>${result}</h3>
    </c:if>

    <form action="login" method="post">
        <input type="text" name="email">
        <input type="password" name="password">
        <input type="submit" value="submit">
    </form>

    <form action="signup" method="post">
      <input type="text" name="email">
      <input type="password" name="password">
      <input type="submit" value="submit">
    </form>
</body>
</html>
