package ru.itis.javalab.servlets;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.repositories.UsersRepositoryJdbcImpl;
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
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    public UsersService usersService;
    UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println(usersService.getAllUsers());
//        System.out.println(usersRepository.findAllByAge(26));
//        super.doGet(request, response);
        List<User> users = usersService.getAllUsers();
        users.add(User.builder()
                .id(1L)
                .firstName("Marsel")
                .lastName("Sidikov")
                .age(26)
                .build());
        users.add(
        User.builder()
                .id(2L)
                .firstName("Rasim")
                .lastName("Garipov")
                .age(19)
                .build());
        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String color = request.getParameter("color");
        Cookie cookie=new Cookie("color", color);
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        response.sendRedirect("/users");
    }
}
