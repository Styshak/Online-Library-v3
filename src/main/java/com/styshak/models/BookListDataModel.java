package com.styshak.models;

import com.styshak.entity.Book;
import com.styshak.service.BookService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;

public class BookListDataModel extends LazyDataModel<Book> {

    
    private final BookService bookService;
    
    public BookListDataModel(BookService bookService) {
        this.bookService = bookService;
    } 
  
    @Override  
    public Object getRowKey(Book book) {  
        return book.getId();  
    }  

    @Override  
    public List<Book> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {  
        bookService.setFrom(first);
        bookService.setTo(pageSize); 
        bookService.populateBooksList();
        this.setRowCount((int) bookService.getTotalBooksCount());  
        return bookService.getList();
    }
}
