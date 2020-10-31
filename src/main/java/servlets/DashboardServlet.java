package servlets;

import beans.Book;
import com.google.gson.Gson;
import requests.BookRequest;
import requests.LoginRequest;
import responses.ExceptionResponse;
import responses.GetDashboardReponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Dashboard", urlPatterns = "/dashboard/*")
public class DashboardServlet extends HttpServlet {

    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BookRequest bookRequest = gson.fromJson(request.getReader(), BookRequest.class);
            ServletContext servletContext = getServletContext();

            if (checkInputParam(bookRequest)){
                addBook(bookRequest, request, servletContext);
                doException("Successful data entry", 200, response);
            } else
                doException("Incorrect data", 500, response);

        }catch(Exception ex){
            doException(ex.getLocalizedMessage(), 500, response);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();

        try{
            String[] pathParams = request.getPathInfo().split("/");
            int bookId = Integer.parseInt(pathParams[pathParams.length - 1]);

            if(isBook(request,servletContext, bookId)){
                deleteBook(request, servletContext, bookId);
                doException("Book deleted", 200, response);
            }else
                doException("There isnt any book with that number", 500, response);

        }catch(Exception ex){
            doException(ex.getLocalizedMessage(), 500, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            showBooks(request, response);
        } catch (Exception ex) {
            doException(ex.getLocalizedMessage(), 500, response);
        }
    }

    private ExceptionResponse doException(String ex, int status, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(500);
        ExceptionResponse exResponse = new ExceptionResponse(ex, status);
        gson.toJson(exResponse, response.getWriter());
        return exResponse;
    }

    private void showBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Book> books = (List<Book>) request.getServletContext().getAttribute("books");
        GetDashboardReponse res = new GetDashboardReponse(books, 200);
        gson.toJson(res, response.getWriter());
    }

    private void addBook(BookRequest bookRequest, HttpServletRequest request, ServletContext servletContext){
        List<Book> books = (List<Book>) request.getServletContext().getAttribute("books");
        List<Book> newBooks = new ArrayList<>();

        for (int i = 0; i < books.size();i++)
        {
            newBooks.add(books.get(i));
        }
        newBooks.add(new Book(
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getReleaseDate()));

        servletContext.setAttribute("books", newBooks);
    }

    private boolean checkInputParam(BookRequest bookRequest){
        if(bookRequest.getAuthor().isEmpty() ||
                bookRequest.getTitle().isEmpty() ||
                bookRequest.getReleaseDate().isEmpty())
            return false;
        return true;
    }

    private void deleteBook(HttpServletRequest request, ServletContext servletContext, int bookId){
        List<Book> books = (List<Book>) request.getServletContext().getAttribute("books");
        List<Book> newBooks = new ArrayList<> ();

        for (int i = 0; i < books.size();i++)
        {
            if((bookId != books.get(i).getId()))
                newBooks.add(books.get(i));
        }
        servletContext.setAttribute("books", newBooks);

    }

    private boolean isBook(HttpServletRequest request, ServletContext servletContext, int bookId){

        List<Book> books = (List<Book>) request.getServletContext().getAttribute("books");

        if(books.stream().noneMatch(str -> str.getId()==(bookId))){
            return false;
        }
        return true;
    }
}
