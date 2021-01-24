package ru.itis.javalab.servlets;

import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        List<User> users = usersService.getUserByLoginPassword(login, password);
        if (users.isEmpty()) {
            resp.sendRedirect("/login");
        }
        else {
            User user = users.get(0);
            if (user != null) {
                Cookie cookie = new Cookie("auth", URLEncoder.encode(user.getUuid(), "UTF-8"));
                cookie.setMaxAge(900);
                resp.addCookie(cookie);
                resp.sendRedirect("/profile");
            }
            else {
                resp.sendRedirect("/login");
            }
        }
    }
}
