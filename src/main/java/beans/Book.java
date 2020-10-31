package beans;

public class Book {

    private final int id;
    private final String title;
    private final String author;
    private final String releaseDate;
    static int counter = 0;

    public Book(String title, String author, String releaseDate){
        this.id = counter++;;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "</br>Title: " + title + "</br>" +
                "Author: " + author + "</br>" +
                "Release date: " + releaseDate+"</br>";
    }

    public int getId() { return id;}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
