<%--
  Created by IntelliJ IDEA.
  User: tomasfrancisco
  Date: 13/11/15
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<% if(request.getSession().getAttribute("user") == null)
  response.sendRedirect("login.jsp");
%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
      $(document).ready(function() {
        $('#edit-playlist').click(function () {
          $.post("playlist", $('form').serialize(), function (data) {
            if(data.error !== undefined) {
              $('body').prepend("<p>" + data.error + "</p>");
            } else {
              $('form').attr('value', data.playlistName);
              $('h1').html(data.playlistName);
            }
          });
          return false;
        });

        $('[class^="add-to-playlist"]').click(function (e) {
          var music = $(this).attr('music');
          var selector = "select[music='" + music + "']";
          console.log($(selector).val());
          $.post("add-to-playlist",
                  {
                    playlistId: $(selector).val(),
                    musicId: music
                  },
                  function (data) {
                    if(data.error !== undefined) {
                      $('body').prepend("<p>" + data.error + "</p>");
                    } else {
                      $('#musics').append("<a href='musics?id=" + data.musicId + "'>" + data.musicTitle + "</a><br/>");
                    }
                  });
          return false;
        });

        $('[class^="remove-from-playlist"]').click(function (e) {
          var music = $(this).attr('music');
          $.post("remove-from-playlist",
                  {
                    playlistId: $('form input[name="id"]').val(),
                    musicId: music
                  },
                  function (data) {
                    if(data.error !== undefined) {
                      $('body').prepend("<p>" + data.error + "</p>");
                    } else {
                      var selector = "div[music='" + music + "']";
                      $(selector).hide();
                    }
                  });
          return false;
        });
      });

    </script>
</head>
<body>
  <h1>${playlist.name}</h1>
  <form>
    Name
    <input type="hidden" name="id" value="${playlist.id}">
    <input type="text" name="name" value="${playlist.name}">
    <button id="edit-playlist">Edit Playlist</button>
  </form>
  <div id="musics">
    <c:choose>
      <c:when test="${not empty musics}">
        <c:forEach var="music" items="${musics}">
          <div music="${music.id}">
            <a href="music?id=${music.id}">${music.title}</a>
            <select music="${music.id}">
              <c:forEach var="playlist" items="${playlists}">
                <option value="${playlist.id}">${playlist.name}</option>
              </c:forEach>
            </select>
            <button music="${music.id}" class="add-to-playlist">Add to Playlist</button>
            <button music="${music.id}" class="remove-from-playlist">Remove from Playlist</button>
            <br/>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <p>There's no traks associated to this playlist</p>
      </c:otherwise>
    </c:choose>
  </div>
  <a href="logout">Logout</a>
</body>
</html>
