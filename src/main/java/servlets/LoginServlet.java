package servlets;

import beans.User;
import com.google.gson.Gson;
import enums.Role;
import requests.LoginRequest;
import responses.ExceptionResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final String adminUsername = "admin";
    private final String adminPassword = "admin";
    private final String userId = "userId";

    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);

            if(adminUsername.contains(loginRequest.getLogin())) {
                    if (adminPassword.contains(loginRequest.getPassword()))
                        makeUser(loginRequest, request, response, Role.ADMIN);
                    else
                        doException("Login or password are incorrect", 400, response);
            }
            else{
                if(User.checkUser(User.init(),loginRequest.getLogin(),loginRequest.getPassword()))
                    makeUser(loginRequest, request, response, Role.USER);
                else
                    doException("Login or password are incorrect", 400, response);
            }

        }catch (Exception ex){
            doException(ex.getLocalizedMessage(), 500, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher loginPageDispatcher = request.getRequestDispatcher("WEB-INF/html/login.html");
        loginPageDispatcher.forward(request, response);
    }

    private String getBase64FromString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    private void makeUser(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response, Role role) throws ServletException, IOException {
        User user = new User(loginRequest.getLogin(),loginRequest.getPassword(), role);
        request.getSession().setAttribute(loginRequest.getLogin(), user);
        String encodedLoginBase64 = getBase64FromString(user.getLogin());
        response.addCookie(new Cookie(userId, encodedLoginBase64));
        gson.toJson(user, response.getWriter());
    }

    private ExceptionResponse doException(String ex, int status, HttpServletResponse response) throws IOException {
        response.setStatus(500);
        ExceptionResponse exResponse = new ExceptionResponse(ex, status);
        gson.toJson(exResponse, response.getWriter());
        return exResponse;
    }
}
