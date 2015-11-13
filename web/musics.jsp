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
  <title>Musics | Cloudyfree</title>
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script>
    $(document).ready(function() {
      $('#create-music').click(function () {
        $.post("musics", $('form').serialize(), function (data) {
          if(data.error !== undefined) {
            $('body').prepend("<p>" + data.error + "</p>");
          } else {
            $('#musics').append("<a href='musics?id=" + data.musicId + "'>" + data.musicTitle + "</a><br/>");
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
    });
  </script>
</head>
<body>
<h1>Musics</h1>
<form>
  Title
  <input type="text" name="title">
  Artist
  <input type="text" name="artist">
  Album
  <input type="text" name="album">
  Year
  <input type="text" name="year">
  Path
  <input type="text" name="path">
  <button id="create-music">Create Music</button>
</form>
<div id="musics">
  <c:forEach var="music" items="${musics}">
    <a href="music?id=${music.id}">${music.title}</a>
    <select music="${music.id}">
      <c:forEach var="playlist" items="${playlists}">
        <option value="${playlist.id}">${playlist.name}</option>
      </c:forEach>
    </select>
    <button music="${music.id}" class="add-to-playlist">Add to Playlist</button>
    <br/>
  </c:forEach>
</div>
</body>
</html>