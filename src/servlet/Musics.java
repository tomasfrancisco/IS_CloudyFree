package servlet;

import beans.MusicRemote;
import beans.PlaylistRemote;
import models.User;
import org.json.simple.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Musics", urlPatterns = "musics")
public class Musics extends HttpServlet {
    @EJB
    private MusicRemote musicService;
    @EJB
    private PlaylistRemote playlistService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        JSONObject data = new JSONObject();
        models.Music music = null;

        User user = (User) request.getSession().getAttribute("user");

        String title = request.getParameter("title");
        String artist = request.getParameter("artist");
        String album = request.getParameter("album");
        int year = Integer.parseInt(request.getParameter("year"));
        String path = request.getParameter("path");


        if(user != null && title != null && path != null) {
            if((music = this.musicService.createMusic(title, artist, album, year, path)) != null) {
                data.put("message", "Successfully created!");
                data.put("musicTitle", music.getTitle());
                data.put("musicId", music.getId());
                out.write(data.toJSONString());
            } else {
                data.put("error", "Something went wrong, try again!");
                out.write(data.toJSONString());
            }
        } else if(user == null) {
            response.sendRedirect("login.jsp");
        } else {
            data.put("error", "Something went wrong, try again!");
            out.write(data.toJSONString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            request.setAttribute("musics", musicService.getAll());
            request.setAttribute("playlists", playlistService.getAll(user, true));
            request.getRequestDispatcher("musics.jsp").forward(request, response);
        }
    }
}
