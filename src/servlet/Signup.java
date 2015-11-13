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

@WebServlet(name = "Signup", urlPatterns = "/signup")
public class Signup extends HttpServlet {
    @EJB
    private UserRemote userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("password");
        User user = userService.getUser(email);

        if (user != null) {
            request.setAttribute("result", "E-mail address is already in use.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            user = userService.createUser(email, password);
            if(user != null) {
                request.setAttribute("result", "Account created successfully!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("result", "Something went wrong! Try again");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
