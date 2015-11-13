<%--
  Created by IntelliJ IDEA.
  User: tomasfrancisco
  Date: 13/11/15
  Time: 00:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Playlists | Cloudyfree</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {
            $('#create-playlist').click(function () {
                $.post("playlists", $('form').serialize(), function (data) {
                    if(data.error !== undefined) {
                        $('body').prepend("<p>" + data.error + "</p>");
                    } else {
                        $('#playlists').prepend("<a href='playlist?id=" + data.playlistId + "'>" + data.playlistName + "</a><br/>");
                    }
                });
                return false;
            });
        });
    </script>
</head>
<a>
  <h1>Playlists</h1>
  <form>
      <input id="name" type="text" name="name">
      <button id="create-playlist">Create Playlist</button>
  </form>
    <c:choose>
        <c:when test="${not empty asc and asc == true}">
            <a href="playlists?asc=false"><button>Descendent</button></a>
        </c:when>
        <c:otherwise>
            <a href="playlists?asc=true"><button>Ascendent</button></a>
        </c:otherwise>
    </c:choose>

  <div id="playlists">
      <c:forEach var="playlist" items="${playlists}">
          <a href="playlist?id=${playlist.id}">${playlist.name}</a><br/>
      </c:forEach>
  </div>
</body>
</html>
