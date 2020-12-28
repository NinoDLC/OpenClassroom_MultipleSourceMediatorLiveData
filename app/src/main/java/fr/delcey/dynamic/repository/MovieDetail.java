package fr.delcey.dynamic.repository;

public class MovieDetail {

    private final String id;
    private final String director;

    public MovieDetail(String id, String director) {
        this.id = id;
        this.director = director;
    }

    public String getId() {
        return id;
    }

    public String getDirector() {
        return director;
    }
}
