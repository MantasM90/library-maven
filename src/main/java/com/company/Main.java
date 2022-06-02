package com.company;

import com.company.books.Book;
import com.company.books.BookStatus;
import com.company.books.Genre;

import com.company.exeptions.BookException;
import com.company.exeptions.UserException;
import com.company.services.BookService;
import com.company.services.BookStatusService;
import com.company.services.UserService;
import com.company.users.Role;
import com.company.users.User;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.company.books.Genre.*;
import static com.company.users.Role.*;


public class Main {

    private static final String UNRECOGNIZED_INPUT = "Unrecognized input";
    private static final Scanner SC = new Scanner(System.in);
    private static final String reservationReportsPath = "src/com/company/files/reservationReports.txt";
    static BookService bookService = new BookService();
    static UserService userService = new UserService();

    static BookStatusService bookStatusService = new BookStatusService(reservationReportsPath);

    public static void main(String[] args) throws BookException {


        try {
            while (true) {

                printMenu();
                String selected = SC.nextLine();

                switch (selected) {

                    case "1":

                        System.out.print("Enter username: ");
                        String username = SC.nextLine();
                        System.out.print("Enter password: ");
                        String password = SC.nextLine();

                        try {
                            User loggedInUser = userService.getUser(username, password);
                            System.out.println("Successfully connected");
                            System.out.println();
                            userMenu(loggedInUser);
                        }  catch (NoResultException e) {
                            System.out.println("User doesn't exist or wrong password");
                        }
                        break;
                    case "2":
                        registerReaderUser();
                        // admin
//                        registerNewUser();

                        break;
                    case "3":

                        System.out.println("Program closed");
                        return;
                    default:

                        System.out.println(UNRECOGNIZED_INPUT);
                }
            }
        } catch (IOException | UserException e) {

            System.out.println("File not found");
        }
    }

    private static void userMenu(User user) throws UserException, BookException, IOException {

        if (user.getRole().equals(READER)) readerUserMenu(user);
        if (user.getRole().equals(LIBRARIAN)) librarianUserMenu(user);
    }

    private static void printMenu(){
        System.out.println("[1] Log in");
        System.out.println("[2] Register user (only reader)");
        System.out.println("[3] Exit");
    }

    private static void readerUserMenu(User readerUser) throws BookException {

        while (true) {
            printReaderMenu();

            String choice = SC.nextLine();

            switch (choice) {

                case "1":
                    printReaderUserDetails(readerUser);
                    break;
                case "2":
                    printAllBooks(bookService.getAllBooks());
                    break;

                case "3":
                    Genre genre = getGenreForNewBook();
                    printAllBooks(bookService.getAllBooksByGenre(genre));
                    break;
                case "4":
//                    orderBook(readerUser);
                    break;
                case "5":
//                    printAllBookStatusEntries(bookStatusService.getAllBookStatusEntriesByReaderUsername(readerUser));
                    break;
                case "6":
//                    printAllConfirmedBookStatusEntries(bookStatusService.getAllBookStatusEntriesByReaderUsername(readerUser));
                    break;
                case "7":
//                    returnBookToLibrarian(readerUser);
                    break;
                case "8":
                    return;
                default:
                    System.out.println(UNRECOGNIZED_INPUT);
                    break;
            }
        }
    }

    private static void printReaderMenu() {
        System.out.println();
        System.out.println("-------READER MENU-------");
        System.out.println("[1] My details");
        System.out.println("[2] Books list");
        System.out.println("[3] Books list by genre");

        System.out.println("[4] Order a book");
        System.out.println("[5] Show reserved books (pending librarian approval)");
        System.out.println("[6] Show taken books");
        System.out.println("[7] Return the book");
//        System.out.println("[7] Finished books");
        System.out.println("[8] Exit");
        System.out.println();
    }


    private static void librarianUserMenu(User librarianUser) throws UserException, BookException, IOException {

        while (true) {

            printLibrarianMenu();
            String choice = SC.nextLine();

            switch (choice) {

                case "1":
                        printLibrarianUserDetails(librarianUser);
                    break;
                case "2":
                    List<User> allUsers = userService.getAllUsers();
                    printAllUsers(allUsers);
                    break;
                case "3":
                    registerNewUser();
                    break;
                case "4":
                    System.out.println("--------USER DELETION-------------");
                    System.out.print("Enter username : ");
                    String usernameToDelete = SC.nextLine();

                    try {
                        deleteUser(librarianUser, userService, usernameToDelete);
                        System.out.printf("User '%s' successfully deleted.\n", usernameToDelete);

                    } catch ( UserException  e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    confirmOrderedBook(librarianUser);
                    break;
                case "6":
                    bookReturnConfirmedByLibrarian();
                    break;
                case "7":
                    printAllBooksReservations(bookStatusService.getAllBookStatusEntries());
                    break;
                case "8":
                    printAllBooksReturnRequests(bookStatusService.getAllBookStatusEntries());
                    break;
                case "9":
                    printAllBooks(bookService.getAllBooks());
                    break;
                case "10":
                    Genre genre = getGenreForNewBook();
                    printAllBooks(bookService.getAllBooksByGenre(genre));
                    break;
                case "11":
                    addNewBook();
                    break;
                case "12":

                     try {
                         deleteValidBook();
                     } catch (BookException | IOException e) {
                         System.out.println(e.getMessage());
                     }
                    break;
                case "13":
                    return;
                default:
                    System.out.println(UNRECOGNIZED_INPUT);
                    break;
            }
        }
    }

    private static void printLibrarianMenu() {
        System.out.println();
        System.out.println("------------LIBRARIAN MENU-----------------");
        System.out.println("[1] My details");
        System.out.println("[2] Show all users information");
        System.out.println("[3] Register user (reader and librarian");
        System.out.println("[4] Remove user (can't delete user with books)");

        System.out.println("[5] Confirm book orders");
        System.out.println("[6] Confirm request to return a book");
        System.out.println("[7] Show all book reservations");
        System.out.println("[8] Show all requests to return a book");

        System.out.println("[9] Books list");
        System.out.println("[10] Books list by genre");
        System.out.println("[11] Add book");
        System.out.println("[12] Remove book");
        System.out.println("[13] Exit");
        System.out.println();
    }

    private static void printAllUsers(List<User> users) {

        for (User user : users) {

            printLibrarianUserDetails(user);
        }
    }

    private static void printAllBooks(List<Book> books) {

        if (books.size() != 0 ) {
            for (Book book : books) {

                System.out.println("--------------------------------------");
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Genre: " + book.getGenre());
            }
            System.out.println();
        } else {
            System.out.println("Library empty");
        }
    }

    private static void printAllBooksReservations(ArrayList<BookStatus> bookStatuses) {

        System.out.println("All BOOK RESERVATIONS:");
        if (bookStatuses.size() > 0 ) {
            for (BookStatus bookStatus : bookStatuses) {
                if(bookStatus.isBookReservation())   {

                    System.out.println("---------------------------");
                    System.out.printf("Book name: %s\n",bookStatus.getBookName());
                    System.out.printf("Reader username: %s \n",bookStatus.getReaderUsername());
                    System.out.println("Reservation status: RESERVED");
                }
            }
            System.out.println();

        } else {
            System.out.println("Here is no reserved books");
        }

    }

    private static void printAllBooksReturnRequests(ArrayList<BookStatus> bookStatuses) {

        System.out.println("All REQUESTS TO RETURN A BOOK:");
        if (bookStatuses.size() > 0 ) {
            for (BookStatus bookStatus : bookStatuses) {
                if(bookStatus.isReturnBook())   {

                    System.out.println("---------------------------");
                    System.out.printf("Book name: %s \n",bookStatus.getBookName());
                    System.out.printf("Reader username: %s \n",bookStatus.getReaderUsername());
                    if(bookStatus.isReturnBook())
                        System.out.println("Book return status: RETURN REQUESTED");

                }
            }
            System.out.println();

        } else {
            System.out.println("Here is no book return requests");
        }
    }

    private static void printAllBookStatusEntries(ArrayList<BookStatus> bookStatuses) {

        System.out.println("Reserved books List:");
        if (bookStatuses.size() > 0 ) {
            for (BookStatus bookStatus : bookStatuses) {
                if(!bookStatus.isConfirmBookReservation())   {

                    System.out.println("---------------------------");
                    System.out.printf("Book by name %s was reserved.",bookStatus.getBookName());
                }
            }
            System.out.println();

        } else {
            System.out.println("Here is no reserved books");
        }
    }

    private static void printAllConfirmedBookStatusEntries(ArrayList<BookStatus> bookStatuses) {

        System.out.println("Taken books List:");
        if (bookStatuses.size() > 0 ) {
            for (BookStatus bookStatus : bookStatuses) {

                if(bookStatus.isConfirmBookReservation()) {

                    System.out.println("---------------------------");
                    System.out.printf("Book by name %s was taken. ", bookStatus.getBookName());
                    System.out.printf("Approved by librarian - %s", bookStatus.getLibrarianName());
                }
            }
            System.out.println();
        } else {
            System.out.println("Here is no taken books");
        }
    }


    private static void printReaderUserDetails(User readerUser) {
        System.out.println("--------------------------------------");
        System.out.println("Username: " + readerUser.getUsername());
        System.out.println("Name: " + readerUser.getName());
        System.out.println("Surname: " + readerUser.getSurname());
        System.out.println();
    }

    private static void printLibrarianUserDetails(User librarianUser) {
        System.out.println("--------------------------------------");
        System.out.println("Username: " + librarianUser.getUsername());
        System.out.println("Name: " + librarianUser.getName());
        System.out.println("Surname: " + librarianUser.getSurname());
        System.out.println("Role: " + librarianUser.getRole());
        System.out.println();
    }

    private static void registerReaderUser() throws UserException, IOException, BookException {

        System.out.println("------------NEW READER REGISTRATION------------");
        System.out.print("Enter username: ");
        String username =  SC.nextLine();
        System.out.print("Enter name: ");
        String name = SC.nextLine();
        System.out.print("Enter surname: ");
        String surname = SC.nextLine();
        System.out.print("Enter password: ");
        String password = SC.nextLine();

        User user = new User(username, name, surname, password);
        try {
            userService.addUser(user);
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("User created successfully");
        readerUserMenu(user);
    }

    private static void deleteUser(User librarianUser, UserService userService, String usernameToDelete) throws  UserException {

        if (!librarianUser.getUsername().equalsIgnoreCase(usernameToDelete)) {
            userService.deleteUserByUsername(usernameToDelete);
            return;
        }
        throw new UserException("Librarian user deletion not allowed");
    }

    private static void registerNewUser() throws UserException, IOException {

        System.out.println("------------NEW USER REGISTRATION------------");
        System.out.print("Enter username: ");
        String username = SC.nextLine();
        System.out.print("Enter name: ");
        String name = SC.nextLine();
        System.out.print("Enter surname: ");
        String surname = SC.nextLine();
        Role role = getRoleForNewUser();
        System.out.print("Enter password: ");
        String password = SC.nextLine();

        User user = new User(username, name, surname, password, role);

        try {
            userService.addUser(user);
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("User created successfully");
    }

    private static Role getRoleForNewUser() {

        System.out.println("Select new user role");

        while (true) {
            System.out.println("[1] Reader");
            System.out.println("[2] LIBRARIAN");
            String choice = SC.nextLine();

            switch (choice) {
                case "1":
                    return READER;
                case "2":
                    return LIBRARIAN;
                default:
                    System.out.println(UNRECOGNIZED_INPUT);
            }
        }
    }

    // reader order book from library
    private static void orderBook(User readerUser) throws IOException {

        System.out.println("RESERVE A BOOK");

        System.out.println("Enter book name:");
        String validBookName = SC.nextLine();
        String bookName = "";
        // if

        BookStatus bookStatusEntry = new BookStatus(bookName, readerUser.getUsername());
        bookStatusService.addBookStatusEntry(bookStatusEntry);
        System.out.printf("Book '%s' successfuly reserved", bookName);
    }

    // librarian confirm book order from library
    private static void confirmOrderedBook(User librarianUser) throws IOException {

        System.out.println("Enter book name:");
        String validBookName = SC.nextLine();
        String bookName = "";
        // if
        System.out.println("Enter reader name:");
        String username = SC.nextLine();

        bookStatusService.confirmTakenBook(librarianUser,bookName,username);
        System.out.printf("User '%s' took a book '%s' from library.\n",username, bookName);
    }

    // reader send for confirmation
    private static void returnBookToLibrarian(User readerUser) throws IOException {

        System.out.println("REQUEST TO RETURN BOOK TO LIBRARY");
        System.out.println("Enter book name:");
        String validBookName = SC.nextLine();
        String bookName = "";
        // if

        bookStatusService.returnBook(readerUser,bookName);
        System.out.printf("Book '%s' successfuly set to confirm return list", bookName);
    }

    // librarian confirmed return of book
    private static void  bookReturnConfirmedByLibrarian() throws IOException {

        System.out.println("Enter book name:");
        String validBookName = SC.nextLine();
        String bookName = "";
        // if
        System.out.println("Enter reader name:");
        String username = SC.nextLine();

        bookStatusService.confirmReturnBook(bookName,username);
        System.out.printf("Book '%s' successfuly returned", bookName);
    }

    private static void addNewBook() throws BookException {

        System.out.println("------------ADD NEW BOOK------------");
        System.out.println("Book title");
        String name = SC.nextLine();
        System.out.println("Book author");
        String author = SC.nextLine();
        Genre genre = getGenreForNewBook();

        bookService.addBook(new Book(name, author, genre));
        System.out.println("Book added successfully");
    }

    private static void deleteValidBook() throws BookException, IOException {

        System.out.println("--------BOOK DELETION-------------");
        System.out.print("Enter book name: ");
        String title = SC.nextLine();

        bookService.deleteBook(title);
        System.out.printf("Book '%s' successfully deleted", title);
    }

    private static Genre getGenreForNewBook() {

        System.out.println("Select book genre");

        while (true) {

            printGenreMenu();
            String choice = SC.nextLine();

            switch (choice) {
                case "1":
                    return FANTASY;
                case "2":
                    return CLASSICS;
                case "3":
                    return DETECTIVE;
                case "4":
                    return HORROR;
                case "5":
                    return ACTION;
                default:
                    System.out.println(UNRECOGNIZED_INPUT);
            }
        }
    }

    private static void printGenreMenu() {
        System.out.println("[1] Fantasy");
        System.out.println("[2] Classics");
        System.out.println("[3] Detective");
        System.out.println("[4] Horror");
        System.out.println("[5] Action");
        System.out.println("[6] Exit");
    }

}





