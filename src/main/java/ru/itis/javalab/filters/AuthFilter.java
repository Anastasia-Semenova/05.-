package ru.itis.javalab.filters;

import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.services.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

//TODO: данный фильтр проверяет запрос и наличие в нем куки Auth.
// Значение данной куки должно быть предварительно сохранено в бд.
// Т.е. если куки в запросе нет, или она есть, но со значением,
// которого нет в бд - перенести пользователя на страницу /login.
// Если кука в БД есть - пустить пользователя на запрашиваемую страницу (например, profile).

//TODO: В качестве значения куки использовать UUID

//TODO: Можно реализовать страницу входа по желанию - она выдает пользователю нужную куку по логину и паролю.

@WebFilter("/profile")
public class AuthFilter implements Filter {
    UsersService usersService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie cookie=null;
        Cookie[] cookies = request.getCookies();
        String cookieName = "auth";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }if(cookie!=null){
                    List<User> users = usersService.getUsersByUuid(cookie.getValue());
                    if (users.isEmpty()) {
                        response.sendRedirect("/login");
                    }else{
                        return;
                    }
                } else{
                    response.sendRedirect("/login");
                }

        } else{
            response.sendRedirect("/login");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


}
