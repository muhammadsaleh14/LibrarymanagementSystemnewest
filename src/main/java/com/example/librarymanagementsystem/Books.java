package com.example.librarymanagementsystem;

import javafx.scene.image.ImageView;

public class Books {
    private final String title;
    private final String author;
    private final String category;
    private final String languagee;
    private final String bookStatus;
    private final String arrivalDate;
    private final String copiesOwned;
    private ImageView imageView;
    public Books(String title, String author, String category, String languagee, String bookStatus, String arrivalDate, String copiesOwned) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.languagee = languagee;
        this.bookStatus = bookStatus;
        this.arrivalDate = arrivalDate;
        this.copiesOwned = copiesOwned;
    }



    public Books(String title, String author, String category, String language, String bookStatus, String arrivalDate, String copiesOwned, ImageView imageView) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.languagee = language;
        this.bookStatus = bookStatus;
        this.arrivalDate = arrivalDate;
        this.copiesOwned = copiesOwned;
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguagee() {
        return languagee;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getCopiesOwned() {
        return copiesOwned;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    public static final class BooksBuilder {
        private ImageView imageView;
        private String title;
        private String author;
        private String category;
        private String languagee;
        private String bookStatus;
        private String arrivalDate;
        private String copiesOwned;

        private BooksBuilder() {
        }

        public static BooksBuilder aBooks() {
            return new BooksBuilder();
        }

        public BooksBuilder withImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public BooksBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public BooksBuilder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public BooksBuilder withCategory(String category) {
            this.category = category;
            return this;
        }

        public BooksBuilder withLanguagee(String languagee) {
            this.languagee = languagee;
            return this;
        }

        public BooksBuilder withBookStatus(String bookStatus) {
            this.bookStatus = bookStatus;
            return this;
        }

        public BooksBuilder withArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        public BooksBuilder withCopiesOwned(String copiesOwned) {
            this.copiesOwned = copiesOwned;
            return this;
        }

        public Books build() {
            Books books = new Books(title, author, category, languagee, bookStatus, arrivalDate, copiesOwned);
            books.setImageView(imageView);
            return books;
        }
    }
}
