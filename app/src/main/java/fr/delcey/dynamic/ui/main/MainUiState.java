package fr.delcey.dynamic.ui.main;

public class MainUiState {
    private String title; // <-- Known with basic request
    private String director; // <-- Known with detail request

    public MainUiState(String title, String director) {
        this.title = title;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }
}
