package com.styshak.utils;


import com.styshak.entity.Book;
import java.util.List;

public class BookUtils {

    private BookUtils() {

    }

    public static Book getBookById(List<Book> bookList, long book_id) {
        for (Book book : bookList) {
            if (book.getId() == book_id) {
                return book;
            }
        }
        return null;
    }
}
