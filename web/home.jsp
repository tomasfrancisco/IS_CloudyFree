<%--
  Created by IntelliJ IDEA.
  User: tomasfrancisco
  Date: 12/11/15
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<% if(request.getSession().getAttribute("user") == null)
    response.sendRedirect("login.jsp");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home | Cloudyfree</title>
  </head>
    <a href="playlists">Playlists</a>
    <a href="musics">Musics</a>
    <a href="logout">Logout</a>
  </body>
</html>