package servlet;

import beans.MusicRemote;
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
import java.util.Collection;

@WebServlet(name = "Music", urlPatterns = "/music")
public class Music extends HttpServlet {
    @EJB
    private MusicRemote musicService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        JSONObject data = new JSONObject();
        models.Music music = null;

        User user = (User) request.getSession().getAttribute("user");

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String artist = request.getParameter("artist");
        String album = request.getParameter("album");
        String year = request.getParameter("year");
        int yearNum = 0;
        if(year != null) {
            try {
                yearNum = Integer.parseInt(year);
            } catch (Exception e) {

            }
        }
        String path = request.getParameter("path");


        if(user != null && title != null && path != null) {
            music = this.musicService.getMusic(id);
            if(music != null) {
                if((music = this.musicService.modifyMusic(music, title, artist, album, yearNum, path)) != null) {
                    data.put("message", "Successfully created!");
                    data.put("musicTitle", music.getTitle());
                    data.put("musicArtist", music.getArtist());
                    data.put("musicAlbum", music.getAlbum());
                    data.put("musicYear", music.getYear());
                    data.put("musicPath", music.getPath());
                    out.write(data.toJSONString());
                } else {
                    data.put("error", "Something went wrong, try again!");
                    out.write(data.toJSONString());
                }
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
        String id = request.getParameter("id");
        models.Music music = null;
        if(id != null) {
            music = musicService.getMusic(id);

            request.setAttribute("music", music);
            request.getRequestDispatcher("music.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
