package ru.itis.javalab.servlets;

import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.services.UsersService;
import ru.itis.javalab.services.UsersServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("registration.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("here");
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String age = req.getParameter("age");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String uuid = UUID.randomUUID().toString();

        System.out.println("---------------------------------");
        System.out.println("Login: " + login);
        System.out.println("Password: " + password);
        System.out.println("FirstName" + firstName);
        System.out.println("Cookie: " + uuid);

        User user = User.builder().firstName(firstName).lastName(lastName).age(Integer.parseInt(age)).login(login).password(password).uuid(uuid).build();
        usersService.saveUser(user);

        Cookie cookie = new Cookie("auth", uuid);
        cookie.setMaxAge(900);
        resp.addCookie(cookie);
        resp.sendRedirect("/profile");
    }
}
