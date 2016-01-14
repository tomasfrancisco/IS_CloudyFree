package servlet;

import beans.PlaylistRemote;
import models.Music;
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

@WebServlet(name = "Playlist", urlPatterns = "/playlist")
public class Playlist extends HttpServlet {
    @EJB
    private PlaylistRemote playlistService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        JSONObject data = new JSONObject();
        models.Playlist playlist = null;

        User user = (User) request.getSession().getAttribute("user");

        String id = request.getParameter("id");
        String name = request.getParameter("name");

        if(user != null && name != null) {
            playlist = this.playlistService.getPlaylist(id);
            if(playlist != null) {
                if((playlist = this.playlistService.modifyPlaylist(playlist, name)) != null) {
                    data.put("message", "Successfully created!");
                    data.put("playlistName", playlist.getName());
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
        User user = (User) request.getSession().getAttribute("user");
        Collection<Music> musics = null;
        if(id != null && user != null) {
            musics = playlistService.getMusicsPlaylist(id);

            request.setAttribute("musics", musics);
            request.setAttribute("playlist", playlistService.getPlaylist(id));
            request.setAttribute("playlists", playlistService.getAll(user, true));
            request.getRequestDispatcher("playlist.jsp").forward(request, response);
        } else {

        }
    }
}
