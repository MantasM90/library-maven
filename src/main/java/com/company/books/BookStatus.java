package com.company.books;

import com.company.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//@Entity
//@Table(name = "users-books")
public  class BookStatus {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String id;
//    private boolean bookReservation;
//    private boolean confirmBookReservation;
//    private boolean returnBook;
//    private boolean confirmReturnBook;
//
//    @ManyToOne(cascade = CascadeType.ALL)  @JoinColumn(name = "reader_id")
//    private User reader;
//    @ManyToOne(cascade = CascadeType.ALL)  @JoinColumn(name = "librarian_id")
//    private User librarian;
//    @ManyToOne(cascade = CascadeType.ALL)  @JoinColumn(name = "book_id")
//    private Book book;
//
//    public BookStatus() {
//
//    }

    private String bookName;
    private String readerUsername;
    private boolean bookReservation;
    private boolean confirmBookReservation;
    private boolean returnBook;
    private boolean confirmReturnBook;
    private String librarianName;

    public BookStatus(String bookName, String readerUsername, boolean bookReservation, boolean confirmBookReservation, boolean returnBook, boolean confirmReturnBook, String librarianName) {
        this.bookName = bookName;
        this.readerUsername = readerUsername;
        this.bookReservation = bookReservation;
        this.confirmBookReservation = confirmBookReservation;
        this.returnBook = returnBook;
        this.confirmReturnBook = confirmReturnBook;
        this.librarianName = librarianName;
    }
    public BookStatus(String bookName, String readerUsername) {
        this.bookName = bookName;
        this.readerUsername = readerUsername;
        this.bookReservation = true;
        this.confirmBookReservation = false;
        this.returnBook = false;
        this.confirmReturnBook = false;
        this.librarianName = null;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReaderUsername() {
        return readerUsername;
    }

    public void setReaderUsername(String readerUsername) {
        this.readerUsername = readerUsername;
    }

    public boolean isBookReservation() {
        return bookReservation;
    }

    public void setBookReservation(boolean bookReservation) {
        this.bookReservation = bookReservation;
    }

    public boolean isConfirmBookReservation() {
        return confirmBookReservation;
    }

    public void setConfirmBookReservation(boolean confirmBookReservation) {
        this.confirmBookReservation = confirmBookReservation;
    }

    public boolean isReturnBook() {
        return returnBook;
    }

    public void setReturnBook(boolean returnBook) {
        this.returnBook = returnBook;
    }

    public boolean isConfirmReturnBook() {
        return confirmReturnBook;
    }

    public void setConfirmReturnBook(boolean confirmReturnBook) {
        this.confirmReturnBook = confirmReturnBook;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public void setLibrarianName(String librarianName) {
        this.librarianName = librarianName;
    }
}
