package com.styshak.service;

import com.styshak.dao.DaoFactory;
import com.styshak.entity.Book;
import com.styshak.entity.User;
import com.styshak.enums.CurrentRequest;
import com.styshak.enums.SearchType;
import java.util.List;
import javax.faces.bean.SessionScoped;

@SessionScoped
public class BookService {

    private long totalBooksCount;
    private int from;
    private int to;
    private List<Book> list;
    private CurrentRequest currentRequest;
    private Long selectedGenreId;
    private Character selectedLetter;
    private SearchType selectedSearchType;
    private String currentSearchString;

    public void setCurrentSearchString(String currentSearchString) {
        this.currentSearchString = currentSearchString;
    }

    public void setSelectedSearchType(SearchType selectedSearchType) {
        this.selectedSearchType = selectedSearchType;
    }

    public void setSelectedGenreId(Long selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }

    public void setSelectedLetter(Character selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public long getTotalBooksCount() {
        return totalBooksCount;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public List<Book> getList() {
        return list;
    }

    public void setTotalBooksCount(long totalBooksCount) {
        this.totalBooksCount = totalBooksCount;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }

    public CurrentRequest getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(CurrentRequest currentRequest) {
        this.currentRequest = currentRequest;
    }

    public void populateCountBooksList() {
        if (currentRequest == null) {
            totalBooksCount = DaoFactory.getInstance().getBookDao().getCountAllBooks();
        } else {
            switch (currentRequest) {
                case GENRE:
                    totalBooksCount = DaoFactory.getInstance().getBookDao().getCountBooksByGenre(selectedGenreId);
                    break;
                case LETTER:
                    totalBooksCount = DaoFactory.getInstance().getBookDao().getCountBooksByLetter(selectedLetter);
                    break;
                case SEARCH_STRING:
                    if (selectedSearchType == SearchType.TITLE) {
                        totalBooksCount = DaoFactory.getInstance().getBookDao().getCountBooksListByName(currentSearchString);
                    } else {
                        totalBooksCount = DaoFactory.getInstance().getBookDao().getCountBooksListByAuthor(currentSearchString);
                    }
                case RATE:
                    totalBooksCount = DaoFactory.getInstance().getBookDao().getCountAllBooks();
                    break;
            }
        }
    }

    public void populateBooksList() {
        if (currentRequest == null) {
            list = DaoFactory.getInstance().getBookDao().getAllBooksList(from, to);
        } else {
            switch (currentRequest) {
                case GENRE:
                    list = DaoFactory.getInstance().getBookDao().getBooksListByGenre(selectedGenreId, from, to);
                    break;
                case LETTER:
                    list = DaoFactory.getInstance().getBookDao().getBooksListByLetter(selectedLetter, from, to);
                    break;
                case SEARCH_STRING:
                    if (selectedSearchType == SearchType.TITLE) {
                        list = DaoFactory.getInstance().getBookDao().getBooksListByName(currentSearchString, from, to);
                    } else {
                        list = DaoFactory.getInstance().getBookDao().getBooksListByAuthor(currentSearchString, from, to);
                    }
                    break;
                case RATE:
                    list = DaoFactory.getInstance().getBookDao().getBooksListByRate(from, to);
                    break;
            }
        }
    }

    public void delete(Book book) {
        DaoFactory.getInstance().getBookDao().delete(book);
    }

    public void update(Book book) {
        DaoFactory.getInstance().getBookDao().update(book);
    }
    
    public void add(Book book) {
        DaoFactory.getInstance().getBookDao().add(book);
    }

    public void rate(Book book, User user) {
        DaoFactory.getInstance().getVoteDao().vote(book, user);
        DaoFactory.getInstance().getBookDao().updateRate(book);
    }
}
