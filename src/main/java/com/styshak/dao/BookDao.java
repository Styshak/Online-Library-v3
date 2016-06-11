
package com.styshak.dao;

import com.styshak.entity.Book;
import java.util.List;

public interface BookDao {
    
    public List<Book> getAllBooksList(int from, int to);
    
    public Long getCountAllBooks();
    
    public List<Book> getBooksListByGenre(Long genreId, int from, int to);
    
    public Long getCountBooksByGenre(Long genreId);
    
    public List<Book> getBooksListByLetter(Character letter, int from, int to);
    
    public Long getCountBooksByLetter(Character letter);
    
    public List<Book>getBooksListByName(String searchString, int from, int to);
    
    public Long getCountBooksListByName(String searchString);
    
    public List<Book>getBooksListByAuthor(String searchString, int from, int to);
    
    public Long getCountBooksListByAuthor(String searchString);
    
    public byte[] getBookContentById(Long bookId);
    
    public void delete(Book book);
    
    public void update (Book book);
    
    public void add(Book book);
    
    public void updateRate(Book book);
    
    public List<Book> getBooksListByRate(int from, int to);
    
}
