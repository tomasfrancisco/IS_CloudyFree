<%--
  Created by IntelliJ IDEA.
  User: tomasfrancisco
  Date: 13/11/15
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script>
    $(document).ready(function() {
      $('#edit-music').click(function () {
        $.post("music", $('form').serialize(), function (data) {
          if(data.error !== undefined) {
            $('body').prepend("<p>" + data.error + "</p>");
          } else {
            $('body').prepend("<p>" + data.message + "</p>");
            $("input[name='id']").attr('value', data.musicId);
            $("input[name='title']").attr('value', data.musicTitle);
            $("input[name='artist']").attr('value', data.musicArtist);
            $("input[name='album']").attr('value', data.musicAlbum);
            $("input[name='year']").attr('value', data.musicYear);
            $("input[name='path']").attr('value', data.musicPath);
          }
        });
        return false;
      });
    });
  </script>
</head>
<body>
<h1>${music.title}</h1>
<form>
  <input type="hidden" name="id" value="${music.id}">
  Title
  <input type="text" name="title" value="${music.title}">
  Artist
  <input type="text" name="artist" value="${music.artist}">
  Album
  <input type="text" name="album" value="${music.album}">
  Year
  <input type="number" name="year" value="${music.year}">
  Path
  <input type="text" name="path" value="${music.path}">
  <button id="edit-music">Edit Music</button>
</form>
</body>
</html>
