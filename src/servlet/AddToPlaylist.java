package servlet;

import beans.MusicRemote;
import beans.PlaylistRemote;
import models.*;
import models.Playlist;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddToPlaylist", urlPatterns = "/add-to-playlist")
public class AddToPlaylist extends HttpServlet {
    @EJB
    private PlaylistRemote playlistService;
    @EJB
    private MusicRemote musicService;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String playlistId = request.getParameter("playlistId");
        String musicId = request.getParameter("musicId");

        Playlist playlist = playlistService.getPlaylist(playlistId);
        models.Music music = musicService.getMusic(musicId);

        if(playlist != null && music != null)
            playlistService.addMusicPlaylist(playlist, music);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
