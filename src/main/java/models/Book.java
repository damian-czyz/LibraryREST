package models;

public class Book {

    private String title;
    private final String author;
    private final String releaseDate;

    public Book(String title, String author, String releaseDate){
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
