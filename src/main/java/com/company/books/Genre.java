package com.company.books;

public enum Genre {

    FANTASY("FANTASY"),
    CLASSICS("CLASSICS"),
    DETECTIVE("DETECTIVE"),
    HORROR("HORROR"),
    ACTION("ACTION");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    public static Genre fromGenre(String genre) {
        if (genre.equals(FANTASY.genre)) {
            return FANTASY;
        } else if (genre.equals(CLASSICS.genre)) {
            return CLASSICS;
        } else if (genre.equals(DETECTIVE.genre)) {
            return DETECTIVE;
        } else if (genre.equals(HORROR.genre)) {
            return HORROR;
        } else if (genre.equals(ACTION.genre)) {
            return ACTION;
        } else {
            return null;
        }
    }

    public String getGenre() {
        return genre;
    }
}
