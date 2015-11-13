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

@WebServlet(name = "Playlists", urlPatterns = "/playlists")
public class Playlists extends HttpServlet {
    @EJB
    private PlaylistRemote playlistService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        JSONObject data = new JSONObject();
        models.Playlist playlist = null;

        User user = (User) request.getSession().getAttribute("user");
        String name = request.getParameter("name");

        if(user != null && name != null) {
            if((playlist = this.playlistService.createPlaylist(name, user)) != null) {
                data.put("message", "Successfully created!");
                data.put("playlistName", playlist.getName());
                data.put("playlistId", playlist.getId());
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
        String isAscendent = request.getParameter("asc");

        if(user != null) {
            if(isAscendent != null && isAscendent.equals("true")) {
                request.setAttribute("playlists", playlistService.getAll(user, true));
                request.setAttribute("asc", true);
            } else {
                request.setAttribute("playlists", playlistService.getAll(user, false));
                request.setAttribute("asc", false);
            }
            request.getRequestDispatcher("playlists.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
