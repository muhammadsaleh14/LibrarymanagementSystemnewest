package com.example.librarymanagementsystem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Users {
    private String date;
    private String name;
    private String book = "";
    private String password;
    private String contact;
    private String bookIssued;
    private String dateOfIssue;
    private String fine;
    private String issueRequest;
    private String bookOverDueBy;
    private int daysToReturn;
    private int fineAmount;
    private int copiesAvailible;
    private String fineReason;
    @FXML
    private Button button;
    public Users(String name, String password, String contact) {
        this.name = name;
        this.password = password;
        this.contact = contact;
    }

    public int getCopiesAvailible() {
        return copiesAvailible;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public String getFineReason() {
        return fineReason;
    }

    public int getDaysToReturn() {
        return daysToReturn;
    }

    public void setDaysToReturn(int daysToReturn) {
        this.daysToReturn = daysToReturn;
    }

    public String getBookIssued() {
        return bookIssued;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public String getFine() {
        return fine;
    }

    public String getIssueRequest() {
        if(issueRequest == null){
            issueRequest = "";
        }
        return issueRequest;
    }

    public String getBookOverDueBy() {
        return bookOverDueBy;
    }

    public void setBookOverDueBy(String bookOverDueBy) {
        this.bookOverDueBy = bookOverDueBy;
    }

    public Button getButton() {
        return button;
    }

    public String getBook() {
        if(this.book == null){
            this.book = "";
        }
        return book;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        if(this.name == null){
            this.name = "";
            return name;
        }
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }

    public static final class UsersBuilder {
        private String date;
        private String name;
        private String book;
        private String password;
        private String contact;
        private String bookIssued;
        private String dateOfIssue;
        private String fine;
        private String issueRequest;
        private String bookOverDueBy;
        private int daysToReturn;
        private int fineAmount;
        private int copiesAvailible;
        private String fineReason;
        private Button button;

        private UsersBuilder() {
        }

        public static UsersBuilder anUsers() {
            return new UsersBuilder();
        }

        public UsersBuilder buildDate(String date) {
            this.date = date;
            return this;
        }

        public UsersBuilder buildName(String name) {
            this.name = name;
            return this;
        }

        public UsersBuilder buildBook(String book) {
            this.book = book;
            return this;
        }

        public UsersBuilder buildPassword(String password) {
            this.password = password;
            return this;
        }

        public UsersBuilder buildContact(String contact) {
            this.contact = contact;
            return this;
        }

        public UsersBuilder buildBookIssued(String bookIssued) {
            this.bookIssued = bookIssued;
            return this;
        }

        public UsersBuilder buildDateOfIssue(String dateOfIssue) {
            this.dateOfIssue = dateOfIssue;
            return this;
        }

        public UsersBuilder buildFine(String fine) {
            this.fine = fine;
            return this;
        }

        public UsersBuilder buildIssueRequest(String issueRequest) {
            this.issueRequest = issueRequest;
            return this;
        }

        public UsersBuilder buildBookOverDueBy(String bookOverDueBy) {
            this.bookOverDueBy = bookOverDueBy;
            return this;
        }

        public UsersBuilder buildDaysToReturn(int daysToReturn) {
            this.daysToReturn = daysToReturn;
            return this;
        }

        public UsersBuilder buildFineAmount(int fineAmount) {
            this.fineAmount = fineAmount;
            return this;
        }

        public UsersBuilder buildCopiesAvailible(int copiesAvailible) {
            this.copiesAvailible = copiesAvailible;
            return this;
        }

        public UsersBuilder buildFineReason(String fineReason) {
            this.fineReason = fineReason;
            return this;
        }

        public UsersBuilder buildButton(Button button) {
            this.button = button;
            return this;
        }

        public Users build() {
            Users users = new Users(name, password, contact);
            users.setBookOverDueBy(bookOverDueBy);
            users.setDaysToReturn(daysToReturn);
            users.fine = this.fine;
            users.button = this.button;
            users.issueRequest = this.issueRequest;
            users.date = this.date;
            users.copiesAvailible = this.copiesAvailible;
            users.fineReason = this.fineReason;
            users.book = this.book;
            users.bookIssued = this.bookIssued;
            users.dateOfIssue = this.dateOfIssue;
            users.fineAmount = this.fineAmount;
            return users;
        }
    }
}
