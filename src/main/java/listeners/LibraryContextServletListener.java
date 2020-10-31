package listeners;

import beans.Book;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;

@WebListener
public class LibraryContextServletListener implements ServletContextListener, ServletContextAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        servletContextEvent.getServletContext().setAttribute("books", generate());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public ArrayList<Book> generate(){
        ArrayList<Book> books = new ArrayList<>();
        Book b1 = new Book("Clean Code", "Robert Cecil Martin", "2008-08-01");
        Book b2 = new Book("The Pragmatic Programmer", "Andy Hunt and Dave Thomas", "1999-10-01");
        Book b3 = new Book("Code Complete", "Steve McConnell", "1993-05-24");
        books.add(b1);
        books.add(b2);
        books.add(b3);

        return books;
    }
}
