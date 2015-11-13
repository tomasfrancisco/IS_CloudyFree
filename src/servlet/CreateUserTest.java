package servlet;

import beans.UserRemote;
import models.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet("/CreateUserTest")
public class CreateUserTest extends HttpServlet {
    @EJB
    UserRemote ejbremote;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if (request.getParameter("email") != null && request.getParameter("password") != null) {
            if(ejbremote.createUser(request.getParameter("email"), request.getParameter("password")) != null)
                out.println("<h1>New user created successfuly!</h1>");
            else
                out.println("<h1>This email is already in use!</h1>");
        }
        else {
            out.println("<h1>Users</h1>");
            Collection<User> users = ejbremote.getAllUsers();
            for(User user : users)
                out.println(user.getEmail() + "<br/>");
        }
    }
}
